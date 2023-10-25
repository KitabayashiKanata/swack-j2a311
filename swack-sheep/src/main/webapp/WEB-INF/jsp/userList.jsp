<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Swack - ダイレクトメッセージの開始</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/workspaceList.css">
</head>
<body>
	<div class="container">

		<h1>Swack</h1>
		<h2>ワークスペースにサインインする</h2>
		<table class="sample">
			<c:forEach var="item" items="${userList}">
				<tr><td>
					<a onclick="clickWorkspace('${item}")" class="hover4">
						<c:out value="${item}" />
						<div class="right">→</div>
					</a>
				</td></tr>
				<br>
			</c:forEach>
        </table>

	</div>

</body>
</html>