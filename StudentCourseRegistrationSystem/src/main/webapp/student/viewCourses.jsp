<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>My Registered Courses</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-gray-100" style="background-image: url('${pageContext.request.contextPath}/images/background.jpg'); background-size: cover; background-attachment: fixed;">
    <%@ include file="header.jsp" %>
    <div class="max-w-4xl mx-auto mt-10">
        <img src="${pageContext.request.contextPath}/images/banner.jpg" alt="Campus Banner" class="w-full h-48 object-cover rounded-lg shadow-md mb-6">
        <h2 class="text-2xl font-bold mb-6 text-center">My Registered Courses</h2>
        <% if (request.getAttribute("message") != null) { %>
            <p class="text-green-500 text-center mb-4"><%= request.getAttribute("message") %></p>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <p class="text-red-500 text-center mb-4"><%= request.getAttribute("error") %></p>
        <% } %>
        <c:if test="${not empty registeredCourses}">
            <table class="w-full border-collapse bg-white rounded-lg shadow-lg">
                <thead>
                    <tr class="bg-gray-200">
                        <th class="border p-2">Course Code</th>
                        <th class="border p-2">Title</th>
                        <th class="border p-2">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="course" items="${registeredCourses}">
                        <tr>
                            <td class="border p-2 flex items-center">
                                <img src="${pageContext.request.contextPath}/images/course-icon.png" alt="Course Icon" class="h-6 mr-2">
                                ${course.courseCode}
                            </td>
                            <td class="border p-2">${course.title}</td>
                            <td class="border p-2">
                                <a href="student?action=dropCourse&courseCode=${course.courseCode}" class="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600 flex items-center justify-center" onclick="return confirm('Are you sure you want to drop this course?')">
                                    <i class="fas fa-times mr-1"></i> Drop
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty registeredCourses}">
            <p class="text-center text-gray-600">You are not registered for any courses.</p>
        </c:if>
    </div>
</body>
</html>