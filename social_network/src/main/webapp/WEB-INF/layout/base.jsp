<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            <tiles:insertAttribute name="title" />
        </title>

        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link
        href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap"
        rel="stylesheet"
        />
        
        <link href="<c:url value="/resources/vendor/fonts/boxicons.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/vendor/css/core.css" />" class="template-customizer-core-css" rel="stylesheet">
        <link href="<c:url value="/resources/vendor/css/theme-default.css" />" class="template-customizer-theme-css" rel="stylesheet">
        <link href="<c:url value="/resources/css/demo.css" />" rel="stylesheet">

        <link href="<c:url value="/resources/vendor/libs/perfect-scrollbar/perfect-scrollbar.css" />" class="template-customizer-theme-css" rel="stylesheet">
        <link href="<c:url value="/resources/vendor/libs/apex-charts/apex-charts.css" />" rel="stylesheet">
        <!-- Page CSS -->        
        <tiles:insertAttribute name="pageCss" />

        <script src="<c:url value='/resources/vendor/js/helpers.js'></c:url>"></script>
        <script src="<c:url value='/resources/js/config.js'></c:url>"></script>
        <!-- Jquery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    </head>
    <body>
        <div class="layout-wrapper layout-content-navbar">
            <div class="layout-container">
                <tiles:insertAttribute name="aside-menu" />
                <div class="layout-page">
                    <tiles:insertAttribute name="navigagion-bar" />
                    <div class="content-wrapper">
                        <div class="container-xxl flex-grow-1 container-p-y">
                            <tiles:insertAttribute name="content" />
                        </div>
                        <tiles:insertAttribute name="footer" />
                        <div class="content-backdrop fade"></div>
                    </div>
                </div>
            </div>
            <div class="layout-overlay layout-menu-toggle"></div>
        </div>
        
        <script src="<c:url value='/resources/vendor/libs/jquery/jquery.js'></c:url>"></script>
        <script src="<c:url value='/resources/vendor/libs/popper/popper.js'></c:url>"></script>
        <script src="<c:url value='/resources/vendor/js/bootstrap.js'></c:url>"></script>
        <script src="<c:url value='/resources/vendor/libs/perfect-scrollbar/perfect-scrollbar.js'></c:url>"></script>

        <script src="<c:url value='/resources/vendor/js/menu.js'></c:url>"></script>
        <!-- endbuild -->

        <!-- Vendors JS -->
        <script src="<c:url value='/resources/vendor/libs/apex-charts/apexcharts.js'></c:url>"></script>

        <!-- Main JS -->
        <script src="<c:url value='/resources/js/main.js'></c:url>"></script>

        <!-- Page JS -->
        <script src="<c:url value='/resources/js/dashboards-analytics.js'></c:url>"></script>

        <!-- Place this tag in your head or just before your close body tag. -->
        <script async defer src="https://buttons.github.io/buttons.js"></script>
    </body>
</html>