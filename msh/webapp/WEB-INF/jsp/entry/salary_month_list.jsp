<%--
  Created by
  User: Tang Yong Di
  Date: 2016/3/8
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="msh" uri="http://www.qiqiao.com/moshihuai" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>按月统计</title>
    <script>
        $(function(){
            $('.input-daterange').datepicker({
                autoclose:true,
                format:'yyyy-mm',
                minViewMode:1
            });
        });
    </script>
</head>
<body>
<div class="row">
    <div class="col-xs-12">
        <form id="search_form" class="form-inline" action="/entry/statistics/salaryForMonth" method="post" role="form">
             统计结账日期范围：
            <div class="input-daterange input-group">
                <input type="text" class="input-sm form-control" name="beginDate" value="${entrySalaryDTO.beginDate}" />
				    <span class="input-group-addon">
					    <i class="fa fa-exchange"></i>
				    </span>
                <input type="text" class="input-sm form-control" name="endDate" value="${entrySalaryDTO.endDate}"/>
            </div>
            <button class="btn btn-white btn-info btn-round" type="submit">
                <i class="ace-icon glyphicon glyphicon-search blue"></i>
                查询
            </button>
            <button class="btn btn-white btn-default btn-round" type="button" onclick="forward('/entry/statistics/salaryForDay')">
                <i class="ace-icon glyphicon glyphicon-plus red2"></i>
                按日统计
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
                                <th><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>月份</th>
                                <th>结账金额</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pagination.list}" var="salary">
                            <tr>
                                <td>${salary.dayOrMonth}</td>
                                <td>${salary.dayOrMonthOfSalary}</td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td align="right">合计:</td>
                            <td>${entrySalaryDTO.sumSalary}元</td>
                        </tr>
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
