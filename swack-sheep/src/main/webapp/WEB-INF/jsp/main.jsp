<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Swack - メイン画面</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/invitation.css">

</head>
<body>

	<div class="container">
		<header class="header">
			<div>${nowUser.userName}</div>
			<form action="LogoutServlet">
				<input type="submit" value="ログアウト">
			</form>
		</header>
		<section class="main">
			<div class="left">
				<h2>Swack</h2>
				<hr>
				<details open>
					<summary>ルーム</summary>
					<c:forEach var="room" items="${roomList}">
					<a class="list-name"
						href="MainServlet?roomId=${room.roomId}">
						<c:set var="privated" value="${room.privated}"/>
						<c:choose>
							<c:when test='${privated == "True"}'>
								@ ${room.roomName}
							</c:when>
							<c:otherwise>
								# ${room.roomName}
							</c:otherwise>
						</c:choose>
					</a>
					<br>
					</c:forEach>
					
				</details>
				<a href="RoomListServlet">ルーム一覧</a>
				
				<!-- モーダルウィンドウ -->
				<center>
					<a  class="modal-button" onclick="clickRoomCreate()">ルーム作成</a>
				</center>
				<div class="modal-wrapper"  id="room-modal">
				  <a href="#!" class="modal-overlay" onclick="clickOverlayClose()"></a>
				  
				  <div class="modal-window">
				    <div class="modal-content">
				    <a href="#!" class="modal-close" onclick="clickButtonClose()">✕</a>
				      <p class="modal_title">Create a channel</p>
				      <a class="modal_body">名前</a>
				      <p class="error" id="errorMsg">${errorMsg}</p>
				      <form class="modal-form" action="CreateRoomServlet" method="post">
				      	<input type="text" name="roomName" placeholder="# 例:計画-予算">
				      	<p class="radio">パブリック</p>
				      	<input type="radio" name="privated" value="public">
				      	<!-- ユーザがworkspaceadminの場合のみ表示 -->
				      	<c:set var="nowUserId" value="${nowUser.userId}"/>
				      	<c:set var="workspaceAdminFlag" value="False"/>
				      	<c:forEach var="workspaceAdminUser" items="${workspaceAdminList}">
				      		<c:if test='${nowUserId == workspaceAdminUser}'>
				      			<c:set var="workspaceAdminFlag" value="True"/>
				      		</c:if>
				      	</c:forEach>
				      	<c:choose>
				      		<c:when test='${workspaceAdminFlag == "True"}'>
								<p class="radio">プライベート</p>
				      		<input type="radio" name="privated" value="private">
							</c:when>
				      		<c:otherwise>
				      			<p>※プライベート設定は管理者のみ可能です
				      		</c:otherwise>
				      	</c:choose>
				      	<input type="submit" value="作成">
				      </form>
				    </div>
				  </div>
				</div>
				
				<details open>
					<summary>ダイレクト</summary>
					<c:forEach var="room" items="${directList}">
					<a class="list-name"
						href="MainServlet?roomId=${room.roomId}">
						# ${room.roomName}
					</a>
					<br>
					</c:forEach>
				</details>
				<!-- 仮設置です -->
				<a href="UserListServlet">ダイレクトチャット開始</a>
				<a href="WorkspaceServlet">ワークスペースに参加</a>
				<a href="InvitationRoomServlet">ルーム招待</a>
				
				<!-- モーダルウィンドウ(ルーム招待) -->
				<div class="modal-wrapper" id="modal-02">
					 <a href="#!" class="modal-overlay"></a>
					 
					 <div class="modal-window">
					   <div class="modal-content">
					    <a href="#!" class="modal-close">✕</a>
					    <p class="modal_title">#Test</p><hr><br>
					    
					    <p class="error" id="errorMsg">${errorMsg}</p>
					    <div class="flex">
						    <form class="modal-form	search_box" action="CreateRoomServlet" method="post">
					      		<input type="text" name="userName" placeholder="メンバーを検索">
					      	</form>
					      	<select name="item">
									<option value="item1">全員</option>
									<option value="item2">チャンネルマネージャー</option>
									<option value="item3">メンバー</option>
									<option value="item4">ゲスト</option>
							</select>
						</div>
					    <table class="design11">
						 	<c:forEach var="item" items="${userList}">
								<tr><td>
								<div class="modal-open">
									<a href="#modal" onclick="clickUser('${item.userName}','${item.userId}')" class="hover">
										<c:out value="${item.userName}" />
										<div class="right">→</div>
									</a>
									</div>
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
										<p><span id="uSpan2"></span><br>
										<span id="uSpan1"></span></p>
										
										<form action="InvitationRoomServlet" method="post">
											<input type="hidden" id="userID" name="userID" />
										 	<input type="hidden" id="userName" />
										 	<input type="submit" value="決定" />
										</form>
										
									</div>
								</div>
							</div>
						</div>
					   </div>
					</div>
				</div>
				
			</div>
			<div class="contents">
				<div class="contents-header">
					<h2>${nowRoom.roomName}(${nowRoom.memberCount}人)</h2>
					<hr>
				</div>
				<div id="logArea" class="contents-area">
					<!-- ログインしているユーザがアドミンか判定 -->
					<c:set var="nowUserId" value="${nowUser.userId}"/>
					<c:forEach var="adminUser" items="${roomAdminListr}">
						<c:choose>
							<c:when test="${nowUserId == adminUser}">
								<c:set var="editFlag" value="True"/>
							</c:when>
							<c:otherwise>
								<c:set var="editFlag" value="False"/>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:forEach var="log" items="${chatLogList}">
					<div class="log-area">
						<div class="log-box">
							<span class="log-name">${log.userName} </span>
							<span class="log-time">[${log.createdAt}]</span>
							<span class="log-reaction" onclick="clickMessageReaction('${log.chatLogId}')">顔</span>
							<!-- 編集と削除はユーザに権限がある場合のみ表示(if文を使用する) -->
							<c:if test='${editFlag != "True"}'>
								<c:set var="logUserId" value="${log.userId}"/>
								<c:set var="nowLogId" value="${log.chatLogId }"/>
								<c:if test="${logUserId == nowUserId}" >
									<c:set var="editFlag" value="True"/>
								</c:if>
							</c:if>
							<c:if test='${editFlag == "True"}'>
								<span class="log-edit" id="edit" onclick="clickMessageEdit('${log.chatLogId}','${log.message}')">編集</span>
								<span class="log-delete" id="delete" onclick="clickMessageDelete('${log.chatLogId}')">削除</span>
								<input type="hidden" name="chatLogId" value="${log.chatLogId}"> 
								<c:set var="editFlag" value="False"/>
							</c:if>
							<br>
							${log.message}
						</div>
					</div>
					</c:forEach>
				</div>
				<div class="contents-footer" id="massegeSubmit">
					<form action="ChatServlet" method="post" >
						<input type="hidden" name="roomId"
							 value="${nowRoom.roomId}">
						<div class="form-wrap">
							<input type="text" name="message">
							<input type="submit" value="送信">
						</div>
					</form>
				</div>
			</div>
		</section>
	</div>
	
	<!-- モーダルウィンドウ -->
	<div class="modal-wrapper" id="message-edit-modal">
	  <a href="#!" class="modal-overlay" onclick="clickOverlayClose()"></a>
	  <div class="modal-window">
	    <div class="modal-content">
	    <a href="#!" class="modal-close" onclick="clickButtonClose()">✕</a>
	      <p class="modal_title">Edit Message</p>
	      <a class="modal_body">編集メッセージを入力してください。</a>
	      <p class="error" id="errorMsg">${errorMsg}</p>
	      <form class="modal-form" action="MessageEditServlet" method="post">
	      	<input type="hidden" name="chatLogIdE" value="" id="modalChatLogIdE">
	      	<input type="text" name="newMessage" value="" id="editMessage">
	      	<input type="button" name="" value="キャンセル" onclick="clickButtonClose()">
	      	<input type="submit" value="保存">
	      </form>
	    </div>
	  </div>
	</div>
	
	<div class="modal-wrapper" id="message-delete-modal">
	  <a href="#!" class="modal-overlay" onclick="clickOverlayClose()"></a>
	  <div class="modal-window">
	    <div class="modal-content">
	    <a href="#!" class="modal-close" onclick="clickButtonClose()">✕</a>
	      <p class="modal_title">Delete Message</p>
	      <a class="modal_body">本当に削除しますか？</a>
	      <p class="error" id="errorMsg">${errorMsg}</p>
	      <form class="modal-form" action="MessageDeleteServlet" method="post">
	      	<input type="hidden" name="chatLogIdD" value="" id="modalChatLogIdD">
	      	<input type="button" name="" value="キャンセル" onclick="clickButtonClose()">
	      	<input type="submit" value="削除">
	      </form>
	    </div>
	  </div>
	</div>
	
	<div class="modal-wrapper" id="message-reaction-modal">
	  <a href="#!" class="modal-overlay" onclick="clickOverlayClose()"></a>
	  <div class="modal-window">
	    <div class="modal-content">
	    <a href="#!" class="modal-close" onclick="clickButtonClose()">✕</a>
	      <p class="modal_title">Reaction</p>
	      <a class="modal_body"></a>
	      <p class="error" id="errorMsg">${errorMsg}</p>
	      <form class="modal-form" action="MessageDeleteServlet" method="post">
	      	<input type="hidden" name="chatLogIdR" value="" id="modalChatLogIdR">
	      </form>
	    </div>
	  </div>
	</div>
	
<script src="js/modal.js"></script>

</body>
</html>