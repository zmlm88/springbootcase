<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">


	$(document).ready(function(){
		
		
		
		$("#inputForm").validate({
			submitHandler: function(form){
				var ids = [], nodes = tree.getCheckedNodes(true);
				for(var i=0; i<nodes.length; i++) {
					ids.push(nodes[i].id);
				}
				if(ids.length<1){
					top.$.jBox.tip("请选择资源", 'info');
				}else{
					$("#menuTreeIds").val(ids);
					loading('正在提交，请稍等...');
					form.submit();
				}

			},
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});		
		
		
		var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
				data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
					tree.checkNode(node, !node.checked, true, true);
					return false;
		}}};
		
		// 用户-菜单
		var zNodes=[
				<c:forEach items="${userRoleList}" var="menu">{id:"${menu.id}", pId:"${menu.parentid}", name:"${menu.name}"},
	            </c:forEach>];
		// 初始化树结构
		var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
		// 不选择父节点
		tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };

		<c:forEach items="${ roleMenu}" var="menuRole">
			var node = tree.getNodeByParam("id", ${menuRole.id});
			try{tree.checkNode(node, true, false);}catch(e){}
		</c:forEach>
		
		
		
		// 默认展开全部节点
		tree.expandAll(true);
		
	});
	
	</script>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">
			<a href="#">
			<c:choose>
				<c:when test="${roleOper == 'ADD' }">添加</c:when>
				<c:when test="${roleOper == 'MODIFY' }">修改</c:when>
				<c:otherwise>其它</c:otherwise>
			</c:choose>角色
			</a>
		</li>
	</ul>
	<br/>
	<form:form id="inputForm"   action="${ctx}/web/sys/role/roleForm" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${userRole.id }"/>
		<input type="hidden" name="roleOper" id="roleOper" value="${ roleOper}"/>
		<div class="control-group">
			<label class="control-label">角色名称:</label>
			<div class="controls">
				<input  type="text" htmlEscape="false" maxlength="50" class="required" name="name" value="${userRole.name }"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
 
 		<div class="control-group">
			<label class="control-label">角色授权:</label>
			<div class="controls">
				<div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
			</div>
			<input type="hidden" name="menuTreeIds" id="menuTreeIds"/>
 		</div>
 
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>		
		
		
		
	</form:form>



</body>
</html>
 