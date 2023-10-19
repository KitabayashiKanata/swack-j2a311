<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Swack - メイン画面</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/main.css">

</head>
<body>

	<div class="container">
		<header class="header">
			<div>${nowUser.userName}</div>
			<form action="LogoutServlet">
				<input type="submit" value="ログアウト">
			</form>
		</header>
		<section class="main">
			<div class="left">
				<h2>Swack</h2>
				<hr>
				<details open>
					<summary>ルーム</summary>
					<c:forEach var="room" items="${roomList}">
					<a class="list-name"
						href="MainServlet?roomId=${room.roomId}">
						# ${room.roomName}
					</a>
					<br>
					</c:forEach>
				</details>

				<details open>
					<summary>ダイレクト</summary>
					<c:forEach var="room" items="${directList}">
					<a class="list-name"
						href="MainServlet?roomId=${room.roomId}">
						# ${room.roomName}
					</a>
					<br>
					</c:forEach>
				</details>
			</div>
			<div class="contents">
				<div class="contents-header">
					<h2>${nowRoom.roomName}(${nowRoom.memberCount}人)</h2>
					<hr>
				</div>
				<div id="logArea" class="contents-area">
					<c:forEach var="log" items="${chatLogList}">
					<div class="log-area">
						<div class="log-box">
							<span class="log-name">${log.userName} </span>
							<span class="log-time">[${log.createdAt}]</span><br>
							${log.message}
						</div>
					</div>
					</c:forEach>
				</div>
				<div class="contents-footer">
					<form action="ChatServlet" method="post">
						<input type="hidden" name="roomId"
							 value="${nowRoom.roomId}">
						<div class="form-wrap">
							<input type="text" name="message">
							<input type="submit" value="送信">
						</div>
					</form>
				</div>
			</div>
		</section>

	</div>


<script src="js/main.js"></script>

</body>
</html>