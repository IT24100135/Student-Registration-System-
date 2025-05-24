<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Submit Feedback</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-gray-100" style="background-image: url('${pageContext.request.contextPath}/images/background.jpg'); background-size: cover; background-attachment: fixed;">
    <%@ include file="header.jsp" %>
    <div class="max-w-md mx-auto mt-10 bg-white p-6 rounded-lg shadow-lg">
        <h2 class="text-2xl font-bold mb-4 text-center">Submit Feedback</h2>
        <c:if test="${not empty message}">
            <p class="text-green-500 text-center mb-4">${message}</p>
        </c:if>
        <c:if test="${not empty error}">
            <p class="text-red-500 text-center mb-4">${error}</p>
        </c:if>
        <form action="student" method="post" class="space-y-4">
            <input type="hidden" name="action" value="submitFeedback">
            <div class="mb-4">
                <label class="block text-gray-700 font-bold mb-2" for="feedbackText">Feedback:</label>
                <textarea id="feedbackText" name="feedbackText" class="w-full p-2 border rounded" rows="5" required></textarea>
            </div>
            <button type="submit" class="bg-purple-500 text-white p-2 rounded hover:bg-purple-600 w-full flex items-center justify-center">
                <i class="fas fa-comment mr-2"></i> Submit Feedback
            </button>
        </form>
        <a href="student?action=dashboard" class="mt-4 inline-block bg-gray-500 text-white p-2 rounded hover:bg-gray-600 flex items-center justify-center w-full">
            <i class="fas fa-arrow-left mr-2"></i> Back to Dashboard
        </a>
    </div>
</body>
</html>