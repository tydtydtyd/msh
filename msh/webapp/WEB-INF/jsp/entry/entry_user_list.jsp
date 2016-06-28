<%--
  Created by
  User: Tang Yong Di
  Date: 2016/3/3
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="msh" uri="http://www.qiqiao.com/moshihuai" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <script type="application/javascript">
        $(function(){
            $('.input-daterange').datepicker({autoclose:true});
        });

        //新增
        function saveOrUpdateUser(title, id){
            var params = isEmpty(id) ? '' : '?id='+id;
            msh.util.openUrl(title, '/entry/user/goSaveOrUpdate' + params, '400px', '600px');
        }

        //删除
        function deleteUser(id){
            msh.util.confirm("你确定要删除该入职人员吗？", {
                ok: function () {
                    msh.ajax.doAjax({
                        method: 'post',
                        url: '/entry/user/delete',
                        data: {id: id},
                        success: function (result) {
                            if(result.success) {
                                msh.util.alert("删除成功", {ok:function(){
                                    reload();
                                }});
                            }else {
                                msh.util.alert(result.message);
                            }
                        }
                    });
                }
            });
        }
    </script>
</head>
<body>
<div class="row">
    <div class="col-xs-12">
        <form id="search_form" class="form-inline" action="/entry/user/list" method="post" role="form">
            姓名/手机号：
            <input type="text" class="input-sm form-control" placeholder="姓名/手机号" name="name" value="${entryUserDTO.name}" maxlength="11">&nbsp;&nbsp;
            入职日期：
            <div class="input-daterange input-group">
                <input type="text" class="input-sm form-control" name="beginDate" value="${entryUserDTO.beginDate}" />
				    <span class="input-group-addon">
					    <i class="fa fa-exchange"></i>
				    </span>
                <input type="text" class="input-sm form-control" name="endDate" value="${entryUserDTO.endDate}"/>
            </div>
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
                            <th>姓名</th>
                            <th>性别</th>
                            <th class="hidden-480">联系电话</th>
                            <th>
                                <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
                                入职日期
                            </th>
                            <th>操作</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${pagination.list}" var="user">
                            <tr>
                                <td>${user.name}</td>
                                <td>${user.gender.label}</td>
                                <td class="hidden-480">${user.telephone}</td>
                                <td>${user.joinDate}</td>
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
