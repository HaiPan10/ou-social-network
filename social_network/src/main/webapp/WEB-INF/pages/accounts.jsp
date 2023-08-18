<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Tài khoản /</span> Quản lý tài khoản</h4>

<div class="card">
    <h5 class="card-header">Danh sách Tài khoản</h5>
    <div class="table-responsive text-nowrap">
        <table class="table">
            <thead>
                <tr>
                    <th>Số thứ tự</th>
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
                        <c:set var="count" value="${count + 1}" scope="page"/>
                        <td><i class="fab fa-angular fa-lg text-danger me-3"></i> <strong>${count}</strong></td>
                        <td>${p.email}</td>
                        <td>${p.user.lastName}</td>
                        <td>${p.user.firstName}</td>
                        <td>
                            ${p.status}
                        </td>
                        <c:url value="/admin/accounts/${p.id}" var="editAction"/>
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
                        <c:url value="/admin/accounts/verification/" var="pageAction">
                            <c:param name="page" value="${i}" />
                        </c:url>
                        <li class="page-item" id="page${i}">
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
    var d = document.getElementById("accounts-admin");
    d.className += " active";
    var page = document.getElementById("page${currentPage}");
    page.className += " active";
</script>