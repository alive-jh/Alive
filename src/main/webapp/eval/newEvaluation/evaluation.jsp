<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>英语测评</title>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/frozenui-1.3.0/css/frozen.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/eval/css/style.css">
</head>

<body>
    <div class="progress">
        <div class="ui-progress">
            <span style="width:20%"></span>
        </div>
        <span>1/5</span>
    </div>
    <div class="question-title">
        <div onclick="playSound(this)">
            <img src="<%=request.getContextPath() %>/eval/images/laba2x.png" alt="">
        </div>
        <div>
            听：panda
        </div>
    </div>
    <div class="options video-list">
        <div class="text-justify-list">
            <img src="http://placeholder.qiniudn.com/500x500" alt="">
        </div>
        <div class="text-justify-list">
            <img src="http://placeholder.qiniudn.com/500x500" alt="">
        </div>
        <div class="text-justify-list">
            <img src="http://placeholder.qiniudn.com/500x500" alt="">
        </div>
        <div class="text-justify-list">
            <img src="http://placeholder.qiniudn.com/500x500" alt="">
        </div>
        <span class="justify_fix"></span>
    </div>
    <footer class="ui-footer ui-footer-btn ui-border-t">
        <button class="ui-btn-lg submit-btn	" onclick="nextQuestion()">下一题</button>
    </footer>
    <div style="display: none;">
		<audio id="audioController" src=""></audio>
	</div>
	<div style="display: none;">
		<audio id="audioFont" src=""></audio>
	</div>
    <script src="<%=request.getContextPath() %>/eval/js/jquery-3.1.1.min.js"></script>
    <script>
        var data;var AllBingo;
        var index = 0;
        var audio = [];
        var carom = 0;
        var bingo = 0;
        function getEvaluation(level) {
            $.ajax({
                url: '/wechat/h5/evaluation/getEvaluation',
                type: 'post',
                data: { level: level, all: 1 },
                success: function (success) {
                    if (success.code == 200) {
                        console.log(success);
                        data = success.data.list;
                        index = 0;
                        audio = [];
                        carom = 0;
                        bingo = 0;
                        AllBingo = 0;
                        $('.ui-btn-lg').text('下一题');
                    	$('.ui-progress span').css('width',(index+1)*2+'0%')
                    	$('.progress > span').html(index+1+'/5')
                        showData(index);
                    }
                }
            });
        }
        var QuestionGrade = 1;
        var level = [0,43,45,47,49,51];
        getEvaluation(level[QuestionGrade]);
        function showData(index) {
			$('.question-title div:nth-child(2)').text(data[index].question.questionText);
        	$('#audioController').attr('src',data[index].question.questionSound);
            var html = '';
            audio = [];
            var options1 = data[index].options;
            for(var i=0;i<options1.length;i++){
            	 var img = '<img data-isCorrect="'+options1[i].isCorrect+'" src="'+options1[i].optionPic+'" alt="">';
                 if(options1[i].optionSound!='not'){
                    img = '<img id="SoundRecord" data-isCorrect="'+options1[i].isCorrect+'" src="../images/laba2x.png" alt="">';
                 	audio.push(options1[i].optionSound);
                 }

                 html += '<div class="text-justify-list" data-optionId="'+options1[i].optionId+'" >'+img;
                 if(options1[i].optionText == 'not'){
                	 html += '<span></span></div>';
                 }else{
                	 html += '<span>'+options1[i].optionText+'</span></div>';
                 }
                 
            }
            
            html += '<span class="justify_fix"></span>';
			
            $('.options').html(html);
            var optionLen = data[index].options.length;
            if (optionLen <= 2) {
                $('.options div img').css('width', '65%')
            }
            $('.text-justify-list img').click(function(){
                $('.text-justify-list img').removeClass('select');
            	$(this).addClass('select');
            });
        }

        function nextQuestion() {
        	if($('.select').length==0){
        		alert("没有选择");
        	}else{
	        	if($('.select').data('iscorrect')==1){
	        		AllBingo++;
	        		bingo++;}else {bingo=0;}
	        	if(bingo>carom){
	        		carom = bingo;
	        		if(carom>=4&&QuestionGrade!=3){
	        			QuestionGrade++;
	        		}
	        	}
	        	if(AllBingo==3){
	        		QuestionGrade++;
	        	}
	        	index += 1;
	        	if(index==4&&AllBingo>=3){
	        		$('.ui-btn-lg').text('挑战下一关');
	        	}else if(index==4){
	        		$('.ui-btn-lg').text('提交成绩');	        		
	        	}
	        	if(index==5&&QuestionGrade>=4||index==5&&AllBingo<3){
	        		alert("恭喜你答对了"+AllBingo+"题");
	        		window.location.href="/wechat/eval/evalEnd.jsp?level="+(QuestionGrade>4?4:QuestionGrade);
	        	}else if(index==5&&AllBingo>=3){
	        		getEvaluation(QuestionGrade);
	        	}else{	        		
		        	showData(index);
	        	}
	        	$('.ui-progress span').css('width',(index+1)*2+'0%')
	        	$('.progress > span').html(index+1+'/5')
        	}
        }
        
        function playSound(){
        	$('.question-title div:first-child').addClass('toUp');	
        	setTimeout(function(){$('.question-title div:first-child').addClass('toDown')},1000);
        	setTimeout(function(){
        		$('.question-title div:first-child').removeClass('toUp');
        		$('.question-title div:first-child').removeClass('toDown');
        		},2200);
        	
        	
			$('#audioController')[0].play();
		}
        
        $('.options').on('click','#SoundRecord',function(){
        	$('#audioFont').attr('src',audio[$(this).parent().index()]);
        	$('#audioFont')[0].play();
        });
        
       
    </script>
</body>

</html>