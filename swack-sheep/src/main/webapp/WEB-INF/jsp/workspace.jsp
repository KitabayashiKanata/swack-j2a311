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
<link rel="stylesheet" href="css/workspace.css">
<link rel="stylesheet" href="css/invitation.css">
<script src="js/count.js"></script>

</head>
<body>

<div class="bg"></div>
<div class="bg bg2"></div>
<div class="bg bg3"></div>

	<div class="container">

		<h1>
		<div class="wave back">
        <span><img src="images/favicon.ico" alt="Swack" style="width: 60px; height: 60px;"></span>
        <span>S</span>
        <span>w</span>
        <span>a</span>
        <span>c</span>
        <span>k</span>
        </div>
        </h1>
        <h2><span class="marker-normal">　ワークスペースにサインインする　</span></h2>

		<div class="card">
			<p class="error">${errorMsg}</p>
			
			<form action="WorkspaceServlet" method="post">
			    <input type="username" name="userName" placeholder="ユーザー名"><br>
				<input type="email" name="mailAddress" placeholder="メールアドレス"><br>
				<input type="password" name="password" placeholder="パスワード"><br>
				<input onclick="count(reCnt())" type="submit" value="ワークスペースに参加">
			</form>
		</div>
		<div class="btn-gradient">
			<button onclick="history.go(reCnt()), delSession()"><span>　戻る　</span></button>
			<div class="sample_link12">
				<a href="PasswordUserServlet">
					<span data-text="ここをクリック">パスワード変更</span>
				</a>
			</div>
		</div>
	</div>
</body>
</html>