<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/web/sys/user/index">用户列表</a></li>
		<li class="active">
			<a href="#">
				用户<c:choose>
					<c:when test="${ userOper == 'ADD'}">添加</c:when>
					<c:when test="${ userOper == 'VIEW'}">查看</c:when>
					<c:when test="${ userOper == 'MODIFY'}">编辑</c:when>
					<c:otherwise>未知</c:otherwise>
				</c:choose>
			</a>
		</li>
	</ul>
	<br/>
	<form:form id="inputForm"   action="${ctx}/web/sys/user/save" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${user.id}"/>
		<input type="hidden" name="userOper" value="${userOper}"/>
		<div class="control-group">
			<label class="control-label">工号:</label>
			<div class="controls">
				<input  type="text" htmlEscape="false" maxlength="50" class="required" name="userName"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input  type="password" htmlEscape="false" maxlength="50" class="required" name="password"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>				
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>		
		
		
		
	</form:form>



</body>
</html>