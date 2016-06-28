<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="七巧" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <%@ include file="/WEB-INF/jsp/decorators/file_header.jsp"%>
        <script type="text/javascript">
            jQuery(function($) {
                $('#salaryDate').datepicker({
                    autoclose:true,
                    format:'yyyy-mm-dd'
                });

                $("#searchEntryUserName").autocomplete({
                    source: function (request, response) {
                        showSearchLoading();
                        $.ajax({
                            url: "/entry/user/getUserList",
                            data: {name: $("#searchEntryUserName").val()},
                            success: function (data) {
                                hideSearchLoading();
                                response($.map(data.users, function (item) {
                                    return {
                                        label: item.name + " (" + item.telephone + ")",
                                        id: item.id
                                    }
                                }));
                            }
                        });
                    },
                    minLength: 1,
                    select: function (event, ui) {
                        $('#entryUserName').val(ui.item.label);
                        $('#entryUserId').val(ui.item.id);
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
<body class="background-color-white">
<div class="page-content custom-body">
<form class="form-horizontal" role="form" id="entrySalaryForm" action="/entry/salary/saveOrUpdate" method="post">
    <input type="hidden" name="id" value="${entrySalaryDTO.id}">
    <input type="hidden" id="entryUserId" name="entryUserId" value="${entrySalaryDTO.entryUserId}">
        <div class="form-group">
            <label for="searchEntryUserName" class="col-xs-12 control-label">搜索并选择入职人员：${empty entrySalaryDTO.id ? '':'<span class="text-danger">禁止修改入职人员</span>'}</label>
            <div class="col-xs-12">
                <span class="block input-icon input-icon-right">
                    <input type="text" class="width-100" id="searchEntryUserName" name="searchEntryUserName" placeholder="搜索并选择入职人员" ${empty entrySalaryDTO.id ? '':'readonly'} maxlength="11">
                    <span id="search_loading" style="display: none" class="ace-icon fa fa-spinner fa-spin orange bigger-175"></span>
                </span>
            </div>
        </div>
        <div class="form-group" id="has_error_entryUserName">
            <label for="entryUserName" class="col-xs-12 control-label">入职人员：<span class="text-danger">*</span></label>
            <div class="col-xs-12">
                <span class="block input-icon input-icon-right">
                    <input type="text" class="width-100" id="entryUserName" name="entryUserName" placeholder="输入入职人员" readonly
                           value="${entrySalaryDTO.entryUserName.concat('(').concat(entrySalaryDTO.telephone).concat(')')}"/>
                </span>
            </div>
            <div class="help-block col-xs-12 col-sm-reset inline" id="error_entryUserName"></div>
        </div>
        <div class="form-group" id="has_error_salaryDate">
            <label for="salaryDate" class="col-xs-12 control-label">结账日期：<span class="text-danger">*</span></label>
            <div class="col-xs-12">
                <span class="block input-icon input-icon-right">
                    <input type="text" class="width-100" id="salaryDate" name="salaryDate" placeholder="选择结账日期" readonly
                           value="${entrySalaryDTO.salaryDate}"/>
                </span>
            </div>
            <div class="help-block col-xs-12 col-sm-reset inline" id="error_salaryDate"></div>
        </div>
        <div class="form-group" id="has_error_salary">
            <label class="col-xs-12 control-label" for="salary">结账金额：<span class="text-danger">*</span></label>

            <div class="col-xs-12">
                <input type="text" id="salary" placeholder="请输入结账金额" class="form-control" name="salary" value="${entrySalaryDTO.salary}" maxlength="10" onkeyup="if(!/^[+-]*(\d)*(\.\d{0,2})*$/.test(value)) this.value='';" onafterpaste="if(!/^[+-]*(\d)*(\.\d{0,2})*$/.test(value)) this.value='';"/>
            </div>
            <div class="help-block col-xs-12 col-sm-reset inline" id="error_salary"></div>
        </div>
        <div class="form-group">
            <div class="col-sm-8 col-sm-offset-4">
                <input type="button" class="btn btn-sm btn-primary" onclick="validateOpenSaveOrUpdate($('#entrySalaryForm'))" value="保存"/>
                <input type="button" class="btn btn-sm" onclick="msh.util.closeOpenedUrl();" value="取消"/>
            </div>
        </div>
    </form>
</div>
<%@ include file="/WEB-INF/jsp/decorators/file_bodyer.jsp"%>
</body>
</html>