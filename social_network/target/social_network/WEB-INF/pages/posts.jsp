<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Bài đăng /</span> Quản lý bài đăng</h4>
<c:if test="${status != null}">
    <div class="alert alert-success">
        ${status}
    </div>
</c:if>
<div class="card">
    <h5 class="card-header">Danh sách bài đăng</h5>
    <div class="table-responsive text-nowrap">
        <table class="table">
            <thead>
                <tr>
                    <!-- <th>Số thứ tự</th> -->
                    <th>Id tác giả</th>
                    <th>Tên tác giả</th>
                    <th style="max-width: 200px; text-overflow: ellipsis; overflow: hidden;">Nội dung</th>
                    <th>Ngày tạo</th>
                    <th>Loại bài đăng</th>
                    <th></th>
                </tr>
            </thead>
            <!-- <c:set var="count" value="0" scope="page" /> -->
            <tbody class="table-border-bottom-0">
                <c:forEach items="${posts}" var="p">
                    <tr>
                        <!-- <c:set var="count" value="${count + 1}" scope="page"/>
                        <td><i class="fab fa-angular fa-lg text-danger me-3"></i> <strong>${count}</strong></td> -->
                        <td>${p.userId.id}</td>
                        <td>${p.userId.lastName} ${p.userId.firstName}</td>
                        <td style="max-width: 200px; text-overflow: ellipsis; overflow: hidden;">${p.content}</td>
                        <td>${p.createdAt}</td>
                        <td>
                            <c:choose>
                                <c:when test="${p.postSurvey == null && p.postInvitation != null}">
                                    <i class='bx bxs-envelope' ></i> Bài đăng thư mời thông báo
                                </c:when>
                                <c:when test="${p.postSurvey != null && p.postInvitation == null}">
                                    <i class='bx bx-bar-chart-square'></i> Bài đăng thống kê
                                </c:when>
                                <c:otherwise>
                                    <i class='bx bxs-dock-right' ></i> Bài đăng thông thường
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <c:url value="/admin/posts/${p.id}" var="editAction"/>
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
                        <c:url value="/admin/posts" var="pageAction">
                            <c:param name="page" value="${i}" />
                            <c:if test="${not empty kw}">
                                <c:param name="kw" value="${kw}" />
                            </c:if>
                        </c:url>
                        <li class="page-item" id="pagePosts${i}">
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
    var postsAdmin = document.getElementById("posts-admin");
    postsAdmin.className += " active";
    var pagePosts = document.getElementById("pagePosts${currentPage}");
    pagePosts.className += " active";
</script>