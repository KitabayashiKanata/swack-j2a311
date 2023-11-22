window.onload = function(){

	document.getElementById("target2").addEventListener('contextmenu',function (e){
	    //マウスの位置をstyleへ設定（左上の開始位置を指定）
	    document.getElementById('contextmenu').style.left=e.pageX+"px";
	    document.getElementById('contextmenu').style.top=e.pageY+"px";
	    //メニューをblockで表示させる
	    document.getElementById('contextmenu').style.display="block";
		});
    
    document.getElementById("target3").addEventListener('contextmenu',function (e){
	    //マウスの位置をstyleへ設定（左上の開始位置を指定）
	    document.getElementById('contextmenu1').style.left=e.pageX+"px";
	    document.getElementById('contextmenu1').style.top=e.pageY+"px";
	    //メニューをblockで表示させる
	    document.getElementById('contextmenu1').style.display="block";
		});
		
	document.body.addEventListener('click', () => {
      //メニューをnoneで非表示にさせる
      document.getElementById('contextmenu').style.display="none";
      document.getElementById('contextmenu1').style.display="none";
    });
    
}