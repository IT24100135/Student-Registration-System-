package servlet;

import model.*;
import service.RegistrationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

        switch (action) {
            case "login":
                request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
                break;
            case "dashboard":
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
                    List<Map.Entry<Student, Long>> registeredStudents = registrationService.getAdmin()
                            .viewRegisteredCourseStudents(registrationService.getAllCourses(), courseCode);
                    request.setAttribute("registeredStudents", registeredStudents);
                    request.setAttribute("courseCode", courseCode);
                }
                request.setAttribute("courses", registrationService.getAllCourses());
                request.getRequestDispatcher("/admin/viewCourseRegistrations.jsp").forward(request, response);
                break;
            case "processRequests":
                request.setAttribute("queue", registrationService.getAdmin().getRegistrationQueue());
                request.getRequestDispatcher("/admin/processRequests.jsp").forward(request, response);
                break;
            case "removeStudent":
                String studentId = request.getParameter("studentId");
                if (studentId != null && !studentId.trim().isEmpty()) {
                    boolean removed = registrationService.removeStudent(studentId);
                    request.setAttribute(removed ? "message" : "error",
                            removed ? "Student removed successfully" : "Failed to remove student");
                }
                request.setAttribute("students", registrationService.getStudents());
                request.getRequestDispatcher("/admin/viewStudents.jsp").forward(request, response);
                break;
            case "removeCourse":
                String removeCourseCode = request.getParameter("courseCode");
                if (removeCourseCode != null && !removeCourseCode.trim().isEmpty()) {
                    boolean removed = registrationService.removeCourse(removeCourseCode);
                    request.setAttribute(removed ? "message" : "error",
                            removed ? "Course removed successfully" : "Failed to remove course");
                }
                request.setAttribute("courses", registrationService.getAllCourses());
                request.getRequestDispatcher("/admin/viewCourseRegistrations.jsp").forward(request, response);
                break;
            case "editCourse":
                String editCourseCode = request.getParameter("courseCode");
                if (editCourseCode != null && !editCourseCode.trim().isEmpty()) {
                    Course course = registrationService.findCourseById(editCourseCode);
                    if (course != null) {
                        request.setAttribute("course", course);
                        request.getRequestDispatcher("/admin/editCourse.jsp").forward(request, response);
                        return;
                    }
                }
                request.setAttribute("error", "Course not found");
                request.setAttribute("courses", registrationService.getAllCourses());
                request.getRequestDispatcher("/admin/viewCourseRegistrations.jsp").forward(request, response);
                break;
            case "viewFeedback":
                request.setAttribute("feedbackList", registrationService.getAllFeedback());
                request.getRequestDispatcher("/admin/viewFeedback.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect("admin?action=login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "login":
                handleLogin(request, response);
                break;
            case "addStudent":
                handleAddStudent(request, response);
                break;
            case "addCourse":
                handleAddCourse(request, response);
                break;
            case "processRequests":
                registrationService.getAdmin().processRegistrationRequests(registrationService);
                response.sendRedirect("admin?action=processRequests");
                break;
            case "approveRequest":
            case "rejectRequest":
                handleRequestApproval(request, response, action.equals("approveRequest"));
                break;
            case "updateCourse":
                handleUpdateCourse(request, response);
                break;
            default:
                response.sendRedirect("admin?action=login");
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("adminId");
        String password = request.getParameter("password");
        if (registrationService.validateAdminLogin(username, password)) {
            request.getSession().setAttribute("admin", registrationService.getAdmin());
            response.sendRedirect("admin?action=dashboard");
        } else {
            request.setAttribute("error", "Invalid admin credentials!");
            request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
        }
    }

    private void handleAddStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("studentId");
        String name = request.getParameter("name");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String district = request.getParameter("district");
        registrationService.addStudent(new Student(id, name, phoneNumber, email, gender, district));
        response.sendRedirect("admin?action=viewStudents");
    }

    private void handleAddCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseId = request.getParameter("courseId");
        String courseName = request.getParameter("name");
        try {
            int capacity = Integer.parseInt(request.getParameter("capacity"));
            if (capacity <= 0) {
                request.setAttribute("error", "Capacity must be a positive number.");
                request.setAttribute("categories", new String[]{"Undergraduate IT", "Undergraduate Business", "Postgraduate IT", "Postgraduate Business"});
                request.getRequestDispatcher("/admin/addCourse.jsp").forward(request, response);
                return;
            }
            registrationService.addCourse(new Course(courseId, courseName, capacity));
            response.sendRedirect("admin?action=viewCourseRegistrations");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid capacity value");
            request.setAttribute("categories", new String[]{"Undergraduate IT", "Undergraduate Business", "Postgraduate IT", "Postgraduate Business"});
            request.getRequestDispatcher("/admin/addCourse.jsp").forward(request, response);
        }
    }

    private void handleRequestApproval(HttpServletRequest request, HttpServletResponse response, boolean approve)
            throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        String courseCode = request.getParameter("courseCode");
        registrationService.getAdmin().processRegistrationRequest(studentId, courseCode, approve, registrationService);
        request.setAttribute("message", "Request " + (approve ? "approved" : "rejected") + " successfully.");
        request.setAttribute("queue", registrationService.getAdmin().getRegistrationQueue());
        request.getRequestDispatcher("/admin/processRequests.jsp").forward(request, response);
    }

    private void handleUpdateCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String courseCode = request.getParameter("courseCode");
        String title = request.getParameter("title");
        try {
            int capacity = Integer.parseInt(request.getParameter("capacity"));
            if (capacity <= 0) {
                throw new NumberFormatException();
            }
            boolean updated = registrationService.updateCourse(courseCode, title, capacity);
            if (updated) {
                request.setAttribute("message", "Course updated successfully.");
            } else {
                request.setAttribute("error", "Failed to update course.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid capacity value");
        }
        Course course = registrationService.findCourseById(courseCode);
        request.setAttribute("course", course);
        request.getRequestDispatcher("/admin/editCourse.jsp").forward(request, response);
    }
}