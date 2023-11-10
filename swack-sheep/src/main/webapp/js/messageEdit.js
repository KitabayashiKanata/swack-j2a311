/**
 * メッセージの編集または削除を行う
 */

function clickEdit(chatLogId,message) {
	var edit = '<form action="MessageEditServlet" method="post">'+
					'<input type="hidden" name="chatLogId"value="'+chatLogId+'">'+
						'<div class="form-wrap">'+
							'<input type="text" name="newMessage" value="'+message+'">'+
							'<input type="button" value="キャンセル" onclick="clickEditCancel()">'+
							'<input type="submit" value="保存">'+
						'</div>'+
				'</form>';
	document.getElementById("massegeSubmit").innerHTML = edit;
	
};

function clickEditCancel() {
	var original = '<form action="ChatServlet" method="post" >'+
						'<input type="hidden" name="roomId" value="${nowRoom.roomId}">'+
						'<div class="form-wrap">'+
							'<input type="text" name="message">'+
							'<input type="submit" value="送信">'+
						'</div>'+
					'</form>';
	document.getElementById("massegeSubmit").innerHTML = original;
};

//function clickDelete(chatLogId) {
//	document.getElementById("modalChatLogId").value = chatLogId
//	const modal = document.getElementById("modal-delete");
//	modal.classList.add('is-open')
//};
//
//function clickModalClose(){
//	const modal = document.getElementById("modal-delete");
//	modal.classList.remove('is-open')
//}

//jsで取得した値をjstlで使用できないため諦め
//<c:choose>
//							    <c:when test="${ == nowLogId }">
//									<form action="MessageEditServlet" method="post">
//										<input type="hidden" name="roomId"
//											 value="${nowRoom.roomId}">
//										<div class="form-wrap">
//											<input type="text" name="message">
//											<input type="submit" value="送信">
//										</div>
//									</form>
//								</c:when>
//								<c:otherwise>
//									${log.message}
//								</c:otherwise>
//							</c:choose>

//function clickEdit(chatLogId){
//	console.log(chatLogId);
//	console.log(document.getElementById("inputMessage"));
//	var a = '<c:choose>\t'+
//				'<c:when test="${ '+chatLogId+' == nowLogId }">\t'+
//					'<form action="MessageEditServlet" method="post">\t'+
//						'<input type="hidden" name="roomId"value="${nowRoom.roomId}">\t'+
//							'<div class="form-wrap">\t'+
//								'<input type="text" name="message">\t'+
//									'<input type="submit" value="送信">\t'+
//							'</div>\t'+
//					'</form>\t'+
//				'</c:when>\t'+
//				'<c:otherwise>\t';
//				
//	var b = '</c:otherwise>\t'+
//			'</c:choose>';
//	document.getElementById("jsInsert").insertAdjacentHTML("afterbegin",a);
//	document.getElementById("jsInsert").insertAdjacentHTML("beforeend",b);
//	document.getElementById("inputMessage").replaceWith(document.getElementById("inputMessage"),edit);
//	document.getElementById("selectLogId").value = chatLogId;
//}