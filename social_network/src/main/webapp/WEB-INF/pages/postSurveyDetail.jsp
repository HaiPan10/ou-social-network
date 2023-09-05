<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Quản lý bài đăng /</span> Thông tin chi tiết</h4>

<div class="card">
    <h1 class="text-center">${post.postSurvey.surveyTitle}</h1>
    <h5 class="text-center">${post.content}</h5>
    <div class="text-end">
        <c:url value="/admin/posts/delete/${post.id}" var="deleteAction"/>
        <c:url value="/admin/posts" var="redirectAction"/>
        <button onclick="deletePost(`${deleteAction}`, `${redirectAction}`)" class="btn-delete"><i class='bx bx-trash' style="color: red; width: 100%;"></i></button>
    </div>
    <div class="table-responsive text-nowrap">
        <table class="table">
            <thead>
                <tr>
                    <!-- <th>Số thứ tự</th> -->
                    <th>Id câu hỏi</th>
                    <th style="max-width: 200px; text-overflow: ellipsis; overflow: hidden;">Nội dung câu hỏi</th>
                    <th>Bắt buộc</th>
                    <th></th>
                </tr>
            </thead>
            <!-- <c:set var="count" value="0" scope="page" /> -->
            <tbody class="table-border-bottom-0">
                <c:forEach items="${post.postSurvey.questions}" var="q">
                    <tr>
                        <td>${q.id}</td>
                        <td style="max-width: 200px; text-overflow: ellipsis; overflow: hidden;">${q.questionText}</td>
                        <td>${q.isMandatory}</td>
                        <c:url value="/admin/posts/${p.id}" var="statsAction"/>
                        <th><a href="${statsAction}">Xem thống kê</a></th>
                    </tr>
                </c:forEach>
            </tbody>  
        </table>
    </div>
</div>

<script>
    function deletePost(path, redirectPath) {
    if (confirm("Bạn chắc chắn xóa không?") === true) {
        fetch(path, {
            method: "DELETE"
        }).then(res => {
            if (res.status === 204) {
                console.log("REDIRECTING TO " + redirectPath);
                window.location.replace(redirectPath);
            }
            else
                alert("Something wrong!!!");
        });
    }
}
</script>