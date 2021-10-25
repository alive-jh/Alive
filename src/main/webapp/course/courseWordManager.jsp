<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://www.faceye.com/core"%>

<link rel="stylesheet" href="<%=request.getContextPath()%>/js/validator/jquery.validator.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/qiniu/qiniu_files/main.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/qiniu/qiniu_files/highlight.css">



<div class="main-content">
	<div id="myJson" style="display: none;">${jsonData}</div>
	<div class="breadcrumbs" id="breadcrumbs">
		<ul class="breadcrumb">
			<li><i class="icon-home home-icon"></i> <a href="main.htm">首页</a>
			</li>
			<li class="active">课程管理</li>
			<li class="active">单词库管理</li>
		</ul>
	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/course/courseWordManager"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>单词名</td>
						<td><input type="text" name="searchStr"
							value="${queryDto.searchStr}"></td>
						<td>
							<div class="form-group col-lg-6" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right">创建日期</label>
								<div class="col-sm-9">
									<div class="input-medium" style="">
										<div class="input-group">
											<input class="input-medium date-picker" type="text"
												name="startDate" value="${queryDto.startDate}"
												data-date-format="yyyy-mm-dd" placeholder="年-月-日" /> <span
												class="input-group-addon"> <i class="icon-calendar"></i>
											</span> <span class="input-group-addon"
												style="border:none;background-color:transparent;"> <i>--</i>
											</span> <input class="input-medium date-picker" type="text"
												name="endDate" value="${queryDto.endDate}"
												data-date-format="yyyy-mm-dd" placeholder="年-月-日" /> <span
												class="input-group-addon"> <i class="icon-calendar"></i>
										</div>
									</div>
								</div>
							</div>
						</td>
					</tr>

				</table>

				<div class="btn-group col-md-12">
					<button class="btn  btn-purple" type="button" data-toggle="modal"
						data-target="#myModal-data" onclick="editData('')">
						<span class="icon-plus"></span>添加
					</button>
					<button type="submit" class="btn btn-purple ml10 mb10">
						查询 <i class="icon-search icon-on-right bigger-110"></i>
					</button>
					<button class="btn  btn-purple" type="button" data-toggle="modal"
						data-target="#myModal-data-label" onclick="editData('')">
						<span class="icon-plus"></span>分类管理
					</button>
				</div>

			</form>
		</div>


		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<div class="row">
					<div class="col-xs-12">
						<div class="table-responsive" style="text-align:right;">
							<table id="dataListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
								<thead>
									<tr>
										<th width="100">单词名</th>
										<th width="100">发音</th>
										<th width="100">单词卡片</th>
										<th width="100">发音解释</th>
										<th width="100">中文解释</th>
										<th width="100">创建时间</th>
										<th width="100">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./courseWordManager"
								params="<%=request.getParameterMap()%>" />

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<form name="modifyData" id="modifyData" action="" method="post"  >
<div class="modal fade" id="myModal-data" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:800px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">添加/修改单词</h4>
			</div>
			<div class="modal-body">
				<input type="hidden" id="domain" value="http://oh351o3re.bkt.clouddn.com/">
				<input type="hidden" id="uptoken_url" value="<%=request.getContextPath()%>/api/getUptoken">
				<input type="hidden" id="index" value="">
				<input type="hidden" id="courseWordId" name="id" value="">
					
				   <div class="col-md-12" style="padding-top: 30px;" align="center">
                       <div id="container" style="position: relative;">
                           <a class="btn btn-default btn-lg " id="pickfiles" href="#" style="position: relative; z-index: 1;">
                               <i class="glyphicon glyphicon-plus"></i>
                               <span>选择文件</span>
                           </a>
                       <div id="html5_1b14b5epighoa2u8aog41a0c3_container" class="moxie-shim moxie-shim-html5" style="position: absolute; top: 0px; left: 0px; width: 171px; height: 46px; overflow: hidden; z-index: 0;"><input id="html5_1b14b5epighoa2u8aog41a0c3" style="font-size: 999px; opacity: 0; position: absolute; top: 0px; left: 0px; width: 100%; height: 100%;" multiple accept="" type="file"></div></div>
                   </div>
                   <div style="display:none" id="success" class="col-md-12">
                       <div class="alert-success">
                           	队列全部文件处理完毕
                       </div>
                   </div>
                   <div class="col-md-12 ">
                       <table class="table table-striped table-hover text-left" style="margin-top:40px;display:none">
                           <thead>
                             <tr>
                               <th class="col-md-4">文件名</th>
                               <th class="col-md-2">大小</th>
                               <th class="col-md-6">详情</th>
                             </tr>
                           </thead>
                           <tbody id="fsUploadProgress">
                           </tbody>
                       </table>
                   </div>
                   
                   <div class="col-md-12 ">
                   		<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">单词名</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="wordTxt" id="wordTxt"
										type="text" data-rule="单词名:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
						</div>
                   </div>
                   
                    <div class="col-md-12 ">
                   		<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">单词释义</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="txtexplain" id="txtexplain"
										type="text" data-rule="单词释义:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
						</div>
                   </div>
                   
                   <div class="col-md-12 ">
                   		<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">单词卡片</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<select name="wordPic" id="wordPic" style="width: 500px">
									</select>
								</div>
							</div>
						</div>
						</div>
                   </div>
                   
                   <div class="col-md-12 ">
                   		<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">单词读音</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<select name="wordAudio" id="wordAudio" style="width: 500px">
									</select>
								</div>
							</div>
						</div>
						</div>
                   </div>
                   
                   <div class="col-md-12 ">
                   		<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">单词释义读音</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<select name="audioexplain" id="audioexplain" style="width: 500px">
									</select>
								</div>
							</div>
						</div>
						</div>
                   </div>
                   
                   	<div class="col-md-12 ">
                   		<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">单词拼写</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<select name="audioexplain" id="audioexplain" style="width: 500px">
									</select>
								</div>
							</div>
						</div>
						</div>
                   </div>
                   
                   
                   <div class="col-md-12 ">
                   		<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">单词音效</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<select name="audioexplain" id="audioexplain" style="width: 500px">
									</select>
								</div>
							</div>
						</div>
						</div>
                   </div>
                   <div class="col-md-12 ">
						<div id="showKeyword"  class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">关键词列表</label>
									<div class="col-sm-10">
									<div class="input-medium">
										<div class="input-group" style="max-width:250px;width:100%;">
										<input type="text" style="max-width:250px;width:100%;" name="tempName">
										 <button type="button" onclick="addKeyword()"placeholder="请输关键词,多个以,号分隔"  class="btn btn-purple ml10 mb10"><span class="icon-plus">添加 </button>
										<div class="tagLists tags" style="border: none;width:auto;padding-left:0;width:650px;">
										</div>
		
									</div>
								</div>
							</div>
						</div>
					</div>
                   	<div class="col-md-12 ">
                   		<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">状态</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<label>&nbsp;&nbsp;<input name="status" type="radio" value="0" checked/>屏蔽</label>&nbsp;&nbsp; 
									<label>&nbsp;&nbsp;<input name="status" type="radio" value="1" />启用 </label> 
								</div>
							</div>
						</div>
						</div>
                   </div>
				
				
			
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="updateData()">保存</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>
</div>
</form>


<form name="modifyData" id="modifyData" action="" method="post"  >
<div class="modal fade" id="myModal-data-label" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:800px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">添加/修改单词分类</h4>
			</div>
			<div class="modal-body">
				
										<table id="dataListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
								<thead>
									<tr>
										<th width="100">标签名称</th>
										<th width="100">标签注解</th>
										<th width="100">图片</th>
										<th width="100">分组</th>
										<th width="100">状态</th>
										<th width="100">创建时间</th>
										<th width="100">操作</th>
									</tr>
								</thead>

							</table>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="updateData()">保存</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>
</div>
</form>

<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/js/validator/jquery.validator.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/js/validator/local/zh_CN.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/js/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/js/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/js/jquery.base64.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/qiniu/qiniu_files/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/qiniu/qiniu_files/moxie.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/qiniu/qiniu_files/plupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/qiniu/qiniu_files/zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/qiniu/qiniu_files/ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/qiniu/qiniu_files/qiniu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/qiniu/qiniu_files/highlight.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/qiniu/main.js"></script>
<script type="text/javascript">hljs.initHighlightingOnLoad();</script>
<script>

var newcontent = '';

var jsonData;

//更新总记录
function updateTotalCount(count){
	//更新总记录
	var total=$("font#fPageTotal").text();
	total=eval(total)+count;
	$("font#fPageTotal").text(total);
}

//标签添加
function addKeyword()
{
	$('.tagLists').find('.tag-info').not('.tag-modal').remove();
	var tempStr = document.forms['modifyData'].tempName.value;
	var temp = tempStr.split(',');

	for(var i=0;i<temp.length;i++)
	{
		if(temp[i]!= '')
		{
			addDidstrict(temp[i]);
		}
	}
}

function addDidstrict(info)
{
	var _html = info;
					
	var newTag = $('.tag-modal').clone(true);
	$(newTag).find('span').html(_html);
	$(newTag).appendTo($('.tagLists')).css('display','').removeClass('tag-modal');

}

//更新界面的方法
function updateUI(){
	newcontent='';
	var dataListTab = $('#dataListTab');
	$("#dataListTab tr:not(:has(th))").remove();
	for(var i=0;i<jsonData.length;i++){
			newcontent = newcontent + '<tr>';	
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].wordTxt+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><audio id="music'+i+'" src="'+jsonData[i].wordAudio+'"  loop="loop">你的浏览器不支持audio标签。</audio>'+
			'<a id="audio_btn" onclick="audioBtnClick('+i+')" ><img src="../images/pause.png" width="48" height="50" id="music_btn'+i+'" border="0"></a></td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+jsonData[i].wordPic+'" /></td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><audio id="music1'+i+'" src="'+jsonData[i].audioexplain+'"  loop="loop">你的浏览器不支持audio标签。</audio>'+
			'<a id="audio_btn" onclick="audioBtnClick1('+i+')" ><img src="../images/pause.png" width="48" height="50" id="music_btn1'+i+'" border="0"></a></td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].txtexplain+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].createtime+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editData('+i+')" data-toggle="modal" data-target="#myModal-data">修改单词</button></td>';
			newcontent = newcontent + '</tr>';
	}
	dataListTab.append(newcontent);
	
}


function editData(index){
	if(index.length == '0'){
		$("#index").val("");
		$("#courseWordId").val("");
		$("#wordTxt").val("");
		$("#txtexplain").val("");
	}else{
		//第一步，传输数组数据到弹出框
		var myData=jsonData[index];
		$("#index").val(index);
		$("#courseWordId").val(myData.id);
		$("#wordTxt").val(myData.wordTxt);
		$("#txtexplain").val(myData.txtexplain);
		$("#wordPic").append("<option value='"+myData.wordPic+"'>"+myData.wordPic+"</option>");
        $("#audioexplain").append("<option value='"+myData.audioexplain+"'>"+myData.audioexplain+"</option>");
        $("#wordAudio").append("<option value='"+myData.wordAudio+"'>"+myData.wordAudio+"</option>");
	}
	updateUI();
}

function updateData(){
	//隐藏modal
	 //$("#myModal-data").modal("hide");
     var formData = new FormData($("#modifyData")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/saveCourseWord' ,
          type: 'POST',
          data: formData,
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		   var dataListTab = $('#dataListTab'); 
		   $("#dataListTab tr:not(:has(th))").remove();
		   dataListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		  },
          success: function (returndata) {
              var index=$("#index").val();
			  if(index.length == '0'){
					//更新数组数据
					//添加到最后
					jsonData.push(returndata.data); 
					updateTotalCount(1);
				}else{
					//更新数组数据
					jsonData[index]=returndata.data;
			  }
			  //更新界面
			  updateUI();
          },
          error: function (returndata) {
          
          }
     });
}


function audioBtnClick(index){
	var music = document.getElementById("music"+index+"");
	if(music.paused){
		music.play();
		$("#music_btn"+index+"").attr("src","<%=request.getContextPath()%>/images/play.png");
	}else{
		music.pause();
		$("#music_btn"+index+"").attr("src","<%=request.getContextPath()%>/images/pause.png");
	}
}

function audioBtnClick1(index){
	var music = document.getElementById("music1"+index+"");
	if(music.paused){
		music.play();
		$("#music_btn1"+index+"").attr("src","<%=request.getContextPath()%>/images/play.png");
	}else{
		music.pause();
		$("#music_btn1"+index+"").attr("src","<%=request.getContextPath()%>/images/pause.png");
	}
}

//初始化
$(document).ready( function () {

	jsonData=$.parseJSON($("#myJson").text()).data;
	//更新界面
	updateUI();
	
});



</script>



