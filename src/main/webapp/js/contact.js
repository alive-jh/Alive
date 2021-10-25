$(function(){
	//获取对话框
	var $dialogBox=$('#dialog-box');
	var $leftDialog=$('.left-dialog.model',$dialogBox);
	var $rightDialog=$('.right-dialog.model',$dialogBox);
	var $inputText=$('#input-text');
	var $sendBtn=$('#send-btn');
	var $elecSend=$('#elec-send');
	
	$dialogBox.css('height',($(window).height()-110)+'px'); 
	
	$sendBtn.click(function(){
		var _text=$inputText.val().trim();
		if(_text.length>0){
			$inputText.val('');
			$inputText.focus();
			var _rightD=$rightDialog.clone().removeClass('model');
			_rightD.find('.input-words').html(_text);
			_rightD.appendTo($dialogBox);
			$dialogBox[0].scrollTop=$dialogBox[0].scrollHeight;
		}
	})
	
	$elecSend.click(function(){
		var _text='电工回复的文字';
		if(_text.length>0){
			$inputText.val('');
			$inputText.focus();
			var _leftD=$leftDialog.clone().removeClass('model');
			_leftD.find('.input-words').html(_text);
			_leftD.appendTo($dialogBox);
			$dialogBox[0].scrollTop=$dialogBox[0].scrollHeight;
		}
	})
	


})
