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
<script src="js/list.js"></script>
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
		<h2><span class="marker-normal">　ダイレクトチャットを開始する　</span></h2>
		<table class="sample">
			<c:forEach var="item" items="${userList}">
				<tr><td>
				<div class="modal-open">
					<a onclick="clickUser('${item.userName}','${item.userId}')" class="btn_design">
						<c:out value="${item.userName}" />
						<div class="right">→</div>
					</a>
					</div>
				</td></tr>
				<br>
			</c:forEach>
        </table>
        <div class="btn-gradient">
			<button onclick="history.go(reCnt()), delSession()"><span>　戻る　</span></button>
		</div>
	</div>
	
	<form name="userForm" action="CreateRoomServlet" method="post">
     	<input type="hidden" name="userId" value="" id="userId">
     	<input type="hidden" name="userName" value="" id="userName" />
    </form>

</body>
</html>