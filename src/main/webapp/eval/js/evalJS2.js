	var qData;
	var index=0;
	var num=0;
	var map={};
	var level=1;
	var name="";
	$(document).ready(function(){ 
	
		getQusition();
		
		//下一题按钮
		$("#btn").click(function(){
			if($('input:radio:checked').val()==null||$('input:radio:checked').val()==""||$('input:radio:checked').val()==undefined){
				alert("你还没选择呢");
				return false;
			}
			$('body,html').animate({ scrollTop: 0 }, 100);
			map[$("#qtitle").attr("name")]=$('input:radio:checked').val();
			index++;
			$("#jindu").css("width",((index+1)*10)+"%");
			$(".span1").text(index+1+"/10");
			if(index==9){
			$("#btn").css('display','none');
	    	$("#btn2").css('display','inline');
			}
			$("#question").fadeOut();
			$("#qtitle").text("");
			$("#ul1").empty();
			showQusition();
			$("#question").fadeIn();
		});
		
		$("#icons").click(function(){
				if($("#qtitle").val()!=null&&$("#qtitle").val()!=""&&$("#qtitle").val()!=undefined){
				$("#icons").css("-webkit-animation","scaleout 1.0s infinite ease-in-out");
				var audio = document.getElementById("audio");
				audio.setAttribute("src", $("#qtitle").val());
				audio.play();
				audio.onended = function() {$("#icons").css("-webkit-animation","");};
				}
			});
		
		$("#options").click(function(){
			var audio = document.getElementById("audio");
			audio.setAttribute("src", $("#options").attr("name"));
			audio.play();
		});
		
		
		//提交答案按钮
		$("#btn2").click(function(){
			map[$("#qtitle").attr("name")]=$('input:radio:checked').val();
			$.ajax({
	                url:"../eval/saveResult",
	                type:"post",
	                data:JSON.stringify(map),
	                contentType:'application/json;charset=utf-8',
	                success:function(result){
	                	if(result.score>=60){
	                		alert("你答对了"+result.count+"题");
	                		name=result.nickName;
	                		if(level!=4){
	                		$("#btn").css('display','none');
	                		$("#btn2").css('display','none');
	    					$("#btn3").css('display','inline');
	                		}else{
	                		window.location.href = "evalEnd.jsp?name="+encodeURIComponent(name)+"&level="+level;
	                		}
	                		
	                	}else{
	                		alert("你答对了"+result.count+"题");
	                		window.location.href = "evalEnd.jsp?level="+level;
	                	}
	                },
	                error:function(e){
	                    alert("请求失败");
	                }
			});
		});
		
		//挑战下一关按钮
		$("#btn3").click(function(){
				level++;
				$("#ul1").empty();
				qData=null;
				index=0;
				num=0;
				map={};
				$("#btn2").css('display','none');
	            $("#btn3").css('display','none');
	    		$("#btn").css('display','inline');
				$("#jindu").css("width","10%");
				$(".span1").text(index+1+"/10");
	            $.ajax({
	                url:"../eval/loadQuestions?level="+level,
	                async: false,
	                type:"post",
	                data:{"level":1},
	                processData:false,
	                contentType:false,
	                success:function(result){
	                	if(result.questions[0]!=null){
	                    	qData=result;
	                    	showQusition();       
                    	}else{
                    	$(".span1").text("0/0");
                    	$("#jindu").css("width","0%");
                    	alert("获取题目失败");
                    	}
	                },
	                error:function(e){
	                    alert("请求失败");
	                }
	         	});
		});
		
	});
	
       
       function getQusition(){
            $.ajax({
                url:"../eval/loadQuestions?level="+level,
                async: false,
                cache:false,	
                type:"post",
                data:{"level":level},
                contentType:'application/json;charset=utf-8',
                success:function(result){
                    	if(result.questions[0]!=null){
	                    	qData=result;
	                    	name=result.nickName;
	                    	showQusition();       
                    	}else{
                    	$(".span1").text("0/0");
                    	$("#jindu").css("width","0%");
                    	alert("获取题目失败");
                    	}
                },
                error:function(e){
                    alert("请求失败");
                }
         	});
		}
		
		function showQusition(){
        var ul1 = document.getElementById("ul1");
        var num=new Array("A.","B.","C.","D.");
        if(qData.questions != null&&qData.questions != ""&&qData.questions != undefined){
		$("#qtitle").text(qData.questions[index].text);
		if(qData.questions[index].soundUrl!=null&&qData.questions[index].soundUrl!=""&&qData.questions[index].soundUrl != undefined){
		$("#qtitle").val(qData.questions[index].soundUrl);
		var audio = document.getElementById("audio");
		audio.setAttribute("src", $("#qtitle").val());
		setTimeout("audio.play()",2000);
		}
		$("#qtitle").attr("name",qData.questions[index].id);
		if(qData.questions[index].soundUrl==undefined){
			$("#qtitle").hide();
		}
		var options = qData.questions[index].options;
		for(var i=0;i<options.length;i++){
            var li = document.createElement("li");
            var span = document.createElement("span"); 
            span.innerText=num[i];
            if(options[i]["text"] != undefined && options[i]["text"]!=null && options[i]["text"]!=""){
            	var $li1 = $(li);
            	$li1.append("<br><br>");
            }
            li.appendChild(span);
			if(options[i]["text"] != undefined && options[i]["text"]!=null && options[i]["text"]!=""){
				var img = document.createElement("img");
                img.setAttribute("src", "images/laba2x.png");
                img.setAttribute("id", "ico1");
                img.setAttribute("onclick", "play(this)");
                var span1 = document.createElement("span");
                span1.innerText=options[i].text;
                span1.style.fontSize="20px";
                span1.setAttribute("id", "options"+i)
                span1.setAttribute("name", options[i].soundUrl);
                var input = document.createElement("input");
                input.setAttribute("id", options[i].id) ; 
                input.setAttribute("value", options[i].isCorrect); 
                input.setAttribute("type", "radio") ;
                input.setAttribute("name", "radio") ;
                li.appendChild(img);
                li.appendChild(span1);
                li.appendChild(input);    
            }
            if(options[i]["picUrl"] != undefined && options[i]["picUrl"]!=null && options[i]["picUrl"]!=""){
                var img = document.createElement("img");
                img.setAttribute("src", options[i].picUrl);
                img.setAttribute("id", "img1");
                var input = document.createElement("input");
                input.setAttribute("id", options[i].id) ; 
                input.setAttribute("value", options[i].isCorrect); 
                input.setAttribute("type", "radio") ;
                input.setAttribute("name", "radio") ;
                li.appendChild(img);
                li.appendChild(input);    
             }
             ul1.appendChild(li);
        }
        }
        }
        
        function play(obj){
        	var sound = obj.nextElementSibling;
        	var tobj = $(obj);
        		tobj.css("-webkit-animation","scaleout 1.0s infinite ease-in-out");
				var audio = document.getElementById("audio");
				audio.setAttribute("src", $(sound).attr("name"));
				audio.play();
				audio.onended = function() {tobj.css("-webkit-animation","");};
        	
}