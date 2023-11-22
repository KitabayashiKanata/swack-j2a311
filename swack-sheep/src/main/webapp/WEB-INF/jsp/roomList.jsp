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
<script src="js/count.js"></script>

<style>
     /* クリック領域を示すためのスタイル */
     .clickable {
         cursor: pointer;
     }
</style>

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
					<a onclick="clickRoom('${item.roomName}', '${item.roomId}')" class="btn_design">
					<button href="" class="color-change" style="left: 173%;">ホームで開く</button>
					<button onclick='location.href="https://convertio.co/ja/webp-png/"; event.stopPropagation()' class="color-change clickable" style="left: 166%;">参加する</button>
					
						<c:out value="${item.roomName}" />
					</a>
				</td></tr>
				<br>
			</c:forEach>
        </table>
        <div class="btn-gradient">
			<button onclick="history.go(reCnt()), delSession()"><span>　戻る　</span></button>
		</div>
    </div>
    
    <form name="roomForm" action="MainServlet" method="get">
   		<input type="hidden" name="roomId" value="" id="roomId">
   		<input type="hidden" name="roomName" value="" id="roomName" />
    </form>
    

</body>
</html>