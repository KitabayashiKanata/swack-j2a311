<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Swack - ワークスペースへの参加</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/workspaceList.css">
<script src="js/list.js"></script>
</head>
<body>
	<div class="container">

		<h1>Swack</h1>
		<h2>ワークスペースにサインインする</h2>
		<table class="sample">
			<tr><td><p>${nowUser.userName}のワークスペース</p></td></tr>
			<c:forEach var="item" items="${workspaceList}">
				<tr><td>
				<div class="modal-open">
					<a href="#modal" onclick="clickWorkspace('${item.workspaceName}','${item.workspaceID}')" class="hover">
						<c:out value="${item.workspaceName}" />
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
						<p><span id="wSpan2"></span><br>
						<span id="wSpan1"></span></p>
						
						<form action="MainServlet" method="get">
							<input type="hidden" id="workspaceID" />
						 	<input type="hidden" id="workspaceName" />
						 	<input type="submit" value="決定" />
						</form>
						
					</div>
				</div>
			</div>
		</div>

	</div>

</body>
</html>