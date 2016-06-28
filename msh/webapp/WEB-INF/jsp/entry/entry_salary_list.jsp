<%--
  Created by
  User: Tang Yong Di
  Date: 2016/3/6
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="msh" uri="http://www.qiqiao.com/moshihuai" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>结账</title>
    <script type="application/javascript">
        //结账
        function saveOrUpdateSalary(title, id){
            var params = isEmpty(id) ? '' : '?id='+id;
            msh.util.openUrl(title, '/entry/salary/goSaveOrUpdate' + params, '400px', '600px');
        }
    </script>
</head>
<body>
<div class="row">
    <div class="col-xs-12">
        <button class="btn btn-white btn-default btn-round" type="button" onclick="saveOrUpdateSalary('结账')">
            <i class="ace-icon glyphicon glyphicon-plus red2"></i>
            结账
        </button>
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
                            <th class="hidden-480">联系电话</th>
                            <th><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>结账日期</th>
                            <th>结账金额</th>
                            <th>操作</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${pagination.list}" var="salary">
                            <tr>
                                <td>${salary.entryUserName}</td>
                                <td class="hidden-480">${salary.telephone}</td>
                                <td>${salary.salaryDate}</td>
                                <td>${salary.salary}</td>
                                <td>
                                    <div class="hidden-sm hidden-xs action-buttons">
                                        <a class="green" href="#" title="修改账目" onclick="saveOrUpdateSalary('修改账目', '${salary.id}')">
                                            <i class="ace-icon fa fa-pencil bigger-130"></i>
                                        </a>
                                    </div>
                                    <div class="hidden-md hidden-lg">
                                        <div class="inline position-relative">
                                            <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                                <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                                            </button>
                                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                                <li>
                                                    <a href="#" class="tooltip-success" data-rel="tooltip" title="修改账目" onclick="saveOrUpdateSalary('修改账目', '${salary.id}')">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
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
