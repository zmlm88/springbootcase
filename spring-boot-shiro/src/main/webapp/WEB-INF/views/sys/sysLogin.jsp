<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>登录</title>
<meta name="decorator" content="login" />

 
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({
				messages: {
					userName: {required: "请填写用户名."},password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				} 
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>

</head>
<body>

	<div class="loginMain newuseryd"
		style="top: 50%; margin-top: -280.5px; height: 561px;">

		<div class="loginBox1">
			<!-- logo 
			<div class="logo" style="cursor: pointer"
				onclick="#">
				<img src="#">
			</div>
			-->
			<h2>stoken科技</h2>

			<br/>
			<div class="login-content ">
				<div class="form">
				<form id="loginForm" class="form-signin"   action="${ctx}/sys/login" method="post">	
					<c:if test="${not empty loginErrorMessage }">					
					<div class="form-group clearfix" style="margin-bottom: 25px !important;">						
						<div class="noticemsg">
							  <div class="alert alert-danger">  
								<button data-dismiss="alert" class="close">×</button>
								<label id="loginError" class="error">${loginErrorMessage}</label>
							</div>
						</div>
					</div>	
					</c:if>	
						<div class="form-group clearfix" >
							<div class="usernameBox">
								<span class="glyphicon glyphicon-user"></span> <em></em>  
								<input style="display:none" type="text"/>
								<input style="display:none" type="password"/>
								<input type="text" autocomplete="off" maxlength="60" name="userName" class="form-control required" id="userName" placeholder="手机号/工号"/>									
							</div>
						</div>
						<div class="form-group clearfix">
							<div class="passwordBox">
								<span class="glyphicon glyphicon-lock"></span> <em></em>
								<input type="password" autocomplete="off" maxlength="60" name="password" class="form-control required" id="password" placeholder="请输入密码">
								
							</div>
						</div>


						<!--自动登录以及忘记密码
						<div class="form-group clearfix"
							style="margin-bottom: 5px !important;">
							<div class="checkbox">
								<label> <input type="checkbox" name="rem" value="1">下次自动登录
								</label> <span class="pull-right"><a
									href="#">忘记密码？</a></span>
							</div>
						</div>
						-->
						<div class="form-group">
							<button type="submit" class="btn loginBtn">登录</button>
						</div>
						<!-- 
						<div class="form-group reminderDiv">
							<p style="color: #333; font-size: 14px;">
								还没有账号？<a style="font-size: 14px;"
									href="http://user.ichunqiu.com/register/index"
									class="nowRegister">立即注册</a> <span id="http_referer"
									style="display: none">http://www.ichunqiu.com/</span>
							</p>
						</div>
						 -->
					</form>


				</div>
			</div>
		</div>
		<div class="copyrights">
		Copyright &copy;  - Powered By  善林科技
		</div>
	</div>


	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
</body>
</html>