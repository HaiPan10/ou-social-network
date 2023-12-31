<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="layout-navbar container-xxl navbar navbar-expand-xl navbar-detached align-items-center bg-navbar-theme"
    id="layout-navbar">
    <div class="layout-menu-toggle navbar-nav align-items-xl-center me-3 me-xl-0 d-xl-none">
        <a class="nav-item nav-link px-0 me-xl-4" href="javascript:void(0)">
            <i class="bx bx-menu bx-sm"></i>
        </a>
    </div>

    <div class="navbar-nav-right d-flex align-items-center" id="navbar-collapse">
        <!-- Search -->
        <div class="navbar-nav align-items-center">
            <button onclick="clearFilter()" class="btn btn-outline-secondary">Clear Filter</button>
            <form method="get">
                <div class="nav-item d-flex align-items-center">
                    <button class="btn bg-transparent" type="submit"><i class="bx bx-search fs-4 lh-0"></i></button>
                    <input name="kw" type="text" class="form-control border-0 shadow-none" placeholder="Search..."
                        aria-label="Search..." value="${not empty kw ? kw : ''}"/>
                    <c:if test="${filterStatus != null}">
                        <input hidden name="status" type="text" value="${filterStatus}" />
                    </c:if>
                </div>
            </form>
        </div>
        <!-- /Search -->

        <ul class="navbar-nav flex-row align-items-center ms-auto">
            <!-- User -->
            <li class="nav-item navbar-dropdown dropdown-user dropdown">
                <a class="nav-link dropdown-toggle hide-arrow" href="javascript:void(0);" data-bs-toggle="dropdown">
                    <div class="avatar avatar-online">
                        <img src="https://ou.edu.vn/wp-content/uploads/2019/01/OpenUniversity.png" alt
                            class="w-px-40 h-auto rounded-circle" />
                    </div>
                </a>
                <ul class="dropdown-menu dropdown-menu-end">
                    <li>
                        <a class="dropdown-item" href="#">
                            <div class="d-flex" style=" align-items: center; justify-content: center;">
                                <div class="flex-shrink-0 me-3">
                                    <div class="avatar avatar-online">
                                        <img src="https://ou.edu.vn/wp-content/uploads/2019/01/OpenUniversity.png" alt
                                            class="w-px-40 h-auto rounded-circle" />
                                    </div>
                                </div>
                                <div>
                                    <span class="fw-semibold d-block">Admin OU Social network</span>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li>
                        <div class="dropdown-divider"></div>
                    </li>
                    <li>
                        <c:url value="/logout" var="logout" />
                        <a class="dropdown-item" href="${logout}">
                            <i class="bx bx-power-off me-2"></i>
                            <span class="align-middle">Đăng xuất</span>
                        </a>
                    </li>
                </ul>
            </li>
            <!--/ User -->
        </ul>
    </div>
</nav>

<script>
    function clearFilter(){
        var currentUrl = window.location.href;
        var requestUrl = currentUrl.split("?")[0];
        window.location = requestUrl;
    }
</script>