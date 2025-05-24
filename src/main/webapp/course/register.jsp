<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register for Course</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100 flex items-center justify-center h-screen">
    <div class="bg-white p-6 rounded-lg shadow-lg w-96">
        <h2 class="text-2xl font-bold mb-4 text-center">Register for Course</h2>
        <% if (request.getAttribute("error") != null) { %>
            <p class="text-red-500 mb-4"><%= request.getAttribute("error") %></p>
        <% } %>
        <form action="course" method="post">
            <input type="hidden" name="action" value="register">
            <div class="mb-4">
                <label class="block text-gray-700">Course ID</label>
                <input type="text" name="courseId" class="w-full p-2 border rounded">
            </div>
            <button type="submit" class="w-full bg-blue-500 text-white p-2 rounded hover:bg-blue-600">Register</button>
        </form>
    </div>
</body>
</html>