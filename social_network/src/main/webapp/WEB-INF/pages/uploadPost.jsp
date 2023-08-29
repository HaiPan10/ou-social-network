<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Bài đăng /</span> Đăng bài</h4>
<c:if test="${status != null}">
    <div class="alert alert-success">
        ${status}
    </div>
</c:if>
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
            <!-- Upload default post form -->
            <c:url value="/admin/posts/upload" var="upload" />
            <form:form action="${upload}" modelAttribute="post" method="post" enctype="multipart/form-data" id="upload_default_post" style="display: block;">
                <div class="mb-3">
                    <form:errors path="*" cssClass="text-danger" element="div"/>
                </div>
                <div class="mb-3 pt-5">
                    <label class="form-label">Nội dung bài đăng mặc định</label>
                    <div class="input-group input-group-merge">
                    <span class="input-group-text"
                        ><i class="bx bxs-book-content"></i
                    ></span>
                    <form:textarea class="form-control" maxlength="250" name="content" path="content"></form:textarea>
                    </div>
                </div>
                <div class="form-check form-switch mb-2">
                    <form:checkbox value="true" class="form-check-input" id="flexSwitchCheckDefault" name="isActiveComment" path="isActiveComment" checked="true"/>
                    <label class="form-check-label" for="flexSwitchCheckDefault">Cho phép bình luận</label>
                </div>
                <div class="mb-3">
                    <label class="form-label">Hình ảnh đăng kèm</label>
                    <div class="mb-3">
                        <input id="imageInput" class="form-control" type="file" name="images" accept="image/png, image/jpeg" multiple />
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Đăng bài</button>
            </form:form>

            <!-- Upload survey post form -->
            <c:url value="/admin/posts/upload_survey" var="uploadSurvey" />
            <form id="upload_survey_post" style="display: none;">
                <!-- <div class="mb-3">
                    <div class="text" id="error"></div>
                </div> -->
                <div class="mb-3 pt-5">
                    <label class="form-label">Tiêu đề</label>
                    <div class="input-group input-group-merge">
                    <span class="input-group-text"
                        ><i class="bx bx-heading"></i
                    ></span>
                    <input
                        type="text" class="form-control" maxlength="100" name="surveyTitle" required
                    ></input>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Nội dung bài đăng khảo sát</label>
                    <div class="input-group input-group-merge">
                    <span class="input-group-text"
                        ><i class="bx bxs-book-content"></i
                    ></span>
                    <textarea name="content"
                        class="form-control"
                        maxlength="250"
                        required
                    ></textarea>
                    </div>
                </div>
                <div class="form-check form-switch mb-2">
                    <input class="form-check-input" name="isActiveComment" type="checkbox" id="isActiveComment" checked/>
                    <label class="form-check-label"
                    >Cho phép bình luận</label
                    >
                </div>
                <div class="mb-3" id="question-container">
                </div>
                <div class="btn-group">
                    <button
                    type="button"
                    class="btn btn-outline-secondary dropdown-toggle"
                    data-bs-toggle="dropdown"
                    id="dropdownQuestion"
                    >
                    </button>
                    <ul class="dropdown-menu" id="dropdownQuestionType" style="cursor: pointer;">
                    <li><div class="dropdown-item" data-value="text">Đoạn</div></li>
                    <li><div class="dropdown-item" data-value="multiple">Trắc nghiệm</div></li>
                    <li><div class="dropdown-item" data-value="checkbox">Hộp kiểm</div></li>
                    </ul>
                </div>
                <button id="add_question" class="btn btn-secondary" type="button">Thêm câu hỏi</button>
                <button type="submit" class="btn btn-primary" id="surveySubmit">Đăng bài</button>
            </form>

            <!-- Upload invitation post form -->
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
        $("#dropdownQuestion").text("Đoạn");
        $("#dropdownQuestionType li div").click(function () {
            var selectedText = $(this).text();
            $("#dropdownQuestion").text(selectedText);
        });

        var count=0;
        $("#add_question").click(function () {
            const currentCount = ++count;
            var questionType = $("#dropdownQuestion").text()
            var newDiv;
            if (questionType === "Đoạn") {
            newDiv = $(`
                <div id="question_\${currentCount}" class="mb-3 question-wrapper">
                <input type="hidden" name="questionTypeId_\${currentCount}" value="2" />
                <div class="question-title">
                    <div style="display: flex; gap: 15px;">
                    <label class="form-label">Câu hỏi đoạn</label>
                    <div class="form-check form-switch">
                        <input class="form-check-input" type="checkbox" name="isMandatory_\${currentCount}"/>
                        <label class="form-check-label"
                        >Bắt buộc</label
                        >
                    </div>
                    </div>
                    
                    <button class="btn btn-danger p-0 px-2" type="button" id="delete_\${currentCount}"><i class='bx bxs-trash'></i>Xóa</button>
                </div>
                <div class="mb-3">
                    <div class="input-group input-group-merge">
                    <span class="input-group-text"><i class="bx bx-question-mark"></i></span>
                    <input type="text" name="questionText_\${currentCount}" class="form-control" maxlength="100" placeholder="Nhập câu hỏi" required></input>
                    </div>
                </div>
                </div>`
            );
            } else if (questionType === "Trắc nghiệm") {
            newDiv = $(`
                <div id="question_\${currentCount}" class="mb-3 question-wrapper">
                <input type="hidden" name="questionTypeId_\${currentCount}" value="1" />
                <div class="question-title">
                    <div style="display: flex; gap: 15px;">
                    <label class="form-label">Câu hỏi trắc nghiệm</label>
                    <div class="form-check form-switch">
                        <input class="form-check-input" type="checkbox" name="isMandatory_\${currentCount}"/>
                        <label class="form-check-label" 
                        >Bắt buộc</label
                        >
                    </div>
                    </div>
                    <button class="btn btn-danger p-0 px-2" id="delete_\${currentCount}" type="button"><i class='bx bxs-trash' ></i>Xóa</button>
                </div>
                <div class="mb-3">
                    <div class="input-group input-group-merge">
                    <span class="input-group-text"><i class='bx bx-circle' ></i></i></span>
                    <input type="text" name="questionText_\${currentCount}" class="form-control" maxlength="100" placeholder="Nhập câu hỏi" required></input>
                    </div>
                </div>
                <div id="option-wrapper_\${currentCount}" class="mb-3">
                    <div id="option_\${currentCount}_1" class="d-flex pb-2" style="gap: 10px">
                    <button class="btn btn-danger p-0 px-2" id="delete_option_\${currentCount}_1" type="button"><i class='bx bxs-trash' ></i></button>
                    <div class="input-group input-group-merge">
                        <input type="text" name="value_\${currentCount}_1" class="form-control" maxlength="100" placeholder="Nhập tùy chọn" required></input>
                    </div>
                    </div>
                </div>
                <button class="btn btn-success" type="button" id="addMultiple_\${currentCount}">Thêm tùy chọn</button>
                </div>
            `)
            } else {
            newDiv = $(`
            <div id="question_\${currentCount}" class="mb-3 question-wrapper">
                <input type="hidden" name="questionTypeId_\${currentCount}" value="3" />
                <div class="question-title">
                    <div style="display: flex; gap: 15px;">
                    <label class="form-label">Câu hỏi hộp kiểm</label>
                    <div class="form-check form-switch">
                        <input class="form-check-input" type="checkbox" name="isMandatory_\${currentCount}"/>
                        <label class="form-check-label" 
                        >Bắt buộc</label
                        >
                    </div>
                    </div>
                    <button class="btn btn-danger p-0 px-2" id="delete_\${currentCount}" type="button"><i class='bx bxs-trash' ></i>Xóa</button>
                </div>
                <div class="mb-3">
                    <div class="input-group input-group-merge">
                    <span class="input-group-text"><i class='bx bx-square-rounded'></i></i></span>
                    <input name="questionText_\${currentCount}" type="text" class="form-control" maxlength="100" placeholder="Nhập câu hỏi" required></input>
                    </div>
                </div>
                <div id="option-wrapper_\${currentCount}" class="mb-3">
                    <div id="option_\${currentCount}_1" class="d-flex pb-2" style="gap: 10px">
                    <button class="btn btn-danger p-0 px-2" id="delete_option_\${currentCount}_1" type="button"><i class='bx bxs-trash' ></i></button>
                    <div class="input-group input-group-merge">
                        <input type="text" name="value_\${currentCount}_1" class="form-control" maxlength="100" placeholder="Nhập tùy chọn" required></input>
                    </div>
                    </div>
                </div>
                <button class="btn btn-success" type="button" id="addMultiple_\${currentCount}">Thêm tùy chọn</button>
                </div>
            `)
            }

            $("#question-container").append(newDiv);
            newDiv.slideDown();
            document.getElementById(`delete_\${currentCount}`).addEventListener("click", function() {
            deleteQuestion(`delete_\${currentCount}`);
            });
            if (questionType !== "Đoạn") {
            document.getElementById(`addMultiple_\${currentCount}`).addEventListener("click", function() {
                addOption(`addMultiple_\${currentCount}`);
            });
            document.getElementById(`delete_option_\${currentCount}_1`).addEventListener("click", function() {
                deleteOption(`delete_option_\${currentCount}_1`);
            });
            }
        });

        function deleteQuestion(buttonId) {
          let currentPos = buttonId.split("_")[1];
          $(`#question_\${currentPos}`).slideUp(function() {
            $(this).remove();
          })
        }

        function addOption(buttonId) {
            let currentPos = buttonId.split("_")[1];
            let nextId = $(`#option-wrapper_\${currentPos}`).children().length + 1;
            var newOption = $(`
                <div id="option_\${currentPos}_\${nextId}" class="d-flex pb-2" style="gap: 10px; display: none">
                <button class="btn btn-danger p-0 px-2" id="delete_option_\${currentPos}_\${nextId}" type="button"><i class='bx bxs-trash' ></i></button>
                <div class="input-group input-group-merge">
                    <input type="text" name="value_\${currentPos}_\${nextId}" class="form-control" maxlength="100" placeholder="Nhập tùy chọn" required></input>
                </div>
                </div>
            `)
            $(`#option-wrapper_\${currentPos}`).append(newOption);
            newOption.slideDown();
            document.getElementById(`delete_option_\${currentPos}_\${nextId}`).addEventListener("click", function() {
            deleteOption(`delete_option_\${currentPos}_\${nextId}`);
            });
        }

        function deleteOption(buttonId) {
            let currentPos = buttonId.split("_")[2];
            let currentOpt = buttonId.split("_")[3];
            if ($(`#option-wrapper_\${currentPos}`).children().length > 1) {
            $(`#option_\${currentPos}_\${currentOpt}`).slideUp(function() {
                $(this).remove();
            })
            }
        }

        $("#upload_survey_post").submit(function(event) {
          event.preventDefault();
          let form = $("#upload_survey_post")[0];
          let formData = new FormData(form);
          let post = {};
          post.content = formData.get("content");
          post.isActiveComment = (formData.get("isActiveComment") ? true: false);
          let postSurvey = {};
          postSurvey.surveyTitle = formData.get("surveyTitle");
          let questions = [];
          let questionOrder = 1;
          $("#question-container").children().each(function() {
            let divId = $(this).attr("id").split("_")[1];
            let questionObject = {};
            let questionTypeId = {}
            questionTypeId.id = parseInt(formData.get(`questionTypeId_\${divId}`));
            questionObject.questionTypeId = questionTypeId;
            questionObject.questionText = formData.get(`questionText_\${divId}`);
            questionObject.isMandatory = (formData.get(`isMandatory_\${divId}`) ? true: false);
            questionObject.questionOrder = questionOrder++;
            if (questionTypeId.id !== 2) {
              let questionOptions = [];
              let optionOrder = 1;
              $(`#option-wrapper_\${divId}`).children().each(function() {
                let optionId = $(this).attr("id").split("_")[2];
                let optionObject = {};
                optionObject.value = formData.get(`value_\${divId}_\${optionId}`);
                optionObject.questionOrder = optionOrder++;
                questionOptions.push(optionObject);
              })
              questionObject.questionOptions = questionOptions;
            }
            questionObject.questionOpions 
            questions.push(questionObject);
          });
          
          postSurvey.questions = questions;
          post.postSurvey = postSurvey;
          console.log(JSON.stringify(post, null, 4))
          let json = JSON.stringify(post);
          uploadSurveyPost(json);
        });

        async function uploadSurveyPost(json){
            let url = "${uploadSurvey}";
            let response = await fetch(url, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: json
            })

            let data = await response.json();
            console.log(data);

            if(reponse.ok){

            } else {
                alert("Lỗi");
            }
        }
    });
  </script>