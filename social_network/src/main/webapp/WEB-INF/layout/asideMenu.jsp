<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <aside id="layout-menu" class="layout-menu menu-vertical menu bg-menu-theme">
        <div class="app-brand demo" style="justify-content: center;">
            <img style="width: 60%; height: 100%;" src="<c:url value="/resources/img/logo/full-size-logo.png" />" />      
        </div>
    
        <div class="menu-inner-shadow"></div>
    
        <ul class="menu-inner py-1">
            <!-- Dashboard -->
            <li class="menu-item" id="dash-board">
                <c:url value="/admin/dashboard" var="dashBoardAction" />
                <a href="${dashBoardAction}" class="menu-link">
                    <i class="menu-icon tf-icons bx bx-home-circle"></i>
                    <div>Bảng điều khiển</div>
                </a>
            </li>
    
            <!-- Layouts -->
            <li class="menu-header small text-uppercase">
                <span class="menu-header-text">Tài khoản</span>
            </li>
            <li class="menu-item" id="account-verification-menu">
                <c:url value="/admin/accounts/verification/" var="accountVerificationAction" />
                <a href="${accountVerificationAction}" class="menu-link">
                    <i class="menu-icon tf-icons bx bx-check-shield"></i>
                    <div>Duyệt tài khoản</div>
                </a>
            </li>
            <li class="menu-item" id="account-providers-menu">
                <c:url value="/admin/accounts/provider" var="accountProviderAction" />
                <a href="${accountProviderAction}" class="menu-link">
                    <i class="menu-icon tf-icons bx bx-lock-open-alt"></i>
                    <div>Cấp tài khoản giảng viên</div>
                </a>
            </li>
            <li class="menu-item" id="accounts-admin">
                <c:url value="/admin/accounts" var="accountsAction" />
                <a href="${accountsAction}" class="menu-link">
                    <i class="menu-icon tf-icons bx bx-dock-top"></i>
                    <div>Quản lý tài khoản</div>
                </a>
            </li>
            <li class="menu-header small text-uppercase">
                <span class="menu-header-text">Bài đăng</span>
            </li>
            <li class="menu-item" id="posts-admin">
                <c:url value="/admin/posts" var="postManagement" />
                <a href="${postManagement}" class="menu-link">
                    <i class="menu-icon tf-icons bx bxs-dock-right"></i>
                    <div>Quản lý bài đăng</div>
                </a>
            </li>
            <li class="menu-item" id="upload-post">
                <c:url value="/admin/posts/upload" var="uploadPost" />
                <a href="${uploadPost}" class="menu-link">
                    <i class="menu-icon tf-icons bx bx-upload"></i>
                    <div>Đăng bài</div>
                </a>
            </li>
            <!-- <li class="menu-item">
                <a href="javascript:void(0);" class="menu-link menu-toggle">
                    <i class="menu-icon tf-icons bx bx-lock-open-alt"></i>
                    <div>Quản lý tài khoản</div>
                </a>
            </li> -->
            <!-- <li class="menu-item">
                <a href="javascript:void(0);" class="menu-link menu-toggle">
                    <i class="menu-icon tf-icons bx bx-cube-alt"></i>
                    <div data-i18n="Misc">Misc</div>
                </a>
                <ul class="menu-sub">
                    <li class="menu-item">
                        <a href="pages-misc-error.html" class="menu-link">
                            <div data-i18n="Error">Error</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="pages-misc-under-maintenance.html" class="menu-link">
                            <div data-i18n="Under Maintenance">Under Maintenance</div>
                        </a>
                    </li>
                </ul>
            </li>
            <!-- Components -->
            <!-- <li class="menu-header small text-uppercase"><span class="menu-header-text">Components</span></li> -->
            <!-- Cards -->
            <!-- <li class="menu-item">
                <a href="cards-basic.html" class="menu-link">
                    <i class="menu-icon tf-icons bx bx-collection"></i>
                    <div data-i18n="Basic">Cards</div>
                </a>
            </li> -->
            <!-- User interface -->
            <!-- <li class="menu-item">
                <a href="javascript:void(0)" class="menu-link menu-toggle">
                    <i class="menu-icon tf-icons bx bx-box"></i>
                    <div data-i18n="User interface">User interface</div>
                </a>
                <ul class="menu-sub">
                    <li class="menu-item">
                        <a href="ui-accordion.html" class="menu-link">
                            <div data-i18n="Accordion">Accordion</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-alerts.html" class="menu-link">
                            <div data-i18n="Alerts">Alerts</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-badges.html" class="menu-link">
                            <div data-i18n="Badges">Badges</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-buttons.html" class="menu-link">
                            <div data-i18n="Buttons">Buttons</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-carousel.html" class="menu-link">
                            <div data-i18n="Carousel">Carousel</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-collapse.html" class="menu-link">
                            <div data-i18n="Collapse">Collapse</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-dropdowns.html" class="menu-link">
                            <div data-i18n="Dropdowns">Dropdowns</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-footer.html" class="menu-link">
                            <div data-i18n="Footer">Footer</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-list-groups.html" class="menu-link">
                            <div data-i18n="List Groups">List groups</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-modals.html" class="menu-link">
                            <div data-i18n="Modals">Modals</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-navbar.html" class="menu-link">
                            <div data-i18n="Navbar">Navbar</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-offcanvas.html" class="menu-link">
                            <div data-i18n="Offcanvas">Offcanvas</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-pagination-breadcrumbs.html" class="menu-link">
                            <div data-i18n="Pagination &amp; Breadcrumbs">Pagination &amp; Breadcrumbs</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-progress.html" class="menu-link">
                            <div data-i18n="Progress">Progress</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-spinners.html" class="menu-link">
                            <div data-i18n="Spinners">Spinners</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-tabs-pills.html" class="menu-link">
                            <div data-i18n="Tabs &amp; Pills">Tabs &amp; Pills</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-toasts.html" class="menu-link">
                            <div data-i18n="Toasts">Toasts</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-tooltips-popovers.html" class="menu-link">
                            <div data-i18n="Tooltips & Popovers">Tooltips &amp; popovers</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="ui-typography.html" class="menu-link">
                            <div data-i18n="Typography">Typography</div>
                        </a>
                    </li>
                </ul>
            </li> -->
    
            <!-- Extended components -->
            <!-- <li class="menu-item">
                <a href="javascript:void(0)" class="menu-link menu-toggle">
                    <i class="menu-icon tf-icons bx bx-copy"></i>
                    <div data-i18n="Extended UI">Extended UI</div>
                </a>
                <ul class="menu-sub">
                    <li class="menu-item">
                        <a href="extended-ui-perfect-scrollbar.html" class="menu-link">
                            <div data-i18n="Perfect Scrollbar">Perfect scrollbar</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="extended-ui-text-divider.html" class="menu-link">
                            <div data-i18n="Text Divider">Text Divider</div>
                        </a>
                    </li>
                </ul>
            </li> -->
    
            <!-- <li class="menu-item">
                <a href="icons-boxicons.html" class="menu-link">
                    <i class="menu-icon tf-icons bx bx-crown"></i>
                    <div data-i18n="Boxicons">Boxicons</div>
                </a>
            </li> -->
    
            <!-- Forms & Tables -->
            <!-- <li class="menu-header small text-uppercase"><span class="menu-header-text">Forms &amp; Tables</span></li> -->
            <!-- Forms -->
            <!-- <li class="menu-item active open">
                <a href="javascript:void(0);" class="menu-link menu-toggle">
                    <i class="menu-icon tf-icons bx bx-detail"></i>
                    <div data-i18n="Form Elements">Form Elements</div>
                </a>
                <ul class="menu-sub">
                    <li class="menu-item active">
                        <a href="forms-basic-inputs.html" class="menu-link">
                            <div data-i18n="Basic Inputs">Basic Inputs</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="forms-input-groups.html" class="menu-link">
                            <div data-i18n="Input groups">Input groups</div>
                        </a>
                    </li>
                </ul>
            </li> -->
            <!-- <li class="menu-item">
                <a href="javascript:void(0);" class="menu-link menu-toggle">
                    <i class="menu-icon tf-icons bx bx-detail"></i>
                    <div data-i18n="Form Layouts">Form Layouts</div>
                </a>
                <ul class="menu-sub">
                    <li class="menu-item">
                        <a href="form-layouts-vertical.html" class="menu-link">
                            <div data-i18n="Vertical Form">Vertical Form</div>
                        </a>
                    </li>
                    <li class="menu-item">
                        <a href="form-layouts-horizontal.html" class="menu-link">
                            <div data-i18n="Horizontal Form">Horizontal Form</div>
                        </a>
                    </li>
                </ul>
            </li> -->
            <!-- Tables -->
            <!-- <li class="menu-item">
                <a href="tables-basic.html" class="menu-link">
                    <i class="menu-icon tf-icons bx bx-table"></i>
                    <div data-i18n="Tables">Tables</div>
                </a>
            </li> -->
            <!-- Misc -->
            <!-- <li class="menu-header small text-uppercase"><span class="menu-header-text">Misc</span></li>
            <li class="menu-item">
                <a href="https://github.com/themeselection/sneat-html-admin-template-free/issues" target="_blank"
                    class="menu-link">
                    <i class="menu-icon tf-icons bx bx-support"></i>
                    <div data-i18n="Support">Support</div>
                </a>
            </li> -->
            <!-- <li class="menu-item">
                <a href="https://themeselection.com/demo/sneat-bootstrap-html-admin-template/documentation/" target="_blank"
                    class="menu-link">
                    <i class="menu-icon tf-icons bx bx-file"></i>
                    <div data-i18n="Documentation">Documentation</div>
                </a>
            </li> -->
        </ul>
    </aside>
</sec:authorize>