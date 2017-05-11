<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资源管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	
	<script type="text/javascript">
		loading("加载中...");
		closeLoading();
	</script>
	
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 4}).show();
		});
    </script>	
</head>	
    <body>
		<ul class="nav nav-tabs">
			<li class="active"><a href="${ctx}/sys/menu/">资源列表</a></li>
		</ul>
    
		<table id="treeTable"   class="table table-striped table-bordered table-condensed ">
			<tr>
		        <td>资源</td>
		        <td>操作</td>
		    </tr>
		    <c:set var="indexSeat" value="0"></c:set>
			<c:forEach items="${userMenuListRight.subList }" var="userMenu" varStatus="vstatus">
			    <tr id="${userMenu.tMenu.id }">
			        <td>${userMenu.tMenu.name }</td>
			        <td>
			        	<a href="${ctx}/web/sys/user/menu/form?parentId=${userMenu.tMenu.id}&oper=ADD&modelId=${modelId}">添加下级资源</a> 
			        </td>
			    </tr>
			    
			    <c:forEach items="${userMenu.subList}" var="subListMenu">
	 		    	 <tr id="${subListMenu.tMenu.id }" pId="${userMenu.tMenu.id }">
	 		    	   <td>${subListMenu.tMenu.name }</td>
				        <td>
				        	<a href="${ctx}/web/sys/user/menu/form?parentId=${subListMenu.tMenu.parentid}&userMenuOper=MODIFY&modelId=${modelId}&id=${subListMenu.tMenu.id}">修改资源</a>
				        	<a href="${ctx}/web/sys/user/menu/form?parentId=${subListMenu.tMenu.id}&userMenuOper=ADD&modelId=${modelId}&id=${subListMenu.tMenu.id}">添加下级资源</a> 
						</td>	 		    	   
	 		    	 </tr>
			    </c:forEach>
			</c:forEach>
		</table>
		
    </body>
</html>    
