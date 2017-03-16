<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<%@ include file="/WEB-INF/views/include/bootstarpTable.jsp"%>
	<meta name="decorator" content="default"/>
       <script type="text/javascript">
		$(function () {
		 	$("#search").bind("click", initTable);		 	
		});
 
	 <tableTagBootStarp:table id="cusTable">
		<btables>    
	    	 <btable url="<c:url value='/web/sys/user/list'/>" onLoadSuccess="" id="cusTable" onLoadError=""  queryParams="queryParams" tableFunName="initTable">
	    	 	<columns>
	    	 		<column field="id" title="用户ID" align="center" formatter=""/>
	    	 		<column field="userName" title="用户名" align="center" formatter=""/>
	    	 		<column field="oper" title="操作" align="center" formatter="operFormatter"/>
	    	 	</columns>
	    	 	<queryParams>
	    	 		<queryParam name="userName"/>
	    	 	</queryParams>
	    	 </btable>
    	 </btables>
    </tableTagBootStarp:table>		
    
    function operFormatter(){
    	
    	
    }
    
    
		
	</script>	
	
	
</head>
 
 <body>
 	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/list">用户列表</a></li>
		<li><a href="${ctx}/sys/user/form">用户添加</a></li>
	</ul>
 	<form:form id="searchForm"  action="${ctx}/sys/user/listData" method="post" class="form-horizontal bs-form-title">
 
			<div class="row-fluid">
			    <div class="row-fluid">
			        <div class="row-fluid">
			          <div class="span3">
			          	<label>用戶名称：</label>
			          	<input class="form-control input-medium" type="text" id="userName"/>
			          </div>
			          <!--
			          <div class="span3"><label>用戶状态：</label></div>
			          -->
			        </div>
			    </div>
			</div>
 	</form:form>
 	<pre class="prettyprint">
 		<input class="btn btn-primary  pull-right" type="button" id="search" value="查询"/>
 	</pre>
 
 
    <table id="cusTable"
           data-toggle="table">
    </table>
        


</body>


 
</html>