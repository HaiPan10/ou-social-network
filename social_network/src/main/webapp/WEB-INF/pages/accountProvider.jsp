<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1 class="custom">Cấp tài khoản giảng viên</h1>
<form action="#" id="provideAccount" method="post">
    <div class="form-floating mb-3 mt-3">
        <input class="form-control" type="text" id="email" name="email"
            placeholder="Tài khoản email" />
        <label for="email">Email</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <input class="form-control" type="password" id="password" name="password"
            placeholder="Mật khẩu" value="ou@123" />
        <label for="password">Mật khẩu</label>
    </div>
    <button type="submit">Cấp tài khoản</button>
</form>

<script>
    var provider = document.getElementById("account-providers-menu");
    provider.className += " active";
    const form = document.getElementById("provideAccount");
    form.addEventListener("submit", function(e){
        e.preventDefault();
        console.log("[DEBUG] - prevent the form submit");

        let accountObject = {};
        let formData = new FormData(e.target);
        formData.forEach((key, value) => {
            accountObject[key] = value;
        })

        let jsonObject = {};
        jsonObject["account"] = accountObject;

        console.log(JSON.stringify(jsonObject));
    })
</script>