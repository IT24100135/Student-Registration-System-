package servlet;

import model.Course;
import model.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.RegistrationService;

import java.io.IOException;
import java.util.ArrayList;

public class CourseServlet extends HttpServlet {
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
        if (action == null) action = "list";
        switch (action) {
            case "list":
                String category = request.getParameter("category");
                ArrayList<Course> courses = registrationService.getCoursesByCategory(category);
                request.setAttribute("courses", courses);
                request.setAttribute("category", category);
                request.getRequestDispatcher("/course/list.jsp").forward(request, response);
                break;
            case "register":
                request.getRequestDispatcher("/course/register.jsp").forward(request, response);
                break;
            case "drop":
                request.getRequestDispatcher("/course/drop.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect("course?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Student student = (Student) request.getSession().getAttribute("student");
        if (student == null) {
            response.sendRedirect("student?action=login");
            return;
        }
        switch (action) {
            case "register":
                String courseId = request.getParameter("courseId");
                Course course = registrationService.findCourseById(courseId);
                if (course != null) {
                    registrationService.registerCourse(student, course);
                    response.sendRedirect("student?action=courses");
                } else {
                    request.setAttribute("error", "Course not found!");
                    request.getRequestDispatcher("/course/register.jsp").forward(request, response);
                }
                break;
            case "drop":
                courseId = request.getParameter("courseId");
                course = registrationService.findCourseById(courseId);
                if (course != null) {
                    registrationService.dropCourse(student, course);
                    response.sendRedirect("student?action=courses");
                } else {
                    request.setAttribute("error", "Course not found!");
                    request.getRequestDispatcher("/course/drop.jsp").forward(request, response);
                }
                break;
            default:
                response.sendRedirect("course?action=list");
        }
    }
}