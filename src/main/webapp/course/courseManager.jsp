<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://www.faceye.com/core"%>


<div class="main-content">
	<div id="myJson" style="display: none;">${jsonData}</div>
	<div class="breadcrumbs" id="breadcrumbs">
		<ul class="breadcrumb">
			<li><i class="icon-home home-icon"></i> <a href="main.htm">首页</a>
			</li>
			<li class="active">课程管理</li>
			<li class="active">课程管理</li>
		</ul>

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/course/courseManager"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>课程名称</td>
						<td><input type="text" name="searchStr"
							value="${queryDto.searchStr}">
						</td>
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
							</div></td>
					</tr>

				</table>

				<div class="btn-group col-md-12">
					<button class="btn  btn-purple" type="button" data-toggle="modal"
						data-target="#addMainCourse-data" >
						<span class="icon-plus"></span>添加主课程
					</button>
					<button type="submit" class="btn btn-purple ml10 mb10">
						查询 <i class="icon-search icon-on-right bigger-110"></i>
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
										<th width="20%">课程ID</th>
										<th width="20%">课程封面</th>
										<th width="20%">课程名称</th>
										<th width="20%">创建时间</th>
										<th width="20%">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./courseManager"
								params="<%=request.getParameterMap()%>" />

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/validator/jquery.validator.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/validator/local/zh_CN.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/jquery.base64.min.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/validator/jquery.validator.css">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/uploadify/uploadify.css"
	type="text/css"></link>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/uploadify/jquery.uploadify-3.1.min.js"></script>

<!-- 公共模块JS -->
<script type="text/javascript">

var newcontent = '';
var jsonData;

//初始化
$(document).ready( function () {
	
	jsonData=$.parseJSON($("#myJson").text()).data;
	//更新界面
	updateUI();

});


//更新界面的方法
function updateUI(){
	newcontent='';
	var dataListTab = $('#dataListTab');
	$("#dataListTab tr:not(:has(th))").remove();
	for(var i=0;i<jsonData.length;i++){
			newcontent = newcontent + '<tr>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].id+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img  src="'+jsonData[i].logo+'" style="width:50px;" /></td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].name+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].createDate+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="center" ><button class="btn btn-xs btn-info"  onclick="editData('+i+')" data-toggle="modal" data-target="#myModal-data" >修改主课程</button><button class="btn btn-xs btn-danger"  onclick="deleteData('+i+')" >删除主课程</button><button class="btn btn-xs btn-info" data-toggle="modal" data-target="#myCoursesInfo"  onclick="showCourses('+i+')" >子课程列表</button><button class="btn btn-xs btn-default" data-toggle="modal" data-target="#myModifyCourse"  onclick="addCourseData('+i+')" >添加子课程</button></td>';
			newcontent = newcontent + '</tr>';
	}
	dataListTab.append(newcontent);
	
}


//更新总记录
function updateTotalCount(count){
	//更新总记录
	var total=$("font#fPageTotal").text();
	total=eval(total)+count;
	$("font#fPageTotal").text(total);
}


</script>


<!-- 添加/批量添加主课程模态框 -->
<form class="data" method="post" name="addMainCourseData" id="addMainCourseData"
	enctype="multipart/form-data" action="<%=request.getContextPath() %>/course/addMainCourses">
	
	<div class="modal fade" id="addMainCourse-data" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:900px;height:1200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">添加/批量添加主课程</h4>
				</div>
				<div class="modal-body">
					<IFRAME ID="Frame2" width="800px;" style="min-height:500px;" SRC="<%=request.getContextPath() %>/js/bootstrap/index.html" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes" ></IFRAME> 
				</div>
				
			</div>
		</div>
	</div>
</form>

<!-- 修改主课程信息模态框 -->
<form class="data" method="post" name="modifyData" id="modifyData" enctype="multipart/form-data" action="">
	<input type="hidden"  id="mainCourseIndex" value=""> 
	<input type="hidden" name="productId" id="productId" value=""> 
	<input type="hidden" name="courseBooksJson" id="courseBooksJson" value=""> 
	<div class="modal fade" id="myModal-data" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:1200px;height:800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">修改主课程信息</h4>
				</div>
				<div class="modal-body">

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">课程名</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="name" id="name" type="text"
										data-rule="课程名:required;" style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					<div id="showGuige" class="form-group" style="height:200px;">
							<label class="col-sm-2 control-label no-padding-right text-right"><span id="name4">课程书籍</span></label>
								<div class="col-sm-10">
									<div class="input-medium">
										<div class="input-group">
												<button class="btn btn-info btn-sm" type="button" onclick="addBookLib()"><span id="name5">添加书籍</span></button>
												<table id="mallTab"style="width:800px;" class="table table-striped table-bordered table-hover" style="text-align:left;">
												<thead>
													<tr>
														<th width="100"><span id="name6">书名</span></th>
														<th width="100"><span id="name7">图片</span></th>
														<th width="100"><span id="name8">ISBN</span></th>
														<th width="120">操作</th>
													</tr>
												</thead>
												</table>
										</div>
								</div>
							</div>
						</div>


				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="updateData()">保存</button>

					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</form>

<style>
#testform{
  display: none;
}

.label1{
  display:block;
  width: 300px;
  height:150px;
  margin: 30px auto;
  border-radius: 10px;
  border:2px dashed #ddd;
  overflow: hidden;
  cursor: pointer;
}
.label1 span{
  display:block;
  width:50px;
  height:50px;
  border-radius: 100%;
  border:3px solid #0983C7;
  margin:30px auto 15px;
  position:relative;
}
.label1 em{
  font-style: normal;
  text-align: center;
  display:block;
}
.label1 span:before, .label1 span:after{
  display:block;
  content:'';
  background-color: #0983C7;
  position: absolute;
}
.label1 span:before{
  width:3px;
  height:30px;
  top:10px;
  left:24px;
}
.label1 span:after{
  width:30px;
  height:3px;
  top:24px;
  left:10px;
}

</style>

<script type="text/javascript">
$(function(){
   //var domain = "http://7xocov.com1.z0.glb.clouddn.com/";
   var domain = "http://source.fandoutech.com.cn/";
   var $key = $('#key'); 
   var $userfile = $('#userfile'); 
   var tokenUrl='<%=request.getContextPath()%>/api/getUptoken?bucketName=source';
   $.get(tokenUrl, function(data) {
      var dat=jQuery.parseJSON(data);
      //$("#tokenfile").val("xozWSPMxkMjIVoHg2JyXq4-7-oJaEADLOKHVR0vU:LX3n6uOWuObogDlZ5w3R191b9Po=:eyJzY29wZSI6Impzc2RrIiwiZGVhZGxpbmUiOjE0ODc4MjAwNTh9");
      $("#tokenfile").val(dat.uptoken);
   });
   $("#userfile").change(function() {  
    var selectedFile = $userfile.val();
    if (selectedFile) {
      // randomly generate the final file name
      var ramdomName = Math.random().toString(36).substr(2) + $userfile.val().match(/\.?[^.\/]+$/);
      $key.val(ramdomName);
    } else {
      return false;
    }
    
    var f = new FormData(document.getElementById("uploadFileForm"));
    
    $.ajax({
      url:  'http://up.qiniu.com',  // Different bucket zone has different upload url, you can get right url by the browser error massage when uploading a file with wrong upload url.
      type: 'POST',
      data: f,
      processData: false,
      contentType: false,
      xhr: function(){
        myXhr = $.ajaxSettings.xhr();  
        if(myXhr.upload){
          myXhr.upload.addEventListener('progress',function(e) {
            // console.log(e);
            if (e.lengthComputable) {
              var percent = e.loaded/e.total*100;
            //  $progress.html('上传：' + e.loaded + "/" + e.total+" bytes. " + percent.toFixed(2) + "%");
            }
          }, false);
        }
        return myXhr;
      },
      success: function(res) {
      	alert("上传成功");
        console.log("成功：" + JSON.stringify(res));
        var str = '<span>已上传：' + res.key + '</span>';
        if (res.key && res.key.match(/\.(jpg|jpeg|png|gif)$/)) {
          str += '<img src="' + domain + res.key + '"/>';
        }
        inputPic.value=domain + res.key;
        //alert(domain + res.key);
        $("#uploadFileModal").modal("hide");
        
      },
      error: function(res) {  
        alert("上传失败");
        console.log("失败:" +  JSON.stringify(res));
       // $uploadedResult.html('上传失败：' + res.responseText);
      }
    });
    return false;
  });
 });

</script>

<!-- 上传文件到七牛模态框 -->
<form class="data" method="post" name="uploadFileForm"
	id="uploadFileForm" enctype="multipart/form-data" action="">
	<div class="modal fade" id="uploadFileModal" tabindex="-1"
		role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:500px;height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">上传文件到七牛</h4>
				</div>
				<div class="modal-body">

					<div id="testform" class="form-group" style="height:500px;">
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input name="key" id="key" type="hidden" value="">
									<input id="tokenfile" name="token" type="hidden" value="">
							        <input id="userfile" name="file" type="file" />
							        <input name="accept" type="hidden" />
									
								</div>
							</div>
						</div>
					</div>
					
					<!-- add file -->
				    <label class="label1" for="userfile">
				      <span></span>
				      <em>添加文件</em>
				    </label>
				     

				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick="saveUploadFile()">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</form>

<!-- 主课程JS -->
<script type="text/javascript">

var inputPic;


//预修改主课程
function editData(index)
{
	var myData=jsonData[index];
	$("#mainCourseIndex").val(index);
	$("#productId").val(myData.id);
	$("#name").val(myData.name);
	var url='<%=request.getContextPath() %>/api/findCourseBookLib?productId='+myData.id;
	var resultData=$.ajax({url:url,async:false});
	var courseBooksJson=$.parseJSON(resultData.responseText);
	
	var courseBooks=courseBooksJson.data;
	var tempRow = $("#mallTab").find("tr").length;
	//清空之前的书籍
	for(var k=1;k<tempRow;k++){
		delRows(k);
	}
	for(var z=0;z<courseBooks.length;z++){
		 var $tr=$("#mallTab tr:last");
		 var tempRow = $("#mallTab").find("tr").length;
		 var trHtml = '<tr id="tempRow'+tempRow+'">';
	     trHtml = trHtml + '<td><input type="text" name="names" required value="'+courseBooks[z].bookName+'"></td>';
		 trHtml = trHtml + '<td><input type="text" name="prices" onFocus=uploadPic(this) required value="'+courseBooks[z].bookCover+'"></td>';
		 trHtml = trHtml + '<td><input type="text" name="counts" required value="'+courseBooks[z].bookISBN+'"></td>';
	     trHtml = trHtml + '<td><a href="javascript:delRows('+tempRow+')">删除</a></td></tr>';
	     $tr.after(trHtml);
	}
	
}

//修改数据主课程
function updateData() {
	 var index=$("#mainCourseIndex").val();
     //隐藏modal
	 $("#myModal-data").modal("hide");
	 $("#courseBooksJson").val(getRows());
     var formData = new FormData($("#modifyData")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/updateMainCourseByProductId' ,
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
			 
			  //更新数组数据
			  jsonData[index]=returndata.data;
			  //更新界面
			  updateUI();
          },
          error: function (returndata) {
          
          }
     });
}


//删除整个课程
function deleteData(index){
	if(confirm("确定要删除"+jsonData[index].name+"吗？"))
	{
		$.ajax({
	            //提交数据的类型 POST GET
	            type:"POST",
	            //提交的网址
	            url:"<%=request.getContextPath() %>/api/deleteMainCourse",
	            //提交的数据
	            data:{productId:""+jsonData[index].id},
	            //返回数据的格式
	            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
	            //在请求之前调用的函数
	            beforeSend:function(){
	            	
	            },
	            //成功返回之后调用的函数             
	            success:function(data){
	            	alert("删除成功！");
	            	window.location.href="<%=request.getContextPath() %>/course/courseManager";
	            },
	            //调用执行后调用的函数
	            complete: function(XMLHttpRequest, textStatus){
	            	
	            },
	            //调用出错执行的函数
	            error: function(){
	                //请求出错处理
	                alert("删除失败！");
	            	window.location.href="<%=request.getContextPath() %>/course/courseManager";
	            }         
	         });
         }
}


//添加主课程附加书籍
function addBookLib()
{
	 var $tr=$("#mallTab tr:last");
	 var tempRow = $("#mallTab").find("tr").length;
	 var trHtml = '<tr id="tempRow'+tempRow+'">';
     trHtml = trHtml + '<td><input type="text" name="names" required value=""></td>';
	 trHtml = trHtml + '<td><input type="text" name="prices" onFocus=uploadPic(this) required value=""></td>';
	 trHtml = trHtml + '<td><input type="text" name="counts" required value=""></td>';
     trHtml = trHtml + '<td><a href="javascript:delRows('+tempRow+')">删除</a></td></tr>';
     $tr.after(trHtml);
}

//Focus上传文件
function uploadPic(pic){
	inputPic=pic;
	$("#uploadFileModal").modal("show");
	return;
}

//删除附加图书行
function delRows(index)
{
	$("#tempRow"+index).remove();
}

//得到所有附加图书行
function getRows(){
	
	var tempRow = $("#mallTab").find("tr").length;
	var objJson = [];
	for ( var z = 1; z < tempRow; z++) {
		var bookJsonStr='{';
		$("#tempRow"+z+" :input[type='text']").each(function(i){ 
		    switch (i) {
				case 0:
					bookJsonStr=bookJsonStr+'"bookName":"'+this.value+'",';
				break;
				case 1:
					bookJsonStr=bookJsonStr+'"bookCover":"'+this.value+'",';
				break;
				case 2:
					bookJsonStr=bookJsonStr+'"bookISBN":"'+this.value+'"';
				break;
				default:
					break;
				}
		});
		
		bookJsonStr=bookJsonStr+'}';
		objJson.push(bookJsonStr); 
	}
	return '['+objJson.toString()+']';
}



</script>



<!-- 子课程信息列表模态框 -->
<form class="data" method="post" name="coursesInfo" id="coursesInfo"
	enctype="multipart/form-data" action="">
	<div class="modal fade" id="myCoursesInfo" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:1000px;height: 1200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">子课程信息列表</h4>
				</div>
				<div class="modal-body">

					<table id="coursesInfoListTab"
						class="table table-striped table-bordered table-hover"
						style="text-align:left;">
						<thead>
							<tr>
								<th width="100">子课程名称</th>
								<th width="100">总课时</th>
								<th width="120">操作</th>
							</tr>
						</thead>

					</table>

				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</form>


<!-- 子课程课时信息列表模态框 -->
<form class="data" method="post" name="coursePeriodsInfo"
	id="coursePeriodsInfo" enctype="multipart/form-data" action="">
	<div class="modal fade" id="myCoursePeriods" tabindex="-1"
		role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:1000px;height: 1200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">子课程课时信息列表</h4>
				</div>
				<div class="modal-body">

					<table id="coursePeriodsInfoListTab"
						class="table table-striped table-bordered table-hover"
						style="text-align:left;">
						<thead>
							<tr>
								<th width="100">课时名称</th>
								<th width="120">操作</th>
							</tr>
						</thead>

					</table>

				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</form>



<!-- 修改子课程信息模态框 -->
<form class="data" method="post" name="modifyCourse" id="modifyCourse"
	enctype="multipart/form-data" action="">
	<input type="hidden"  id="childCourseIndex" value=""> 
	<input type="hidden" name="courseId" id="courseId" value="">
	<input type="hidden" name="mainCourseId" id="mainCourseId" value="">
	<div class="modal fade" id="myModifyCourse" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:1000px;height: 1200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">修改子课程信息</h4>
				</div>
				<div class="modal-body">

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">子课程名</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="courseName" id="courseName"
										type="text" data-rule="子课程名:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">子课程总课时</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="totalClass" id="totalClass"
										type="text" data-rule="子课程总课时:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>

				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="updateCourseData()">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</form>

<!-- 子课程JS -->
<script type="text/javascript">

var courseData;

//展示子课程列表
function showCourses(i){
	var coursesInfoListTab = $('#coursesInfoListTab'); 
	$.ajax({
		type: "POST",
		data:"productId="+jsonData[i].id,
		dataType: "json",
		url: "<%=request.getContextPath() %>/api/getAllCoursesByProductId", 
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
		   $("#coursesInfoListTab tr:not(:has(th))").remove();
		   coursesInfoListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		},
		complete:function(){
			
		},
		success: function(data){
			courseData=data.data;
			var courseContent='';
			$("#coursesInfoListTab tr:not(:has(th))").remove();
			for(var i=0;i<courseData.length;i++){
					courseContent = courseContent + '<tr>';	
					courseContent = courseContent + '<td nowrap="nowrap" align="left" >'+courseData[i].courseName+'</td>';
					courseContent = courseContent + '<td nowrap="nowrap" align="left" >'+courseData[i].totalClass+'</td>';
					courseContent = courseContent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editCourseData('+i+')" data-toggle="modal" data-target="#myModifyCourse">修改子课程</button><button class="btn btn-xs btn-danger"  onclick="deleteChildCourse('+i+')" >删除子课程</button><button class="btn btn-xs btn-info" data-toggle="modal" data-target="#myCoursePeriods"  onclick="showCoursePeriods('+i+')" >子课时列表</button><button class="btn btn-xs btn-default"  onclick="addCoursePeriodData('+i+')" data-toggle="modal" data-target="#myModifyCoursePeriod">添加子课时</button></td>';
					courseContent = courseContent + '</tr>';
			}
			coursesInfoListTab.append(courseContent);
		}
	});	
	
}

//预修改子课程
function editCourseData(index)
{
	//第一步，传输数组数据到弹出框
	var myData=courseData[index];
	$("#childCourseIndex").val(index);
	$("#courseId").val(myData.courseId);
	$("#courseName").val(myData.courseName);
	$("#totalClass").val(myData.totalClass);
	$("#mainCourseId").val(myData.id);
}

//预添加子课程
function addCourseData(index)
{
	//第一步，传输数组数据到弹出框
	var myData=courseData[index];
	$("#childCourseIndex").val("");
	$("#courseId").val("");
	$("#courseName").val("");
	$("#totalClass").val("");
	$("#mainCourseId").val(myData.id);
}



//修改子课程数据
function updateCourseData(index) {
     //隐藏modal
	 $("#myModifyCourse").modal("hide");
     var formData = new FormData($("#modifyCourse")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/updateChildCourseByCourseId',
          type: 'POST',
          data: formData,
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		  },
          success: function (returndata) {
              $("#myCoursesInfo").modal("hide");
          },
          error: function (returndata) {
          
          }
     });
}


//删除子课程
function deleteChildCourse(index){
	if(confirm("确定要删除"+courseData[index].courseName+"吗？"))
	{
		$.ajax({
	            //提交数据的类型 POST GET
	            type:"POST",
	            //提交的网址
	            url:"<%=request.getContextPath() %>/api/deleteChildCourse",
	            //提交的数据
	            data:{courseId:""+courseData[index].courseId},
	            //返回数据的格式
	            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
	            //在请求之前调用的函数
	            beforeSend:function(){
	            	
	            },
	            //成功返回之后调用的函数             
	            success:function(data){
	            	alert("删除成功！");
	            	window.location.href="<%=request.getContextPath() %>/course/courseManager";
	            },
	            //调用执行后调用的函数
	            complete: function(XMLHttpRequest, textStatus){
	            	
	            },
	            //调用出错执行的函数
	            error: function(){
	                //请求出错处理
	                alert("删除失败！");
	                window.location.href="<%=request.getContextPath() %>/course/courseManager";
	            }         
	         });
         }
}




</script>



<!-- 修改课时信息模态框 -->
<form class="data" method="post" name="modifyCoursePeriod"
	id="modifyCoursePeriod" enctype="multipart/form-data" action="">
	<input type="hidden"  id="coursePeriodIndex" value=""> 
	<input type="hidden" name="periodId" id="periodId" value="">
	<input type="hidden" name="childCourseId" id="childCourseId" value="">
	<div class="modal fade" id="myModifyCoursePeriod" tabindex="-1"
		role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:1000px;height: 1200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">修改课时信息</h4>
				</div>
				<div class="modal-body">

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">课时名</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="courseperiodName"
										id="courseperiodName" type="text" data-rule="课时名:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:350px;">
						<label class="col-sm-2 control-label no-padding-right text-right">课时执行脚本</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
								<!-- <input class="form-control" name="missionCmdList"
										id="missionCmdList" type="text" data-rule="课时执行脚本:required;"
										style="max-width:650px;width:100%;" /> -->	
									<textarea rows="20" cols="80" name="missionCmdList" id="missionCmdList" >
									</textarea>
								</div>
							</div>
						</div>
					</div>

				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="updateCoursePeriodData()">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</form>

<!-- 子课时信息JS -->
<script type="text/javascript">

var coursePeriodsData;

//展示课时列表
function showCoursePeriods(i){
	var coursePeriodsInfoListTab = $('#coursePeriodsInfoListTab'); 
	$.ajax({
		type: "POST",
		data:"courseId="+courseData[i].courseId,
		dataType: "json",
		url: "<%=request.getContextPath() %>/api/getAllCoursePeriodsByCourseId", 
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
		   $("#coursePeriodsInfoListTab tr:not(:has(th))").remove();
		   coursePeriodsInfoListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		},
		complete:function(){
			
		},
		success: function(data){
			coursePeriodsData=data.data;
			var coursePeriodsContent='';
			$("#coursePeriodsInfoListTab tr:not(:has(th))").remove();
			for(var i=0;i<coursePeriodsData.length;i++){
					coursePeriodsContent = coursePeriodsContent + '<tr>';	
					coursePeriodsContent = coursePeriodsContent + '<td nowrap="nowrap" align="left" >'+coursePeriodsData[i].courseperiodName+'</td>';
					coursePeriodsContent = coursePeriodsContent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editCoursePeriodData('+i+')" data-toggle="modal" data-target="#myModifyCoursePeriod">修改课时</button><button class="btn btn-xs btn-danger"  onclick="deletePeriodCourse('+i+')" >删除子课时</button></td>';
					coursePeriodsContent = coursePeriodsContent + '</tr>';
			}
			coursePeriodsInfoListTab.append(coursePeriodsContent);
		}
	});	
	
}

//预修改课时
function editCoursePeriodData(index)
{
		//第一步，传输数组数据到弹出框
		var myData=coursePeriodsData[index];
		$("#coursePeriodIndex").val(index);
		$("#periodId").val(myData.id);
		$("#courseperiodName").val(myData.courseperiodName);
		$("#missionCmdList").val(myData.missionCmdList);
		$("#childCourseId").val(childCourseId);
}

//预添加课时
function addCoursePeriodData(index)
{
	$("#coursePeriodIndex").val("");
	$("#periodId").val("");
	$("#courseperiodName").val("");
	$("#missionCmdList").val("");
	var myData=courseData[index];
	var childCourseId=myData.courseId;
	$("#childCourseId").val(childCourseId);
}

//修改数据课时
function updateCoursePeriodData() {
     //隐藏modal
	 $("#myModifyCoursePeriod").modal("hide");
     var formData = new FormData($("#modifyCoursePeriod")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/updateCoursePeriodByPeriodId',
          type: 'POST',
          data: formData,
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		  },
          success: function (returndata) {
              $("#myCoursePeriods").modal("hide");
          },
          error: function (returndata) {
          
          }
     });
}


//删除子课时
function deletePeriodCourse(index){
	if(confirm("确定要删除"+coursePeriodsData[index].courseperiodName+"吗？"))
	{
		$.ajax({
	            //提交数据的类型 POST GET
	            type:"POST",
	            //提交的网址
	            url:"<%=request.getContextPath() %>/api/deletePeriodCourse",
	            //提交的数据
	            data:{periodId:""+coursePeriodsData[index].id},
	            //返回数据的格式
	            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
	            //在请求之前调用的函数
	            beforeSend:function(){
	            	
	            },
	            //成功返回之后调用的函数             
	            success:function(data){
	            	alert("删除成功！");
	            	window.location.href="<%=request.getContextPath() %>/course/courseManager";
	            },
	            //调用执行后调用的函数
	            complete: function(XMLHttpRequest, textStatus){
	            	
	            },
	            //调用出错执行的函数
	            error: function(){
	                //请求出错处理
	                alert("删除失败！");
	                window.location.href="<%=request.getContextPath() %>/course/courseManager";
	            }         
	         });
         }
}



</script>






