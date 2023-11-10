/**
 *	モーダルウィンドウの 処理を行うjs
 */
deleteModal = document.getElementById("message-delete-modal");
editModal = document.getElementById("message-edit-modal");
roomModal = document.getElementById("room-modal");

function clickRoomCreate(){
	roomModal.classList.add('is-open')
	modal = roomModal;
};

function clickMessageEdit(chatLogId,message){
	document.getElementById("modalChatLogId").value = chatLogId
  	document.getElementById("editMessage").value = message
	editModal.classList.add('is-open')
	modal = editModal;
};

function clickMessageDelete(chatLogId) {
	document.getElementById("modalChatLogId").value = chatLogId
	deleteModal.classList.add('is-open')
	modal = deleteModal;
};

function clickOverlayClose(){
	modal.classList.remove('is-open');
};

function clickButtonClose(){
	modal.classList.remove('is-open');
};
