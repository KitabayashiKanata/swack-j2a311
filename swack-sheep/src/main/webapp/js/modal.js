/**
 *	モーダルウィンドウの 処理を行うjs
 */
editModal = document.getElementById("message-edit-modal");
deleteModal = document.getElementById("message-delete-modal");
reactionModal = document.getElementById("message-reaction-modal");
roomModal = document.getElementById("room-modal");
userListModal = document.getElementById("user-list-modal");
workInvitationModal = document.getElementById("work-invitation-modal") ;
nameModal1 = document.getElementById("name-modal1");
nameModal2 = document.getElementById("name-modal2");
nameModal3 = document.getElementById("name-modal3");

function clickNameCreate1(){
	target = document.getElementById("target1");
	// 要素の位置座標を取得
	var clientRect = target.getBoundingClientRect();
	var name = document.getElementById("name-modal1")
	var over = document.getElementById("overlay1")
	
	// 画面の左端から、要素の左端までの距離
	var x = clientRect.left ;
	
	// 画面の上端から、要素の上端までの距離
	var y = clientRect.top ;
	name.style.left = x + "px";
	name.style.top = y - 10 + "px";
	
	over.style.left = x * -1 + "%";
	over.style.top = y * -1 + "%";

	nameModal1.classList.add('is-open')
	modal = nameModal1;
}

function clickNameCreate2(){
	target = document.getElementById("target2");
	// 要素の位置座標を取得
	var clientRect = target.getBoundingClientRect();
	var name = document.getElementById("name-modal2")
	var over = document.getElementById("overlay2")
	
	// 画面の左端から、要素の左端までの距離
	var x = clientRect.left ;
	
	// 画面の上端から、要素の上端までの距離
	var y = clientRect.top ;
	name.style.left = x + "px";
	name.style.top = y - 12 + "px";
	
	over.style.left = x * -1 + "%";
	over.style.top = y * -1 + "%";

	nameModal2.classList.add('is-open')
	modal = nameModal2;
}

function clickNameCreate3(){
	target = document.getElementById("target3");
	// 要素の位置座標を取得
	var clientRect = target.getBoundingClientRect();
	var name = document.getElementById("name-modal3")
	var over = document.getElementById("overlay3")
	
	// 画面の左端から、要素の左端までの距離
	var x = clientRect.left ;
	
	// 画面の上端から、要素の上端までの距離
	var y = clientRect.top ;
	name.style.left = x + "px";
	name.style.top = y - 12 + "px";
	
	over.style.left = x * -1 + "%";
	over.style.top = y * -1 + "%";

	nameModal3.classList.add('is-open')
	modal = nameModal3;
}

function clickRoomCreate(){
	roomModal.classList.add('is-open');
	modal = roomModal;
};

function clickMessageEdit(chatLogId,message){
	document.getElementById("modalChatLogIdE").value = chatLogId;
  	document.getElementById("editMessage").value = message;
	editModal.classList.add('is-open');
	modal = editModal;
};

function clickMessageDelete(chatLogId) {
	document.getElementById("modalChatLogIdD").value = chatLogId;
	deleteModal.classList.add('is-open');
	modal = deleteModal;
};

function clickMessageReaction(chatLogId){
	document.getElementById("modalChatLogIdR").value = chatLogId;
	reactionModal.classList.add('is-open');
	modal = reactionModal;
};

function clickUserList(){
	userListModal.classList.add('is-open');
	modal = userListModal;
};

function clickInvitationWorkspace(){
	workInvitationModal.classList.add('is-open');
	modal = workInvitationModal
}

function clickOverlayClose(){
	modal.classList.remove('is-open');
};

function clickButtonClose(){
	modal.classList.remove('is-open');
};

// エラーメッセージがある場合読み込み時にモーダルウィンドウを表示する
//window.onload = function(){
//	console.log("tsts");
//	var errorFlag =  document.getElementById("errorFlag").value;
//	console.log(errorFlag);
//	if(errorFlag == "createRoomeError"){
//		clickRoomCreate();
//	};
//	
//	
//	var roomCreateFlag =  document.getElementById("roomCreateFlag").value;
//	console.log(roomCreateFlag);
//	if(roomCreateFlag == "True"){
//		clickUserList();
//	};
//};

// createRoomFlagがTrueの場合モーダルウィンドウを表示
//window.onload = function(){
//	
//};
