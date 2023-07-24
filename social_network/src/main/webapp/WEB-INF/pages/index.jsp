<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="container">
    <h1>Trang chủ</h1>
    <c:choose>
        <c:when test="${pageContext.request.userPrincipal.name == null}">
            <c:url value="/login" var="login" />
            <a href="${login}">Đăng nhập</a>
        </c:when>
        <c:when test="${pageContext.request.userPrincipal.name != null}">
            <c:url value="/logout" var="logout" />
            <h1>Đăng nhập thành công với quyền admin</h1>
            <a href="${logout}">Đăng xuất</a>
        </c:when>
    </c:choose>
</section>