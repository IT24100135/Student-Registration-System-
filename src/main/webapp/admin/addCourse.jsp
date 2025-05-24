<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Add Course</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-gray-100" style="background-image: url('${pageContext.request.contextPath}/images/background.jpg'); background-size: cover; background-attachment: fixed;">
    <%@ include file="header.jsp" %>
    <div class="max-w-md mx-auto mt-10 bg-white p-6 rounded-lg shadow-lg">
        <div class="text-center mb-6">
            <img src="${pageContext.request.contextPath}/images/course.jpg" alt="Course Image" class="mx-auto h-32">
            <h2 class="text-2xl font-bold mt-4">Add New Course</h2>
        </div>
        <form action="admin" method="post">
            <input type="hidden" name="action" value="addCourse">
            <div class="mb-4">
                <label class="block text-gray-700">Course ID</label>
                <input type="text" name="courseId" class="w-full p-2 border rounded" required>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Course Name</label>
                <input type="text" name="name" class="w-full p-2 border rounded" required>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Capacity</label>
                <input type="number" name="capacity" class="w-full p-2 border rounded" required>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Category</label>
                <select name="category" class="w-full p-2 border rounded" required>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category}">${category}</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="w-full bg-green-500 text-white p-2 rounded hover:bg-green-600 flex items-center justify-center">
                <i class="fas fa-book mr-2"></i> Add Course
            </button>
        </form>
    </div>
</body>
</html>