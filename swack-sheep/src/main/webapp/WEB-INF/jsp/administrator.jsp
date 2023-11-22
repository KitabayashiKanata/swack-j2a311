<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者端末</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/administrator.css">
<script src="js/list.js"></script>
<script src="js/count.js"></script>
</head>

<body class="icarus">

	<div class="container">
		<h1>管理者操作画面</h1>
		<h2>アカウントロックの解除</h2>
		<c:choose>
			<c:when test="${lockList != null}">
				<table class="sample">
					<c:forEach var="item" items="${lockList}">
						<tr><td>
						<div class="modal-open">
							<a onclick="clickUser('${item.userName}','${item.userId}'), count(reCnt())" class="btn_design">
								<c:out value="${item.userName}" />
								<div class="right">→<img src="images/key.png" style="width: 25px; height: 25px;"></div>
							</a>
							</div>
						</td></tr>
						<br>
					</c:forEach>
		        </table>
		    </c:when>
	        <c:otherwise>
	        	<a>アカウントロックされているユーザは存在しません</a>
	        </c:otherwise>
        </c:choose>
        
		<div class="btn-gradient">
			<button onclick="history.go(reCnt()), delSession()"><span>　戻る　</span></button>
		</div>
	</div>
	
	<form name="userForm" action="LockClearServlet" method="post">
   		<input type="hidden" name="userId" value="" id="userId">
   		<input type="hidden" name="userName" value="" id="userName" />
    </form>
</body>

</html>