<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Tài khoản /</span> Cấp tài khoản giảng viên</h4>
<c:if test="${status != null}">
    <div class="alert alert-success">
        ${status}
    </div>
</c:if>
<c:url value="/admin/accounts/provider" var="provider" />
<div class="row">
    <div class="col-xl">
        <form:form action="${provider}" id="provideAccount" modelAttribute="account" method="post" enctype="multipart/form-data">
            <div class="card mb-4">
                <div class="card-header d-flex justify-content-between align-items-center">
                  <h5 class="mb-0">Thông tin tài khoản</h5>
                </div>
                <div class="card-body">
                  <form>
                    <div class="mb-3">
                        <form:errors path="*" cssClass="text-danger" element="div"/>
                    </div>
                    <div class="mb-3">
                      <label class="form-label">Email</label>
                      <div class="input-group input-group-merge">
                        <span class="input-group-text"><i class="bx bx-envelope"></i></span>
                        <form:input class="form-control" type="text" path="email" name="email"/>
                      </div>
                    </div>
                    <div class="mb-3">
                      <label class="form-label">Mật khẩu</label>
                      <div class="input-group input-group-merge">
                        <span class="input-group-text"><i class="bx bxs-key"></i></span>
                        <form:input class="form-control" type="text" path="password" name="password" value="${defaultPassword}" readonly="true"/>
                      </div>
                    </div>
                    <div class="mb-3">
                      <label class="form-label">Tên giảng viên</label>
                      <div class="input-group input-group-merge">
                        <span class="input-group-text"
                          ><i class="bx bx-user"></i
                        ></span>
                        <form:input class="form-control" id="firstName" type="text" path="user.firstName" name="firstName"/>
                      </div>
                    </div>
                    <div class="mb-3">
                      <label class="form-label">Họ tên lót giảng viên</label>
                      <div class="input-group input-group-merge">
                        <span class="input-group-text"
                          ><i class="bx bx-user"></i
                        ></span>
                        <form:input class="form-control" type="text" path="user.lastName" name="lastName" placeholder="Họ tên lót" />
                      </div>
                    </div>
                    <div class="mb-3">
                      <label class="form-label">Ngày tháng năm sinh</label>
                      <div class="input-group input-group-merge">
                        <form:input id ="dob" class="form-control" type="date" path="user.dob" name="dob" />
                      </div>
                    </div>
                    <div class="mb-3">
                      <label class="form-label">Avatar tài khoản</label>
                      <div class="input-group input-group-merge">
                        <span class="input-group-text"
                          ><i class="bx bx-image-alt"></i
                        ></span>
                        <input class="form-control" id="formFile" type="file" name="fileInput">
                      </div>
                    </div>
                    <form:button type="submit" class="btn btn-primary">Cấp tài khoản</form:button>
                  </form>
                </div>
              </div>
        </form:form>
    </div>
  </div>
</div>


<!-- <h1 class="custom">Cấp tài khoản giảng viên</h1>
<c:if test="${status != null}">
    <div class="alert alert-success">
        ${status}
    </div>
</c:if> -->
<!-- <c:url value="/admin/accounts/provider" var="provider" /> -->
<!-- <form:form action="${provider}" id="provideAccount" modelAttribute="account" method="post" enctype="multipart/form-data">
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
</form:form> -->

<script>
    var provider = document.getElementById("account-providers-menu");
    provider.className += " active";

</script>