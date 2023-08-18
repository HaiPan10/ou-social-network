<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1 class="custom">Thông tin chi tiết</h1>
<!-- <c:if test="${status != null}">
    <div class="alert alert-success">
        ${status}
    </div>
</c:if> -->
<c:url value="/admin/accounts/${account.id}" var="accountAction" />
<form:form action="${accountAction}" id="accountDetail" modelAttribute="account" method="post">
    <!-- <div class="form-group">
        <div class="mb-3">
            <form:errors path="*" cssClass="text-danger" element="div"/>
        </div>
    </div> -->
    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control" type="text" path="email" 
            name="email" readonly="true" value="${account.email}"/>
        <label for="email">Email</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control" id="firstName" type="text" path="user.firstName" 
            name="firstName" value="${account.user.firstName}" readonly="true"/>
        <label for="firstName">Tên giảng viên</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control" type="text" path="user.lastName" 
            name="lastName" value="${account.user.lastName}" readonly="true"/>
        <label for="lastName">Họ tên lót giảng viên</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input id ="dob" type="text" class="form-control" path="user.dob"
            name="dob" value="${account.user.dob}" readonly="true"/>
        <label for="dob">Ngày tháng năm sinh</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input id ="status" type="text" class="form-control" path="status"
            name="status" value="${account.status}" readonly="true"/>
        <label for="status">Tình trạng</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:button id="unlock" class="btn btn-primary" type="submit">Gỡ Khóa Tài Khoản</form:button>
        <form:button id="passwordChange" class="btn btn-primary" type="submit">Reset Thời Gian Đổi Mật Khẩu</form:button>
        <form:button id="lock" class="btn btn-primary" type="submit">Khóa Tài Khoản</form:button>
    </div>
    
</form:form>

<script>
    var provider = document.getElementById("account-detail");
    provider.className += " active";
    var status = document.getElementById("status");

    document.getElementById("lock").addEventListener('submit', (e) => {
        status.value = "LOCKED"
    });

    document.getElementById("unlock").addEventListener('submit', (e) => {
        status.value = "ACTIVE"
    });

    document.getElementById("passwordChange").addEventListener('submit', (e) => {
        status.value = "PASSWORD_CHANGE_REQUIRED"
    });
</script>