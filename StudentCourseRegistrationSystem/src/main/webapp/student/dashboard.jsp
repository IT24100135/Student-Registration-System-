<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-gray-100" style="background-image: url('${pageContext.request.contextPath}/images/background.jpg'); background-size: cover; background-attachment: fixed;">
    <%@ include file="header.jsp" %>
    <div class="max-w-4xl mx-auto mt-10">
        <img src="${pageContext.request.contextPath}/images/banner.jpg" alt="Campus Banner" class="w-full h-48 object-cover rounded-lg shadow-md mb-6">
        <h2 class="text-2xl font-bold mb-6 text-center">Welcome, ${student.name}!</h2>
        <div class="bg-white p-6 rounded-lg shadow-lg mb-6">
            <h3 class="text-xl font-semibold mb-4 flex items-center">
                <img src="${pageContext.request.contextPath}/images/student-icon.png" alt="Student Icon" class="h-6 mr-2">
                Your Profile
            </h3>
            <p><strong>Student ID:</strong> ${student.studentId}</p>
            <p><strong>Name:</strong> ${student.name}</p>
            <p><strong>Email:</strong> ${student.email}</p>
            <p><strong>Phone Number:</strong> ${student.phoneNumber}</p>
            <p><strong>Gender:</strong> ${student.gender}</p>
            <p><strong>District:</strong> ${student.district}</p>
            <a href="student?action=editInformation" class="mt-4 inline-block text-blue-500 hover:underline">Edit Information</a>
        </div>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
            <a href="student?action=courses" class="bg-blue-500 text-white p-4 rounded-lg hover:bg-blue-600 flex items-center justify-center">
                <i class="fas fa-list mr-2"></i> View Available Courses
            </a>
            <a href="student?action=register" class="bg-green-500 text-white p-4 rounded-lg hover:bg-green-600 flex items-center justify-center">
                <i class="fas fa-book mr-2"></i> Register for Courses
            </a>
            <a href="student?action=viewCourses" class="bg-purple-500 text-white p-4 rounded-lg hover:bg-purple-600 flex items-center justify-center">
                <i class="fas fa-eye mr-2"></i> View My Courses
            </a>
        </div>
    </div>
</body>
</html>