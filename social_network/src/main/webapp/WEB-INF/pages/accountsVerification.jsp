<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Tài khoản /</span> Duyệt tài khoản</h4>

<!-- Basic Bootstrap Table -->
<div class="card">
    <h5 class="card-header">Danh sách chờ duyệt</h5>
    <div class="table-responsive text-nowrap">
        <table class="table">
            <thead>
                <tr>
                    <th>Mã số sinh viên</th>
                    <th>Gmail</th>
                    <th>Họ</th>
                    <th>Tên</th>
                    <th></th>
                </tr>
            </thead>
            <tbody class="table-border-bottom-0">
                <c:forEach items="${pendingAccounts}" var="p">
                    <tr>
                        <td><i class="fab fa-angular fa-lg text-danger me-3"></i> <strong>${p.user.userStudent.studentIdentical}</strong></td>
                        <td>${p.email}</td>
                        <td>${p.user.lastName}</td>
                        <td>${p.user.firstName}</td>
                        <td>
                            <div class="dropdown" style="display: flex; flex-direction: row;">
                                <div class="alert-success" style="width: 50%; text-align: center;">
                                    <c:url value="/admin/accounts/verification/${p.id}" var="acceptAction">
                                        <c:param name="status" value="ACTIVE" />
                                    </c:url>
                                    <a href="${acceptAction}" class="alert-success"><i class="bx bx-user-check me-1"></i>
                                        Đồng ý</a>
                                </div>
                                <div class="alert-danger" style="width: 50%; text-align: center;">
                                    <c:url value="/admin/accounts/verification/${p.id}" var="rejectAction">
                                        <c:param name="status" value="REJECT" />
                                    </c:url>
                                    <a href="${rejectAction}" class="alert-danger"><i class="bx bx-user-x"></i>
                                        Từ chối</a>
                                </div> 
                            </div>
                        </td>
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
    var menu = document.getElementById("account-verification-menu");
    menu.className += " active";
    var page = document.getElementById("page${currentPage}");
    page.className += " active";
</script>