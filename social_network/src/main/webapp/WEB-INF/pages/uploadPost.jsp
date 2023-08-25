<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Bài đăng /</span> Đăng bài</h4>

<div class="row">
    <div class="col-xl">
        <div class="card mb-4">
        <div class="card-body">
            <div class="btn-group">
            <button
                type="button"
                class="btn btn-primary dropdown-toggle"
                data-bs-toggle="dropdown"
                id="dropdownButton"
            >
            </button>
            <ul class="dropdown-menu" id="dropdownMenu" style="cursor: pointer;">
                <li><div class="dropdown-item" data-value="upload_default_post">Bài đăng mặc định</div></li>
                <li><div class="dropdown-item" data-value="upload_survey_post">Bài đăng khảo sát</div></li>
                <li><div class="dropdown-item" data-value="upload_invitation_post">Bài đăng thư mời thông báo</div></li>
            </ul>
            </div>
            <form id="upload_default_post" style="display: block;">
            <div class="mb-3 pt-5">
                <label class="form-label">Nội dung bài đăng mặc định</label>
                <div class="input-group input-group-merge">
                <span class="input-group-text"
                    ><i class="bx bxs-book-content"></i
                ></span>
                <textarea
                    class="form-control"
                    maxlength="250"
                ></textarea>
                </div>
            </div>
            <div class="form-check form-switch mb-2">
                <input class="form-check-input" type="checkbox" id="flexSwitchCheckDefault" />
                <label class="form-check-label" for="flexSwitchCheckDefault"
                >Cho phép bình luận</label
                >
            </div>
            <div class="mb-3">
                <label class="form-label">Hình ảnh đăng kèm</label>
                <div class="mb-3">
                <input id="imageInput" class="form-control" type="file" accept="image/png, image/jpeg" multiple />
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Đăng bài</button>
            </form>

            <form id="upload_survey_post" style="display: none;">
            <div class="mb-3 pt-5">
                <label class="form-label">Nội dung bài đăng khảo sát</label>
                <div class="input-group input-group-merge">
                <span class="input-group-text"
                    ><i class="bx bxs-book-content"></i
                ></span>
                <textarea
                    class="form-control"
                    maxlength="250"
                ></textarea>
                </div>
            </div>
            </form>
            <form id="upload_invitation_post" style="display: none;">
            <div class="mb-3 pt-5">
                <label class="form-label">Nội dung bài đăng thư mời thông báo</label>
                <div class="input-group input-group-merge">
                <span class="input-group-text"
                    ><i class="bx bxs-book-content"></i
                ></span>
                <textarea
                    class="form-control"
                    maxlength="250"
                ></textarea>
                </div>
            </div>
            </form>
        </div>
        </div>
    </div>
</div>

<script>
    var provider = document.getElementById("upload-post");
    provider.className += " active";
</script>
<script>
    $(document).ready(function (e) {
      $("#dropdownButton").text("Bài đăng mặc định");
      $("#dropdownMenu li div").click(function () {
          var selectedText = $(this).text();
          var selectedValue = $(this).data("value");
          $("[id^='upload_']").hide();
          $("#" + selectedValue).show();
          $("#dropdownButton").text(selectedText);
      });
  });
  </script>