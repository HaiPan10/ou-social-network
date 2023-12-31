<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Bài đăng /</span> Đăng bài</h4>
<!-- <c:if test="${status != null}">
    <div class="alert alert-success">
        ${status}
    </div>
</c:if> -->
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
                    <input type="text" class="form-control" maxlength="100" name="surveyTitle" required></input>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Nội dung bài đăng khảo sát</label>
                    <div class="input-group input-group-merge">
                    <span class="input-group-text"
                        ><i class="bx bx-bar-chart-alt-2"></i
                    ></span>
                    <textarea name="content" class="form-control" maxlength="250"></textarea>
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

            <c:url value="/admin/posts/upload_invitation" var="uploadInvitation" />
            <!-- Upload invitation post form -->
            <form id="upload_invitation_post" style="display: none;">
                <div class="mb-3 pt-5">
                  <label class="form-label">Tên sự kiện</label>
                  <div class="input-group input-group-merge">
                    <span class="input-group-text"><i class='bx bx-calendar-event'></i></span>
                    <input type="text" class="form-control" maxlength="100" name="eventName" required></input>
                  </div>
                </div>
                <div class="mb-3">
                  <label class="form-label">Nội dung bài đăng thư mời thông báo</label>
                  <div class="input-group input-group-merge">
                    <span class="input-group-text"
                      ><i class="bx bx-envelope"></i
                    ></span>
                    <textarea class="form-control" maxlength="250" name="content"></textarea>
                  </div>                          
                </div>
                <div class="mb-3">
                    <label class="col-md-2 col-form-label">Ngày diễn ra</label>
                    <input class="form-control" type="datetime-local" required name="startAt"/>
                  </div>
                <div class="mb-3">
                  <label class="form-label">Người nhận thư mời</label>
                  <div class="input-group input-group-merge">
                    <span class="input-group-text"
                      ><i class="bx bxs-user"></i
                    ></span>
                    <div id="user-box" class="form-control">
                      <div class="user-row tiny-box" id="user_1">
                        <div class="user-column">
                          <i class='bx bx-globe' ></i>
                        </div>
                        <div class="user-column">
                          Mọi người
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="mb-3 d-flex" style="gap: 10px;">
                    <div>
                        <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" id="dropDownParticipant">
                        </button>
                        <ul class="dropdown-menu" id="dropDownParticipantMenu" style="cursor: pointer;">
                          <li><div class="dropdown-item" data-value="everyone">Mọi người</div></li>
                          <li><div class="dropdown-item" data-value="personal">Cá nhân</div></li>
                          <li><div class="dropdown-item" data-value="group">Nhóm</div></li>
                          <li><div class="dropdown-item" data-value="new_group">Tự chỉ định</div></li>
                        </ul>
                    </div>

                    <div id="personalDropDown" class="dropdown" style="display: none;">
                        <button type="button" id="personalDropDownBtn" class="btn btn-outline-secondary dropdown-toggle">Chọn người nhận</button>
                        <div id="personalDropDownMenu" class="dropdown-content">
                        <input type="text" placeholder="Tìm kiếm.." id="personalInput" onkeyup="autoCompletePerson()">
                        <c:forEach items="${accountList}" var="a">
                            <div class="user-row" id="user_${a[0]}">
                                <div class="user-column">
                                    <img src="${a[3]}" alt="">
                                </div>
                                <div class="user-column">
                                    <div class="fullName">${a[2]}</div>
                                    <div class="mail">${a[1]}</div>
                                </div>
                            </div>
                        </c:forEach>
                        </div>
                    </div>

                    <c:url value="/admin/invitation_groups/" var="getUsers" />
                    <div id="groupDropDown" class="dropdown" style="display: none;">
                        <button type="button" id="groupDropDownBtn" class="btn btn-outline-secondary dropdown-toggle">Chọn nhóm</button>
                        <div id="groupDropDownMenu" class="dropdown-content">
                        <input type="text" placeholder="Tìm kiếm.." id="groupInput" onkeyup="autoCompleteGroup()">
                        <c:forEach items="${invitationGroups}" var="g">
                            <div class="user-row" id="group_${g.id}">
                                <div class="user-column">
                                    <div class="groupName">${g.groupName}</div>
                                </div>
                            </div>
                        </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="mb-3" id="save-group-btn" style="display: none;">
                    <div class="form-check form-switch mb-2">
                      <input class="form-check-input" id="save-group" type="checkbox"/>
                      <label class="form-check-label">Lưu nhóm</label>
                    </div>
                    <div id="group-name-input" style="display: none;">
                      <label class="form-label">Tên nhóm</label>
                      <div class="input-group input-group-merge">
                        <span class="input-group-text"><i class='bx bxs-group'></i></span>
                        <input id="groupName" type="text" class="form-control" maxlength="100" name="groupName"></input>
                      </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Đăng bài</button>
              </form>
        </div>
        </div>
    </div>
</div>
<c:url value="/admin/posts" var="posts" />
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
                        <input class="form-check-input" type="checkbox" disabled name="isMandatory_\${currentCount}"/>
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

        $("#dropDownParticipant").text("Mọi người");
        $("#dropDownParticipantMenu li div").click(function () {
            var selectedText = $(this).text();
            $("#user-box").empty();

            if (selectedText === "Cá nhân" || selectedText === "Tự chỉ định") {
                $("#personalDropDown").show();
                $("#groupDropDown").hide();
                if (selectedText === "Tự chỉ định") {
                    $("#save-group-btn").show();
                } else {
                    $("#save-group-btn").hide();
                }
            } else if (selectedText === "Mọi người") {
                $("#user-box").append($(`
                <div class="user-row tiny-box" id="user_1">
                    <div class="user-column">
                    <i class='bx bx-globe' ></i>
                    </div>
                    <div class="user-column">
                    Mọi người
                    </div>
                </div>
                `))
                $("#personalDropDown").hide();
                $("#groupDropDown").hide();
                $("#save-group-btn").hide();
            } else {
                $("#groupDropDown").show();
                $("#personalDropDown").hide();
                $("#save-group-btn").hide();
            }
            $("#dropDownParticipant").text(selectedText);
        });
        $("#dropDownUserPersonal").text("Chọn người nhận");

        $("#personalDropDownBtn").click(function() {
            document.getElementById("personalDropDownMenu").classList.toggle("show");
        })

        $("#groupDropDownBtn").click(function() {
            document.getElementById("groupDropDownMenu").classList.toggle("show");
        })

        showLessDivPerson();
        showLessDivGroup();

        $(".user-row").click(function() {
            if ($("#dropDownParticipant").text() === "Cá nhân") {
                document.getElementById("personalDropDownMenu").classList.toggle("show");
                $("#user-box").empty();
            }
            if ($("#dropDownParticipant").text() === "Cá nhân" || $("#dropDownParticipant").text() === "Tự chỉ định") {
                let htmlCode = $(this)[0].outerHTML.replace('class="user-row"', 'class="user-row tiny-box"');
                var $id = $(htmlCode).attr('id');
                let childDivs = $("#user-box").find(".user-row");
                let idList = [];
                childDivs.each(function() {
                    idList.push($(this).attr("id"));
                });
                if (!idList.includes($id)) {
                    $("#user-box").append(htmlCode);
                }
            } else if ($("#dropDownParticipant").text() === "Nhóm") {
                let groupId = $(this).attr('id').split("_")[1];
                let url = "${getUsers}" + groupId;
                getUsers(url)
            }
        })

        $(document).on("click", function(event) {
          var elementToCheckPerson = $("#personalDropDownMenu");
          if (elementToCheckPerson.css("display") === "block") {
            var toggleButtonPerson = $("#personalDropDownBtn");
            if (!elementToCheckPerson.is(event.target) && elementToCheckPerson.has(event.target).length === 0 && !toggleButtonPerson.is(event.target)) {
                elementToCheckPerson.removeClass("show")
            }
          }
          var elementToCheckGroup = $("#groupDropDownMenu");
          if (elementToCheckGroup.css("display") === "block") {
            var toggleButtonGroup = $("#groupDropDownBtn");
            if (!elementToCheckGroup.is(event.target) && elementToCheckGroup.has(event.target).length === 0 && !toggleButtonGroup.is(event.target)) {
                elementToCheckGroup.removeClass("show")
            }
          }
        });

        $("#save-group").on("change", function() {
          let childDivs = $("#user-box").find(".user-row");
          let idList = [];
          childDivs.each(function() {
            idList.push($(this).attr("id"));
          });
          if (idList.length > 1) {
            $(this).toggle(!$("#save-group").checked)
          } else {
            $(this).prop("checked", false);
            alert("Phải có từ 2 người dùng!");
          }
          if ($("#save-group").prop("checked")) {
            $("#group-name-input").show();
            $("#groupName").attr("required", "required");
          } else {
            $("#group-name-input").hide();
            $("#groupName").removeAttr("required");
          }
        })

        $("#upload_invitation_post").submit(function() {
          event.preventDefault();
          let form = $("#upload_invitation_post")[0];
          let formData = new FormData(form);
          let post = {};
          post.content = formData.get("content");
          post.isActiveComment = false;
          let postInvitation = {};
          postInvitation.eventName = formData.get("eventName");
          postInvitation.startAt = convertDate(formData.get("startAt"));
          if ($("#save-group").prop("checked")) {
            let groupId = {};
            groupId.groupName = formData.get("groupName");
            postInvitation.groupId = groupId;
          }
          if ($("#dropDownParticipant").text() !== "Mọi người") {
            let postInvitationUsers = [];
            let childDivs = $("#user-box").find(".user-row");
            childDivs.each(function() {
              let object = {};
              object.userId = $(this).attr("id").split("_")[1];
              postInvitationUsers.push(object);
            });
            postInvitation.postInvitationUsers = postInvitationUsers;
          }
          post.postInvitation = postInvitation;
          let json = JSON.stringify(post);
        //   console.log(JSON.stringify(post, null, 4));
          uploadInvitationPost(json);
        })
    });

    async function uploadInvitationPost(json){
        let url = "${uploadInvitation}";
        let response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: json
        })

        // let data = await response.json();

        if(response.ok){
            // location.replace("${upload}/?status=success")
            location.replace("${posts}/?status=success")
        } else {
            const errorText = await response.text();
            alert(errorText);
        }
    }

    async function uploadSurveyPost(json){
        let url = "${uploadSurvey}";
        let response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: json
        })

        // let data = await response.json();

        if(response.ok){
            // location.replace("${upload}/?status=success")
            location.replace("${posts}/?status=success")
        } else {
            const errorText = await response.text();
            alert(errorText);
        }
    }

    async function getUsers(url){
        let response = await fetch(url, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
        })

        if(response.ok){
            let data = await response.json();
            $("#user-box").empty();
            data.forEach(function (item, index) {
                $("#user-box").append($(`
                    <div class="user-row" id="user_\${item[0]}">
                        <div class="user-column">
                            <img src="\${item[3]}" alt="">
                        </div>
                        <div class="user-column">
                            <div class="fullName">\${item[2]}</div>
                            <div class="mail">\${item[1]}</div>
                        </div>
                    </div>
                `))
            });
        } else {
            const errorText = await response.text();
            alert(errorText);
        }
    }

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

    function convertDate(inputDate) {
        const originalDate = new Date(inputDate);
        const day = originalDate.getDate().toString().padStart(2, '0');
        const month = (originalDate.getMonth() + 1).toString().padStart(2, '0');
        const year = originalDate.getFullYear();
        const hours = originalDate.getHours().toString().padStart(2, '0');
        const minutes = originalDate.getMinutes().toString().padStart(2, '0');
        const seconds = originalDate.getSeconds().toString().padStart(2, '0');
        return `\${day}-\${month}-\${year} \${hours}:\${minutes}:\${seconds}`;
    }

    function showLessDivPerson() {
        var divs = document.querySelectorAll("#personalDropDownMenu .user-row:not([style*='display: none'])");
        if (divs.length > 5) {
            for (var i = 0; i < divs.length; i++) {
            if (i < 5) {
                divs[i].style.display = "";
            } else {
                divs[i].style.display = "none";
            }
        }
        } else {
        }
    }

    function showLessDivGroup() {
        var divs = document.querySelectorAll("#groupDropDownMenu .user-row:not([style*='display: none'])");
        if (divs.length > 5) {
            for (var i = 0; i < divs.length; i++) {
            if (i < 5) {
                divs[i].style.display = "";
            } else {
                divs[i].style.display = "none";
            }
        }
        } else {
        }
    }

    function autoCompletePerson() {
        var input, filter, ul, li, a, i;
        input = document.getElementById("personalInput");
        filter = input.value.toUpperCase();
        div = document.getElementById("personalDropDownMenu");
        a = div.getElementsByClassName("user-row");
        for (i = 0; i < a.length; i++) {
          txtValue = a[i].textContent || a[i].innerText;
          if (txtValue.toUpperCase().indexOf(filter) > -1) {
            a[i].style.display = "";
          } else {
            a[i].style.display = "none";
          }
        }
        showLessDivPerson();
    }

    function autoCompleteGroup() {
        var input, filter, ul, li, a, i;
        input = document.getElementById("groupInput");
        filter = input.value.toUpperCase();
        div = document.getElementById("groupDropDownMenu");
        a = div.getElementsByClassName("user-row");
        for (i = 0; i < a.length; i++) {
          txtValue = a[i].textContent || a[i].innerText;
          if (txtValue.toUpperCase().indexOf(filter) > -1) {
            a[i].style.display = "";
          } else {
            a[i].style.display = "none";
          }
        }
        showLessDivGroup();
    }
  </script>