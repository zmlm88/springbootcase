<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<%@ include file="/WEB-INF/views/include/bootstarpTable.jsp"%>
	<meta name="decorator" content="default"/>
       <script type="text/javascript">
		
       
  	 <c:if test="${not empty message }">	
   		// 模拟2秒后完成操作
   		window.setTimeout(function () { $.jBox.tip('${message}', 'success'); }, 3000);
      </c:if>
  		
       $(function () {
		 	$("#search").bind("click", initTable);		 
		});

		
	 <tableTagBootStarp:table id="userRoleTable">
		<btables>    
	    	 <btable url="<c:url value='${ctx}/web/sys/user/selectUserByRoleAndCodeDataPage'/>" onLoadSuccess="" onLoadError=""  queryParams="queryParams" tableFunName="initTable">
	    	 	<columns>
	    	 		<column field="id" title="用户ID" align="center" formatter=""/>
	    	 		<column field="userName" title="用户名" align="center" formatter=""/>
	    	 	</columns>
	    	 	<queryParams>
	    	 		<queryParam name="userName"/>
	    	 		<queryParam name="roleId"/>
	    	 	</queryParams>
	    	 </btable>
    	 </btables>
    </tableTagBootStarp:table>		

 

	</script>	
	
	
</head>
 
 <body>
	
 	<form:form id="searchForm"  action="${ctx}/web/sys/user/selectUserByRoleAndCodeDataPage" method="post" class="form-horizontal bs-form-title">
 			<input id="roleId" type="hidden" value="${roleId }"/>
			<div class="row-fluid">
			    <div class="row-fluid">
			        <div class="row-fluid">
			          <div class="span3">
			          	<label>用戶名称：</label>
			          	<input class="form-control input-medium" type="text" id="userName" value="${userListUserName}"/>
			          </div>
			          <!--
			          <div class="span3"><label>用戶状态：</label></div>
			          -->
			        </div>
			    </div>
			</div>
 	</form:form>
 	<pre class="prettyprint">
 		<input class="btn btn-primary   pull-right" type="button" id="search" value="查询"/>
 	</pre>
 
 
    <table id="userRoleTable"
           data-toggle="table">
    </table>


 


</body>


 
</html>