<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<section class="container">
    <h1>Trang chủ</h1>
    <sec:authorize access="hasRole('ROLE_ADMIN')"> 
        <h1>Đăng nhập thành công với quyền admin</h1>
        ADMIN USER
    </sec:authorize>
    <c:choose>
        <c:when test="${pageContext.request.userPrincipal.name == null}">
            <h1>Đăng nhập</h1>
            <div class="alert alert-danger">
                <c:if test="${param.error != null}">
                    <spring:message code="user.login.error1" />
                </c:if>
                <c:if test="${param.accessDenied != null}">
                    <spring:message code="user.login.error2" />
                </c:if>
            </div>
            <string:url value="/login" var="login" />
            <form action="${login}" method="post">
                <div class="container">
                    <label for="email"><b>Username</b></label>
                    <input type="text" placeholder="Enter Username" name="email" required>

                    <label for="password"><b>Password</b></label>
                    <input type="password" placeholder="Enter Password" name="password" required>

                    <button type="submit">Login</button>
                </div>
            </form>
        </c:when>
        <c:when test="${pageContext.request.userPrincipal.name != null}">
            <c:url value="/logout" var="logout" />
            <a href="${logout}">Đăng xuất</a>
        </c:when>
    </c:choose>
</section>