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
        <script>
            $(function(){
                $('#joinDate').datepicker({
                    autoclose:true,
                    format:'yyyy-mm-dd'
                });
            })
        </script>

</head>
<body class="background-color-white">
<div class="page-content custom-body">
    <form class="form-horizontal" role="form" id="entryUserForm" action="/entry/user/saveOrUpdate" method="post">
        <input type="hidden" name="id" value="${entryUserDTO.id}">
        <div class="form-group" id="has_error_name">
            <label for="name" class="col-xs-12 control-label">姓名：<span class="text-danger">*</span></label>
            <div class="col-xs-12">
                <span class="block input-icon input-icon-right">
                    <input type="text" class="width-100" id="name" name="name" placeholder="请输入姓名" value="${entryUserDTO.name}" maxlength="5">
                </span>
            </div>
            <div class="help-block col-xs-12 col-sm-reset inline" id="error_name"></div>
        </div>
        <div class="form-group">
            <label class="col-xs-12 control-label no-padding-right" for="genderCheck">性别：</label>
            <div class="col-xs-12">
                <label class="control-label">
                    <input class="ace ace-switch ace-switch-8 btn-rotate" type="checkbox" id="genderCheck" name="genderCheck" ${entryUserDTO.genderCheck == 0 ? '':'checked'} value="1"/>
                    <span class="lbl" data-lbl="男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;女"></span>
                </label>
            </div>
        </div>
        <div class="form-group" id="has_error_telephone">
            <label class="col-xs-12 control-label" for="telephone">手机号：</label>
            <div class="col-xs-12">
                <input type="text" id="telephone" placeholder="请输入手机号" class="form-control" name="telephone" value="${entryUserDTO.telephone}" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
            </div>
            <div class="help-block col-xs-12 col-sm-reset inline" id="error_telephone"></div>
        </div>
        <div class="form-group" id="has_error_joinDate">
            <label class="col-xs-12 control-label" for="joinDate">入职日期：</label>
            <div class="col-xs-12">
                <input type="text" id="joinDate" placeholder="点击选择入职日期" class="form-control" name="joinDate" value="${entryUserDTO.joinDate}"/>
            </div>
            <div class="help-block col-xs-12 col-sm-reset inline" id="error_joinDate"></div>
        </div>
        <c:if test="${empty entryUserDTO.id}">
            <div class="form-group">
                <label class="col-xs-12 control-label no-padding-right" for="quickSalary">快速结账：</label>

                <div class="col-xs-12">
                    <input type="text" id="quickSalary" placeholder="请输入快速结账" class="form-control" name="quickSalary" maxlength="10" onkeyup="if(!/^[+-]*(\d)*(\.\d{0,2})*$/.test(value)) this.value='';" onafterpaste="if(!/^[+-]*(\d)*(\.\d{0,2})*$/.test(value)) this.value='';"/>
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <div class="col-sm-8 col-sm-offset-4">
                <input type="button" class="btn btn-sm btn-primary" onclick="validateOpenSaveOrUpdate($('#entryUserForm'))" value="保存"/>
                <input type="button" class="btn btn-sm" onclick="msh.util.closeOpenedUrl();" value="取消"/>
            </div>
        </div>
    </form>
</div>
<%@ include file="/WEB-INF/jsp/decorators/file_bodyer.jsp"%>
</body>
</html>