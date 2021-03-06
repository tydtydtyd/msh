<%--
  Created by
  User: Tang Yong Di
  Date: 2016/4/16
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="msh" uri="http://www.qiqiao.com/moshihuai" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>系统用户</title>
    <script type="text/javascript">
        jQuery(function($) {
            $("#searchSystemUserName").autocomplete({
                source: function (request, response) {
                    showSearchLoading();
                    $.ajax({
                        url: "/system/role/getRoleList",
                        data: {name: $("#searchSystemUserName").val()},
                        success: function (data) {
                            hideSearchLoading();
                            response($.map(data.roles, function (item) {
                                return {
                                    label: item.name,
                                    id: item.id
                                }
                            }));
                        }
                    });
                },
                minLength: 0,
                select: function (event, ui) {
                    $('#systemRoleId').val(ui.item.id);
                },
                open: function () {
                    $(this).removeClass("ui-corner-all").addClass("ui-corner-top");
                },
                close: function () {
                    $(this).removeClass("ui-corner-top").addClass("ui-corner-all");
                }
            });
        });

        function showSearchLoading(){
            $('#search_loading').show();
        }

        function hideSearchLoading(){
            $('#search_loading').hide();
        }
    </script>
</head>
<body>
<div class="row">
    <div class="col-xs-12">
        <form id="search_form" class="form-inline" action="/system/user/list" method="post" role="form">
            姓名/手机号：
            <input type="text" class="input-sm form-control" placeholder="姓名/账号" name="username" value="${systemUserDTO.username}" maxlength="11">&nbsp;&nbsp;
            角色：
            <span class="input-icon input-icon-right">
                <input type="text" class="width-100" id="searchSystemUserName" name="searchSystemUserName" placeholder="搜索并选择角色" maxlength="11">
                <span id="search_loading" style="display: none" class="ace-icon fa fa-spinner fa-spin orange bigger-175"></span>
                <input type="hidden" name="roleId" value="${systemUserDTO.roleId}">
            </span>
            <button class="btn btn-white btn-info btn-round" type="submit">
                <i class="ace-icon glyphicon glyphicon-search blue"></i>
                查询
            </button>
            <button class="btn btn-white btn-default btn-round" type="button" onclick="saveOrUpdateUser('新增入职人员')">
                <i class="ace-icon glyphicon glyphicon-plus red2"></i>
                新增
            </button>
            <button class="btn btn-white btn-default btn-round" type="button" onclick="window.location.href='/entry/user/excel'">
                <i class="ace-icon fa fa-file-excel-o green"></i>
                导出
            </button>
        </form>
    </div>
</div>
<div class="space-6"></div>
<div class="row">
    <div class="col-xs-12">
        <div class="row">
            <div class="col-xs-12">
                <div>
                    <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>用户名</th>
                            <th>账号</th>
                            <th>性别</th>
                            <th>角色</th>
                            <th>
                                <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
                                最后登录时间
                            </th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${pagination.list}" var="user">
                            <tr>
                                <td>${user.username}</td>
                                <td>${user.account}</td>
                                <td>${user.gender.label}</td>
                                <td>${user.roleName}</td>
                                <td>${user.lastLoginTime}</td>
                                <td>${user.status.label}</td>
                                <td>
                                    <div class="hidden-sm hidden-xs action-buttons">
                                        <a class="green" href="#" title="编辑" onclick="saveOrUpdateUser('编辑入职人员', ${user.id})">
                                            <i class="ace-icon fa fa-pencil bigger-130"></i>
                                        </a>

                                        <a class="red" href="#" title="删除" onclick="deleteUser(${user.id})">
                                            <i class="ace-icon fa fa-trash-o bigger-130"></i>
                                        </a>
                                    </div>
                                    <div class="hidden-md hidden-lg">
                                        <div class="inline position-relative">
                                            <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                                <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                                            </button>
                                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                                <li>
                                                    <a href="#" class="tooltip-success" data-rel="tooltip" title="编辑" onclick="saveOrUpdateUser('编辑入职人员', ${user.id})">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                    </a>
                                                </li>
                                                <li>
                                                    <a href="#" class="tooltip-error" data-rel="tooltip" title="删除" onclick="deleteUser(${user.id})">
                                                    <span class="red">
                                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                    </span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <msh:paging paging="${pagination}" formName="search_form"/>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
