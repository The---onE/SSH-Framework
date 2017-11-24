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
<title>前台首页</title>
<script type="text/javascript">
	$(function() {

	});

	function logout() {
		$.ajax({
			url : 'User/logout.action',
			type : 'post',
			dataType : 'json',
			success : function(result) {
				if (result.status == 1) {
					alert(result.prompt);
					window.location.reload();
				} else {
					alert(result.prompt);
				}
			},
			fail : function(result) {
				alert(result);
			}
		})
	}
</script>
</head>
<body>
	<div>
		<c:if test="${user == null}">
			<a href="User/login">登录</a>
			<a href="User/register">注册</a>
		</c:if>
		<c:if test="${user != null}">
			欢迎 ${user.username}!
			<a href="javascript:logout()">注销</a>
		</c:if>
	</div>
</body>
</html>