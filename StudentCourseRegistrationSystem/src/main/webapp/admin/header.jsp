<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header class="bg-white shadow-md p-4 flex items-center justify-between">
    <div class="flex items-center">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="University Logo" class="h-12 mr-4">
        <h1 class="text-2xl font-bold text-gray-800">Student Course Registration System</h1>
    </div>
    <a href="${pageContext.request.contextPath}/admin?action=login" class="text-blue-500 hover:underline">Logout</a>
</header>