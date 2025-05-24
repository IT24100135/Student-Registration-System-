<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Edit Course</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-gray-100" style="background-image: url('${pageContext.request.contextPath}/images/background.jpg'); background-size: cover; background-attachment: fixed;">
    <%@ include file="header.jsp" %>
    <div class="max-w-md mx-auto mt-10 bg-white p-6 rounded-lg shadow-lg">
        <h2 class="text-2xl font-bold mb-4 text-center">Edit Course</h2>
        <c:if test="${not empty message}">
            <p class="text-green-500 text-center mb-4">${message}</p>
        </c:if>
        <c:if test="${not empty error}">
            <p class="text-red-500 text-center mb-4">${error}</p>
        </c:if>
        <form action="admin" method="post">
            <input type="hidden" name="action" value="updateCourse">
            <input type="hidden" name="courseCode" value="${course.courseCode}">
            <div class="mb-4">
                <label class="block text-gray-700 font-bold mb-2" for="courseCode">Course Code</label>
                <input type="text" id="courseCode" name="courseCode" value="${course.courseCode}" class="w-full p-2 border rounded" disabled>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700 font-bold mb-2" for="title">Title</label>
                <input type="text" id="title" name="title" value="${course.title}" class="w-full p-2 border rounded" required>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700 font-bold mb-2" for="capacity">Capacity</label>
                <input type="number" id="capacity" name="capacity" value="${course.capacity}" class="w-full p-2 border rounded" min="1" required>
            </div>
            <div class="flex justify-between">
                <button type="submit" class="bg-blue-500 text-white p-2 rounded hover:bg-blue-600">
                    <i class="fas fa-save mr-2"></i> Save Changes
                </button>
                <a href="admin?action=viewCourseRegistrations" class="bg-gray-500 text-white p-2 rounded hover:bg-gray-600">
                    <i class="fas fa-arrow-left mr-2"></i> Back
                </a>
            </div>
        </form>
    </div>
</body>
</html>
