<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
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
        </div>
    </div>
</body>
</html>