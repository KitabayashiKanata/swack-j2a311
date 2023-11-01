/**
 * メイン画面用のJavaScrept
 */

 /** 
  * モーダルウィンドウ
  */
 
 //初回のみモーダルをすぐ出す判定。flagがモーダル表示のstart_open後に代入される
//  var access = $.cookie('access')
//  if(!access){
//    flag = true;
//    $.cookie('access', false);
//  }else{
//    flag = false  
//  }
//  
  //モーダル表示
//  $(".modal-button").modaal({
//  start_open:ture, // ページロード時に表示するか
//  overlay_close:true,//モーダル背景クリック時に閉じるか
//  before_open:function(){// モーダルが開く前に行う動作
//    $('html').css('overflow-y','hidden');/*縦スクロールバーを出さない*/
//  },
//  after_close:function(){// モーダルが閉じた後に行う動作
//    $('html').css('overflow-y','scroll');/*縦スクロールバーを出す*/
//  }
//  });
  
  $(document).ready(function() {
	  console.log(document.getElementById("errorMsg").textContent);  
	  if (document.getElementById("errorMsg").textContent != ""){
		$(".modal-button").modaal({
//			ここで表示されるモーダルウィンドウの表示がおかしい
	    start_open: true, // ページロード時に表示する/
	  	});
	  }
  
});