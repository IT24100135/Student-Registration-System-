<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Course" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="service.RegistrationService" %>
<html>
<head>
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-gray-100" style="background-image: url('${pageContext.request.contextPath}/images/background.jpg'); background-size: cover; background-attachment: fixed;">
    <%@ include file="header.jsp" %>
    <div class="max-w-4xl mx-auto mt-10">
        <img src="${pageContext.request.contextPath}/images/banner.jpg" alt="Campus Banner" class="w-full h-48 object-cover rounded-lg shadow-md mb-6">
        <h2 class="text-2xl font-bold text-white text-center">Admin Dashboard</h2>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
            <a href="admin?action=addStudent" class="bg-blue-500 text-white p-4 rounded-lg hover:bg-blue-600 flex items-center justify-center">
                <i class="fas fa-user-plus mr-2"></i> Add Student
            </a>
            <a href="admin?action=addCourse" class="bg-green-500 text-white p-4 rounded-lg hover:bg-green-600 flex items-center justify-center">
                <i class="fas fa-book mr-2"></i> Add Course
            </a>
            <a href="admin?action=viewStudents" class="bg-purple-500 text-white p-4 rounded-lg hover:bg-purple-600 flex items-center justify-center">
                <i class="fas fa-users mr-2"></i> View Students
            </a>
            <a href="admin?action=viewCourseRegistrations" class="bg-yellow-500 text-white p-4 rounded-lg hover:bg-yellow-600 flex items-center justify-center">
                <i class="fas fa-list-alt mr-2"></i> View Course Registrations
            </a>
            <a href="admin?action=processRequests" class="bg-red-500 text-white p-4 rounded-lg hover:bg-red-600 flex items-center justify-center">
                <i class="fas fa-cogs mr-2"></i> Process Requests
            </a>
            <a href="admin?action=viewFeedback" class="bg-purple-500 text-white p-4 rounded-lg hover:bg-purple-600 flex items-center justify-center">
                <i class="fas fa-comments mr-2"></i> Review Student Feedback
            </a>
        </div>

        <!-- Edit and Remove Course Sections -->
        <%
            RegistrationService registrationService = (RegistrationService) application.getAttribute("registrationService");
            ArrayList<Course> allCourses = registrationService.getAllCourses();
            request.setAttribute("courses", allCourses);
        %>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <!-- Edit Course Box -->
            <div class="bg-white p-6 rounded-lg shadow-lg">
                <h3 class="text-2xl font-bold mb-4 text-center flex items-center justify-center">
                    <i class="fas fa-edit mr-2"></i> Edit Course
                </h3>
                <form action="admin" method="get" class="space-y-4">
                    <input type="hidden" name="action" value="editCourse">
                    <div class="mb-4">
                        <label class="block text-gray-700 font-bold mb-2" for="editCourseCode">Select Course:</label>
                        <select name="courseCode" id="editCourseCode" class="w-full p-2 border rounded" required>
                            <option value="">-- Select a Course --</option>
                            <% for (Course course : allCourses) { %>
                                <option value="<%= course.getCourseCode() %>">
                                    <%= course.getCourseCode() %> - <%= course.getTitle() %>
                                </option>
                            <% } %>
                        </select>
                    </div>
                    <button type="submit" class="bg-blue-500 text-white p-2 rounded hover:bg-blue-600 w-full flex items-center justify-center">
                        <i class="fas fa-edit mr-2"></i> Edit Course
                    </button>
                </form>
            </div>

            <!-- Remove Course Box -->
            <div class="bg-white p-6 rounded-lg shadow-lg">
                <h3 class="text-2xl font-bold mb-4 text-center flex items-center justify-center">
                    <i class="fas fa-trash-alt mr-2"></i> Remove Course
                </h3>
                <form action="admin" method="get" class="space-y-4">
                    <input type="hidden" name="action" value="removeCourse">
                    <div class="mb-4">
                        <label class="block text-gray-700 font-bold mb-2" for="removeCourseCode">Select Course:</label>
                        <select name="courseCode" id="removeCourseCode" class="w-full p-2 border rounded" required>
                            <option value="">-- Select a Course --</option>
                            <% for (Course course : allCourses) { %>
                                <option value="<%= course.getCourseCode() %>">
                                    <%= course.getCourseCode() %> - <%= course.getTitle() %>
                                </option>
                            <% } %>
                        </select>
                    </div>
                    <button type="submit" class="bg-red-500 text-white p-2 rounded hover:bg-red-600 w-full flex items-center justify-center" onclick="return confirm('Are you sure you want to remove this course? This will also remove all associated registrations.')">
                        <i class="fas fa-trash-alt mr-2"></i> Remove Course
                    </button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>