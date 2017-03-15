<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<%@ include file="/WEB-INF/views/include/bootstarpTable.jsp"%>
	<meta name="decorator" content="default"/>
       <script type="text/javascript">
		$(function () {
		 	initTable();
		 	$("#search").bind("click", initTable);		 	
		});
		function queryParams(pageReqeust){
			pageReqeust.userName=$("#userName").val();
			pageReqeust.pageNumber=this.pageNumber;
			return pageReqeust;
		}
		function initTable(){
			$('#cusTable').bootstrapTable('destroy');
			$("#cusTable").bootstrapTable({
				pagination: true,
				sidePagination: 'server',
				method:"post",
				url:"<c:url value='/web/sys/user/list'/>",
			    queryParams:queryParams,
				 onLoadSuccess: function(data){  //加载成功时执行
					 // alert("加载成功"+data);
				 },
				 onLoadError: function(){  //加载失败时执行
					 // layer.msg("加载数据失败", {time : 1500, icon : 2});
				 },
	            columns: [           
	                      {
	                    	  field: 'id',
	                          title: '用户ID',
	                          align: 'center'
	                      },
	                      {
	                    	  field: 'userName',
	                          title: '用户名',
	                          align: 'center'
	                      }	                      
	           ]
				           
			});
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