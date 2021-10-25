<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://www.faceye.com/core"%>

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

//更新界面的方法
function updateUI(){
	newcontent='';
	var dataListTab = $('#dataListTab');
	$("#dataListTab tr:not(:has(th))").remove();
	for(var i=0;i<jsonData.length;i++){
			newcontent = newcontent + '<tr><td nowrap="nowrap" align="left" >'+jsonData[i].classGradesName+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img src="'+jsonData[i].cover+'" width="50" height="50" /></td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><a href="/wechat/lesson/findClassGrades?teacherId='+ jsonData[i].teacherId +'">'+jsonData[i].teacherName+'</a></td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].summary+'</td>';
			if(jsonData[i].status==1){
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >启用</td>';
			}else{
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >暂停</td>';
			}
			
			var auditingStatus;
			if(jsonData[i].auditingStatus == 4){
				auditingStatus = "审核通过";
			}else if(jsonData[i].auditingStatus==2){
				auditingStatus = "审核中";
			}else if(jsonData[i].auditingStatus==0){
				auditingStatus = "审核不通过";
			}else if(jsonData[i].auditingStatus==-1){
				auditingStatus = "内容违规";
			}else if(jsonData[i].auditingStatus==1){
				auditingStatus = "待审核";			
			}else{
				auditingStatus = "未知状态";
			}
			newcontent = newcontent + '<td nowrap="nowrap" align="left" style=""><div class="input-group"><select class="input-medium" onchange="changeAuditingStatus('+i+')" id="auditingStatusSelect_'+i+'" ><option value="">'+auditingStatus+'</option><option value="-1">内容违规</option><option value="0">审核不通过</option><option value="1">待审核</option><option value="2">审核中</option><option value="4">已审核</option></select></div></td>'
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].price+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].studentCount+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].sort+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editArticle('+i+')" ><i class="icon-edit bigger-120"></i></button>';
			newcontent = newcontent + '&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="updateClassGradesStatus('+i+',-1)" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button>';
			newcontent = newcontent + '&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="showStudent('+jsonData[i].classGradesId+')" class="btn btn-xs btn-info"><i class="icon-edit bigger-120"></i>查看学生</button>';
			newcontent = newcontent + '&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="setOpenDate('+i+')" class="btn btn-xs btn-info"><i class="icon-edit bigger-120"></i>设置开班日期</button>';
			newcontent = newcontent + '&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="quoteClassCourse('+i+')" class="btn btn-xs btn-info"><i class="icon-edit bigger-120"></i>引用课程表</button>';
			newcontent = newcontent + '&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="settingGrades('+jsonData[i].classGradesId+')" class="btn btn-xs btn-info"><i class="icon-edit bigger-120"></i>课程管理</button></td>';
			
			newcontent = newcontent + '</tr>';
	}
	dataListTab.append(newcontent);
	
}


function changeAuditingStatus(index){
	var classGradesId = jsonData[index].classGradesId;
	var auditingStatus = $("#auditingStatusSelect_"+index+" option:selected").val();
	var formData = new FormData();
	formData.append("classGradesId",classGradesId);
	formData.append("auditingStatus",auditingStatus);
	$.ajax({
          url: '<%=request.getContextPath() %>/api/updateClassGradesAuditingStatus' ,
          type: 'POST',
          data: formData,
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		  },
          success: function (data) {
				jsonData[index].auditingStatus = auditingStatus;
				alert("已经保存！");
          },
          error: function (returndata) {
          
          }
     });
}
function setOpenDate(index){
	$("#classGradesOpenDate").modal("show");
	var classGradesId = jsonData[index].classGradesId;
	$("#classGradesId").val(classGradesId);
	var classOpenDate = jsonData[index].classOpenDate;
	$("#classOpenDate").val();
}



function saveOpenDate(){
	var classOpenDate = $("#classOpenDate").val(); 
	var classGradesId = $("#classGradesId").val(); 
	var formData = new FormData();
	formData.append("id",classGradesId);
	formData.append("classOpenDate",classOpenDate);
	$.ajax({
          url: '<%=request.getContextPath() %>/api/updateClassGradesOpenDate' ,
          type: 'POST',
          data: formData,
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		  },
          success: function (data) {
				$("#classGradesOpenDate").modal("hide");
          },
          error: function (returndata) {
          
          }
     });


}
//初始化
$(document).ready( function () {
	
	jsonData=$.parseJSON($("#myJson").text()).data;
	//更新界面
	updateUI();

});

function updateClassGradesStatus(index,status){
	if(window.confirm('你确定要删除班级吗？')){
          
    }else{
          return false;
    }
	var classGradesId = jsonData[index].classGradesId;
	var formData = new FormData();
	formData.append("id",classGradesId);
	formData.append("status",status);
	$.ajax({
          url: '<%=request.getContextPath() %>/api/updateClassGradesStatus' ,
          type: 'POST',
          data: formData,
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		  },
          success: function (data) {
          		jsonData.remove(index);
          		updateTotalCount(-1);
          		updateUI();

          },
          error: function (returndata) {
          
          }
     });
}
function deleteClassGrades(index){
	if(window.confirm('你确定要删除班级吗？')){
          
    }else{
          return false;
    }
    if(window.confirm('请再次确认？')){
          
    }else{
          return false;
    }
	var classGradesId = jsonData[index].classGradesId
	var formData = new FormData();
	formData.append("id",classGradesId);
	$.ajax({
          url: '<%=request.getContextPath() %>/api/deleteClassGrades' ,
          type: 'POST',
          data: formData,
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		  },
          success: function (data) {
          		jsonData.remove(index)
          		updateTotalCount(-1);
          		updateUI()

          },
          error: function (returndata) {
          
          }
     });


}
var studentList;
var currentClassGradesId;
function showStudent(classGradesId){
	$("#classGradesStudentList").modal({backdrop: 'static', keyboard: false},"show");
	currentClassGradesId = classGradesId;
	var formData = new FormData();
	formData.append("classGradesId",classGradesId);
	formData.append("page","1");
	formData.append("pageSize","1000");
	$.ajax({
          url: '<%=request.getContextPath() %>/api/findClassStudentsRecordByClassGradesId' ,
          type: 'POST',
          data: formData,
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		   		$("#studentListTab tr:not(:has(th))").remove();
		   		$("#studentListTab").append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		  },
          success: function (data) {
          	  	studentList = data.data;
				showStudentList();
          },
          error: function (returndata) {
          
          }
     });


}

function showStudentList(){
	$("#studentListTab tr:not(:has(th))").remove();
	var newcontent = "";
	for(var i=0;i<studentList.length;i++){
		newcontent = newcontent + '<tr>';
		newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+studentList[i].id+'</td>';
		newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+studentList[i].studentName+'</td>';
		newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+studentList[i].epalId+'</td>';
		if(studentList[i].gradesStatus == -1){
			newcontent = newcontent + '<td nowrap="nowrap" align="left" class="btn btn-xs btn-danger">已退出</td>';
		}else{
		
			newcontent = newcontent + '<td nowrap="nowrap" align="left" class="btn btn-xs btn-info">正常</td>';
		}
		newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+studentList[i].studyCount+'/'+studentList[i].classRoomCount+'</td>';
		newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+studentList[i].lastStudyTime+'</td>';
		
		newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+studentList[i].bindMobile+'</td>';
		
		
		newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="showStudentClassRecord('+studentList[i].id+','+classGradesId+')" ><i class="icon-edit bigger-120"></i>查看学习记录</button>';
		
		if(studentList[i].gradesStatus == -1){
			newcontent = newcontent + '&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="joinClassGrades('+studentList[i].id+','+classGradesId+')" class="btn btn-xs btn-info"><i class="icon-edit bigger-120"></i>重新加入</button>';
		}else{
			newcontent = newcontent + '&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="signOutClassGrades('+studentList[i].id+','+classGradesId+')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i>踢出班级</button>';
		}		
		newcontent = newcontent + '</td>';

		newcontent = newcontent + '</tr>';					
	}
	$("#studentListTab").append(newcontent);

}
var currentIndex;
//预添加或者修改
function editData(index)
{	
	if(index.length == '0'){
		$("#index").val("");
		$("#id").val("");
		$("#albumId").val("");
		$("#name").val("");
		$("#intro").val("");
		$("#image").val("");
		$("#channelId").val("");
		$("#status").val("");
		$("#sortId").val("");

	}else{
		//第一步，传输数组数据到弹出框
		var myData=jsonData[index];
		$("#index").val(index);
		$("#id").val(myData.id);
		currentAlbumId = myData.id;
		currentIndex = index;
		$("#albumId").val(myData.album_id);
		$("#name").val(myData.name);
		$("#intro").val(myData.intro);
		$("#image").val(myData.image);
		$("#channelId").val(myData.channel_id);
		$("#status").val(myData.status);
		$("#sortId").val(myData.sort_id);
		showTag(myData.id);
	}
}




//删除标签
function deleteTag(id){
	var id = id;
	$.ajax({
          url: '<%=request.getContextPath() %>/api/channel/deleteTag' ,
          type: 'GET',
          data: "id="+id,
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		   
		  },
          success: function (returndata) {
          	  editData(currentIndex);
          },
          error: function (returndata) {
          
          }
     });
}

//修改数据
function updateData(index) {
     //隐藏modal
	 $("#myModal-data").modal("hide");
     var formData = new FormData($("#modifyData")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/channel/saveAlbum' ,
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
			  if(index == ''){
					//更新数组数据
					//添加到最后
					jsonData.push(returndata.data); 
					updateTotalCount(1);
				}else{
					//更新数组数据
					var data = returndata.data;
					data["channel_id"] = data["channelId"]
					data["album_id"] = data["albumId"]
					data["sort_id"] = data["sortId"]
					jsonData[index]= data;
			  }
			  //更新界面
			  updateUI();
          },
          error: function (returndata) {
          
          }
     });
}

	var classCourse;
	
	function getClassCourse(gradesId){
		var formData = new FormData();
		formData.append("gradesId",gradesId);
		$.ajax({
          url: '<%=request.getContextPath() %>/api/getClassCourseByClassGradesId' ,
          type: 'POST',
          data:formData,
          dataType: "json",  
          async: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		  },
          success: function (returndata) {
			  classCourse = returndata.data;
			  
          },
          error: function (returndata) {
          
          }
     });
	
	}
	function settingGrades(gradesId){
		$("#settingGrades").modal("show");
		$("#currentGradesId").val(gradesId);
		initClassCourseTab();
		updateSettingClassUI(gradesId);
	}
	
	
	function getMyDay(date){
		var str = "";  
		var arr1 = date.split("-");   
		var date1 = new Date(arr1[0],parseInt(arr1[1])-1,arr1[2]);   
		var week = date1.getDay();  
		return week+1;
	}

	function getRow(startDate,endDate){  
	    var startTime = new Date(Date.parse(startDate.replace(/-/g,   "/"))).getTime();     
	    var endTime = new Date(Date.parse(endDate.replace(/-/g,   "/"))).getTime();     
	    var dates = Math.abs((startTime - endTime))/(1000*60*60*24); 
	    var temp =  dates/7;   
	    return  parseInt(temp)+1;    
	}
	function getNextDate(fromDate){
		var curDate = new Date(Date.parse(fromDate.replace(/-/g,"/")));
		curDate.setDate(curDate.getDate()+1);
		var year = curDate.getFullYear();
		var month = (curDate.getMonth()+1)<10?"0"+(curDate.getMonth()+1):(curDate.getMonth()+1);
		var day = curDate.getDate()<10?"0"+curDate.getDate():curDate.getDate();
		return year+"-"+month+"-"+day;
	}
	function initClassCourseTab(){
		$("#classCourseTab tr:not(:has(th))").remove();
		var newcontent = "";
		for(var i=1;i<11;i++){
			newcontent += '<tr><td><div class="classRoom-item" id="'+i+'-1"><button>+</button></div></td>';
			newcontent += '<td><div class="classRoom-item" id="'+i+'-2"><button>+</button></div></td>';
			newcontent += '<td><div class="classRoom-item" id="'+i+'-3"><button>+</button></div></td>';
			newcontent += '<td><div class="classRoom-item" id="'+i+'-4"><button>+</button></div></td>';
			newcontent += '<td><div class="classRoom-item" id="'+i+'-5"><button>+</button></div></td>';
			newcontent += '<td><div class="classRoom-item" id="'+i+'-6"><button>+</button></div></td>';
			newcontent += '<td><div class="classRoom-item" id="'+i+'-7"><button>+</button></div></td></tr>';					
		}
		$("#classCourseTab").append(newcontent);
	}
	function updateSettingClassUI(gradesId) {
	    getClassCourse(gradesId);
	    var startDate = "2017-07-12";
	    var currentData = startDate;
	    var startColumn = getMyDay(startDate);
	    var row = 1
	    for (var column = 1; column < 8; column++) {
	        if (column < startColumn) {} else {
	            $("#" + row + "-" + column).empty();
	            var newContent = "";
	            var doDate = currentData;
	            if (classCourse.hasOwnProperty(currentData)) {
	                for (var j = 0; j < classCourse[currentData].length; j++) {
	                    var temp = classCourse[currentData][j];
	                    newContent = newContent + '<div>';
	                    newContent = newContent + '《';
	                    newContent = newContent + temp["className"];
	                    newContent = newContent + '》';
	                    if (temp["doSlot"] == 100) {
	                        newContent = newContent + '早读';
	                    } else if (temp["doSlot"] == 300) {
	                        newContent = newContent + '课堂';
	                    } else {
	                        newContent = newContent + '晚听';
	                    }
	                    newContent = newContent + currentData;
	                    newContent = newContent + '</div>';
	                }
	                $("#" + row + "-" + column).append(newContent);
	                currentData = getNextDate(currentData);
	
	            } else {
	                newContent = newContent + currentData;
	                $("#" + row + "-" + column).append(newContent);
	                currentData = getNextDate(currentData);
	            }
	
	        }
	
	    }
	
	    for (var row = 2; row < 11; row++) {
	        for (var column = 1; column < 8; column++) {
				$("#" + row + "-" + column).empty();
	            var newContent = "";
	            var doDate = currentData;
	            if (classCourse.hasOwnProperty(currentData)) {
	                for (var j = 0; j < classCourse[currentData].length; j++) {
	                    var temp = classCourse[currentData][j];
	                    newContent = newContent + '<div>';
	                    newContent = newContent + '《';
	                    newContent = newContent + temp["className"];
	                    newContent = newContent + '》';
	                    if (temp["doSlot"] == 100) {
	                        newContent = newContent + '早读';
	                    } else if (temp["doSlot"] == 300) {
	                        newContent = newContent + '课堂';
	                    } else {
	                        newContent = newContent + '晚听';
	                    }
	                    newContent = newContent + currentData;
	                    newContent = newContent + '</div>';
	                }
	                $("#" + row + "-" + column).append(newContent);
	                currentData = getNextDate(currentData);
	
	            } else {
	                newContent = newContent + currentData;
	                $("#" + row + "-" + column).append(newContent);
	                currentData = getNextDate(currentData);
	            }
			}
	    }
	}
</script>


<div class="main-content">
	<div id="myJson" style="display: none;">${jsonData}</div>
	<div class="breadcrumbs" id="breadcrumbs">
		<ul class="breadcrumb">
			<li><i class="icon-home home-icon"></i> <a href="main.htm">首页</a>
			</li>
			<li class="active">在线课堂管理</li>
			<li class="active">班级管理</li>
		</ul>

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/lesson/findClassGrades"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>班级名称</td>
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
					<button type="submit" class="btn btn-purple ml10 mb10">
						查询 <i class="icon-search icon-on-right bigger-110"></i>
					</button>
					<button class="btn btn-purple ml10 mb10" onclick="editData('')" data-toggle="modal" data-target="#myModal-data" value="添加">
					添加班级</button>
					<!-- <button class="btn btn-purple ml10 mb10" onclick="recommendAlbums()" data-toggle="modal" data-target="#recommendAlbums">
					添加到推荐</button> -->
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
										<th width="100">班级名字</th>
										<th width="100">封面</th>
										<th width="100">老师名字</th>
										<th width="100">简单描述</th>
										<th width="50">状态</th>
										<th width="50">审核状态</th>
										<th width="50">班级价格</th>
										<th width="50">班级人数</th>
										<th width="50">排序</th>
										<th width="300">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./findClassGrades"
								params="<%=request.getParameterMap()%>" />

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<style>
	.classRoom-item{
		height:100px;
	}
</style>

<!-- 课程表弹窗 -->
<div class="modal fade" id="settingGrades" tabindex="-1" 
		role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:90%;height: auto;">
			<div class="modal-content">
				<input id="currentGradesId" name="currentGradesId" style="display:none;"></input>
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">班级设置</h4>
				</div>
				<div class="modal-body">

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">开班日期</label>
						<div class="col-sm-9">
							<div class="input-medium" style="">
								<div class="input-group">
									<input class="input-medium date-picker" type="text"
										name="startDate" value="${queryDto.startDate}"
										data-date-format="yyyy-mm-dd" placeholder="年-月-日" /> <span
										class="input-group-addon"> <i class="icon-calendar"></i>
									</span> 

								</div>
							</div>
						</div>
					</div>
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">开班规则</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<label class="checkbox-inline">
									<input type="checkbox" id="inlineCheckbox7" value="option1"> 星期日
								</label>
								<label class="checkbox-inline">
									<input type="checkbox" id="inlineCheckbox1" value="option2"> 星期一
								</label>
								<label class="checkbox-inline">
									<input type="checkbox" id="inlineCheckbox2" value="option3"> 星期二
								</label>
								<label class="checkbox-inline">
									<input type="checkbox" id="inlineCheckbox3" value="option4"> 星期三
								</label>
								<label class="checkbox-inline">
									<input type="checkbox" id="inlineCheckbox4" value="option5"> 星期四
								</label>
								<label class="checkbox-inline">
									<input type="checkbox" id="inlineCheckbox5" value="option6"> 星期五
								</label>
								<label class="checkbox-inline">
									<input type="checkbox" id="inlineCheckbox6" value="option7"> 星期六
								</label>
							</div>
						</div>
					</div>
					
					<table id="classCourseTab"
						class="table table-striped table-bordered table-hover"
						style="text-align:left;">
						<thead>
							<tr>
								<th width="100">星期天</th>
								<th width="100">星期一</th>
								<th width="100">星期二</th>
								<th width="100">星期三</th>
								<th width="100">星期四</th>
								<th width="100">星期五</th>
								<th width="100">星期六</th>
							</tr>
							<tr>
								<td>
									<div class="classRoom-item" id="1-1"><button>+</button></div>
								</td>
								<td>
									<div class="classRoom-item" id="1-2"><button>+</button></div>
								</td>
								<td>
									<div class="classRoom-item" id="1-3"><button>+</button></div>
								</td>
								<td>
									<div class="classRoom-item" id="1-4"><button>+</button></div>
								</td>
								<td>
									<div class="classRoom-item" id="1-5"><button>+</button></div>
								</td>
								<td>
									<div class="classRoom-item" id="1-6"><button>+</button></div>
								</td>
								<td>
									<div class="classRoom-item" id="1-7"><button>+</button></div>
								</td>
							</tr>
							
							<tr>
								<td>
									<div class="classRoom-item" id="2-1"><button>+</button></div>
								</td>
								<td>
									<div class="classRoom-item" id="2-2"><button>+</button></div>
								</td>
								<td>
									<div class="classRoom-item" id="2-3"><button>+</button></div>
								</td>
								<td>
									<div class="classRoom-item" id="2-4"><button>+</button></div>
								</td>
								<td>
									<div class="classRoom-item" id="2-5"><button>+</button></div>
								</td>
								<td>
									<div class="classRoom-item" id="2-6"><button>+</button></div>
								</td>
								<td>
									<div class="classRoom-item" id="2-7"><button>+</button></div>
								</td>
							</tr>
						</thead>
					</table>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick="saveUploadFile()">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
</div>

<script>
	function searchStudentList(){
		var searchStudentStr = $("#searchStudentStr").val();
		$("#studentListTab tr:not(:has(th))").remove();
		var newcontent = "";
		for(var i=0;i<studentList.length;i++){
			var studentName = studentList[i].studentName;
			var epalId = studentList[i].epalId;
			if(studentName.indexOf(searchStudentStr) >= 0 || epalId.indexOf(searchStudentStr) >= 0){
				newcontent = newcontent + '<tr>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+studentList[i].id+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+studentList[i].studentName+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+studentList[i].epalId+'</td>';
				if(studentList[i].gradesStatus == -1){
					newcontent = newcontent + '<td nowrap="nowrap" align="left" class="btn btn-xs btn-danger">已退出</td>';
				}else{
				
					newcontent = newcontent + '<td nowrap="nowrap" align="left" class="btn btn-xs btn-info">正常</td>';
				}
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+studentList[i].studyCount+'/'+studentList[i].classRoomCount+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+studentList[i].lastStudyTime+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="showStudentClassRecord('+studentList[i].id+','+classGradesId+')" ><i class="icon-edit bigger-120"></i>查看学习记录</button>';
				if(studentList[i].gradesStatus == -1){
					newcontent = newcontent + '&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="joinClassGrades('+studentList[i].id+','+classGradesId+')" class="btn btn-xs btn-info"><i class="icon-edit bigger-120"></i>重新加入</button>';
				}else{
					newcontent = newcontent + '&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="signOutClassGrades('+studentList[i].id+','+classGradesId+')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i>踢出班级</button>';
				}
				newcontent = newcontent + '</td>';
				newcontent = newcontent + '</tr>';	
			}

				
		}
		$("#studentListTab").append(newcontent);
	
	
	}


</script>

<style>
      .sortedASC{
          background: url("/lesson/img/02_@3x.png") no-repeat 80% 5px #eee;
      }
      .sorted
      {
          background: url({sh::PUB}img/icon-table-sort.png) no-repeat 80% 9px #eee ;
      }

      .sortedDESC{
          background: url("/lesson/img/02_@3x.png") no-repeat 80% 11px #eee;
      }
  </style>
    
<!-- 查看学生列表弹窗框--> 
<div class="modal fade" id="classGradesStudentList" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:80%;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">学生列表</h4>
			</div>
			<div class="modal-body">
				<div id="divInput" class="form-group" style="height:35px;display: none;">
					<label class="col-sm-2 control-label no-padding-right text-right">序号</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="id" id="id"
									type="text"
									style="max-width:650px;width:100%;"/>
							</div>
						</div>
					</div>
				</div>

				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">关键字搜索</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="searchStudentStr" id="searchStudentStr"
									type="text"
									style="max-width:650px;width:60%;"/>
								<button onclick="searchStudentList()" style="max-width:650px;width:15%;">搜索</button>
							</div>								
						</div>
					</div>
				</div>
				<div id="divInput" class="form-group" style="height:35px;">
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<button onclick="initJoinClassDate()" style="max-width:650px;width:20%;">重置加班时间</button>
							</div>
							
						</div>
					</div>
				</div>
					<table id="studentListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
						<thead>
							<tr>
								<th class="sort" data-type="default" role="columnheader" tabindex="0" aria-controls="sample-table-2" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">学生ID</th>
								<th class="sort <if condition='$sale eq 1'>sortedASC<elseif condition='$sale eq -1'/>sortedDESC<else />sorted</if>" data-type="studentName" role="columnheader" tabindex="0" aria-controls="sample-table-2" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">学生名称</th>
								<th class="sort <if condition='$new eq 1'>sortedASC<elseif condition='$new eq -1'/>sortedDESC<else />sorted</if>" data-type="epalId" role="columnheader" tabindex="0" aria-controls="sample-table-2" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">机器人账号</th>
								<th class="sort <if condition='$sale eq 1'>sortedASC<elseif condition='$sale eq -1'/>sortedDESC<else />sorted</if>" data-type="schedule" role="columnheader" tabindex="0" aria-controls="sample-table-2" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">班级状态</th>
								<th class="sort <if condition='$new eq 1'>sortedASC<elseif condition='$new eq -1'/>sortedDESC<else />sorted</if>" data-type="studentDate" role="columnheader" tabindex="0" aria-controls="sample-table-2" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">学习进度</th>
								<th class="sort <if condition='$price eq 1'>sortedASC<elseif condition='$price eq -1'/>sortedDESC<else />sorted</if>" data-type="status" role="columnheader" tabindex="0" aria-controls="sample-table-2" rowspan="1" colspan="1" aria-label="Update: activate to sort column ascending">
								    <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>最后上课时间
								</th>
								<th class="sort <if condition='$new eq 1'>sortedASC<elseif condition='$new eq -1'/>sortedDESC<else />sorted</if>" data-type="studentDate" role="columnheader" tabindex="0" aria-controls="sample-table-2" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">凡豆账号</th>
								<th width="100">操作</th>
							</tr>
						</thead>
					</table>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="saveAlbumsToChannel()">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
$('.sort').click(function() {
    var type = $(this).data('type');
    alert(type);
    var category_id = '{sh:$category_id}';
    var sort;
    if ($(this).hasClass('sorted')) { // 降序
        $(this).removeClass('sorted').addClass('sortedDESC');
        sort = '-1';
    }else if ($(this).hasClass('sortedASC')) { // 降序
        $(this).removeClass('sortedASC').addClass('sortedDESC');
        sort = '-1';
    }else if ($(this).hasClass('sortedDESC')) { // 升序
        $(this).removeClass('sortedDESC').addClass('sortedASC');
        sort = '1';
    }
    alert(sort);
    
});
</script>
<!-- 开班日期设置--> 
<div class="modal fade" id="classGradesOpenDate" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:40%;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">设置开班日期</h4>
			</div>
			<div class="modal-body">
			
				<input name="classGradesId" id="classGradesId" style="display: none;"/>

				<div class="form-group col-lg-6" style="height:35px;">
					<label class="col-sm-3 control-label no-padding-right">开班日期</label>
					<div class="col-sm-9">
						<div class="input-medium" style="">
							<div class="input-group">
								<input class="input-medium date-picker" type="text"
									name="classOpenDate" value="" id="classOpenDate"
									data-date-format="yyyy-mm-dd" placeholder="年-月-日" /> <span
									class="input-group-addon"> <i class="icon-calendar"></i>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary"
					onclick="saveOpenDate()">保存</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>

<script>

	function initJoinClassDate(){
		$("#initJoinClassDate").modal("show");
		$("#classGradesId").val(classGradesId);
	}
	function saveJoinClassDate(){
		if(confirm('确定要重置加班时间吗?')){ 
    	
   		}else{
   			return false; 
   		}
		var joinClassDate = $("#joinClassDate").val();
		var formData = new FormData();
		formData.append("classGradesId",currentClassGradesId);
		formData.append("joinClassDate",joinClassDate);
		$.ajax({
          url: '<%=request.getContextPath() %>/api/saveJoinClassDate' ,
          type: 'POST',
          data:formData,
          dataType: "json",  
          async: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		  },
          success: function (returndata) {
			  $("#initJoinClassDate").modal("hide");
			  
          },
          error: function (returndata) {
          
          }
     });
	
	}
</script>

<!-- 初始化加班日期--> 
<div class="modal fade" id="initJoinClassDate" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:40%;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">重置加入班级时间</h4>
			</div>
			<div class="modal-body">
			
				<input name="classGradesId" id="classGradesId" style="display: none;"/>

				<div class="form-group col-lg-6" style="height:35px;">
					<label class="col-sm-3 control-label no-padding-right">选择日期</label>
					<div class="col-sm-9">
						<div class="input-medium" style="">
							<div class="input-group">
								<input class="input-medium date-picker" type="text"
									name="joinClassDate" value="" id="joinClassDate"
									data-date-format="yyyy-mm-dd" placeholder="年-月-日" /> <span
									class="input-group-addon"> <i class="icon-calendar"></i>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary"
					onclick="saveJoinClassDate()">保存</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>

<!-- 引用班级课程表弹窗 -->
<div class="modal fade" id="quoteClassCourse" tabindex="-1" role="dialog" data="1"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:900px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">班级引用班级课程</h4>
			</div>
			<div class="modal-body">
			
				<div id="divInput" class="form-group" style="height:35px;display: none">
					<label class="col-sm-2 control-label no-padding-right text-right">班级ID</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="targetClassGradesId" id="targetClassGradesId"
									type="text"
									style="max-width:650px;width:100%;" readonly/>
							</div>
						</div>
					</div>
				</div>
				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">班级名称：</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="classGradesNameStr" id="classGradesNameStr"
									type="text" 
									style="max-width:650px;width:80%;" />&nbsp;&nbsp;&nbsp;&nbsp;
								<button onclick="searchClassGrades()" style="max-width:650px;width:15%;">搜索班级</button>
							</div>
						</div>
					</div>
				</div>

					<div class="modal-body">
					<table id="classGradesListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
						<thead>
							<tr>
								<th width="20"><input type="checkbox" name="" value="" onclick=""></th>
								<th width="100">班级ID</th>
								<th width="100">班级名称</th>
								<th width="100">班级简介</th>
								<th width="100">班级图片</th>
							</tr>
						</thead>
					</table>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="saveQuoteClassCourse()">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	function quoteClassCourse(index){
		$("#quoteClassCourse").modal("show");
		var classGradesId = jsonData[index].classGradesId;
		$("#targetClassGradesId").val(classGradesId);
	
	}
	function searchClassGrades(){
		var classGradesNameStr = $("#classGradesNameStr").val();
		var formData = new FormData();
		formData.append("name",classGradesNameStr);
		formData.append("page","1");
		formData.append("pageSize","1000");
		$.ajax({
	          url: '<%=request.getContextPath() %>/api/getClassGradesList' ,
	          type: 'POST',
	          data: formData,
	          async: false,
	          cache: false,
	          contentType: false,
	          processData: false,
	          beforeSend:function(){
			   //这里是开始执行方法
			   		$("#classGradesListTab tr:not(:has(th))").remove();
			   		$("#classGradesListTab").append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
			  },
	          success: function (data) {
	          	  	var classGradesList = data.data.items;
	     	  		$("#classGradesListTab tr:not(:has(th))").remove();
					var newcontent = "";
					for(var i=0;i<classGradesList.length;i++){
						newcontent = newcontent + '<tr>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left"><input type="checkbox" name="classGradesId" value="'+classGradesList[i].id+'"></td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+classGradesList[i].id+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+classGradesList[i].classGradesName+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+classGradesList[i].summary+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img src="'+classGradesList[i].cover+'" width="50" height="50" /></td>';
				
						newcontent = newcontent + '</tr>';					
					}
					$("#classGradesListTab").append(newcontent);
	          },
	          error: function (returndata) {
	          
	          }
	     	});
	}
	function saveQuoteClassCourse(){
		$("#quoteClassCourse").modal("hide");
		var targetClassGradesId = $("#targetClassGradesId").val();
		var sourceClassGradesId;
     	var sourceClassGradesIds = $("input[name='classGradesId']:checked").val([]);
		if(sourceClassGradesIds.length > 1){
			alert("一次只能引用一个班级的课程表！");
			return false;
			
		}else{
			sourceClassGradesId = sourceClassGradesIds[0].value;
		
		}
		var formData = new FormData();
		formData.append("targetClassGradesId",targetClassGradesId);
		formData.append("sourceClassGradesId",sourceClassGradesId);
		$.ajax({
	          url: '<%=request.getContextPath() %>/api/saveQuoteClassCourse' ,
	          type: 'POST',
	          data: formData,
	          async: false,
	          cache: false,
	          contentType: false,
	          processData: false,
	          beforeSend:function(){
			   //这里是开始执行方法
			  },
	          success: function (data) {

	          },
	          error: function (returndata) {
	          
	          }
	     	});
 	 

	}
</script>
<script>
var tableSort = function(){ 
  this.initialize.apply(this,arguments); 
} 
tableSort.prototype = { 
  initialize : function(tableId,clickRow,startRow,endRow,classUp,classDown,selectClass){ 
    this.Table = document.getElementById(tableId); 
    this.rows = this.Table.rows;//所有行 
    this.Tags = this.rows[clickRow-1].cells;//标签td 
    this.up = classUp; 
    this.down = classDown; 
    this.startRow = startRow; 
    this.selectClass = selectClass; 
    this.endRow = (endRow == 999? this.rows.length : endRow); 
    this.T2Arr = this._td2Array();//所有受影响的td的二维数组 
    this.setShow(); 
  }, 
  //设置标签切换 
  setShow:function(){ 
    var defaultClass = this.Tags[0].className; 
    for(var Tag ,i=0;Tag = this.Tags[i];i++){ 
      Tag.index = i; 
      addEventListener(Tag ,'click', Bind(Tag,statu)); 
    } 
    var _this =this; 
    var turn = 0; 
    function statu(){ 
      for(var i=0;i<_this.Tags.length;i++){ 
        _this.Tags[i].className = defaultClass; 
      } 
      if(turn==0){ 
        addClass(this,_this.down) 
        _this.startArray(0,this.index); 
        turn=1; 
      }else{ 
        addClass(this,_this.up) 
        _this.startArray(1,this.index); 
        turn=0; 
      } 
    } 
  }, 
  //设置选中列样式 
  colClassSet:function(num,cla){ 
    //得到关联到的td 
    for(var i= (this.startRow-1);i<(this.endRow);i++){ 
      for(var n=0;n<this.rows[i].cells.length;n++){ 
        removeClass(this.rows[i].cells[n],cla); 
      } 
      addClass(this.rows[i].cells[num],cla); 
    } 
  }, 
  //开始排序 num 根据第几列排序 aord 逆序还是顺序 
  startArray:function(aord,num){ 
    var afterSort = this.sortMethod(this.T2Arr,aord,num);//排序后的二维数组传到排序方法中去 
    this.array2Td(num,afterSort);//输出 
  }, 
  //将受影响的行和列转换成二维数组 
  _td2Array:function(){ 
    var arr=[]; 
      for(var i=(this.startRow-1),l=0;i<(this.endRow);i++,l++){ 
        arr[l]=[]; 
        for(var n=0;n<this.rows[i].cells.length;n++){ 
          arr[l].push(this.rows[i].cells[n].innerHTML); 
        } 
      } 
    return arr; 
  }, 
  //根据排序后的二维数组来输出相应的行和列的 innerHTML 
  array2Td:function(num,arr){ 
    this.colClassSet(num,this.selectClass); 
    for(var i= (this.startRow-1),l=0;i<(this.endRow);i++,l++){ 
      for(var n=0;n<this.Tags.length;n++){ 
        this.rows[i].cells[n].innerHTML = arr[l][n]; 
      } 
    } 
  }, 
  //传进来一个二维数组，根据二维数组的子项中的w项排序，再返回排序后的二维数组 
  sortMethod:function(arr,aord,w){ 
    arr.sort(function(a,b){ 
      x = killHTML(a[w]); 
      y = killHTML(b[w]); 
      x = x.replace(/,/g,''); 
      y = y.replace(/,/g,''); 
      switch (isNaN(x)){ 
      case false: 
      return Number(x) - Number(y); 
      break; 
      case true: 
      return x.localeCompare(y); 
      break; 
      } 
    }); 
    arr = aord==0?arr:arr.reverse(); 
    return arr; 
  } 
} 
/*-----------------------------------*/
function addEventListener(o,type,fn){ 
  if(o.attachEvent){
    o.attachEvent('on'+type,fn);
  }else if(o.addEventListener){
    o.addEventListener(type,fn,false);
  }else{
    o['on'+type] = fn;
  } 
} 
function hasClass(element, className) { 
  var reg = new RegExp('(\s|^)'+className+'(\s|$)'); 
  return element.className.match(reg); 
} 
function addClass(element, className) { 
  if (!this.hasClass(element, className)) { 
    element.className += " "+className; 
  } 
} 
function removeClass(element, className) { 
  if (hasClass(element, className)) { 
    var reg = new RegExp('(\s|^)'+className+'(\s|$)'); 
    element.className = element.className.replace(reg,' '); 
  } 
} 
var Bind = function(object, fun) { 
  return function() { 
    return fun.apply(object, arguments); 
  } 
} 
//去掉所有的html标记 
function killHTML(str){ 
  return str.replace(/<[^>]+>/g,""); 
} 
//------------------------------------------------ 
//tableid 第几行是标签行，从第几行开始排序，第几行结束排序(999表示最后) 升序标签样式，降序标签样式 选中列样式 
//注意标签行的class应该是一致的 
var ex1 = new tableSort('studentListTab',1,2,999,'up','down','hov');

</script>