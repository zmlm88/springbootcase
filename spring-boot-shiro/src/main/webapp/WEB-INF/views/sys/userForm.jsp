<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$.validator.addMethod('atLeastOneChecked', function(value, element) { 
		var checkedCount = 0; 
		 $("[name = roleIds]:checkbox").each(function() {
		if ($(this).attr('checked')) { checkedCount++; } 
		}); 
		return checkedCount>0; 
		},"请选择至少一项"); 	
	
		$(document).ready(function() {	
			$("#inputForm").validate({
				rules: {
					roleIds: {
						required: function(element) {
							atLeastOneChecked: true
						}
					}
				}
			});
		});
	</script>	
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
				<input  type="text" htmlEscape="false" maxlength="50" class="required" name="userName" value="${user.userName }"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input  type="password" htmlEscape="false" maxlength="50" class="required" name="password" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>			
		<div class="control-group">
			<label class="control-label">用户角色:</label>
			<div class="controls">
			<c:forEach items="${userOperRoles}" var="userOperRoles" varStatus="status">
				<c:if test="${status.index %5 ==0 && status.index>0}">
					<br/>
				</c:if>
	            <input  type="checkbox"  name="roleIds" value="${userOperRoles.id}"  <c:if test="${userOperRoles.id == '1' }">checked="checked"</c:if>/>${userOperRoles.name}
	         </c:forEach>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>				
			
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>		
		
	</form:form>
	
	<script type="text/javascript">
		   $("[name = roleIds]:checkbox").each(function () {
				var roleValue=$(this).val();
				<c:forEach  items="${userRoles}" var="userRoles">
					if(roleValue == '${userRoles.id}'){
						$(this).attr('checked',true);   
					}
				</c:forEach>
		    });
	</script>


</body>
</html>