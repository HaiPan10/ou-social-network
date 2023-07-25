<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <!-- Page CSS -->
        <link href="<c:url value="/resources/vendor/css/pages/page-auth.css" />" rel="stylesheet">
    </head>
    <body>
        <tiles:insertAttribute name="content" />
    </body>
</html>