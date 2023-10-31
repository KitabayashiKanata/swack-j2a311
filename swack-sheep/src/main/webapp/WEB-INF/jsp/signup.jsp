<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Swack - 新規アカウント</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/signup.css">
<script src="js/count.js"></script>
</head>
<body>

<div class="bg"></div>
<div class="bg bg2"></div>
<div class="bg bg3"></div>


	<div class="container">

		<h1><span class="back"><img src="images/favicon.ico" alt="Swack" style="width: 30px; height: 30px;">Swack</span></h1>
		<h2><span class="marker-normal">　アカウントの作成　</span></h2>

		<div class="card">
			<p class="error">${errorMsg}</p>
			
			<form action="CreateUserServlet" method="post">
				<input type="hidden" id="backCnt" name="backCnt">
				<input type="text" name="userName" placeholder="ユーザー名"><br>
				<input type="email" name="mailAddress" placeholder="メールアドレス"><br>
				<input type="password" name="password" placeholder="パスワード"><br>
				<input type="password" name="password2" placeholder="パスワード再確認"><br>
				<input onclick="count(reCnt())" type="submit" value="作成">
			</form>
		</div>
		
		<div class="border-box">
			<a href="" class="btn_design"><img src="images/google.png" alt="Swack" style="width: 22px; height: 22px;">Googleで続行する</a>
        </div>
        
        <div class="border-box">
			<a href="" class="btn_design"><img src="images/apple.png" alt="Swack" style="width: 32px; height: 25px;">Appleで続行する</a>
        </div>
		<button onclick="history.go(reCnt()), history.go(-1)">戻る</button>
	</div>
</body>
</html>