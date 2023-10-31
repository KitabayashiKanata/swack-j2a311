<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/workspaceList.css">
<script src="js/list.js"></script>
</head>
<body>
	<div class="container">

		<h1>test</h1>
		<p class="error">${errorMsg}</p>
		<table class="sample">
			<c:forEach var="item" items="${userList}">
				<tr><td>
				<div class="modal-open">
					<a href="#modal" onclick="clickUser('${item.userName}','${item.userId}')" class="hover">
						<c:out value="${item.userName}" />
						<div class="right">→</div>
					</a>
					</div>
				</td></tr>
				<br>
			</c:forEach>
        </table>
        
        <div class="modal" id="modal">
		    <a href="#!" class="overlay"></a>
		    <div class="modal-wrapper">
				<div class="modal-contents">
		        	<a href="#!" class="modal-close">✕</a>
					<div class="modal-content">
						<p><span id="uSpan2"></span><br>
						<span id="uSpan1"></span></p>
						
						<form action="InvitationRoomServlet" method="post">
							<input type="hidden" id="userID" name="userID" />
						 	<input type="hidden" id="userName" />
						 	<input type="submit" value="決定" />
						</form>
						
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>