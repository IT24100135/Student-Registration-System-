<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Login</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-gray-100" style="background-image: url('${pageContext.request.contextPath}/images/background.jpg'); background-size: cover; background-attachment: fixed;">
    <%@ include file="header.jsp" %>
    <div class="max-w-md mx-auto mt-10 bg-white p-6 rounded-lg shadow-lg">
        <div class="text-center mb-6">
            <img src="${pageContext.request.contextPath}/images/welcome.jpg" alt="Welcome Image" class="mx-auto h-32 object-cover">
            <h2 class="text-2xl font-bold mt-4">Student Login</h2>
        </div>
        <% if (request.getAttribute("error") != null) { %>
            <p class="text-red-500 text-center mb-4"><%= request.getAttribute("error") %></p>
        <% } %>
        <form action="student" method="post">
            <input type="hidden" name="action" value="login">
            <div class="mb-4 relative">
                <label class="block text-gray-700">Student ID</label>
                <div class="flex items-center">
                    <i class="fas fa-user absolute ml-3 text-gray-500"></i>
                    <input type="text" name="studentId" class="w-full p-2 pl-10 border rounded" required>
                </div>
            </div>
            <div class="mb-4 relative">
                <label class="block text-gray-700">Password</label>
                <div class="flex items-center">
                    <i class="fas fa-lock absolute ml-3 text-gray-500"></i>
                    <input type="password" name="password" class="w-full p-2 pl-10 border rounded" required>
                </div>
            </div>
            <button type="submit" class="w-full bg-blue-500 text-white p-2 rounded hover:bg-blue-600 flex items-center justify-center">
                <i class="fas fa-sign-in-alt mr-2"></i> Login
            </button>
        </form>
    </div>
</body>
</html>