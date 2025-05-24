<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page import="model.Feedback" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="service.RegistrationService" %>
<html>
<head>
    <title>Review Feedback</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-gray-100" style="background-image: url('${pageContext.request.contextPath}/images/background.jpg'); background-size: cover; background-attachment: fixed;">
    <%@ include file="header.jsp" %>
    <div class="max-w-4xl mx-auto mt-10 bg-white p-6 rounded-lg shadow-lg">
        <h2 class="text-2xl font-bold mb-4 text-center">Review Student Feedback</h2>
        <c:if test="${not empty message}">
            <p class="text-green-500 text-center mb-4">${message}</p>
        </c:if>
        <c:if test="${not empty error}">
            <p class="text-red-500 text-center mb-4">${error}</p>
        </c:if>
        <%
            ArrayList<Feedback> feedbackList = (ArrayList<Feedback>) request.getAttribute("feedbackList");
            RegistrationService registrationService = (RegistrationService) application.getAttribute("registrationService");
            pageContext.setAttribute("regService", registrationService); // Make registrationService available to EL
        %>
        <table class="w-full border-collapse">
            <thead>
                <tr class="bg-gray-200">
                    <th class="border p-2">Student ID</th>
                    <th class="border p-2">Feedback</th>
                    <th class="border p-2">Timestamp</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="feedback" items="${feedbackList}">
                    <tr>
                        <td class="border p-2">${feedback.studentId}</td>
                        <td class="border p-2">${feedback.feedbackText}</td>
                        <td class="border p-2">
                            ${regService.formatTimestamp(feedback.timestamp)}
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty feedbackList}">
                    <tr>
                        <td colspan="3" class="border p-2 text-center">No feedback available.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        <a href="admin?action=dashboard" class="mt-4 inline-block bg-gray-500 text-white p-2 rounded hover:bg-gray-600 flex items-center justify-center w-full">
            <i class="fas fa-arrow-left mr-2"></i> Back to Dashboard
        </a>
    </div>
</body>
</html>