<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Available Courses</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-gray-100" style="background-image: url('${pageContext.request.contextPath}/images/background.jpg'); background-size: cover; background-attachment: fixed;">
    <%@ include file="header.jsp" %>
    <div class="max-w-4xl mx-auto mt-10">
        <img src="${pageContext.request.contextPath}/images/banner.jpg" alt="Campus Banner" class="w-full h-48 object-cover rounded-lg shadow-md mb-6">
        <h2 class="text-2xl font-bold mb-6 text-center">Available Courses</h2>
        <div class="mb-6">
            <form action="student" method="get">
                <input type="hidden" name="action" value="courses">
                <label class="block text-gray-700 mb-2">Select Category</label>
                <select name="category" class="w-full p-2 border rounded mb-2" onchange="this.form.submit()">
                    <option value="">-- Select Category --</option>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category}" ${category == selectedCategory ? 'selected' : ''}>${category}</option>
                    </c:forEach>
                </select>
            </form>
        </div>
        <c:if test="${not empty courses}">
            <table class="w-full border-collapse bg-white rounded-lg shadow-lg">
                <thead>
                    <tr class="bg-gray-200">
                        <th class="border p-2">Course Code</th>
                        <th class="border p-2">Title</th>
                        <th class="border p-2">Capacity</th>
                        <th class="border p-2">Registered</th>
                        <th class="border p-2">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="course" items="${courses}">
                        <tr>
                            <td class="border p-2 flex items-center">
                                <img src="${pageContext.request.contextPath}/images/course-icon.png" alt="Course Icon" class="h-6 mr-2">
                                ${course.courseCode}
                            </td>
                            <td class="border p-2">${course.title}</td>
                            <td class="border p-2">${course.capacity}</td>
                            <td class="border p-2">${course.registeredStudents.size()}</td>
                            <td class="border p-2">
                                <a href="student?action=register&courseCode=${course.courseCode}" class="bg-green-500 text-white px-3 py-1 rounded hover:bg-green-600 flex items-center justify-center">
                                    <i class="fas fa-check mr-1"></i> Register
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty courses}">
            <p class="text-center text-gray-600">No courses available in this category.</p>
        </c:if>
    </div>
</body>
</html>