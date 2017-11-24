<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="image/favicon.ico">
<script src="js/jquery-2.2.4.min.js" type="text/javascript"></script>
<title>前台注册页</title>
<script type="text/javascript">
	$(function() {
		$('#register').click(function() {
			var name = $('#name').val();
			var pwd = $('#pwd').val();
			var pwd2 = $('#pwd2').val();
			if (name.length == 0) {
				alert('用户名不能为空');
				return;
			}
			if (pwd.length == 0) {
				alert('密码不能为空');
				return;
			}
			if (pwd != pwd2) {
				alert('两次输入密码不一致');
				return;
			}
			$.ajax({
				url: 'User/register.action',
				type: 'post',
				data: {
					username: name,
					password: pwd
				},
				dataType: 'json',
				success: function(result) {
					if (result.status == 1) {
						alert(result.prompt);
						window.location.href = 'User/index';
					} else {
						alert(result.prompt);
					}
				},
				fail: function(result) {
					alert(result);
				}
			})
		})
	});
</script>
</head>
<body>
	<div>
		<div>
			用户名<input id="name" type="text">
		</div>
		<div>
			密码<input id="pwd" type="password">
		</div>
		<div>
			确认密码<input id="pwd2" type="password">
		</div>
		<div>
			<input id="register" type="button" value="注册">
		</div>
	</div>
</body>
</html>