var data;
var level = 1;

function getEvaluation(pageIndex){
	$.post("/wechat/h5/evaluation/getEvaluation",
			{level:level,pageIndex:pageIndex},function(success){  
			if(success.code==200){
				console.log(success);
				data = success.data;
				showData();
			}
    },"json");
	window.localStorage.setItem("level",level);
}

$(function(){
	
	if(window.localStorage.getItem("level")!=null){
		level = window.localStorage.getItem("level");
		$('#evaluationLevel').find('option[id='+level+']').attr("selected",true);
	}
	
	
	
	getEvaluation();
	$("#question-form").ajaxForm({
		dataType: "json",
		success: function(success) {
			location.reload();
		}
	});
	
	$("#option-form").ajaxForm({
		dataType: "json",
		success: function(success) {
			location.reload();
		}
	});
})

$('#option-form').on()   
function showData(){
	var index = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U'];
	$('.tbody').empty();
	for(var i=0;i<data.list.length;i++){
		var td = '<tr><td class="question"><span>'+data.list[i].question.questionText;
		if(data.list[i].question.questionSound!="not"){
			td+='<button id="'+data.list[i].question.questionSound+'" onclick="play(this)" >播放</button>';
		}
		td+='<div class="edit2"><button id="'+data.list[i].question.questionId+'" onclick="delQuestion(this)" >删除</button></div></td>'
	
		td+='<td class="answer">';
		for(var j=0;j<data.list[i].options.length;j++){
			td+='<div><label>'+index[j]+'</label>';
			if(data.list[i].options[j].optionText!="not"){
				td+='<span>'+data.list[i].options[j].optionText+'</span>'
			}
			if(data.list[i].options[j].optionSound!="not"){
				td+='<button id="'+data.list[i].options[j].optionSound+'" onclick="play(this)">播放</button>'
			}
			if(data.list[i].options[j].optionPic!="not"){
				td+='<img alt="" src="'+data.list[i].options[j].optionPic+'">'
			}
			if(data.list[i].options[j].isCorrect==1){
				td+='<span class="glyphicon glyphicon-ok glyphicon-ok-green"></span>';
			}
			td+='<div class="edit"><button id="'+data.list[i].options[j].optionId+'" onclick="delOption(this)">删除</button></div></div>'
		}
		td+='<div class="add-option"><button id="'+data.list[i].question.questionId+'" onclick="saveAnswer(this)" data-toggle="modal" data-target="#mymodal-data2">添加选项</button></div></td></tr>'
		$('.tbody').append(td);
	}
	
	var pagination = '<ul class="pagination">';
	if(!data.isFirstPage){
		pagination+='<li><a href="javascript:getEvaluation('+(data.pageNumber-1)+')" aria-label="Previous"><span aria-hidden="false">&laquo;</span></a></li>'
	}
	for(var i = 1;i<=data.totalPage;i++){
		if(i==data.pageNumber){
			pagination+='<li><a href="javascript:getEvaluation('+i+')" style="background-color: #6faed9">'+i+'</a></li>';
		}else{
			pagination+='<li><a href="javascript:getEvaluation('+i+')">'+i+'</a></li>';
		}
		
	}
	
	if(!data.isLastPage){
		pagination+='<li><a href="javascript:getEvaluation('+(data.pageNumber+1)+')" aria-label="Previous"><span aria-hidden="false">&raquo;</span></a></li>';
	}
	pagination+='</ul>';
	
	$('#page-data').html(pagination);
	
}

function play(_this){
	$('#audio-controller').attr("src",_this.id);
	$('#audio-controller')[0].play();
}

$("#questionType").change(function(){
	var type = $(this).find("option:selected").attr('id');
	if(type==2){
		$('.questionFile').css('display','block');
	}else{
		$('.questionFile').css('display','none');
	}
});

$("#scoreSel").change(function(){
	var type = $(this).find("option:selected").attr('id');
	if(type==1){
		$('#score').val(5);
	}if(type==2){
		$('#score').val(10);
	}if(type==3){
		$('#score').val(15);
	}if(type==4){
		$('#score').val(20);
	}
});

$("#question_weidu").change(function(){
	var type = $(this).find("option:selected").attr('id');
	$('#questionWeidu').val(type);
});


$("#answerType").change(function(){
	var type = $(this).find("option:selected").attr('id');
	$('#optionType').val(type);
});

$("#evaluationLevel").change(function(){
	var type = $(this).find("option:selected").attr('id');
	level = type;
	$('#questionLevel').val(type);
	getEvaluation();
});


function delQuestion(_this){
	
	var id = _this.id;
	
	$.post("/wechat/h5/evaluation/delQuestion",
			{questionId:id},function(success){  
			if(success.code==200){
				location.reload();
			}
    },"json");
	
}


function saveAnswer(_this){
	$("#questionId").val(_this.id);
}

function delOption(_this){
	
	var id = _this.id;
	
	$.post("/wechat/h5/evaluation/delOption",
			{optionId:id},function(success){  
			if(success.code==200){
				location.reload();
			}
    },"json");
	
}
