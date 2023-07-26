<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- <section class="container">
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
</section> -->

<sec:authorize access="hasRole('ROLE_ADMIN')">
    <h1>Đăng nhập thành công với quyền admin</h1>
    ADMIN USER
</sec:authorize>
<c:choose>
    <c:when test="${pageContext.request.userPrincipal.name == null}">
        <div class="container-xxl">
            <div class="authentication-wrapper authentication-basic container-p-y">
                <div class="authentication-inner">
                    <div class="card">
                        <div class="card-body">
                            <h4 class="mb-2">Trang quản trị mạng xã hội cựu sinh viên trường đại học Mở TP.HCM</h4>
                            <p class="mb-4">Vui lòng đăng nhập</p>

                            <string:url value="/login" var="login" />
                            <form id="formAuthentication" class="mb-3" action="${login}" method="post">
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email</label>
                                    <input type="email" class="form-control" id="email" name="email" required />
                                </div>
                                <div class="mb-3 form-password-toggle">
                                    <div class="d-flex justify-content-between">
                                        <label class="form-label" for="password">Mật khẩu</label>
                                    </div>
                                    <div class="input-group input-group-merge">
                                        <input type="password" id="password" class="form-control" name="password" />
                                        <span class="input-group-text cursor-pointer" id="toggle-password"><i class="bx bx-hide" id="toggle-icon"></i></span>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <button class="btn btn-primary d-grid w-100" type="submit">Đăng nhập</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
    <c:when test="${pageContext.request.userPrincipal.name != null}">
        <script>
            <c:url value="/admin/dashboard" var="dashBoardAction" />
            function redirectToOtherSite() {
                window.location.href = "${dashBoardAction}";
            }
            window.onload = redirectToOtherSite;
        </script>
        <!-- <c:url value="/logout" var="logout" />
        <a href="${logout}">Đăng xuất</a> -->
    </c:when>
</c:choose>

<script>
    document.getElementById("toggle-password").onclick = function() {
        var inputPassword = document.getElementById("password");
        var toggleIcon = document.getElementById("toggle-icon");
        if (inputPassword.type === "password") {
            inputPassword.type = "text";
            toggleIcon.classList.remove("bx-hide");
            toggleIcon.classList.add("bx-show");
        } else {
            inputPassword.type = "password";
            toggleIcon.classList.remove("bx-show");
            toggleIcon.classList.add("bx-hide");
        }
    }
</script>