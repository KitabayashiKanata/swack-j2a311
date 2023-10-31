<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Swack - ログイン画面</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/login.css">

</head>
<body>

 <div class="bg"></div>
<div class="bg bg2"></div>
<div class="bg bg3"></div>

	<div class="container">

		<h1><span class="back"><img src="images/favicon.ico" alt="Swack" style="width: 30px; height: 30px;">Swack</span></h1>
		<h2><span class="marker-normal">　ログイン　</span></h2>

		<div class="card">
			<p class="error">${errorMsg}</p>

			<form action="LoginServlet" method="post">
				<input type="email" name="mailAddress" placeholder="メールアドレス"><br>
				<input type="password" name="password" placeholder="パスワード"><br>
				<input type="submit" value="ログイン">
			</form>
		</div>

		<a href="CreateUserServlet">新規登録画面へ</a>
	</div>
</body>
</html>