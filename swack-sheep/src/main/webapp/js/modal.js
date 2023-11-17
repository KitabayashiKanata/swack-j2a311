/**
 *	モーダルウィンドウの 処理を行うjs
 */
editModal = document.getElementById("message-edit-modal");
deleteModal = document.getElementById("message-delete-modal");
reactionModal = document.getElementById("message-reaction-modal");
roomModal = document.getElementById("room-modal");
userListModal = document.getElementById("user-list-modal");

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

function clickOverlayClose(){
	modal.classList.remove('is-open');
};

function clickButtonClose(){
	modal.classList.remove('is-open');
};

// エラーメッセージがある場合読み込み時にモーダルウィンドウを表示する
window.onload = function(){
	var errorMsg =  document.getElementById("errorMsg").textContent;
	console.log(errorMsg);
	if(errorMsg != ""){
		clickRoomCreate();
	};
};

// createRoomFlagがTrueの場合モーダルウィンドウを表示
