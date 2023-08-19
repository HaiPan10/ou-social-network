<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1 class="custom">Thông tin chi tiết</h1>

<div hidden id="alert" class="alert alert-success">Cập nhật trạng thái thành công</div>
<div class="form-floating mb-3 mt-3">
    <button type="button" id="locked" class="btn btn-primary">Khóa tài khoản</button>
    <button type="button" id="unlock" class="btn btn-primary">Gỡ khóa tài khoản</button>
    <button type="button" id="resetPassword" class="btn btn-primary">Reset mật khẩu</button>
</div>
<form action="#" id="accountDetail">
    <!-- <div class="form-group">
        <div class="mb-3">
            <form:errors path="*" cssClass="text-danger" element="div"/>
        </div>
    </div> -->
    <div class="form-floating mb-3 mt-3">
        <input class="form-control" type="text" name="email" readonly="true" value="${account.email}" />
        <label for="email">Email</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <input class="form-control" id="firstName" type="text" name="firstName" value="${account.user.firstName}"
            readonly="true" />
        <label for="firstName">Tên giảng viên</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <input class="form-control" type="text" name="lastName" value="${account.user.lastName}" readonly="true" />
        <label for="lastName">Họ tên lót giảng viên</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <input id="dob" type="text" class="form-control" name="dob" value="${account.user.dob}" readonly="true" />
        <label for="dob">Ngày tháng năm sinh</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <input id="status" type="text" class="form-control" name="status" value="${account.status}" readonly="true" />
        <label for="status">Tình trạng</label>
    </div>
</form>
<c:url value="/admin/accounts/update_status" var="lockedAction" />
<script>
    let url = "${lockedAction}";
    let inputStatus = document.getElementById("status");
    document.getElementById("locked").addEventListener('click', async (e) => {
        await updateStatus("LOCKED");
    });

    document.getElementById("unlock").addEventListener('click', async (e) => {
        await updateStatus("ACTIVE");
    });

    document.getElementById("resetPassword").addEventListener('click', async (e) => {
        await updateStatus("PASSWORD_CHANGE_REQUIRED");
    });

    async function updateStatus(status){
        let requestBody = {
            "id": "${account.id}",
            "status": status
        }
        let reponse = await fetch(url, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json" // Set the content type to JSON
            },
            body: JSON.stringify(requestBody)
        })

        console.log(reponse);

        if(reponse.ok){
            inputStatus.value = status;
            document.getElementById("alert").hidden = false;
        } else {
            alert("Lỗi");
        }
    }
</script>