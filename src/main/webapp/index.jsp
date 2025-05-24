<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-gray-100" style="background-image: url('${pageContext.request.contextPath}/images/background.jpg'); background-size: cover; background-attachment: fixed;">
    <header class="bg-white shadow-md p-4 flex items-center justify-between">
        <div class="flex items-center">
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="University Logo" class="h-12 mr-4">
            <h1 class="text-2xl font-bold text-gray-800">Student Course Registration System</h1>
        </div>
    </header>
    <div class="max-w-4xl mx-auto mt-10 text-center">
        <img src="${pageContext.request.contextPath}/images/banner.jpg" alt="Campus Banner" class="w-full h-48 object-cover rounded-lg shadow-md mb-6">
        <h2 class="text-2xl font-bold text-white">Welcome to the Student Course Registration System</h2>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <a href="admin?action=login" class="bg-blue-500 text-white p-4 rounded-lg hover:bg-blue-600 flex items-center justify-center">
                <i class="fas fa-user-shield mr-2"></i> Admin Login
            </a>
            <a href="student?action=login" class="bg-green-500 text-white p-4 rounded-lg hover:bg-green-600 flex items-center justify-center">
                <i class="fas fa-user-graduate mr-2"></i> Student Login
            </a>
        </div>
    </div>
</body>
</html>