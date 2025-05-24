<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page import="service.RegistrationService" %>
<html>
<head>
    <title>Process Registration Requests</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-gray-100" style="background-image: url('${pageContext.request.contextPath}/images/background.jpg'); background-size: cover; background-attachment: fixed;">
    <%@ include file="header.jsp" %>
    <div class="max-w-4xl mx-auto mt-10 bg-white p-6 rounded-lg shadow-lg">
        <div class="text-center mb-6">
            <img src="${pageContext.request.contextPath}/images/process-icon.png" alt="Process Icon" class="mx-auto h-32">
            <h2 class="text-2xl font-bold mt-4">Process Registration Requests</h2>
        </div>
        <% if (request.getAttribute("message") != null) { %>
            <p class="text-green-500 text-center mb-4">${message}</p>
        <% } %>
        <c:if test="${not empty queue}">
            <table class="w-full border-collapse mb-6">
                <thead>
                    <tr class="bg-gray-200">
                        <th class="border p-2">Student ID</th>
                        <th class="border p-2">Name</th>
                        <th class="border p-2">Course Name</th>
                        <th class="border p-2">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${queue}">
                        <c:set var="student" value="${entry.key}" />
                        <c:set var="courseCode" value="${entry.value}" />
                        <tr>
                            <td class="border p-2 flex items-center">
                                <img src="${pageContext.request.contextPath}/images/student-icon.png" alt="Student Icon" class="h-6 mr-2">
                                <c:choose>
                                    <c:when test="${not empty student and not empty student.studentId}">
                                        <c:out value="${student.studentId}" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="Unknown ID" />
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="border p-2">
                                <c:choose>
                                    <c:when test="${not empty student and not empty student.name}">
                                        <c:out value="${student.name}" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="Unknown Name" />
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="border p-2">
                                <c:set var="course" value="${applicationScope.registrationService.findCourseById(courseCode)}" />
                                <c:out value="${not empty course ? course.title : 'Unknown Course'}" />
                            </td>
                            <td class="border p-2">
                                <form action="admin" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="approveRequest">
                                    <input type="hidden" name="studentId" value="${student.studentId}">
                                    <input type="hidden" name="courseCode" value="${courseCode}">
                                    <button type="submit" class="bg-green-500 text-white p-1 rounded hover:bg-green-600 flex items-center">
                                        <i class="fas fa-check mr-1"></i> Approve
                                    </button>
                                </form>
                                <form action="admin" method="post" style="display:inline; margin-left: 8px;">
                                    <input type="hidden" name="action" value="rejectRequest">
                                    <input type="hidden" name="studentId" value="${student.studentId}">
                                    <input type="hidden" name="courseCode" value="${courseCode}">
                                    <button type="submit" class="bg-red-500 text-white p-1 rounded hover:bg-red-600 flex items-center">
                                        <i class="fas fa-times mr-1"></i> Reject
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <form action="admin" method="post">
                <input type="hidden" name="action" value="processRequests">
                <button type="submit" class="w-full bg-red-500 text-white p-2 rounded hover:bg-red-600 flex items-center justify-center">
                    <i class="fas fa-cogs mr-2"></i> Process All Requests
                </button>
            </form>
        </c:if>
        <c:if test="${empty queue}">
            <p class="text-center text-gray-600">No registration requests to process.</p>
        </c:if>
    </div>
</body>
</html>