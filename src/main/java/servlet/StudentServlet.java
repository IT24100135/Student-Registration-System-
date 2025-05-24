package servlet;

import model.Student;
import model.Course;
import service.RegistrationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class StudentServlet extends HttpServlet {
    private RegistrationService registrationService;

    @Override
    public void init() throws ServletException {
        registrationService = (RegistrationService) getServletContext().getAttribute("registrationService");
        if (registrationService == null) {
            throw new ServletException("RegistrationService not found in ServletContext");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "login";

        Student student = (Student) request.getSession().getAttribute("student");
        if (!"login".equals(action) && !"logout".equals(action) && student == null) {
            response.sendRedirect("student?action=login");
            return;
        }

        switch (action) {
            case "login":
                request.getRequestDispatcher("/student/login.jsp").forward(request, response);
                break;
            case "dashboard":
                request.setAttribute("student", student);
                request.getRequestDispatcher("/student/dashboard.jsp").forward(request, response);
                break;
            case "courses":
                String category = request.getParameter("category");
                ArrayList<String> categories = new ArrayList<>();
                categories.add("Undergraduate IT");
                categories.add("Undergraduate Business");
                categories.add("Postgraduate IT");
                categories.add("Postgraduate Business");
                request.setAttribute("categories", categories);
                request.setAttribute("selectedCategory", category);
                if (category != null && !category.isEmpty()) {
                    ArrayList<Course> courses = registrationService.getCoursesByCategory(category);
                    request.setAttribute("courses", courses);
                }
                request.getRequestDispatcher("/student/courses.jsp").forward(request, response);
                break;
            case "register":
                categories = new ArrayList<>();
                categories.add("Undergraduate IT");
                categories.add("Undergraduate Business");
                categories.add("Postgraduate IT");
                categories.add("Postgraduate Business");
                request.setAttribute("categories", categories);
                category = request.getParameter("category");
                request.setAttribute("selectedCategory", category);
                if (category != null && !category.isEmpty()) {
                    ArrayList<Course> courses = registrationService.getCoursesByCategory(category);
                    request.setAttribute("courses", courses);
                }
                request.getRequestDispatcher("/student/register.jsp").forward(request, response);
                break;
            case "viewCourses":
                request.setAttribute("registeredCourses", student.getRegisteredCourses());
                request.getRequestDispatcher("/student/viewCourses.jsp").forward(request, response);
                break;
            case "dropCourse":
                String courseCode = request.getParameter("courseCode");
                Course courseToDrop = registrationService.findCourseById(courseCode);
                if (courseToDrop != null) {
                    registrationService.dropCourse(student, courseToDrop);
                    request.setAttribute("message", "Course " + courseCode + " dropped successfully.");
                } else {
                    request.setAttribute("error", "Course " + courseCode + " not found.");
                }
                request.setAttribute("registeredCourses", student.getRegisteredCourses());
                request.getRequestDispatcher("/student/viewCourses.jsp").forward(request, response);
                break;
            case "editInformation":
                request.getRequestDispatcher("/student/editInformation.jsp").forward(request, response);
                break;
            case "submitFeedback":
                request.getRequestDispatcher("/student/submitFeedback.jsp").forward(request, response);
                break;
            case "logout":
                request.getSession().invalidate();
                response.sendRedirect("student?action=login");
                break;
            default:
                response.sendRedirect("student?action=login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("login".equals(action)) {
            String studentId = request.getParameter("studentId");
            String password = request.getParameter("password");
            Student student = registrationService.validateStudentLogin(studentId, password);
            if (student != null) {
                request.getSession().setAttribute("student", student);
                response.sendRedirect("student?action=dashboard");
            } else {
                request.setAttribute("error", "Invalid student ID or password!");
                request.getRequestDispatcher("/student/login.jsp").forward(request, response);
            }
        } else if ("register".equals(action)) {
            Student student = (Student) request.getSession().getAttribute("student");
            if (student == null) {
                response.sendRedirect("student?action=login");
                return;
            }
            String courseCode = request.getParameter("courseCode");
            Course course = registrationService.findCourseById(courseCode);
            if (course != null) {
                if (student.getRegisteredCourses().contains(course)) {
                    request.setAttribute("error", "You are already registered for this course.");
                } else {
                    registrationService.requestCourseRegistration(student, course);
                    request.setAttribute("message", "Registration request submitted for course " + courseCode + ".");
                }
            } else {
                request.setAttribute("error", "Course not found.");
            }
            ArrayList<String> categories = new ArrayList<>();
            categories.add("Undergraduate IT");
            categories.add("Undergraduate Business");
            categories.add("Postgraduate IT");
            categories.add("Postgraduate Business");
            request.setAttribute("categories", categories);
            String category = request.getParameter("category");
            request.setAttribute("selectedCategory", category);
            if (category != null && !category.isEmpty()) {
                ArrayList<Course> courses = registrationService.getCoursesByCategory(category);
                request.setAttribute("courses", courses);
            }
            request.getRequestDispatcher("/student/register.jsp").forward(request, response);
        } else if ("updateInformation".equals(action)) {
            Student student = (Student) request.getSession().getAttribute("student");
            if (student == null) {
                response.sendRedirect("student?action=login");
                return;
            }
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            String gender = request.getParameter("gender");
            String district = request.getParameter("district");
            String password = request.getParameter("password");

            student.setName(name);
            student.setEmail(email);
            student.setPhoneNumber(phoneNumber);
            student.setGender(gender);
            student.setDistrict(district);
            student.setPassword(password);

            registrationService.updateStudent(student);
            request.getSession().setAttribute("student", student);
            request.setAttribute("message", "Information updated successfully!");
            request.getRequestDispatcher("/student/editInformation.jsp").forward(request, response);
        } else if ("submitFeedback".equals(action)) {
            Student student = (Student) request.getSession().getAttribute("student");
            if (student == null) {
                response.sendRedirect("student?action=login");
                return;
            }
            String feedbackText = request.getParameter("feedbackText");
            if (feedbackText == null || feedbackText.trim().isEmpty()) {
                request.setAttribute("error", "Feedback text is required.");
            } else {
                registrationService.addFeedback(student.getStudentId(), feedbackText);
                request.setAttribute("message", "Feedback submitted successfully.");
            }
            request.getRequestDispatcher("/student/submitFeedback.jsp").forward(request, response);
        } else {
            response.sendRedirect("student?action=login");
        }
    }
}