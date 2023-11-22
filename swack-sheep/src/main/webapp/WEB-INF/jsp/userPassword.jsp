<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Swack - ワークスペースパスワードの変更</title>
<link rel="shortcut icon" href="images/favicon.ico">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/workspace.css">
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
        <h2><span class="marker-normal">　パスワードの変更　</span></h2>

		<div class="card">
			<p class="error">${errorMsg}</p>
			
			<form action="PasswordUserServlet" method="post">
				<input type="email" name="mailAddress" placeholder="メールアドレス"><br>
				<input type="password" name="password" placeholder="現在のパスワード"><br>
				<input onclick="count(reCnt())" type="submit" value="次へ">
			</form>
		</div>
		<div class="btn-gradient">
		<button onclick="history.go(reCnt()), delSession()">　戻る　</button>
		</div>
	</div>
</body>
</html>