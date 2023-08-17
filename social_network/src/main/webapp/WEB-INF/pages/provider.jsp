<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1 class="custom">Cấp tài khoản giảng viên</h1>
<c:if test="${status != null}">
    <div class="alert alert-success">
        ${status}
    </div>
</c:if>
<c:url value="/admin/accounts/provider" var="provider" />
<form:form action="${provider}" id="provideAccount" modelAttribute="account" method="post" enctype="multipart/form-data">
    <h2>Tài khoản</h2>
    <div class="form-group">
        <div class="mb-3">
            <form:errors path="*" cssClass="text-danger" element="div"/>
        </div>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control" type="text" path="email" name="email" placeholder="Tài khoản email" />
        <label for="email">Email</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control" type="text" path="password" name="password" placeholder="Mật khẩu"
            value="${defaultPassword}" readonly="true"/>
        <label for="password">Mật khẩu</label>
    </div>
    <h2>Thông tin giảng viên</h2>
    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control" id="firstName" type="text" path="user.firstName" 
            name="firstName" placeholder="Tên" />
        <label for="firstName">Tên giảng viên</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control" type="text" path="user.lastName" name="lastName" placeholder="Họ tên lót" />
        <label for="lastName">Họ tên lót giảng viên</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input id ="dob" class="form-control" type="date" path="user.dob" name="dob" />
        <label for="dob">Ngày tháng năm sinh</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <input type="file" name="fileInput">
    </div>
    <form:button type="submit">Cấp tài khoản</form:button>
</form:form>

<script>
    var provider = document.getElementById("account-providers-menu");
    provider.className += " active";

    document.getElementById("firstName").addEventListener('change', (e) => {
        console.log(e.target.value);
    })
</script>