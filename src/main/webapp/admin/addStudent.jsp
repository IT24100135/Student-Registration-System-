<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Student</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-gray-100" style="background-image: url('${pageContext.request.contextPath}/images/background.jpg'); background-size: cover; background-attachment: fixed;">
    <%@ include file="header.jsp" %>
    <div class="max-w-md mx-auto mt-10 bg-white p-6 rounded-lg shadow-lg">
        <div class="text-center mb-6">
            <img src="${pageContext.request.contextPath}/images/student.jpg" alt="Student Image" class="mx-auto h-32">
            <h2 class="text-2xl font-bold mt-4">Add New Student</h2>
        </div>
        <form action="admin" method="post">
            <input type="hidden" name="action" value="addStudent">
            <div class="mb-4">
                <label class="block text-gray-700">Student ID</label>
                <input type="text" name="studentId" class="w-full p-2 border rounded" required>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Name</label>
                <input type="text" name="name" class="w-full p-2 border rounded" required>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Phone Number</label>
                <input type="text" name="phoneNumber" class="w-full p-2 border rounded" required>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Email</label>
                <input type="email" name="email" class="w-full p-2 border rounded" required>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Gender</label>
                <select name="gender" class="w-full p-2 border rounded" required>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                </select>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">District</label>
                <input type="text" name="district" class="w-full p-2 border rounded" required>
            </div>
            <button type="submit" class="w-full bg-blue-500 text-white p-2 rounded hover:bg-blue-600 flex items-center justify-center">
                <i class="fas fa-user-plus mr-2"></i> Add Student
            </button>
        </form>
    </div>
</body>
</html>