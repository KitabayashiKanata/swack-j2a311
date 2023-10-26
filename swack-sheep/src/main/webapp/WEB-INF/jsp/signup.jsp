<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

</head>
<body>
	<div class="container">

		<h1><img src="images/favicon.ico" alt="Swack" style="width: 30px; height: 30px;">Swack</h1>
		<h2>アカウントを作成してください</h2>

		<div class="card">

			<form action="LoginServlet" method="post">
				<input type="text" name="userName" placeholder="ユーザー名"><br>
				<input type="email" name="mailAddress" placeholder="メールアドレス"><br>
				<input type="password" name="password" placeholder="パスワード"><br>
				<input type="password" name="password2" placeholder="パスワード再確認"><br>
				<input type="submit" value="作成">
			</form>
			
		</div>
		
		
	</div>
</body>
</html>