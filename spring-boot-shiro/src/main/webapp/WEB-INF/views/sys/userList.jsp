<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<%@ include file="/WEB-INF/views/include/bootstarpTable.jsp"%>
	<meta name="decorator" content="default"/>
       <script type="text/javascript">
		
       
  	 <c:if test="${not empty message }">	
      	//$.jBox.tip("正在XX，你懂的...", 'loading');
   		// 模拟2秒后完成操作
   		window.setTimeout(function () { $.jBox.tip('${message}', 'success'); }, 3000);
      </c:if>
  		
       $(function () {
		 	$("#search").bind("click", initTable);		 	
		});

		
	 <tableTagBootStarp:table id="cusTable">
		<btables>    
	    	 <btable url="<c:url value='/web/sys/user/list'/>" onLoadSuccess="" onLoadError=""  queryParams="queryParams" tableFunName="initTable">
	    	 	<columns>
	    	 		<column field="id" title="用户ID" align="center" formatter=""/>
	    	 		<column field="userName" title="用户名" align="center" formatter=""/>
	    	 		<column field="operate" title="操作" align="center" formatter="operFormatter"/>
	    	 	</columns>
	    	 	<queryParams>
	    	 		<queryParam name="userName"/>
	    	 	</queryParams>
	    	 </btable>
    	 </btables>
    </tableTagBootStarp:table>		
    
    
    
 	function forwardEdit(id){
 		window.location.href='${ctx}/web/sys/user/form?userOper=MODIFY&id='+id;
 	}
		
    
    function operFormatter(value, row, index){
        var str =[
                     '<a class="like" href="javascript:void(0)" title="删除">',
                     '<i class="icon-remove">删除</i>',
                     '</a>  ',
                     '<a class="remove" href="javascript:void(0)" onclick="forwardEdit('+"'"+row.id+"'"+');" title="编辑">',
                     '<i class="icon-edit">编辑</i>',
                     '</a>'
                 ].join('');	
        return str;
    	
    }

	</script>	
	
	
</head>
 
 <body>
 	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/web/sys/user/list">用户列表</a></li>
		<li><a href="${ctx}/web/sys/user/form?userOper=ADD">用户添加</a></li>
	</ul>
	
 	<form:form id="searchForm"  action="${ctx}/sys/user/listData" method="post" class="form-horizontal bs-form-title">
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
 		<input class="btn btn-primary  pull-right" type="button" id="search" value="查询"/>
 	</pre>
 
 
    <table id="cusTable"
           data-toggle="table">
    </table>
        


</body>


 
</html>