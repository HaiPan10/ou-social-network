<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Quản lý bài đăng /</span> Thông tin chi tiết</h4>

<div id="wrapper" class="is-collapse">
    <div class="">
        <div class="mcontainer">
            <div class="lg:w-1/2 space-y-7">
                <div class="card lg:mx-0 uk-animation-slide-bottom-small">
                    <div class="flex justify-between items-center lg:p-4 p-2.5">
                        <div class="flex flex-1 items-center space-x-4">
                            <a href="#">
                                <img src=${post.userId.avatar} class="bg-gray-200 border border-white rounded-full w-10 h-10" style="object-fit: cover;">
                            </a>
                            <div class="flex-1 font-semibold">
                                <a href="#" class="text-black dark:text-gray-100"> ${post.userId.lastName} ${post.userId.firstName} </a>
                                <div> ${post.createdAt} </div>
                            </div>
                        </div>
                    </div>
        
                    <div class="lg:p-4 p-2.5 pt-0 pb-0 dark:border-gray-700">
                        ${post.content}
                    </div>
        
                    <!-- <c:forEach items="${post.imageInPostList}" var="i">
                        <div>${i.id}</div><br>
                        <div>${i.imageUrl}</div><br>
                    </c:forEach> -->
                    <div class="img-container">
                        <div id="gallery"></div>
                        <script>
                            var images = [
                                <c:forEach items="${post.imageInPostList}" var="i" varStatus="loop">
                                    '${i.imageUrl}'${!loop.last ? ',' : ''}
                                </c:forEach>
                            ];
                            $(document).ready(function() {
                                $.noConflict();
                                $('#gallery').imagesGrid({
                                    images: images
                                });
                            })                        
                        </script>
                    </div>

                    <div class="btn-container">
                        <c:url value="/admin/posts/delete/${post.id}" var="deleteAction"/>
                        <c:url value="/admin/posts" var="redirectAction"/>
                        <button onclick="deletePost(`${deleteAction}`, `${redirectAction}`)" class="btn-delete">Xóa</button>
                    </div>
                </div>
            </div>
        </div>
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
<!-- <div>${post.id}</div><br>
<div>${post.content}</div><br>
<div>${post.createdAt}</div><br>
<div>${post.updatedAt}</div><br>
<c:forEach items="${post.imageInPostList}" var="i">
    <div>${i.id}</div><br>
    <div>${i.imageUrl}</div><br>
</c:forEach>
<div>${post.userId.id}</div><br>
<div>${post.userId.lastName} ${post.userId.firstName}</div><br> -->


