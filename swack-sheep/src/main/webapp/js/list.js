/**
 * workspaceListから押されたリンクに関連するworkspaceをServletに送信
 */

function clickWorkspace(num1, num2) {
	document.getElementById("workspaceName").value = num1;
	document.getElementById("workspaceId").value = num2;
    document.workspaceForm.submit();
};

function clickRoom(num1, num2) {
	document.getElementById("roomName").value = num1;
	document.getElementById("roomId").value = num2;
    document.roomForm.submit();
};

function clickUser(num1, num2) {
	document.getElementById("userName").value = num1;
	document.getElementById("userId").value = num2;
    document.userForm.submit();
};

function clickUser2(userId) {
	document.getElementById("invitationUserIdModal").value = userId;
	document.invitationUserForm.submit();
};

function clickRemoveJoin(userId) {
	document.getElementById("removeJoinModal").value = userId;
	document.removeJoinUserForm.submit();
};