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
<link rel="stylesheet" href="css/menu.css">



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
				<h3><a id="target1" class="menu-botton" onclick="clickNameCreate1()">${workspaceName}</a></h3>
				<hr>
				
				<details open>
					<summary><a id="target2" class="menu-botton" onContextmenu="return false;" onclick="clickNameCreate2()">ルーム</a></summary>
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
				
				<!-- モーダルウィンドウ -->
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
				      	<input type="radio" name="privated" value="public" checked>
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
					<summary><a id="target3" class="menu-botton" onContextmenu="return false;" onclick="clickNameCreate3()">ダイレクト</a></summary>
					<c:forEach var="room" items="${directList}">
					<a class="list-name"
						href="MainServlet?roomId=${room.roomId}">
						# ${room.roomName}
					</a>
					<br>
					</c:forEach>
				</details>
				
			</div>
			<div class="contents">
				<div class="contents-header">
					<h2 class="contents-inline">${nowRoom.roomName}(${nowRoom.memberCount}人)</h2>
					<!-- 改行させないCSS -->
					<a class="contents-inline contents-button" onclick="clickUserList()"><img src="images/plus.png" alt="人追加" style="width: 30px; height: 30px;"></a>
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
							<!-- <span class="log-reaction" onclick="clickMessageReaction('${log.chatLogId}')">顔</span> -->
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
		   </div>
		</div>
	</div>
	
	<!-- ワークスペースにユーザー追加 -->
	<div class="modal-wrapper" id="work-invitation-modal">
	  	<a href="#!" class="modal-overlay" onclick="clickOverlayClose()"></a>
	  	<div class="modal-window">
	    <div class="modal-content">
	    	<a href="#!" class="modal-close" onclick="clickButtonClose()">✕</a>
	      	<a class="modal_body">ワークスペースにユーザーを招待する</a>
	      	<p class="error" id="errorMsg">${errorMsg}</p>
	      	<!-- 参加させたいユーザのメールアドレスを入力 -->
        	<form name="invitationUserForm" action="InvitationWorkspaceServlet" method="post">
        		<input type="email" name="invitationMailaddress" value="" id="invitationMailaddressModal"> 
        		<input type="button" name="" value="キャンセル" onclick="clickButtonClose()">
	      		<input type="submit" value="保存">
        	</form>
	    </div>
	  </div>
	</div>
	
	<!-- ルームにユーザー追加 -->
	<div class="modal-wrapper" id="user-list-modal">
	  	<a href="#!" class="modal-overlay" onclick="clickOverlayClose()"></a>
	  	<div class="modal-window">
	    <div class="modal-content">
	    	<a href="#!" class="modal-close" onclick="clickButtonClose()">✕</a>
	      	<a class="modal_body">ルームにユーザーを招待する</a>
	      	<p class="error" id="errorMsg">${errorMsg}</p>
	      	<!-- ワークスペースに参加しているユーザー一覧を出す -->
	      	<table class="design11">
			<c:forEach var="item" items="${userList}">
				<tr><td>
				<div class="modal-open colorchangeanime_bg">
					<a href="#modal" onclick="clickUser2('${item.userId}')" class="hover">
						<c:out value="${item.userName}" />
						<div class="right">→</div>
					</a>
				</div>
				</td></tr>
				<br>
			</c:forEach>
        	</table>
        	<form name="invitationUserForm" action="InvitationRoomServlet" method="post">
        		<input type="hidden" name="invitationUserId" value="" id="invitationUserIdModal"> 
        	</form>
	    </div>
	  </div>
	</div>
	
	<!-- メッセージ編集 -->
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
	
	<!-- メッセージ削除 -->
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
	
	<!-- メッセージリアクション -->
	<div class="modal-wrapper modal-wrapper2" id="message-reaction-modal">
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
	
	<!-- ワークスペースメニュー -->
	<div class="modal-wrapper modal-wrapper2" id="name-modal1">
	  <a href="#!" id="overlay1" class="modal-overlay2" onclick="clickOverlayClose()"></a>
	  <div class="modal-window2">
	    <div class="modal-content">
	      <p class="modal_title">名前</p>
	      <div class="nemu_body">
		      <div class="nemu_body">
				  <ul class="menu">
					  <li><hr></li>
					  <li><a onclick="clickButtonClose()" href="WorkspaceServlet">ワークスペースに参加</a></li>
					  <li><a onclick="clickButtonClose()" href="">ワークスペースを切り替える</a></li>
					  <li><hr></li>
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
								<li><a onclick="clickButtonClose()" href="JoinWorkspaceServlet">メンバー管理</a></li>
							</c:when>
				      	</c:choose>
					  <li><a onclick="clickButtonClose(),clickInvitationWorkspace()">メンバー追加</a></li>
					  <!-- <c:if test="${adminUser == true}"> -->
					  <!-- </c:if> -->
				  </ul>
		  	  </div>
		  </div>
	    </div>
	  </div>
	</div>
	
	<!-- ルームメニュー -->
	<div class="modal-wrapper modal-wrapper2" id="name-modal2">
	  <a href="#!" id="overlay2" class="modal-overlay2" onclick="clickOverlayClose()"></a>
	  <div class="modal-window2">
	    <div class="modal-content">
	      <div class="nemu_body">
			  <ul class="menu">
				  <li><a onclick="clickButtonClose(), clickRoomCreate()">ルーム作成</a></li>
				  <li><hr></li>
				  <li><a onclick="clickButtonClose()" href="RoomListServlet">ルーム一覧</a></li>
			  </ul>
		  </div>
	    </div>
	  </div>
	</div>
	
	<!-- ルームメニュー -->
	<div class="modal-wrapper modal-wrapper2" id="name-modal3">
	  <a href="#!" id="overlay3" class="modal-overlay2" onclick="clickOverlayClose()"></a>
	  <div class="modal-window2">
	    <div class="modal-content">
	      <div class="nemu_body">
			  <ul class="menu">
				  <li><a onclick="clickButtonClose()" href="UserListServlet">ダイレクトチャット開始</a></li>
			  </ul>
		  </div>
	    </div>
	  </div>
	</div>
	
	<!-- 右クリックメニュー -->
	<div id="contextmenu">
		<div class="nemu_body">
		  <ul class="menu">
			  <li><a onclick="clickButtonClose(), clickRoomCreate()">作成</a></li>
			  <li><hr></li>
			  <li><a onclick="clickButtonClose()" href="RoomListServlet">ルーム一覧</a></li>
		  </ul>
	  </div>
  	</div>
  	
  	<!-- 右クリックメニュー -->
	<div id="contextmenu1">
		<div class="nemu_body">
		  <ul class="menu">
			  <li><a onclick="clickButtonClose()" href="UserListServlet">ダイレクトチャット開始</a></li>
		  </ul>
	  </div>
  	</div>
  	
  	<!-- ページロード時用hiddenフィールド -->
  	<form class="hidden/-form" action="" method="post">
  		<input type="hidden" name="roomCreateFlag" value="${roomCreateFlag }" id="roomCreateFlag">
  		<input type="hidden" name="errorFlag" value="${errorType }" id="errorFlag">
    </form>
	
<script src="js/modal.js"></script>
<script src="js/list.js"></script>
<script src="js/menu.js"></script>

</body>
</html>