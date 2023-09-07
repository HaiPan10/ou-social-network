<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Bài đăng khảo sát /</span> Thông tin thống kê</h4>

<div class="card mb-4">
    <h5 class="card-header text-center"><span class="text-muted fw-light">Câu hỏi: </span> ${questionText}</h5>
    <!-- Checkboxes and Radios -->
    <div class="card-body">
        <c:choose>
            <c:when test="${listTextAnswer != null}">
                <div class="table-responsive text-nowrap">
                    <table class="table">
                        <thead>
                            <tr>
                                <!-- <th>Số thứ tự</th> -->
                                <th>Câu trả lời</th>
                            </tr>
                        </thead>
                        <tbody class="table-border-bottom-0">
                            <c:forEach items="${listTextAnswer}" var="answer">
                                <tr>
                                    <td>${answer.value}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <div class="row gy-3">
                    <div class="col-md-3"></div>
                    <div class="col-md-6">
                        <div>
                            <div>
                                <canvas id="chartQuestion"></canvas>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3"></div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<c:url value="/admin/stat/question/${id}" var="statQuestAction"/>
<c:url value="/admin/stat/question/get_total/${id}" var="statQuestUnchoiceAction"/>

<script>
    var ctxQuestion = document.getElementById("chartQuestion");
    var apiStatQuest = "${statQuestAction}";
    var apiStatUnchoice = "${statQuestUnchoiceAction}";
    var configsChartQuestion = {
        type: 'pie',
        data: {},
        options: {
            scales: {
                y: {
                    beginAtZero: false
                }
            },
            plugins: {
                legend: {
                    display: true,
                    position: 'right'
                },
                // title: {
                //   display: true,
                //   text: 'Hãy chọn các ngôn ngữ bạn đã học dưới đây'
                // },
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

    async function statQuest(api) {
        var data = {
            datasets: [],

            labels: []
        };

        var dataset = {
            data: [],
            datalabels: {
                color: 'black'
            }
        }

        await fetch(api)
        .then(res => {
            if (res.ok) {
                return res.json();
            }
            return res.json().then(text => { throw new Error(text) })
        }).then(d => {
            d.forEach(value => {
                // if (value[2] !== 0) {
                //   data.labels.push(value[1]);
                //   dataset.data.push(value[2]);
                // }
                data.labels.push(value[1] + ": " + String(value[2]));
                dataset.data.push(value[2]);
            })

        }).catch(err => console.log(err));

        var totalUnchoice = 0;

        await fetch(apiStatUnchoice)
        .then(res => {
            if (res.ok) {
                return res.json();
            }
            return res.json().then(text => { throw new Error(text) })
        }).then(d => {

            totalUnchoice = d;

        }).catch(err => console.log(err));

        if (totalUnchoice > 0) {
            data.labels.push("Không chọn: " + String(totalUnchoice));
            dataset.data.push(totalUnchoice);
        }
        data.datasets = [dataset];
        configsChartQuestion.data = data;
        drawChart(data.labels, ctxQuestion, configsChartQuestion);
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

    <c:if test="${listTextAnswer == null}">
        statQuest(apiStatQuest);
    </c:if>
</script>