<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>栏目列表</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}<%--
		.ztree li span.button.level0, .ztree li a.level0 {display:none;height:0;}
		.ztree li ul.level0 {padding:0;background:none;}--%>
		.accordion-inner{padding:2px;}
	</style>
	<script type="text/javascript">
		var tree,key,lastValue="";  
		var setting = {
				data: {
					simpleData: {
						enable: true
					}
				},
				check: {
					enable: true,
					chkStyle: "radio",
					radioType: "level"
				},
				callback: {
					beforeCheck: beforeCheck,
					onCheck: onCheck
				}				
				
		};
		$(document).ready(function(){
			
			var zNodes=[
			         {id:0, pId:0, name:"角色列表"},
		            <c:forEach items="${roleList}" var="tpl">
		            {id:'${tpl.id}', pId:0, name:"${tpl.name}", url:"${ctx}/web/sys/role/roleUserList/${tpl.id}", target:"roleMainFrame"},
		            </c:forEach>];
			for(var i=0; i<zNodes.length; i++) {
				// 移除父节点
				//if (zNodes[i] && zNodes[i].id == 1){
				//	zNodes.splice(i, 1);
				//}<%--
				// 并将没有关联关系的父节点，改为父节点
				var isExistParent = false;
				for(var j=0; j<zNodes.length; j++) {
					if (zNodes[i].pId == zNodes[j].id){
						isExistParent = true;
						break;
					}
				}
				if (!isExistParent){ 
					zNodes[i].pId = 1;
				}--%>
			}
			// 初始化树结构
			tree = $.fn.zTree.init($("#tree"), setting, zNodes);
			// 展开第一级节点
			var nodes = tree.getNodesByParam("level", 0);
			for(var i=0; i<nodes.length; i++) {
				tree.expandNode(nodes[i], true, true, false);
			}
			// 展开第二级节点
			nodes = tree.getNodesByParam("level", 1);
			for(var i=0; i<nodes.length; i++) {
				tree.expandNode(nodes[i], true, true, false);
			}
			wSize();
			
			
			var nodes = tree.getNodes();
			var node = nodes[0].children[0];
			if(node){
				tree.selectNode(node);
				var url=node.url;
				$(window.parent.document).find("#roleMainFrame").attr("src",url); 
			}
			tree.checkNode(node);
			$("#add").bind("click", addForward);
			$("#modify").bind("click", modify);
			$("#remove").bind("click", remove);
			
			key = $("#key");
			key.bind("focus", focusKey).bind("blur", blurKey).bind("change cut input propertychange", searchNode);
			key.bind('keydown', function (e){if(e.which == 13){searchNode();}});
			setTimeout("search();", "300");
			
		});
		function addForward(){
			$(window.parent.document).find("#roleMainFrame").attr("src","<c:url value='/web/sys/role/roleForm?oper=ADD' />"); 
		}
		function modify(){
			nodes = tree.getCheckedNodes();
			if(nodes.length >0){
				var roleId =nodes[0].id;
				var url = "<c:url value='/web/sys/role/roleForm?oper=MODIFY' />"+'&roleId='+roleId;
				$(window.parent.document).find("#roleMainFrame").attr("src",url);
			}else{
				alert('请选择权限')
			}
			
		}	
		function onCheck(e, treeId, treeNode) {
		}				
		function beforeCheck(treeId, treeNode) {
		}
		
		$(window).resize(function(){
			wSize();
		});
		function wSize(){
			$(".ztree").width($(window).width()-16).height($(window).height()-62);
			$(".ztree").css({"overflow":"auto","overflow-x":"auto","overflow-y":"auto"});
			$("html,body").css({"overflow":"hidden","overflow-x":"hidden","overflow-y":"hidden"});
		}
		
		function search() {
			$("#search").slideToggle(200);
			$("#txt").toggle();
			$("#key").focus();
		}
				
		
	  	function focusKey(e) {
			if (key.hasClass("empty")) {
				key.removeClass("empty");
			}
		}
		function blurKey(e) {
			if (key.get(0).value === "") {
				key.addClass("empty");
			}
			searchNode(e);
		}		
		
		//搜索节点
		function searchNode() {
			// 取得输入的关键字的值
			var value = $.trim(key.get(0).value);
			
			// 按名字查询
			var keyType = "name";<%--
			if (key.hasClass("empty")) {
				value = "";
			}--%>
			
			// 如果和上次一次，就退出不查了。
			if (lastValue === value) {
				return;
			}
			
			// 保存最后一次
			lastValue = value;
			
			var nodes = tree.getNodes();
			// 如果要查空字串，就退出不查了。
			if (value == "") {
				showAllNode(nodes);
				return;
			}
			hideAllNode(nodes);
			nodeList = tree.getNodesByParamFuzzy(keyType, value);
			updateNodes(nodeList);
		}		
		
		//隐藏所有节点
		function hideAllNode(nodes){			
			nodes = tree.transformToArray(nodes);
			for(var i=nodes.length-1; i>=0; i--) {
				tree.hideNode(nodes[i]);
			}
		}		
		
		
		//显示所有节点
		function showAllNode(nodes){			
			nodes = tree.transformToArray(nodes);
			for(var i=nodes.length-1; i>=0; i--) {
				/* if(!nodes[i].isParent){
					tree.showNode(nodes[i]);
				}else{ */
					if(nodes[i].getParentNode()!=null){
						tree.expandNode(nodes[i],false,false,false,false);
					}else{
						tree.expandNode(nodes[i],true,true,false,false);
					}
					tree.showNode(nodes[i]);
					showAllNode(nodes[i].children);
				/* } */
			}
		}
		
		//更新节点状态
		function updateNodes(nodeList) {
			tree.showNodes(nodeList);
			for(var i=0, l=nodeList.length; i<l; i++) {
				
				//展开当前节点的父节点
				tree.showNode(nodeList[i].getParentNode()); 
				//tree.expandNode(nodeList[i].getParentNode(), true, false, false);
				//显示展开符合条件节点的父节点
				while(nodeList[i].getParentNode()!=null){
					tree.expandNode(nodeList[i].getParentNode(), true, false, false);
					nodeList[i] = nodeList[i].getParentNode();
					tree.showNode(nodeList[i].getParentNode());
				}
				//显示根节点
				tree.showNode(nodeList[i].getParentNode());
				//展开根节点
				tree.expandNode(nodeList[i].getParentNode(), true, false, false);
			}
		}
				
		function remove(){
			nodes = tree.getCheckedNodes();
			if(nodes.length >0){
				var roleId =nodes[0].id;
				var url ='<c:url value="/web/sys/role/deleteRole/" />'+roleId;
				var delTxt = '要删除角色:'+nodes[0].name+'吗？';
				return confirmx(delTxt, url)
			}else{
				top.$.jBox.tip("请选择角色", 'info');
			}			
		}
		
		
		
	</script>
</head>
<body>
	<div class="accordion-group">

		<div id="left" class="accordion-group">
			<div class="accordion-heading">
		    	<a class="accordion-toggle">角色 &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    		<i class="icon-refresh" onclick="refreshTree();"></i>
		    		<i class="icon-remove" id="remove" alt="添加角色" ></i>
		    		<i class="icon-plus" id="add" alt="添加角色" ></i>
		    		<i class="icon-edit" id="modify" alt="编辑角色" ></i>
		    		<i class="icon-search" onclick="search();"></i><label id="txt"></label>
		    	</a>
		    </div>
 
 
			<div id="search" class="form-search hide" style="padding:10px 5px 0 13px;">
				<label for="key" class="control-label" style="padding:5px 5px 3px 0;">关键字：</label>
				<input type="text" class="empty" id="key" name="key" maxlength="50" style="width:110px;">
				<button class="btn" id="btn" onclick="searchNode()">搜索</button>
			</div>		    
		    
		    
		    <div id="tree" class="ztree"></div>
		</div>

		    
		
 
 
	</div>
</body>
</html>