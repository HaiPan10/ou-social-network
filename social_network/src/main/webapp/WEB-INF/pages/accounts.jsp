<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- <form method="GET">
    <select class="form-select" name="status" onchange="this.form.submit()">
        <c:forEach items="${status}" var="s">
            <c:choose>
                <c:when test="${filterStatus != null && s.toString() eq filterStatus}">
                    <option selected value="${s}">${s}</option>
                </c:when>
                <c:otherwise>
                    <option value="${s}">${s}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </select>
</form> -->

<button type="button" class="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown" id="dropdownButton">
    Lọc theo tình trạng
</button>
<ul class="dropdown-menu" id="dropdownMenu" style="cursor: pointer;">
    <c:forEach items="${status}" var="s">
        <c:url value="/admin/accounts" var="filterAction">
            <c:param name="status" value="${s}" />
        </c:url>
        <a class="dropdown-item" href="${filterAction}">${s}</a>
    </c:forEach>
</ul>

<h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Tài khoản /</span> Quản lý tài khoản</h4>

<div class="card">
    <h5 class="card-header">Danh sách Tài khoản</h5>
    <div class="table-responsive text-nowrap">
        <table class="table">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Gmail</th>
                    <th>Họ</th>
                    <th>Tên</th>
                    <th>Tình trạng</th>
                    <th></th>
                </tr>
            </thead>
            <c:set var="count" value="0" scope="page" />
            <tbody class="table-border-bottom-0">
                <c:forEach items="${accounts}" var="p">
                    <tr>
                        <td><i class="fab fa-angular fa-lg text-danger me-3"></i> <strong>${p.id}</strong></td>
                        <td>${p.email}</td>
                        <td>${p.user.lastName}</td>
                        <td>${p.user.firstName}</td>
                        <td>
                            ${p.status}
                        </td>
                        <c:url value="/admin/accounts/${p.id}" var="editAction" />
                        <th><a href="${editAction}">Xem chi tiết</a></th>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <c:if test="${counter > 1}">
            <div class="demo-inline-spacing">
                <!-- Basic Pagination -->
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <c:forEach begin="1" end="${counter}" var="i">
                            <c:url value="/admin/accounts" var="pageAction">
                                <c:param name="page" value="${i}" />
                                <c:if test="${not empty kw}">
                                    <c:param name="kw" value="${kw}" />
                                </c:if>
                                <c:if test="${not empty filterStatus}">
                                    <c:param name="status" value="${filterStatus}" />
                                </c:if>
                            </c:url>
                            <li class="page-item" id="pageAccounts${i}">
                                <a class="page-link" href="${pageAction}">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
                <!--/ Basic Pagination -->
            </div>
        </c:if>
    </div>
</div>
<!--/ Basic Bootstrap Table -->

<script>
    var accountsAdmin = document.getElementById("accounts-admin");
    accountsAdmin.className += " active";
    var pageAccounts = document.getElementById("pageAccounts${currentPage}");
    if (pageAccounts != null) {
        pageAccounts.className += " active";
    }
</script>