<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">OU Social Network</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <c:url value="/" var="action" />
                    <a class="nav-link" href="${action}">Trang chủ</a>
                </li>
                <li class="nav-item">
                    <c:url value="/accounts" var="accountAction" />
                    <a class="nav-link" href="${accountAction}">Quản lý tài khoản</a>
                </li>
            </ul>
        </div>
    </div>
</nav>