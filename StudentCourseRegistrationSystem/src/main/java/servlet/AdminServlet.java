package servlet;

import model.*;
import service.RegistrationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminServlet extends HttpServlet {
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
        System.out.println("AdminServlet: Handling action = " + action);
        switch (action) {
            case "login":
                request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
                break;
            case "dashboard":
                Admin admin = (Admin) request.getSession().getAttribute("admin");
                if (admin == null) {
                    response.sendRedirect("admin?action=login");
                    return;
                }
                request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
                break;
            case "addStudent":
                request.getRequestDispatcher("/admin/addStudent.jsp").forward(request, response);
                break;
            case "addCourse":
                request.setAttribute("categories", new String[]{"Undergraduate IT", "Undergraduate Business", "Postgraduate IT", "Postgraduate Business"});
                request.getRequestDispatcher("/admin/addCourse.jsp").forward(request, response);
                break;
            case "viewStudents":
                request.setAttribute("students", registrationService.getStudents());
                request.getRequestDispatcher("/admin/viewStudents.jsp").forward(request, response);
                break;
            case "viewCourseRegistrations":
                String courseCode = request.getParameter("courseCode");
                if (courseCode != null) {
                    List<Map.Entry<Student, Long>> registeredStudents = registrationService.getAdmin().viewRegisteredCourseStudents(registrationService.getAllCourses(), courseCode);
                    request.setAttribute("registeredStudents", registeredStudents);
                    request.setAttribute("courseCode", courseCode);
                }
                request.setAttribute("courses", registrationService.getAllCourses()); // Ensure fresh list
                request.getRequestDispatcher("/admin/viewCourseRegistrations.jsp").forward(request, response);
                break;
            case "processRequests":
                request.setAttribute("queue", registrationService.getAdmin().getRegistrationQueue());
                request.getRequestDispatcher("/admin/processRequests.jsp").forward(request, response);
                break;
            case "removeStudent":
                String studentId = request.getParameter("studentId");
                System.out.println("AdminServlet: Attempting to remove student with ID = " + studentId);
                if (studentId != null && !studentId.trim().isEmpty()) {
                    boolean removed = registrationService.removeStudent(studentId);
                    if (removed) {
                        System.out.println("AdminServlet: Student " + studentId + " removed successfully.");
                        request.setAttribute("message", "Student " + studentId + " removed successfully.");
                    } else {
                        System.out.println("AdminServlet: Failed to remove student with ID " + studentId + ".");
                        request.setAttribute("error", "Failed to remove student with ID " + studentId + ". Student may not exist.");
                    }
                } else {
                    System.out.println("AdminServlet: Invalid student ID provided.");
                    request.setAttribute("error", "Invalid student ID.");
                }
                request.setAttribute("students", registrationService.getStudents());
                request.getRequestDispatcher("/admin/viewStudents.jsp").forward(request, response);
                break;
            case "removeCourse":
                courseCode = request.getParameter("courseCode");
                if (courseCode != null && !courseCode.trim().isEmpty()) {
                    boolean removed = registrationService.removeCourse(courseCode);
                    if (removed) {
                        System.out.println("AdminServlet: Course " + courseCode + " removed successfully.");
                        request.setAttribute("message", "Course " + courseCode + " removed successfully.");
                    } else {
                        System.out.println("AdminServlet: Failed to remove course " + courseCode + ".");
                        request.setAttribute("error", "Failed to remove course " + courseCode + ". Course may not exist.");
                    }
                } else {
                    request.setAttribute("error", "Invalid course code.");
                }
                request.setAttribute("courses", registrationService.getAllCourses()); // Refresh course list
                request.getRequestDispatcher("/admin/viewCourseRegistrations.jsp").forward(request, response);
                break;
            case "editCourse":
                courseCode = request.getParameter("courseCode");
                System.out.println("AdminServlet: Edit course requested for courseCode = " + courseCode);
                if (courseCode != null && !courseCode.trim().isEmpty()) {
                    Course course = registrationService.findCourseById(courseCode);
                    if (course != null) {
                        System.out.println("AdminServlet: Course found - " + course.getCourseCode() + ", forwarding to editCourse.jsp");
                        request.setAttribute("course", course);
                        request.getRequestDispatcher("/admin/editCourse.jsp").forward(request, response);
                    } else {
                        System.out.println("AdminServlet: Course not found for courseCode = " + courseCode);
                        request.setAttribute("error", "Course not found.");
                        request.setAttribute("courses", registrationService.getAllCourses());
                        request.getRequestDispatcher("/admin/viewCourseRegistrations.jsp").forward(request, response);
                    }
                } else {
                    System.out.println("AdminServlet: Invalid course code provided for editCourse action.");
                    request.setAttribute("error", "Invalid course code.");
                    request.setAttribute("courses", registrationService.getAllCourses());
                    request.getRequestDispatcher("/admin/viewCourseRegistrations.jsp").forward(request, response);
                }
                break;
            default:
                response.sendRedirect("admin?action=login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String studentId = null;
        switch (action) {
            case "login":
                String username = request.getParameter("adminId");
                String password = request.getParameter("password");
                if (registrationService.validateAdminLogin(username, password)) {
                    request.getSession().setAttribute("admin", registrationService.getAdmin());
                    response.sendRedirect("admin?action=dashboard");
                } else {
                    request.setAttribute("error", "Invalid admin credentials!");
                    request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
                }
                break;
            case "addStudent":
                String id = request.getParameter("studentId");
                String name = request.getParameter("name");
                String phoneNumber = request.getParameter("phoneNumber");
                String email = request.getParameter("email");
                String gender = request.getParameter("gender");
                String district = request.getParameter("district");
                registrationService.addStudent(new Student(id, name, phoneNumber, email, gender, district));
                response.sendRedirect("admin?action=viewStudents");
                break;
            case "addCourse":
                String courseId = request.getParameter("courseId");
                String courseName = request.getParameter("name");
                int capacity = Integer.parseInt(request.getParameter("capacity"));
                String category = request.getParameter("category");
                if (capacity <= 0) {
                    request.setAttribute("error", "Capacity must be a positive number.");
                    request.setAttribute("categories", new String[]{"Undergraduate IT", "Undergraduate Business", "Postgraduate IT", "Postgraduate Business"});
                    request.getRequestDispatcher("/admin/addCourse.jsp").forward(request, response);
                    return;
                }
                registrationService.addCourse(category, new Course(courseId, courseName, capacity));
                response.sendRedirect("admin?action=viewCourseRegistrations");
                break;
            case "processRequests":
                registrationService.getAdmin().processRegistrationRequests(registrationService);
                response.sendRedirect("admin?action=processRequests");
                break;
            case "approveRequest":
            case "rejectRequest":
                studentId = request.getParameter("studentId");
                String courseCode = request.getParameter("courseCode");
                boolean approve = "approveRequest".equals(action);
                registrationService.getAdmin().processRegistrationRequest(studentId, courseCode, approve, registrationService);
                request.setAttribute("message", "Request for " + studentId + " " + (approve ? "approved" : "rejected") + " successfully.");
                request.setAttribute("queue", registrationService.getAdmin().getRegistrationQueue());
                request.getRequestDispatcher("/admin/processRequests.jsp").forward(request, response);
                break;
            case "updateCourse":
                courseCode = request.getParameter("courseCode");
                String title = request.getParameter("title");
                try {
                    capacity = Integer.parseInt(request.getParameter("capacity"));
                    if (capacity <= 0) {
                        request.setAttribute("error", "Capacity must be a positive number.");
                        Course course = registrationService.findCourseById(courseCode);
                        request.setAttribute("course", course);
                        request.getRequestDispatcher("/admin/editCourse.jsp").forward(request, response);
                        return;
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid capacity value. Please enter a valid number.");
                    Course course = registrationService.findCourseById(courseCode);
                    request.setAttribute("course", course);
                    request.getRequestDispatcher("/admin/editCourse.jsp").forward(request, response);
                    return;
                }
                Course updatedCourse = registrationService.findCourseById(courseCode);
                if (updatedCourse != null) {
                    updatedCourse.setTitle(title);
                    updatedCourse.setCapacity(capacity);
                    category = determineCategory(courseCode);
                    registrationService.addCourse(category, updatedCourse);
                    request.setAttribute("message", "Course " + courseCode + " updated successfully.");
                } else {
                    request.setAttribute("error", "Course not found.");
                }
                request.setAttribute("course", updatedCourse);
                request.getRequestDispatcher("/admin/editCourse.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect("admin?action=login");
        }
    }

    private String determineCategory(String courseCode) {
        if (courseCode.startsWith("IT")) return "Undergraduate IT";
        if (courseCode.startsWith("BUS")) return "Undergraduate Business";
        if (courseCode.startsWith("PIT")) return "Postgraduate IT";
        if (courseCode.startsWith("PG")) return "Postgraduate Business";
        return "Unknown";
    }
}