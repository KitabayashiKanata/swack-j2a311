<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メンバー管理</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link rel="stylesheet" href="css/style.css">

<script src="js/list.js"></script>
</head>
<body>
	<div class="container">
		<h1>メンバー管理</h1>
		<table>
			<c:forEach var="user" items="${joinUserList}">
				<tr><td>
					<a onclick="clickRemoveJoin('${user.userId}')" class="hover"><c:out value="${user.userName}" /></a>
				</td></tr>
			</c:forEach>
		</table>
		<form name="removeJoinUserForm" action="JoinWorkspaceServlet" method="post">
        		<input type="hidden" name="removeUserId" value="" id="removeJoinModal"> 
        </form>
	</div>
</body>
</html>