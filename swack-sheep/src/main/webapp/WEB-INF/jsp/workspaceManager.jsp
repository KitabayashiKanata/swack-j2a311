<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メンバー管理</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/workspaceManager.css"

<script src="js/list.js"></script>
</head>
<body>
	<div class="container">
		<h1>メンバー管理</h1>
		<button href="" class="button-container">メンバーを招待する</button>
		<table border="1">
        <tr>
            <th>氏名</th>
            <th>メールアドレス</th>
        </tr>
        </table>
		<c:choose>
			<c:when test="${joinUserList != null}">
				<table>
					<c:forEach var="user" items="${joinUserList}">
						<tr><td>
							<a onclick="clickRemoveJoin('${user.userId}')" class="hover"><c:out value="${user.userName}" /></a>
						</td></tr>
					</c:forEach>
				</table>
			</c:when>
	        <c:otherwise>
	        	<a>参加しているユーザーは存在していません</a>
	        </c:otherwise>
	    </c:choose>
	    </table>
		<form name="removeJoinUserForm" action="JoinWorkspaceServlet" method="post">
        		<input type="hidden" name="removeUserId" value="" id="removeJoinModal"> 
        </form>
	</div>
</body>
</html>