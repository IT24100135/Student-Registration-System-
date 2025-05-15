<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>View Students</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-gray-100" style="background-image: url('${pageContext.request.contextPath}/images/background.jpg'); background-size: cover; background-attachment: fixed;">
    <%@ include file="header.jsp" %>
    <div class="max-w-4xl mx-auto mt-10 bg-white p-6 rounded-lg shadow-lg">
        <h2 class="text-2xl font-bold mb-4 text-center">Student List</h2>
        <% if (request.getAttribute("message") != null) { %>
            <p class="text-green-500 mb-4 text-center"><%= request.getAttribute("message") %></p>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <p class="text-red-500 mb-4 text-center"><%= request.getAttribute("error") %></p>
        <% } %>
        <table class="w-full border-collapse">
            <thead>
                <tr class="bg-gray-200">
                    <th class="border p-2">Student ID</th>
                    <th class="border p-2">Name</th>
                    <th class="border p-2">Phone Number</th>
                    <th class="border p-2">Email</th>
                    <th class="border p-2">Gender</th>
                    <th class="border p-2">District</th>
                    <th class="border p-2">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="student" items="${students}">
                    <tr>
                        <td class="border p-2 flex items-center">
                            <img src="${pageContext.request.contextPath}/images/student-icon.png" alt="Student Icon" class="h-6 mr-2">
                            ${student.studentId}
                        </td>
                        <td class="border p-2">${student.name}</td>
                        <td class="border p-2">${student.phoneNumber}</td>
                        <td class="border p-2">${student.email}</td>
                        <td class="border p-2">${student.gender}</td>
                        <td class="border p-2">${student.district}</td>
                        <td class="border p-2">
                            <a href="admin?action=removeStudent&studentId=${student.studentId}" class="text-red-500 hover:underline flex items-center" onclick="return confirm('Are you sure you want to remove this student?')">
                                <i class="fas fa-trash-alt mr-1"></i> Remove
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>