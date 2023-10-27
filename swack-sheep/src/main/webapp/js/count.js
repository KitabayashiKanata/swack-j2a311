/**
 * 戻る画面数をカウント
 */

 function count(cnt){
	 if (cnt == null){
		 cnt = -1;
	 } else{
		 cnt -= 1;
	 }
	 document.getElementById("backCnt").value = cnt;
 }