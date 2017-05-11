<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	
	
	
	<script type="text/javascript">
	$(document).ready(function() {
		$("#userMenuForm").validate({
			submitHandler: function(form){
				loading('正在提交，请稍等...');
				form.submit();
			} 
		});
	});	
	</script>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">
			<a href="#">
			<c:choose>
				<c:when test="${rootMenuOper == 'ADD' }">添加</c:when>
				<c:when test="${rootMenuOper == 'MODIFY' }">修改</c:when>
				<c:otherwise>其它</c:otherwise>
			</c:choose>资源
			</a>
		</li>
	</ul>
	<br/>
	<form:form id="userMenuForm"   action="${ctx}/web/sys/user/menu/rootMenu" method="post" class="form-horizontal">
		<input type="hidden" name="rootMenuOper" id="rootMenuOper" value="${ rootMenuOper}"/>
		<input type="hidden" name="parentid" id="parentid" value="0"/>
		<input type="hidden" name="id" id="id" value="${tMenu.id }"/>
 
 		<div class="control-group">
			<label class="control-label">资源名称:</label>
			<div class="controls">
				<input  type="text" htmlEscape="false" maxlength="50" class="required" name="name" value="${tMenu.name }"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
 		</div>
 
 
   		<div class="control-group">
			<label class="control-label">状态:</label>
			<div class="controls">
				<span class="help-inline"><font color="red">*</font> </span>
				<input name="status" type="radio" value="1" 
				<c:if test="${rootMenuOper == 'ADD' || rootMenu.status == '1'}">
					checked="checked"
				</c:if>
				/>启用
				<input name="status" type="radio" value="2" 
				<c:if test="${rootMenu.status == '2'}">
					checked="checked"
				</c:if>
				
				/>停用
			</div>
 		</div>
 
 
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>		
		
		
		
	</form:form>



</body>
</html>
 