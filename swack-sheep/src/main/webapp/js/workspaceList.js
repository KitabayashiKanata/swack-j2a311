/**
 * workspaceListから押されたリンクに関連するworkspaceをServletに送信
 */

function clickWorkspace(num1, num2) {
	console.log(num1);
	document.getElementById("workspaceName").value = num1;
	document.getElementById("workspaceID").value = num2;
    document.getElementById("wSpan1").textContent  = document.getElementById("workspaceName").value;
    document.getElementById("wSpan2").textContent  = document.getElementById("workspaceID").value;
};