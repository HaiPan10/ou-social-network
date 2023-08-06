<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1 class="custom">Cấp tài khoản giảng viên</h1>
<form:form modelAttribute="teacherAccount" method="post">
    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control" type="text" id="email" name="email" path="email"
            placeholder="Tài khoản email" />
        <label for="email">Email</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control" type="password" id="password" name="password" path="password"
            placeholder="Mật khẩu" value="ou@123" />
        <label for="password">Mật khẩu</label>
    </div>
    <!-- <div class="form-floating mb-3 mt-3">
        <form:input class="form-control" type="text" id="firstName" name="firstName" path="firstName"
            placeholder="Tên giảng viên"/>
        <label for="firstName">Tên</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control" type="text" id="lastName" name="lastName" path="lastName"
            placeholder="Họ giảng viên" />
        <label for="lastName">Họ</label>
    </div> -->
</form:form>

<script>
    var provider = document.getElementById("account-providers-menu");
    provider.className += " active";
</script>