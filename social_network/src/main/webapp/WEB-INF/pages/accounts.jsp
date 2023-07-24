<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h1 class="custom">QUẢN LÝ TÀI KHOẢN</h1>
<p>${pendingAccount.id}</p><br>
<p>${pendingAccount.email}</p><br>
<p>${pendingAccount.password}</p>

<script>
    var d = document.getElementById("account-providers-menu");
    d.className += " active";
</script>