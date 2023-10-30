/**
 * 戻る画面数をカウント
 */

 function count(cnt){
	 cnt -= 1;
	 
	 sessionStorage.setItem('c', cnt);
	 console.log(cnt);
 }
 
 function count2(cnt){
	 cnt -= 2;
	 
	 sessionStorage.setItem('c', cnt);
	 console.log(cnt);
 }
 
 function reCnt(){
	 c = sessionStorage.getItem('c')
	 if (c == null){
		 c = -1
	 }
	 return c
 }
 
 function delSession(){
	 sessionStorage.clear('c')
 }