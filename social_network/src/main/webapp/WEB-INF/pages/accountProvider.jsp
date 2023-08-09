<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1 class="custom">Cấp tài khoản giảng viên</h1>
<!-- <form id="provideAccount" method="post">
    <div id="account">
        <div class="form-floating mb-3 mt-3">
            <input class="form-control" type="text" id="email" name="email" placeholder="Tài khoản email" />
            <label for="email">Email</label>
        </div>
        <div class="form-floating mb-3 mt-3">
            <input class="form-control" type="password" id="password" name="password" placeholder="Mật khẩu"
                value="ou@123" />
            <label for="password">Mật khẩu</label>
        </div>
    </div>
    <div id="user">
        <div class="form-floating mb-3 mt-3">
            <input class="form-control" type="text" id="firstName" name="firstName" placeholder="Tên" />
            <label for="firstName">Tên giảng viên</label>
        </div>
        <div class="form-floating mb-3 mt-3">
            <input class="form-control" type="text" id="lastName" name="lastName" placeholder="Họ tên lót" />
            <label for="firstName">Họ tên lót giảng viên</label>
        </div>
        <div class="form-floating mb-3 mt-3">
            <input class="form-control" type="date" id="dob" name="dob" />
            <label for="dob">Ngày tháng năm sinh</label>
        </div>
    </div>
    <button type="submit">Cấp tài khoản</button>
</form> -->
<form:form id="provideAccount" modelAttribute="account" method="post">
    <h2>Tài khoản</h2>
    <div class="form-floating mb-3 mt-3">
        <input class="form-control" type="text" path="email" name="email" placeholder="Tài khoản email" />
        <label for="email">Email</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <input class="form-control" type="password" path="password" name="password" placeholder="Mật khẩu"
            value="ou@123" />
        <label for="password">Mật khẩu</label>
    </div>
    <h2>Thông tin giảng viên</h2>
    <div class="form-floating mb-3 mt-3">
        <input class="form-control" type="text" path="user.firstName" name="user.firstName" placeholder="Tên" />
        <label for="firstName">Tên giảng viên</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <input class="form-control" type="text" path="user.lastName" name="user.lastName" placeholder="Họ tên lót" />
        <label for="lastName">Họ tên lót giảng viên</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <input id ="dob" class="form-control" type="date" path="user.dob" name="user.dob" />
        <label for="dob">Ngày tháng năm sinh</label>
    </div>
    <input type="submit" value="Cấp tài khoản" />
</form:form>

<script>
    var provider = document.getElementById("account-providers-menu");
    provider.className += " active";
    // form.addEventListener("submit", function (e) {
    //     e.preventDefault();
    //     console.log("[DEBUG] - prevent the form submit");
    //     let formData = {};
    //     console.log(formData);
    //     let divElements = document.querySelectorAll("#account, #user");
    //     console.log(divElements)
    //     divElements.forEach(element => {
    //         let key = element.id;
    //         formData[key] = {};
    //         console.log(formData);
    //         let inputFields = element.querySelectorAll('input');
    //         inputFields.forEach(input => {
    //             formData[key][input.name] = input.value;
    //         })
    //     });
    //     let json = JSON.stringify(formData);
    //     fetch('http://localhost:8080/social_network/admin/accounts/provider', {
    //         method: 'POST',
    //         headers: {
    //             'Content-Type': 'application/json'
    //         },
    //         body: json
    //     })
    //         .then(response => response.json())
    //         .then(responseData => {
    //             console.log('Response:', responseData);
    //         })
    //         .catch(error => {
    //             console.error('Fetch error:', error);
    //         });
    // })
</script>