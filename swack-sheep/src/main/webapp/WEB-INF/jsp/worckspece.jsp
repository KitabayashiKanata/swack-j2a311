<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

</head>
<body>
	<div class="container">

		<h1>Swack</h1>
		<h2>ワークスペースにサインインする</h2>

		<div class="card">
			<form action="LoginServlet" method="post">
			    <input type="username" name="name" placeholder="ユーザー名"><br>
				<input type="email" name="mailaddress" placeholder="メールアドレス"><br>
				<input type="password" name="password" placeholder="パスワード"><br>
				<input type="submit" value="ワークスペースに参加">
			</form>
		</div>
		
	</div>
</body>
</html>