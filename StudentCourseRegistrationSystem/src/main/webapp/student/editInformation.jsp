<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Edit Student Information</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-gray-100" style="background-image: url('${pageContext.request.contextPath}/images/background.jpg'); background-size: cover; background-attachment: fixed;">
    <%@ include file="header.jsp" %>
    <div class="max-w-md mx-auto mt-10 bg-white p-6 rounded-lg shadow-lg">
        <div class="text-center mb-6">
            <img src="${pageContext.request.contextPath}/images/student-icon.png" alt="Student Icon" class="mx-auto h-16">
            <h2 class="text-2xl font-bold mt-4">Edit Your Information</h2>
        </div>
        <c:if test="${not empty error}">
            <p class="text-red-500 text-center mb-4">${error}</p>
        </c:if>
        <c:if test="${not empty message}">
            <p class="text-green-500 text-center mb-4">${message}</p>
        </c:if>
        <form action="student" method="post">
            <input type="hidden" name="action" value="updateInformation">
            <div class="mb-4">
                <label class="block text-gray-700">Student ID (Read-Only)</label>
                <input type="text" name="studentId" value="${sessionScope.student.studentId}" class="w-full p-2 border rounded bg-gray-100" readonly>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Name</label>
                <input type="text" name="name" value="${sessionScope.student.name}" class="w-full p-2 border rounded" required>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Email</label>
                <input type="email" name="email" value="${sessionScope.student.email}" class="w-full p-2 border rounded" required>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Phone Number</label>
                <input type="text" name="phoneNumber" value="${sessionScope.student.phoneNumber}" class="w-full p-2 border rounded" required>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Gender</label>
                <select name="gender" class="w-full p-2 border rounded" required>
                    <option value="Male" ${sessionScope.student.gender == 'Male' ? 'selected' : ''}>Male</option>
                    <option value="Female" ${sessionScope.student.gender == 'Female' ? 'selected' : ''}>Female</option>
                    <option value="Other" ${sessionScope.student.gender == 'Other' ? 'selected' : ''}>Other</option>
                </select>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">District</label>
                <input type="text" name="district" value="${sessionScope.student.district}" class="w-full p-2 border rounded" required>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Password</label>
                <input type="password" name="password" value="${sessionScope.student.password}" class="w-full p-2 border rounded" required>
            </div>
            <button type="submit" class="w-full bg-blue-500 text-white p-2 rounded hover:bg-blue-600 flex items-center justify-center">
                <i class="fas fa-save mr-2"></i> Update Information
            </button>
        </form>
        <a href="student?action=dashboard" class="block text-center mt-4 text-blue-500 hover:underline">Back to Dashboard</a>
    </div>
</body>
</html>