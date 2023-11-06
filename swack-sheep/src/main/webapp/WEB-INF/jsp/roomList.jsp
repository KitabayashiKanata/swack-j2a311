<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Swack - ルームの選択</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/roomList.css">
<script src="js/list.js"></script>
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
		<h2><span class="marker-normal">　ルームを選択　</span></h2>
		<table class="sample">
			<c:forEach var="item" items="${roomList}">
				<tr><td>
					<a href="#modal" onclick="clickRoom('${item.roomName}', '${item.roomId}')" class="btn_design">
						<c:out value="${item.roomName}" />
						<div class="right">→</div>
					</a>
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
						<p><span id="rSpan2"></span><br>
						<span id="rSpan1"></span></p>
						
						<form action="MainServlet" method="get">
							<input type="hidden" id="roomID" name="roomId" />
						 	<input type="hidden" id="roomName" />
						 	<input type="submit" value="決定" />
						</form>
						
					</div>
				</div>
			</div>
		</div>

	</div>

</body>
</html>