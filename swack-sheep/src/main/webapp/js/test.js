window.onload = function(){
	document.getElementById("target2").addEventListener('contextmenu',function (e){
	    //マウスの位置をstyleへ設定（左上の開始位置を指定）
	    document.getElementById('contextmenu').style.left=e.pageX+"px";
	    document.getElementById('contextmenu').style.top=e.pageY+"px";
	    //メニューをblockで表示させる
	    document.getElementById('contextmenu').style.display="block";
		});
    document.body.addEventListener('click', () => {
      //メニューをnoneで非表示にさせる
      document.getElementById('contextmenu').style.display="none";
    });
}

/**
 *	モーダルウィンドウの 処理を行うjs
 */
deleteModal = document.getElementById("message-delete-modal");
editModal = document.getElementById("message-edit-modal");
roomModal = document.getElementById("room-modal");
nameModal = document.getElementById("name-modal");

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
	name.style.top = y + 22 + "px";
	
	over.style.left = x * -1 + "%";
	over.style.top = y * -1 + "%";

	nameModal.classList.add('is-open')
	modal = nameModal;
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
	name.style.top = y + 22  + "px";
	
	over.style.left = x * -1 + "%";
	over.style.top = y * -1 + "%";

	nameModal.classList.add('is-open')
	modal = nameModal;
}




function clickRoomCreate(){
	roomModal.classList.add('is-open')
	modal = roomModal;
};

function clickMessageEdit(chatLogId,message){
	document.getElementById("modalChatLogIdE").value = chatLogId
  	document.getElementById("editMessage").value = message
	editModal.classList.add('is-open')
	modal = editModal;
};

function clickMessageDelete(chatLogId) {
	document.getElementById("modalChatLogIdD").value = chatLogId
	deleteModal.classList.add('is-open')
	modal = deleteModal;
};

function clickOverlayClose(){
	modal.classList.remove('is-open');
};

function clickButtonClose(){
	modal.classList.remove('is-open');
};