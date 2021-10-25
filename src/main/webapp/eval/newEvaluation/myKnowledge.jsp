<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/validator/jquery.validator.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validator/jquery.validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validator/local/zh_CN.js"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/js/uploadify/uploadify.css" type="text/css"></link>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/uploadify/jquery.uploadify-3.1.min.js"></script>

<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/lang/zh-cn/zh-cn.js"></script>
<style>
#tbody a {
height: 24px;
    line-height: 21px;
    padding: 0 11px;
    background: #ffffff;
    border: 1px #949494 solid;
    border-radius: 3px;
    /* color: #fff; */
    display: inline-block;
    text-decoration: none;
    font-size: 12px;
    outline: none;
    color:#000000;
}
#tbody button {
    height: 24px;
    line-height: 21px;
    padding: 0 11px;
    background: #ffffff;
    border: 1px #949494 solid;
    border-radius: 3px;
    /* color: #fff; */
    display: inline-block;
    text-decoration: none;
    font-size: 12px;
    outline: none;
}
</style>

<div class="main-content">
	<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try {
				ace.settings.check('breadcrumbs', 'fixed')
			} catch (e) {
			}
		</script>

		<ul class="breadcrumb">
			<li>
				<i class="icon-home home-icon"></i>
				<a href="main.htm">首页</a>
			</li>
			<li class="active">知识库</li>
			<li class="active">我的知识库</li>
		</ul>
		<!-- .breadcrumb -->


	</div>

	<div class="page-content">
		<div class="page-header col-xs-12" style="float: left; width: 100%">

				<div class="btn-group col-md-12">
					<button class="btn  btn-purple" type="button" onclick="saveKnowledge()">
						<span class="icon-plus"></span>
						添加知识块
					</button>
					
					<div style="width:300px;position: absolute;left: 20%;top:5px">
						<label style="color: red;font-size: 20px">当前机器人：</label><span id="epalIdname" style="font-size: 20px"></span>
					</div> 
					
					<div class="form-group" style="float: right;right: 50px;">
							<input id="searchInput" type="text" class="form-control" style="width: 1000px;display: inline;"/>
							<button class="btn  btn-purple" type="button" onclick="searchKnowledge()">
						搜索
					</button>
					</div> 
				</div>

		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<div class="row">
					<div class="col-xs-12">
						<div class="table-responsive" style="text-align: right;">
							<table id="articleListTab" class="table table-striped table-bordered table-hover" style="text-align: left;">
								<thead>
									<tr>
										<th width="100">知识块</th>
										<th width="100">问题</th>
										<th width="100">答案</th>
									</tr>
								</thead>
								<tbody id="tbody">
								
									
								</tbody>


							</table>

						</div>
						<!-- /.table-responsive -->
					</div>
					<!-- /.col-xs-12 -->
				</div>
				<!-- /row -->
			</div>
			<!-- /.col-xs-12 -->
		</div>
		<!-- /.row -->


	</div>
	<!-- /.page-content -->

</div>
<!-- /.main-content -->
</div>
<!-- /.main-container-inner -->
<!-- 模态弹出窗内容 -->
<div class="modal fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 1100px; height: 1000px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span>
					<span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">添加/修改问题</h4>
			</div>
			<div class="modal-body">


				<div id="divAccount" class="form-group" style="height: 35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">
						<span id="name1"></span>
						问题
					</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width: 710px;">
								<input class="form-control" name="questionText" id="questionText" type="text" data-rule="商品名称:required;" style="max-width: 650px; width: 100%;" />
							</div>
						</div>
					</div>
				</div>
				<input id="questionId" type="hidden" value=""/>
				

			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-primary" onclick="saveQuestion()">保存</button>

				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>
<form id="answerForm">
<input type="hidden" id = "id" name="id" value="" />
<div class="modal fade" id="mymodal-data2" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 1100px; height: 1000px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span>
					<span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">添加答案</h4>
			</div>
			<div class="modal-body">


				<div id="divAccount" class="form-group" style="height: 35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">
						<span id="name1" id="answerName">名称</span>
						
					</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width: 710px;">
								<input class="form-control" name="answerText" id="answerText" type="text" style="max-width: 650px; width: 100%;" />
							</div>
						</div>
					</div>
				</div>
				
				<div id="divAccount" class="form-group" style="height: 35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">
						<span id="name1"></span>
						答案类型
					</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width: 710px;">
								<select id="answerTypeSelect" style="max-width: 650px; width: 100%;">
									<option id="1" selected="selected">文本</option>
									<option id="2">音频</option>
									<option id="3">图片</option>
									<option id="4">视频</option>
									<option id="5">网页</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				<input id="answerType" name="answerType" type="hidden" value="1"/>
				<input id="content" name="content" type="hidden"/>
				<div id="fileBox" class="form-group" style="height: 35px;display: none">
					<label class="col-sm-2 control-label no-padding-right text-right">
						<span id="name1"></span>
						选择文件
					</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width: 710px;">
								<input type="file" id="file" name="file" />
							</div>
						</div>
					</div>
				</div>
				
				<div id="ueditor1" style="display: none">
				<script id="editor" type="text/plain" style="width:1024px;height:500px;"></script>
				</div>
				
			</div>
			
			<input id="knowledgeId" name="knowledgeId" type="hidden">
			<div class="modal-footer">
				<a class="btn btn-primary" onclick="saveAnswer()">保存</a>
				<button class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>
</form>
</div>
<!-- /.main-container -->
<script>
	var globalEpalId = "${epalId}";
	var data;
	var knowledgeId=0;
	var user = "${user}";
	
	
	
	var ue = UE.getEditor('editor');
	UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function(action) {
        if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
            return '/wechat/api/ueditorUpload';
        }else {
            return this._bkGetActionUrl.call(this, action);
        }
    }
        
    function select(){
    	var type = $('#answerTypeSelect').find("option:selected").attr('id');
    	if(type==5){
    		$('#fileBox').css('display','none');
    		$('#ueditor1').css('display','block');
    	}else if(type==1){
    		$('#fileBox').css('display','none');
    		$('#ueditor1').css('display','none');
    	}else{
    		$('#fileBox').css('display','');
    		$('#ueditor1').css('display','none');
    	}
    	$('#answerType').val(type);
    }
    
    $('#answerTypeSelect').change(function(){
    	select();
    });
    
	function showModal(_that){
		knowledgeId = _that.id;
	}
	
	function saveQuestion(){
		var questionId = $('#questionId').val();
		var paramsData = {text : $('#questionText').val(),knowledgeId : knowledgeId};
		if(questionId!=""||questionId!=null){
			paramsData["id"]=questionId;
		}
		$.post("<%=request.getContextPath()%>/knowledge/saveQuestion",paramsData,
				function(success){  
					if(success.code==200){
						location.href="<%=request.getContextPath()%>/knowledge/myKnowledge?epalId="+globalEpalId;
					}
	    		},"json");
	}
	
	function saveAnswer(){
		
		var temp1 = $("#answerText").val();
		
		if(temp1==null||temp1==""){
			alert("请输入答案名称");
			return;
		}
		
		var arr = [];
		arr.push(UE.getEditor('editor').getContent());
		var content=arr.join("\n");
		$('#knowledgeId').val(knowledgeId);
		$('#content').val(content);
		var form = new FormData(document.getElementById("answerForm"));
		$.ajax({
			 url:"<%=request.getContextPath()%>/knowledge/saveAnswer",
             type:"post",
             data:form,
             processData:false,
             contentType:false,
             success:function(success){
 				if(success.code==200){
 					location.href="<%=request.getContextPath()%>/knowledge/myKnowledge?epalId="+globalEpalId;
 				}
             },
             error:function(e){
            	 console.log('error');
             }
         });        
		 
	}
	
	$(function(){
		getKnowledge(globalEpalId);
	});
	
	function getKnowledge(epalId) {
		
		if(epalId!=null&&epalId!=""){
			
			var paramsData = {epalId : epalId,pageIndex : 1,pageSize : 100};
			
			if(""!=user&&null!=user){
				paramsData["agentId"]=user;
			}
			
			$.post("<%=request.getContextPath()%>/v2/train/getKnowledgeList",paramsData,
					function(success){  
						if(success.code==200&&success.data.totalRow>0){
							console.log(success);
							data = success.data.list;
							showData();
							globalEpalId = epalId;
							$('#epalIdname').html(epalId);
						}else if(success.code==200&&success.data.totalRow==0){
							alert('查询不到该机器人');
						}else{
							alert("获取失败");
						}
		    		},"json");
		}
		
	}
	
	function delKnowledge(_this){
		var id = _this.id;
		$.post("<%=request.getContextPath()%>/knowledge/delKnowledge",
				{knowledgeId : id},
				function(success){
					if(success.code==200){
						location.href="<%=request.getContextPath()%>/knowledge/myKnowledge?epalId="+globalEpalId;
					}
	    		},"json");	
	}
	
	function showData(){		
		
		for(var i=0;i<data.length;i++){
			var td ='<tr><td><p style="font-size:16px;line-height: 25px;float: left">知识块'+data[i].knowledgeId+'<p><button id="'+data[i].knowledgeId+'" style="float: right" onclick="delKnowledge(this)">删除</button></td><td>'
		
			for(var j=0;j<data[i].questionList.length;j++){
				td+='<p><span  id="question'+data[i].questionList[j].id+'" style="font-size:16px;line-height: 20px;">'+data[i].questionList[j].text+'</span><button id="'+data[i].questionList[j].id+'" onclick="delQuestion(this)" style="float: right;">删除</button><button id="'+data[i].questionList[j].id+'" onclick="updateQuestion(this)" style="float: right;">编辑</button></p>';				
			}
			td+='<p style="text-align: center;"><button id="'+data[i].knowledgeId+'" onclick="showModal(this)" data-toggle="modal" data-target="#mymodal-data" class="btn btn-primary">添加问题</button></p></td><td>';
			for(var j=0;j<data[i].answerList.length;j++){
				if((data[i].answerList[j].content).indexOf("http")>-1){
					if(data[i].answerList[j].type==5){
						td+='<p><span style="font-size:16px;line-height: 20px;">'+data[i].answerList[j].text+'</span><button id="'+data[i].answerList[j].id+'" onclick="delAnswer(this)" style="float: right;">删除</button><button id="'+data[i].answerList[j].id+'" onclick="editAnswerPage(this)" style="float: right;">编辑</button><a href="'+data[i].answerList[j].content+'" style="float: right;">预览</a></p>';	
					}else{
						td+='<p><span style="font-size:16px;line-height: 20px;">'+data[i].answerList[j].text+'</span><button id="'+data[i].answerList[j].id+'" onclick="delAnswer(this)" style="float: right;">删除</button><a href="'+data[i].answerList[j].content+'" style="float: right;">预览</a></p>';	
					}
				}else{
					td+='<p><span style="font-size:16px;line-height: 20px;">'+data[i].answerList[j].text+'</span><button id="'+data[i].answerList[j].id+'" onclick="delAnswer(this)"style="float: right;">删除</button></p>';	
				}
					
			}
			td+='<p style="text-align: center;"><button id="'+data[i].knowledgeId+'" onclick="showModal(this)" data-toggle="modal" data-target="#mymodal-data2" class="btn btn-primary">添加答案</button></p></td></tr>';
			$('#tbody').append(td);
		}
		
		
	}
	
	function delAnswer(_that){
		var rid = _that.id;
		$.post("<%=request.getContextPath()%>/knowledge/delAnswer",
				{answerId : rid},
				function(success){  
					if(success.code==200){
						location.href="<%=request.getContextPath()%>/knowledge/myKnowledge?epalId="+globalEpalId;
					}
	    		},"json");
	}
	
	function delQuestion(_that){
		var qid = _that.id;
		$.post("<%=request.getContextPath()%>/knowledge/delQuestion",
				{questionId : qid},
				function(success){  
					if(success.code==200){
						location.href="<%=request.getContextPath()%>/knowledge/myKnowledge?epalId="+globalEpalId;
					}
	    		},"json");	
	}
	
	function saveKnowledge(){	
		if(globalEpalId!=null&&""!=globalEpalId){
			$.post("<%=request.getContextPath()%>/knowledge/saveKnowledge",
					{epalId1:globalEpalId},function(success){  
					if(success.code==200){
						location.href="<%=request.getContextPath()%>/knowledge/myKnowledge?epalId="+globalEpalId;
					}
		    },"json");
		}
			
	}

	$('#mymodal-data').on('show.bs.modal', function () {
		$("#questionText").val("");
		$('#questionId').val("");
	});
	
	$('#mymodal-data2').on('show.bs.modal', function () {
		ue.setContent('');
		$('#answerText').val("");
		$('#answerType').val(1);
		$('#answerTypeSelect option:first').attr("selected", true);
		select();
	})
	
	function updateQuestion(_this){
		var id = _this.id;
		$('#mymodal-data').modal('show');
		$("#questionText").val($('#question'+id).text());
		$('#questionId').val(id);
	}
	
	function editAnswerPage(_this){
		
		var answerId = _this.id;
		$.post("<%=request.getContextPath()%>/knowledge/getAnswerPageByAnswerId",
				{answerId:answerId},function(success){  
				if(success.code==200){
					console.log(success);
					$('#mymodal-data2').modal('show');
					$('#id').val(answerId);
					$('#answerText').val(success.data.text);
					$('#answerType').val(5);
					ue.execCommand('insertHtml', success.data.pageContent);
					$('#answerTypeSelect option:last').attr("selected", true);
					select();
				}
	    },"json");
	}
	
	function searchKnowledge(){
		$('#tbody').empty();
		getKnowledge($('#searchInput').val());
	}
	
	
	
	
	
</script>