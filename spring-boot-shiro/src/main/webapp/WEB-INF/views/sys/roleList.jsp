<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<div id="content" class="row-fluid">
		<div id="left">
			<iframe id="roleMenuFrame" name="roleMenuFrame" src="${ctx}/web/sys/role/tree" style="overflow:visible;"
				scrolling="yes" frameborder="no" width="100%"></iframe>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<iframe id="roleMainFrame" name="roleMainFrame" src="${ctx}/web/sys/role/none" style="overflow:visible;"
				scrolling="yes" frameborder="no" width="100%"></iframe>
		</div>
	</div>
	<script type="text/javascript"> 
	
		var leftWidth = "260"; // 左侧窗口大小
		function wSize(){
			var strs=getWindowSize().toString().split(",");
			$("#roleMenuFrame, #roleMainFrame, #openClose").height(strs[0]-5);
			$("#right").width($("body").width()-$("#left").width()-$("#openClose").width()-20);
		}
		// 鼠标移动到边界自动弹出左侧菜单
		$("#openClose").mouseover(function(){
			if($(this).hasClass("open")){
				$(this).click();
			}
		});
		
		<c:if test="${not empty roleFormMessage }">
			top.$.jBox.tip("${roleFormMessage}", 'info');
		</c:if>
		
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>