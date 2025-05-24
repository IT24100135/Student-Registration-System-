<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Register for a Course</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-gray-100" style="background-image: url('${pageContext.request.contextPath}/images/background.jpg'); background-size: cover; background-attachment: fixed;">
    <%@ include file="header.jsp" %>
    <div class="max-w-md mx-auto mt-10 bg-white p-6 rounded-lg shadow-lg">
        <div class="text-center mb-6">
            <img src="${pageContext.request.contextPath}/images/course.jpg" alt="Course Image" class="mx-auto h-32 object-cover">
            <h2 class="text-2xl font-bold mt-4">Register for a Course</h2>
        </div>
        <% if (request.getAttribute("message") != null) { %>
            <p class="text-green-500 text-center mb-4"><%= request.getAttribute("message") %></p>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <p class="text-red-500 text-center mb-4"><%= request.getAttribute("error") %></p>
        <% } %>
        <form action="student" method="post">
            <input type="hidden" name="action" value="register">
            <div class="mb-4">
                <label class="block text-gray-700">Category</label>
                <select name="category" class="w-full p-2 border rounded" onchange="this.form.submit()" required>
                    <option value="">-- Select Category --</option>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category}" ${category == selectedCategory ? 'selected' : ''}>${category}</option>
                    </c:forEach>
                </select>
            </div>
            <c:if test="${not empty courses}">
                <div class="mb-4 relative">
                    <label class="block text-gray-700">Course</label>
                    <div class="flex items-center">
                        <i class="fas fa-book absolute ml-3 text-gray-500"></i>
                        <select name="courseCode" class="w-full p-2 pl-10 border rounded" required>
                            <option value="">-- Select Course --</option>
                            <c:forEach var="course" items="${courses}">
                                <option value="${course.courseCode}">${course.courseCode} - ${course.title}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <button type="submit" class="w-full bg-green-500 text-white p-2 rounded hover:bg-green-600 flex items-center justify-center">
                    <i class="fas fa-check mr-2"></i> Register
                </button>
            </c:if>
        </form>
    </div>
</body>
</html>