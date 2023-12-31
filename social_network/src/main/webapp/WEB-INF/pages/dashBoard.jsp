<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="row">
    <div class="content-wrapper">
        <div class="container-xxl flex-grow-1 container-p-y">
            <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Bảng điều khiển /</span> Thống kê</h4>

            <div class="card mb-4">
                <h5 class="card-header">Thống kê số người dùng</h5>
                <div class="card-body">
                    <div class="row gy-3">
                        <div class="col-md-8">
                            <div>
                                <div>
                                    <canvas id="chartUser"></canvas>
                                </div>
                            </div>
                        </div>
                        <div class="col-md">
                            <div class="form-check form-check-inline mt-3">
                                <button type="button" class="btn btn-secondary dropdown-toggle"
                                    data-bs-toggle="dropdown" id="dropDownOption">
                                </button>
                                <ul class="dropdown-menu" id="dropDownOptionMenu" style="cursor: pointer;">
                                    <li>
                                        <div id="itemYear" class="dropdown-item" data-value="year">Năm</div>
                                    </li>
                                    <li>
                                        <div class="dropdown-item" data-value="month">Tháng</div>
                                    </li>
                                    <li>
                                        <div class="dropdown-item" data-value="quarter">Quý</div>
                                    </li>
                                </ul>
                            </div>
                            <div class="form-check form-check-inline" id="containerDropDownYear">
                                <button type="button" class="btn btn-secondary dropdown-toggle"
                                    data-bs-toggle="dropdown" id="dropDownYear">
                                </button>
                                <ul class="dropdown-menu" id="dropDownYearMenu" style="cursor: pointer;">

                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card mb-4">
                <h5 class="card-header">Thống kê số bài đăng</h5>
                <div class="card-body">
                    <div class="row gy-3">
                        <div class="col-md-8">
                            <div>
                                <div>
                                    <canvas id="chartPost"></canvas>
                                </div>
                            </div>
                        </div>
                        <div class="col-md">
                            <div class="mb-5">
                                <div class="form-check form-check-inline">
                                    <button type="button" class="btn btn-secondary dropdown-toggle"
                                        data-bs-toggle="dropdown" id="dropDownPostOption">
                                    </button>
                                    <ul class="dropdown-menu" id="dropDownPostOptionMenu" style="cursor: pointer;">
                                        <li>
                                            <div id="itemYearPost" class="dropdown-item" data-value="postYear">Năm</div>
                                        </li>
                                        <li>
                                            <div class="dropdown-item" data-value="postMonth">Tháng</div>
                                        </li>
                                        <li>
                                            <div class="dropdown-item" data-value="postQuarter">Quý</div>
                                        </li>
                                    </ul>
                                </div>
                                <div class="form-check form-check-inline" id="containerDropDownPostYear">
                                    <button type="button" class="btn btn-secondary dropdown-toggle"
                                        data-bs-toggle="dropdown" id="dropDownPostYear">
                                    </button>
                                    <ul class="dropdown-menu" id="dropDownPostYearMenu" style="cursor: pointer;">

                                    </ul>
                                </div>
                            </div>
                            <div class="m-5">
                                <div class="form-check mt-3">
                                    <input class="form-check-input" checked type="checkbox" id="totalPost"
                                        value="post" />
                                    <label class="form-check-label" for="totalPost">Bài đăng thường</label>
                                </div>
                                <div class="form-check mt-3">
                                    <input class="form-check-input" checked type="checkbox" id="totalPostSurvey"
                                        value="postSurvey" />
                                    <label class="form-check-label" for="totalPostSurvey">Bài đăng khảo sát</label>
                                </div>
                                <div class="form-check mt-3">
                                    <input class="form-check-input" checked type="checkbox" id="totalPostInvitation"
                                        value="postInvitation" />
                                    <label class="form-check-label" for="totalPostInvitation">Bài đăng thư mời</label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<c:url value="/admin/statistics/users" var="statUserAction" />
<c:url value="/admin/stat" var="statAction" />
<c:url value="/admin/stat/post" var="statPostAction" />
<c:url value="/admin/stat/post_survey" var="statSurveyAction" />
<c:url value="/admin/stat/post_invitation" var="statInvitationAction" />
<script>
    var d = document.getElementById("dash-board");
    d.className += " active";

    var months = [
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    ];
    var quarter = ["Quý 1", "Quý 2", "Quý 3", "Quý 4"];

    var years = [];

    var ctx = document.getElementById('chartUser');
    var ctxPost = document.getElementById("chartPost");
    // var baseUrl = "http://localhost:8080/social_network";
    // var endpoint = "/api/test/beans";
    // var statisticsEndpoint = "/api/test/statistics";
    var statApi = "${statAction}"
    var statUserApi = "${statUserAction}";
    var statPostApi = "${statPostAction}";
    var statSurveyApi = "${statSurveyAction}";
    var statInvitationApi = "${statInvitationAction}";

    var configs = {
        type: 'bar',
        data: {},
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            },
            plugins: {
                legend: {
                    display: true
                },
                datalabels: {
                    formatter: function (value, index, values) {
                        if (value > 0) {
                            value = value.toString();
                            return value;
                        } else {
                            value = "";
                            return value;
                        }
                    }
                }
            }
        },
        plugins: [ChartDataLabels]
    }

    var configsCharPost = {
        type: 'bar',
        data: {},
        options: {
            scales: {
                x: {
                    stacked: true
                },
                y: {
                    stacked: true,
                    beginAtZero: false
                }
            },
            plugins: {
                legend: {
                    display: true
                },
                datalabels: {
                    formatter: function (value, index, values) {
                        if (value > 0) {
                            value = value.toString();
                            return value;
                        } else {
                            value = "";
                            return value;
                        }
                    }
                }
            },
            responsive: true
        },
        plugins: [ChartDataLabels]
    }

    $(document).ready(function (e) {
        // $("#containerDropDownYear").hide();
        $("#dropDownOption").text("Tháng");
        $("#dropDownOptionMenu li div").click(function () {
            var selectedText = $(this).text();
            if (selectedText === "Tháng") {
                $("#containerDropDownYear").show();
                var year = $("#dropDownYear").text();
                var requestParams = new URLSearchParams();
                requestParams.append("byMonth", "true");
                requestParams.append("year", year)
                stat(months, requestParams, statUserApi);

            } else if (selectedText === "Quý") {
                $("#containerDropDownYear").show();
                var year = $("#dropDownYear").text();
                var requestParams = new URLSearchParams();
                requestParams.append("byQuarter", "true");
                requestParams.append("year", year)
                stat(quarter, requestParams, statUserApi);
            } else if (selectedText === "Năm") {
                $("#containerDropDownYear").hide();
                stat(years, null, statUserApi);
            }
            $("#dropDownOption").text(selectedText);
        });

        $("#dropDownYearMenu li div").click(function () {
            var selectedText = $(this).text();
            var requestParams = new URLSearchParams();
            requestParams.append("year", selectedText);
            var option = $("#dropDownOption").text();
            if (option === "Tháng") {
                requestParams.append("byMonth", "true");
                stat(months, requestParams, statUserApi);
            } else if (option === "Quý") {
                requestParams.append("byQuarter", "true");
                stat(quarter, requestParams, statUserApi);
            }

            $("#dropDownYear").text(selectedText);
        });

        // $("#containerDropDownPostYear").hide();
        $("#dropDownPostOption").text("Tháng");
        $("#dropDownPostOptionMenu li div").click(function () {
            var selectedText = $(this).text();
            if (selectedText === "Tháng") {
                $("#containerDropDownPostYear").show();
                var year = $("#dropDownPostYear").text();
                var requestParams = new URLSearchParams();
                requestParams.append("byMonth", "true");
                requestParams.append("year", year)
                checkPostType(months, requestParams);
            } else if (selectedText === "Quý") {
                $("#containerDropDownPostYear").show();
                var year = $("#dropDownPostYear").text();
                var requestParams = new URLSearchParams();
                requestParams.append("byQuarter", "true");
                requestParams.append("year", year)
                checkPostType(quarter, requestParams);
            } else if (selectedText === "Năm") {
                $("#containerDropDownPostYear").hide();
                checkPostType(years, null);
            }
            $("#dropDownPostOption").text(selectedText);
        });

        $("#dropDownPostYearMenu li div").click(function () {
            var selectedText = $(this).text();
            var requestParams = new URLSearchParams();
            requestParams.append("year", selectedText);
            var option = $("#dropDownPostOption").text();
            if (option === "Tháng") {
                requestParams.append("byMonth", "true");
                checkPostType(months, requestParams);
            } else if (option === "Quý") {
                requestParams.append("byQuarter", "true");
                checkPostType(quarter, requestParams);
            }

            $("#dropDownPostYear").text(selectedText);
        });
    });

    async function stat(labels, requestParams, api) {
        var data = {
            labels: labels,
            datasets: []
        }
        var datasetUser = {
            label: 'Số người dùng',
            data: labels.length > 0 ? Array.from({ length: labels.length }, () => 0) : [],
            borderWidth: 1,
            backgroundColor: "#6699FF",
            datalabels: {
                color: 'black'
            }
        }

        await fetch(`\${api}\${requestParams != null ? '?' + requestParams.toString() : ''}`)
            .then(res => {
                if (res.ok) {
                    return res.json();
                }
                return res.text().then(text => { throw new Error(text) })

            })
            .then(d => {
                var counter = 0;
                d.forEach(element => {
                    if (requestParams != null) {
                        datasetUser.data[element[0] - 1] = element[1];
                    } else {
                        datasetUser.data[counter] = element[1];
                        counter++;
                    }
                })
            })
            .catch(err => console.log(err));

        data.datasets = [datasetUser];
        configs.data = data;
        drawChart(labels, ctx, configs);
    }

    async function statPost(labels, requestParams, api, postType) {
        var data = null;

        if (Object.keys(configsCharPost.data).length === 0) {
            data = {
                labels: labels,
                datasets: []
            };
        } else {
            data = configsCharPost.data;
        }

        var datasets = data.datasets;
        var dataset = null;

        if (postType === "post") {
            dataset = createDataset('Bài đăng thường', labels, '#FFCC66', 1);
            // posts[0] = true;
        } else if (postType === "postSurvey") {
            dataset = createDataset('Bài đăng khảo sát', labels, '#FFFF66', 2);
            // posts[1] = true;
        } else if (postType === "postInvitation") {
            dataset = createDataset('Bài đăng thư mời', labels, '#6699FF', 3);
            // posts[2] = true;
        } else {
            return;
        }
        await fetch(`\${api}\${requestParams != null ? '?' + requestParams.toString() : ''}`)
            .then(res => {
                if (res.ok) {
                    return res.json();
                }
                return res.json().then(text => { throw new Error(text) })
            }).then(d => {
                var counter = 0;
                d.forEach(value => {
                    if (requestParams != null) {
                        dataset.data[value[0] - 1] = value[1];
                    } else {
                        dataset.data[counter] = value[1];
                        counter++;
                    }
                })


            }).catch(err => console.log(err));

        datasets.push(dataset);
        data.datasets = datasets;
        configsCharPost.data = data;
    }

    async function fetchData() {

        await fetch(statPostApi)
            .then(res => {
                if (res.ok) {
                    return res.json();
                }
                return res.text().then(err => { throw new Error(err) });
            }).then(data => {
                data.forEach(value => {
                    var newLi = $(`
                    <li>
                      <div class="dropdown-item">\${value[0]}</div>
                    </li>
                  `)
                    var newLi2 = $(`
                    <li>
                      <div class="dropdown-item">\${value[0]}</div>
                    </li>
                  `)
                    years.push(value[0])
                    $("#dropDownYearMenu").append(newLi);
                    $("#dropDownPostYearMenu").append(newLi2);
                })
                $("#dropDownYear").text($("#dropDownYearMenu li:first div").text());
                $("#dropDownPostYear").text($("#dropDownPostYearMenu li:first div").text());
            })

        // var yearValue = years[years.length - 1];
        // years.push(yearValue + 1);
        // years.push(yearValue + 2);
        // years.push(yearValue + 3);

        if (years.length === 1) {
            $("#itemYear").hide();
            $("#itemYearPost").hide();
        }

        var requestParams = new URLSearchParams();
        requestParams.append("byMonth", "true");
        requestParams.append("year", "2023");
        await statPost(months, requestParams, statPostApi, "post");
        await statPost(months, requestParams, statSurveyApi, "postSurvey");
        await statPost(months, requestParams, statInvitationApi, "postInvitation");
        await stat(months, requestParams, statUserApi);

        drawChart(months, ctxPost, configsCharPost);
    }

    function createDataset(labelName, labels, color, id) {
        return dataset = {
            label: labelName,
            data: labels.length > 0 ? Array.from({ length: labels.length }, () => 0) : [],
            backgroundColor: color,
            datalabels: {
                color: 'black'
            },
            id: id
        }
    }

    function drawChart(labels, chartCtx, chartConfis) {
        if (Object.keys(chartConfis.data).length === 0) {
            var data = {
                labels: labels,
                datasets: [{
                    label: "empty",
                    data: Array.from({ length: labels.length }, () => 0),
                    backgroundColor: 'red',
                    datalabels: {
                        color: 'black'
                    },
                    id: 1
                }]
            };
            chartConfis.data = data;
        }
        var chart = Chart.getChart(chartCtx);
        if (chart) {
            chart.destroy();
        }

        chart = new Chart(chartCtx, chartConfis);
    }

    async function checkPostType(labels, requestParams) {
        configsCharPost.data = {};
        if ($("#totalPost").prop("checked")) {
            await statPost(labels, requestParams, statPostApi, "post");
        }

        if ($("#totalPostSurvey").prop("checked")) {
            await statPost(labels, requestParams, statSurveyApi, "postSurvey");
        }

        if ($("#totalPostInvitation").prop("checked")) {
            await statPost(labels, requestParams, statInvitationApi, "postInvitation");
        }
        console.log(configsCharPost.data)
        drawChart(labels, ctxPost, configsCharPost);
    }

    fetchData();
</script>