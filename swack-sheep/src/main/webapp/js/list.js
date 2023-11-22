/**
 * workspaceListから押されたリンクに関連するworkspaceをServletに送信
 */

function clickWorkspace(num1, num2) {
	document.getElementById("workspaceName").value = num1;
	document.getElementById("workspaceID").value = num2;
    document.getElementById("wSpan1").textContent  = document.getElementById("workspaceName").value;
    document.getElementById("wSpan2").textContent  = document.getElementById("workspaceID").value;
};

function clickRoom(num1, num2) {
	document.getElementById("roomName").value = num1;
	document.getElementById("roomID").value = num2;
    document.getElementById("rSpan1").textContent  = document.getElementById("roomName").value;
    document.getElementById("rSpan2").textContent  = document.getElementById("roomID").value;
};

function clickUser(num1, num2) {
	document.getElementById("userName").value = num1;
	document.getElementById("userID").value = num2;
    document.getElementById("uSpan1").textContent  = document.getElementById("userName").value;
    document.getElementById("uSpan2").textContent  = document.getElementById("userID").value;
};

function clickUser2(userId) {
	document.getElementById("invitationUserIdModal").value = userId;
	document.invitationUserForm.submit();
};

function clickRemoveJoin(userId) {
	console.log(userId)
	document.getElementById("removeJoinModal").value = userId;
	document.removeJoinUserForm.submit();
}