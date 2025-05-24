<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>View Course Registrations</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-gray-100" style="background-image: url('${pageContext.request.contextPath}/images/background.jpg'); background-size: cover; background-attachment: fixed;">
    <%@ include file="header.jsp" %>
    <div class="max-w-4xl mx-auto mt-10 bg-white p-6 rounded-lg shadow-lg">
        <h2 class="text-2xl font-bold mb-4 text-center">Course Registrations</h2>
        <% if (request.getAttribute("message") != null) { %>
            <p class="text-green-500 text-center mb-4">${message}</p>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <p class="text-red-500 text-center mb-4">${error}</p>
        <% } %>
        <div class="mb-6">
            <h3 class="text-xl font-semibold mb-2">Available Courses</h3>
            <table class="w-full border-collapse mb-6">
                <thead>
                    <tr class="bg-gray-200">
                        <th class="border p-2">Course Code</th>
                        <th class="border p-2">Title</th>
                        <th class="border p-2">Capacity</th>
                        <th class="border p-2">Registered</th>
                        <th class="border p-2">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="course" items="${courses}">
                        <tr>
                            <td class="border p-2">${course.courseCode}</td>
                            <td class="border p-2">${course.title}</td>
                            <td class="border p-2">${course.capacity}</td>
                            <td class="border p-2">${course.registeredStudents.size()}</td>
                            <td class="border p-2">
                                <a href="admin?action=editCourse&courseCode=${course.courseCode}" class="text-blue-500 hover:underline mr-2">
                                    <i class="fas fa-edit mr-1"></i> Edit
                                </a>
                                <a href="admin?action=removeCourse&courseCode=${course.courseCode}" class="text-red-500 hover:underline" onclick="return confirm('Are you sure you want to remove this course? This will also remove all associated registrations.')">
                                    <i class="fas fa-trash mr-1"></i> Remove
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty courses}">
                        <tr>
                            <td colspan="5" class="border p-2 text-center">No courses available.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
        <div class="mb-6">
            <h3 class="text-xl font-semibold mb-2">Select a Course to View Registrations</h3>
            <form action="admin" method="get">
                <input type="hidden" name="action" value="viewCourseRegistrations">
                <select name="courseCode" class="w-full p-2 border rounded mb-2" onchange="this.form.submit()">
                    <option value="">-- Select a Course --</option>
                    <c:forEach var="course" items="${courses}">
                        <option value="${course.courseCode}" ${course.courseCode == courseCode ? 'selected' : ''}>
                            ${course.courseCode} - ${course.title}
                        </option>
                    </c:forEach>
                </select>
            </form>
        </div>
        <c:if test="${not empty courseCode}">
            <h3 class="text-xl font-semibold mb-2 flex items-center">
                <img src="${pageContext.request.contextPath}/images/course-icon.png" alt="Course Icon" class="h-6 mr-2">
                Registered Students for ${courseCode}
            </h3>
            <table class="w-full border-collapse">
                <thead>
                    <tr class="bg-gray-200">
                        <th class="border p-2">Student ID</th>
                        <th class="border p-2">Name</th>
                        <th class="border p-2">Registration Time</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${registeredStudents}">
                        <tr>
                            <td class="border p-2 flex items-center">
                                <img src="${pageContext.request.contextPath}/images/student-icon.png" alt="Student Icon" class="h-6 mr-2">
                                ${entry.key.studentId}
                            </td>
                            <td class="border p-2">${entry.key.name}</td>
                            <%
                                java.util.Map.Entry<model.Student, java.lang.Long> entry = (java.util.Map.Entry<model.Student, java.lang.Long>) pageContext.getAttribute("entry");
                                java.lang.Long timestamp = entry.getValue();
                                service.RegistrationService registrationService = (service.RegistrationService) application.getAttribute("registrationService");
                                String formattedTimestamp = registrationService.formatTimestamp(timestamp);
                            %>
                            <td class="border p-2"><%= formattedTimestamp %></td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty registeredStudents}">
                        <tr>
                            <td colspan="3" class="border p-2 text-center">No students registered for this course.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </c:if>
    </div>
</body>
</html>