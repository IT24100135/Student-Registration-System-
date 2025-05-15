<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Course List</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100 p-6">
    <div class="max-w-4xl mx-auto bg-white p-6 rounded-lg shadow-lg">
        <h2 class="text-2xl font-bold mb-4">Available Courses in ${category}</h2>
        <table class="w-full border-collapse">
            <thead>
                <tr class="bg-gray-200">
                    <th class="border p-2">Course ID</th>
                    <th class="border p-2">Title</th>
                    <th class="border p-2">Capacity</th>
                    <th class="border p-2">Available Slots</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="course" items="${courses}">
                    <c:if test="${course.hasAvailableSlots()}">
                        <tr>
                            <td class="border p-2">${course.courseCode}</td>
                            <td class="border p-2">${course.title}</td>
                            <td class="border p-2">${course.capacity}</td>
                            <td class="border p-2">${course.capacity - course.registeredStudents.size()}</td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>
        <div class="mt-4">
            <a href="course?action=register" class="bg-blue-500 text-white p-2 rounded hover:bg-blue-600">Register for a Course</a>
        </div>
    </div>
</body>
</html>