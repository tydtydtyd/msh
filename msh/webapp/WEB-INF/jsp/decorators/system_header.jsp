<%--
  Created by
  User: Tang Yong Di
  Date: 2016/3/2
--%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title>七 巧</title>

    <meta name="description" content="Static &amp; Dynamic Tables" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <%@ include file="file_header.jsp"%>
    <script type="text/javascript">
        var username = '${USER.username}';
    </script>
    <decorator:head/>
</head>

<body class="no-skin">
<!-- #section:basics/navbar.layout -->
<div id="navbar" class="navbar navbar-default">
    <script type="text/javascript">
        try{ace.settings.check('navbar' , 'fixed')}catch(e){}
    </script>

    <div class="navbar-container" id="navbar-container">
        <!-- #section:basics/sidebar.mobile.toggle -->
        <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
            <span class="sr-only">Toggle sidebar</span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>
        </button>

        <!-- /section:basics/sidebar.mobile.toggle -->
        <div class="navbar-header pull-left">
            <!-- #section:basics/navbar.layout.brand -->
            <a href="#" class="navbar-brand">
                <small>
                    <i class="fa fa-leaf"></i>
                    七 巧
                </small>
            </a>
        </div>
        <div class="navbar-buttons navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <li class="light-blue">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="/static/assets/avatars/user.png" alt="Jason's Photo" />
								<span class="user-info">
									<small>欢迎您,</small>
									${USER.username}
								</span>

                        <i class="ace-icon fa fa-caret-down"></i>
                    </a>

                    <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li><a href="#"><i class="ace-icon fa fa-cog"></i>Settings</a></li>
                        <li><a href="profile.html"><i class="ace-icon fa fa-user"></i>Profile</a></li>
                        <li class="divider"></li>
                        <li><a href="/system/login/logout"><i class="ace-icon fa fa-power-off"></i>退出登录</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>

<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try{ace.settings.check('main-container' , 'fixed')}catch(e){}
    </script>

    <!-- #section:basics/sidebar -->
    <div id="sidebar" class="sidebar responsive">
        <script type="text/javascript">
            try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
        </script>

        <ul class="nav nav-list" id="index_header_ul">
            <shiro:hasPermission name="INDEX">
            <li>
                <a href="/system/login/index"><i class="menu-icon fa fa-tachometer"></i><span class="menu-text"> 首页</span></a>
                <b class="arrow"></b>
            </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="SYSTEM_MANAGE">
            <li>
                <a href="#" class="dropdown-toggle"><i class="menu-icon fa fa-gear"></i><span class="menu-text"> 系统管理</span>
                    <b class="arrow fa fa-angle-down"></b>
                </a>
                <b class="arrow"></b>
                <ul class="submenu">
                    <shiro:hasPermission name="SYSTEM_USER_MANAGE">
                    <li><a href="/system/user/list"><i class="menu-icon fa fa-caret-right"></i> 用户管理</a><b class="arrow"></b></li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="SYSTEM_ROLE_MANAGE">
                    <li><a href="/system/role/list"><i class="menu-icon fa fa-caret-right"></i> 角色管理</a><b class="arrow"></b></li>
                    </shiro:hasPermission>
                </ul>
            </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="ENTRY_MANAGE">
            <li>
                <a href="#" class="dropdown-toggle"><i class="menu-icon fa fa-users"></i><span class="menu-text"> 人员管理</span>
                    <b class="arrow fa fa-angle-down"></b>
                </a>
                <b class="arrow"></b>
                <ul class="submenu">
                    <shiro:hasPermission name="ENTRY_USER_MANAGE">
                    <li><a href="/entry/user/list"><i class="menu-icon fa fa-caret-right"></i> 入职人管理</a><b class="arrow"></b></li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="ENTRY_SALARY">
                    <li><a href="/entry/salary/list"><i class="menu-icon fa fa-caret-right"></i> 结账录入</a><b class="arrow"></b></li>
                    </shiro:hasPermission>
                </ul>
            </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="REPORT">
            <li>
                <a href="#" class="dropdown-toggle"><i class="menu-icon fa fa-calendar"></i><span class="menu-text"> 报表</span>
                    <b class="arrow fa fa-angle-down"></b>
                </a>
                <b class="arrow"></b>
                <ul class="submenu">
                    <shiro:hasPermission name="ENTRY_SALARY_FOR_DAY">
                    <li><a href="/entry/statistics/salaryForDay"><i class="menu-icon fa fa-caret-right"></i> 入职工资统计报表</a><b class="arrow"></b></li>
                    </shiro:hasPermission>
                </ul>
            </li>
            </shiro:hasPermission>
        </ul>

        <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
            <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
        </div>

        <script type="text/javascript">
            try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
        </script>
    </div>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
                </script>

                <ul class="breadcrumb" id="breadcrumb_ul">
                </ul>
            </div>

            <div class="page-content">
                <decorator:body/>
            </div>
        </div>
    </div>

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div>
<%@ include file="file_bodyer.jsp"%>
</body>
</html>
