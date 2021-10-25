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
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].studentCount+'</td>';
			if(jsonData[i].gradesType == "virtualClass"){
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><font color="#1E90FF">虚拟班级</font></td>';
			}else if(jsonData[i].gradesType == "eleClass"){
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><font color="#90EE90">电教班级</font></td>';
			}else{
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].gradesType+'</td>';
			}
			
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >';
			if(jsonData[i].gradesType == "virtualClass"){
				newcontent = newcontent + '&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="showVirtualClassStudent('+jsonData[i].classGradesId+')" class="btn btn-xs btn-info"><i class="icon-edit bigger-120"></i>查看学生</button>';
			}else{
				newcontent = newcontent + '&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="showEleClassStudent('+jsonData[i].classGradesId+')" class="btn btn-xs btn-info"><i class="icon-edit bigger-120"></i>查看学生</button>';
			}
			
			newcontent = newcontent + '</td>';
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
var studentList;
var currentClassGradesId;
function showVirtualClassStudent(classGradesId){
	$("#virtualClassStudentList").modal({backdrop: 'static', keyboard: false},"show");
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
function showEleClassStudent(classGradesId){
	$("#eleClassStudentList").modal({backdrop: 'static', keyboard: false},"show");
	currentClassGradesId = classGradesId;
	var formData = new FormData();
	formData.append("classGradesId",classGradesId);
	formData.append("gradesType","eleClass");
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
		  },
          success: function (data) {
          	  	var studentList = data.data;
				$("#eleStudentListTab tr:not(:has(th))").remove();
				var newcontent = "";
				for(var i=0;i<studentList.length;i++){
					newcontent = newcontent + '<tr>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+studentList[i].studentName+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+studentList[i].studentName+'</td>';
					if(studentList[i].gradesStatus == -1){
						newcontent = newcontent + '<td nowrap="nowrap" align="left" class="btn btn-xs btn-danger">已退出</td>';
					}else{
					
						newcontent = newcontent + '<td nowrap="nowrap" align="left" class="btn btn-xs btn-info">正常</td>';
					}
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+studentList[i].studyCount+'/'+studentList[i].classRoomCount+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+studentList[i].lastStudyTime+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >备注</td>';			
					newcontent = newcontent + '</tr>';					
				}
				$("#eleStudentListTab").append(newcontent);
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
	

		newcontent = newcontent + '</tr>';					
	}
	$("#studentListTab").append(newcontent);

}
var currentIndex;
//预添加或者修改
function editData(index)
{	
	$("#addClassGrades").modal("show");
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
						<!-- 
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
						 -->
					</tr>

				</table>

				<div class="btn-group col-md-12">
					<button type="submit" class="btn btn-purple ml10 mb10">
						查询 <i class="icon-search icon-on-right bigger-110"></i>
					</button>
					<button class="btn btn-purple ml10 mb10" onclick="editData('')" data-toggle="modal" data-target="#addClassGrades" value="添加">
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
										<th width="50">班级人数</th>
										<th width="50">班级类型</th>
										<th width="100">操作</th>
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
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+studentList[i].bindMobile+'</td>';
				newcontent = newcontent + '</tr>';	
			}

				
		}
		$("#studentListTab").append(newcontent);
	
	
	}

	function removeCell(columnIndex){
		for(var i=0;i<document.getElementById("table").rows.length;i++){
			document.getElementById("table").rows[i].deleteCell(columnIndex); 
		}
	}
</script>
    
<!-- 查看虚拟班级学生列表弹窗框--> 
<div class="modal fade" id="virtualClassStudentList" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:100%;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">学生列表</h4>
			</div>
			<div class="modal-body" style="overflow:auto;">
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
				<button class="btn btn-info" onclick="switchDetail(0)">概况</button>
				&nbsp;&nbsp;<button class="btn btn-info" onclick="switchDetail(1)">课程表</button>
				<table id="studentListTab"
							class="table table-striped table-bordered table-hover"
							style="text-align:left;">
					<thead>
						<tr>
							<th class="sort" data-type="default" role="columnheader" tabindex="0" aria-controls="sample-table-2" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">学生ID</th>
							<th class="sort <if condition='$sale eq 1'>sortedASC<elseif condition='$sale eq -1'/>sortedDESC<else />sorted</if>" data-type="studentName" role="columnheader" tabindex="0" aria-controls="sample-table-2" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">学生名称</th>
							<th class="sort <if condition='$new eq 1'>sortedASC<elseif condition='$new eq -1'/>sortedDESC<else />sorted</if>" data-type="epalId" role="columnheader" tabindex="0" aria-controls="sample-table-2" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">机器人账号</th>
							<th class="sort <if condition='$sale eq 1'>sortedASC<elseif condition='$sale eq -1'/>sortedDESC<else />sorted</if>" data-type="schedule" role="columnheader" tabindex="0" aria-controls="sample-table-2" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">班级状态</th>
							<th class="sort <if condition='$new eq 1'>sortedASC<elseif condition='$new eq -1'/>sortedDESC<else />sorted</if>" data-type="studentDate" role="columnheader" tabindex="0" aria-controls="sample-table-2" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">学习进度(只统计主课)</th>
							<th class="sort <if condition='$price eq 1'>sortedASC<elseif condition='$price eq -1'/>sortedDESC<else />sorted</if>" data-type="status" role="columnheader" tabindex="0" aria-controls="sample-table-2" rowspan="1" colspan="1" aria-label="Update: activate to sort column ascending">
							    <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>最后上课时间
							</th>
							<th class="sort <if condition='$new eq 1'>sortedASC<elseif condition='$new eq -1'/>sortedDESC<else />sorted</if>" data-type="studentDate" role="columnheader" tabindex="0" aria-controls="sample-table-2" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">联系方式</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>

<script>
	function switchDetail(action){
		var formData = new FormData();
		formData.append("classGradesId",currentClassGradesId);
		if(action == 0){
		
		}else if(action == 1){
			$.ajax({
		          url: '<%=request.getContextPath() %>/api/getClassCourseRecordDetail' ,
		          type: 'POST',
		          data: formData,
		          async: false,
		          cache: false,
		          contentType: false,
		          processData: false,
		          beforeSend:function(){
				   //这里是开始执行方法
				   var dataListTab = $('#dataListTab'); 
				   
				  },
		          success: function (returndata) {
		          		$("#studentListTab").empty();
		 				newContent = '<tr><td></td>';
		 				var data = returndata.data;
		 				var classCourseList = data.classCourseList;
		 				var studentList = data.studentList;
		 				var record = data.record;
		 				for(var i=0;i<classCourseList.length;i++){
		 					newContent = newContent + "<td>"+classCourseList[i].classRoomName+"("+classCourseList[i].doDay+")</td>";
		 				}
		 				newContent = newContent + "</tr>";
		 				var record = data.record;
		 				for(var i=0;i<studentList.length;i++){
		 					newContent = newContent + "</tr><td>" + studentList[i].studentName + "</td>" ;
		 					for(var j=0;j<classCourseList.length;j++){
		 						var classCourseId = classCourseList[j].classCourseId;
		 						var studentId = studentList[i].id;
		 						var key = studentId + "_" + classCourseId;
		 						if (record.hasOwnProperty(key)){
		 							newContent = newContent + '<td><font color="green">'+ record[key].score+'</font></td>';

		 						}else{
		 							newContent = newContent + '<td><font color="red">未学</font></td>';
		 						}
		 					
		 					}
		 					newContent = newContent + "</tr>";
		 				
		 				}
		 				$("#studentListTab").append(newContent);
		          },
		          error: function (returndata) {
		          	
		          }
		     });		
		
		}

	}

</script>

<!-- 查看电教班级学生列表弹窗框--> 
<div class="modal fade" id="eleClassStudentList" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:100%;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">学生列表(电教)</h4>
			</div>
			<div class="modal-body">
				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">关键字搜索</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<button onclick="addStudentToClassGrades()" class="btn btn-info">添加学生</button>
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
								<button onclick="searchStudentList()" class="btn">搜索</button>
							</div>								
						</div>
					</div>
				</div>
				<button class="btn btn-info">概况</button>
				&nbsp;&nbsp;<button class="btn btn-info" onclick="showCourseDetail()">课程表</button>
				<table id="eleStudentListTab"
							class="table table-striped table-bordered table-hover"
							style="text-align:left;">
					<thead>
						<tr>
							<th width="120">学生名字</th>
							<th width="120">学生卡号</th>
							<th width="120">班级状态</th>
							<th width="120">学习进度</th>
							<th width="120">最后上课时间</th>
							<th width="120">备注</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>

<script>
	function addStudentToClassGrades(){
		$("#addStudentToClassGrades").modal("show");
	}
	function saveClassGrades(){
		var classGradesName = $("#classGradesName").val();
		var cover = $("#cover").val();
		var summary = $("#summary").val();
		var classGradesName = $("#classGradesName").val();
		var gradesType = $("#gradesType").val();
		var price = $("#price").val();
		var teacherId = $("#teacherId").val();
		var formData = new FormData();
		formData.append("classGradesName",classGradesName);
		formData.append("cover",cover);
		formData.append("summary",summary);
		formData.append("parentId",0);
		formData.append("sortId",1);
		formData.append("teacherId",teacherId);	
		formData.append("gradesType",gradesType);
		formData.append("status",1);
		formData.append("auditingStatus",1);		
		formData.append("price",price);
		formData.append("auditingStatus",1);		
		 $("#addClassGrades").modal("hide");
	     $.ajax({
	          url: '<%=request.getContextPath() %>/api/saveClassGrades' ,
	          type: 'POST',
	          data: formData,
	          async: false,
	          cache: false,
	          contentType: false,
	          processData: false,
	          beforeSend:function(){
			   //这里是开始执行方法
			   var dataListTab = $('#dataListTab'); 
			   
			  },
	          success: function (returndata) {
	              var result=returndata;
				  jsonData.push(result.data); 
				  updateTotalCount(1);
				  updateUI();
	          },
	          error: function (returndata) {
	          	
	          }
	     });	
	
	
	}
</script>
<!-- 添加班级弹窗 -->
<div class="modal fade" id="addClassGrades" tabindex="-1" 
	role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:800px;height: 500px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">添加班级</h4>
			</div>
			<div class="modal-body">
				<div class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">班级名称</label>
						<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group">
								<input class="form-control" name="classGradesName" id="classGradesName"  type="text" data-rule="班级名称:required;"style="max-width:650px;width:100%;"  />
							</div>
						</div>
					</div>
				</div>
				<div class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">班级简介</label>
						<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group">
								<input class="form-control" name="summary" id="summary"  type="text" data-rule="简介:required;"style="max-width:650px;width:100%;"  />
							</div>
						</div>
					</div>
				</div>
				<div class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">班级海报</label>
						<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group">
								<input class="form-control" name="cover" id="cover"  type="text" data-rule="简介:required;"style="max-width:650px;width:100%;"  />
							</div>
						</div>
					</div>
				</div>
				<div class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">班级价格</label>
						<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group">
								<input class="form-control" name="price" id="price"  type="text" data-rule="简介:required;"style="max-width:650px;width:100%;"  />
							</div>
						</div>
					</div>
				</div>				
				<div class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">班级类型</label>
						<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group">
								<select id="gradesType" name="gradesType">
									<option value="virtualClass">虚拟班级</option>
									<option value="eleClass">电教班级</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				
				
				<div class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">选择老师</label>
						<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group">
								<select id="teacherId" name="teacherId">
									<option value="69">陈老师</option>
									<option value="12">于老师</option>
								</select>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="saveClassGrades()">保存</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>


<!-- 添加学生弹窗 -->
<div class="modal fade" id="addStudentToClassGrades" tabindex="-1" 
	role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:800px;height: 500px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">添加学生</h4>
			</div>
			<div class="modal-body">
				<div class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">学生卡号</label>
						<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group">
								<input class="form-control" name="cardFid" id="cardFid"  type="text" data-rule="学生卡号:required;"style="max-width:650px;width:100%;"  />
							</div>
						</div>
					</div>
				</div>
				<div class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">学生名字</label>
						<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group">
								<input class="form-control" name="studentName" id="studentName"  type="text" data-rule="学生名字:required;"style="max-width:650px;width:100%;"  />
							</div>
						</div>
					</div>
				</div>
				<div class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">备注</label>
						<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group">
								<input class="form-control" name="remark" id="remark"  type="text" data-rule="备注:required;"style="max-width:650px;width:100%;"  />
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="saveEleStudent()">保存</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>

<script>
	function saveEleStudent(){
		var cardFid = $("#cardFid").val();
		var studentName = $("#studentName").val();
		var remark = $("#remark").val();
		var formData = new FormData();
		formData.append("cardFid",cardFid);
		formData.append("studentName",studentName);
		formData.append("remark",remark);
		formData.append("classGradesId",currentClassGradesId);
		$("#addStudentToClassGrades").modal("hide");
		$.ajax({
	          url: '<%=request.getContextPath() %>/api/saveEleStudent' ,
	          type: 'POST',
	          data: formData,
	          async: false,
	          cache: false,
	          contentType: false,
	          processData: false,
	          beforeSend:function(){
			   //这里是开始执行方法
			   var dataListTab = $('#dataListTab'); 
			   
			  },
	          success: function (returndata) {
	              var result=returndata;
				  jsonData.push(result.data); 
				  updateTotalCount(1);
				  updateUI();
	          },
	          error: function (returndata) {
	          	
	          }
	     });
	}

</script>