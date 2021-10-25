$(function (){
		
		var memberId = $("#memberId").val();
		
		$.ajax({
			type: "POST",
			url:"../eval/getSignInfo?memberId="+memberId,
			async: false,
            error: function(request) {
                  	alert("错误！！");
            },
            success: function(data) {
            	if(data.sign!=0){
            		var r=confirm("系统检测到您之前已经注册，是否进入测评");
        			if(r){
        				window.location.href="evaluation.html";
                    }else{
                    	window.location.href="signSuccess.html";
                    }
            	}else{
            		$('#myModal').modal({backdrop: 'static', keyboard: false});
            		$('#myModal').modal('show');
            	}
            }
        });
		
		
		
		
		
			
		$("#btn1").click(function(){
			if($("#uName").val()==""||$("#uName").val()==null||$("#uName").val()==undefined){
				alert("请填写宝贝昵称");
				return false;
			}
			if($("#uGrade").val()==""||$("#uGrade").val()==null||$("#uGrade").val()==undefined){
				alert("请填写宝贝年级");
				return false;
			}
			if($("#uAge").val()==""||$("#uAge").val()==null||$("#uAge").val()==undefined){
				alert("请填写宝贝年龄");
				return false;
			}
			if($("#mobile").val()==""||$("#mobile").val()==null||$("#mobile").val()==undefined){
				alert("请填写手机号码");
				return false;
			}else{
				if(!(/^1[34578]\d{9}$/.test($("#mobile").val()))){ 
					alert("手机号码有误，请重填");  
					return false; 
				} 
			}
			
			$.ajax({
				type: "POST",
				url:"../activityAPI/saveSignInfo",
				data:$('#form1').serialize(),
				async: false,
                error: function(request) {
                      	alert("错误！！");
                },
                success: function(data) {
                	var r=confirm("报名成功,是否开始测评?");
        			if(r){
        				window.location.href="evaluation.html";
                    }else{
                    	window.location.href="signSuccess.html";
                    }
                }
            });

			
		
		}); 
	
});
