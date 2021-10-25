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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/validator/jquery.validator.css">

<script>
    //html存储全局变量
    var newcontent = '';
    //服务器json字符串
    var jsonStr = '${jsonData}';
    //json字符串转json数组
    var jsonData = eval(jsonStr);
    

    //更新总记录
    function updateTotalCount(count) {
        //更新总记录
        var total = $("font#fPageTotal").text();
        total = eval(total) + count;
        $("font#fPageTotal").text(total);
    }

    //更新界面的方法
    function updateUI() {
         newcontent = '';
         var dataListTab = $('#dataListTab');
         $("#dataListTab tr:not(:has(th))").remove();
        
         //角色id
         var roleId=eval('${user.roleId}');
        
	     for(var i=0;i<jsonData.length;i++){
	        var deviceTest=jsonData[i];
	        var testName= deviceTest.registMen;
	        var testTime= deviceTest.registTime;
	        var reviewName = deviceTest.reviewMen;
	        var reviewTime = deviceTest.reviewTime;
	        var backResult=deviceTest.backResult;
	        var reviewColor='btn-warning';
	        var reviewText='';
	        var resultColor1='';
	        var resultColor2='';
	        var resultColor3='';
	        var resultContent ='';
	        switch (deviceTest.status) {
			case 0:
			      reviewColor='btn-warning';
	          	  reviewText='待处理';
	          if(roleId==1){
	          	  resultContent='<button class="btn btn-danger" onclick="editReview(' + i + ')"  style="border:none;border-radius: 3px;"  data-toggle="modal" data-target="#reviewModal-data">审核</button>';
	          }
				break;
			case 1:
			  	 reviewColor='btn-danger';
	          	 reviewText='未通过';
	          	 resultColor1='itemError';
	          	 resultColor2='itemNumError';
	          	 resultColor3='itemMesgError';
	          	 resultContent='<b class="itemState red">退回原因:</b><span class="itemReason red" style="text-align:center;">'+backResult+'</span>';
				break;
			case 2:
			  	 reviewColor='btn-success';
	          	 reviewText='已完结';
	          	 resultColor1='itemDone';
	          	 resultColor2='itemNumDone';
	          	 resultColor3='itemMesgDone';
				break;
			}
			
			//usb连接状态
	        var usbConnect= (deviceTest.usbConnect == 0 ) ? '<td><span class="btn btn-xs btn-danger " style="cursor:default;" ><i class="icon-remove-sign"></i></span></td>' : '<td><span class="btn btn-xs btn-success " style="cursor:default;" ><i class="icon-ok-sign"></i></span></td>'; 
	     	//led灯
	     	var led= (deviceTest.led == 0 ) ? '<td><span class="btn btn-xs btn-danger " style="cursor:default;" ><i class="icon-remove-sign"></i></span></td>' : '<td><span class="btn btn-xs btn-success " style="cursor:default;" ><i class="icon-ok-sign"></i></span></td>'; 
	     	//烧录程序
	     	var burningProcess= (deviceTest.burningProcess == 0 ) ? '<td><span class="btn btn-xs btn-danger " style="cursor:default;" ><i class="icon-remove-sign"></i></span></td>' : '<td><span class="btn btn-xs btn-success " style="cursor:default;" ><i class="icon-ok-sign"></i></span></td>'; 
	     	//speaker
	     	var speaker= (deviceTest.speaker == 0 ) ? '<td><span class="btn btn-xs btn-danger " style="cursor:default;" ><i class="icon-remove-sign"></i></span></td>' : '<td><span class="btn btn-xs btn-success " style="cursor:default;" ><i class="icon-ok-sign"></i></span></td>'; 
	     	//leftmic
	     	var leftMic= (deviceTest.leftMic == 0 ) ? '<td><span class="btn btn-xs btn-danger " style="cursor:default;" ><i class="icon-remove-sign"></i></span></td>' : '<td><span class="btn btn-xs btn-success " style="cursor:default;" ><i class="icon-ok-sign"></i></span></td>'; 
	     	//rightmic
	     	var rightMic= (deviceTest.rightMic == 0 ) ? '<td><span class="btn btn-xs btn-danger " style="cursor:default;" ><i class="icon-remove-sign"></i></span></td>' : '<td><span class="btn btn-xs btn-success " style="cursor:default;" ><i class="icon-ok-sign"></i></span></td>'; 
	     	//摄像头
	     	var camara= (deviceTest.camara == 0 ) ? '<td><span class="btn btn-xs btn-danger " style="cursor:default;" ><i class="icon-remove-sign"></i></span></td>' : '<td><span class="btn btn-xs btn-success " style="cursor:default;" ><i class="icon-ok-sign"></i></span></td>'; 
	     	//手势识别
	     	var gestureRecognition= (deviceTest.gestureRecognition == 0 ) ? '<td><span class="btn btn-xs btn-danger " style="cursor:default;" ><i class="icon-remove-sign"></i></span></td>' : '<td><span class="btn btn-xs btn-success " style="cursor:default;" ><i class="icon-ok-sign"></i></span></td>'; 
	     	//显示屏
	     	var display= (deviceTest.display == 0 ) ? '<td><span class="btn btn-xs btn-danger " style="cursor:default;" ><i class="icon-remove-sign"></i></span></td>' : '<td><span class="btn btn-xs btn-success " style="cursor:default;" ><i class="icon-ok-sign"></i></span></td>'; 
	     	//主机HOME键
	     	var touchKey= (deviceTest.touchKey == 0 ) ? '<td><span class="btn btn-xs btn-danger " style="cursor:default;" ><i class="icon-remove-sign"></i></span></td>' : '<td><span class="btn btn-xs btn-success " style="cursor:default;" ><i class="icon-ok-sign"></i></span></td>'; 
	     	//rfid
	     	var rfid= (deviceTest.rfid == 0 ) ? '<td><span class="btn btn-xs btn-danger " style="cursor:default;" ><i class="icon-remove-sign"></i></span></td>' : '<td><span class="btn btn-xs btn-success " style="cursor:default;" ><i class="icon-ok-sign"></i></span></td>'; 
	     	//旋转功能
	     	var rotationFunction= (deviceTest.rotationFunction == 0 ) ? '<td><span class="btn btn-xs btn-danger " style="cursor:default;" ><i class="icon-remove-sign"></i></span></td>' : '<td><span class="btn btn-xs btn-success " style="cursor:default;" ><i class="icon-ok-sign"></i></span></td>'; 
	     	//wifi
	     	var wifi= (deviceTest.wifi == 0 ) ? '<td><span class="btn btn-xs btn-danger " style="cursor:default;" ><i class="icon-remove-sign"></i></span></td>' : '<td><span class="btn btn-xs btn-success " style="cursor:default;" ><i class="icon-ok-sign"></i></span></td>'; 
	     	
	     	
	     	newcontent = newcontent + '<tr>';
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" >' + deviceTest.device.deviceNo + '</td>';
	        newcontent = newcontent + '<td><span class="btn btn-xs '+reviewColor+' " style="cursor:default;" onclick="$(this).parent().parent().next().toggle(200);" title="点击查看状态详情"><i class="">'+reviewText+'</i></span></td>';
	        newcontent = newcontent + usbConnect;
	        newcontent = newcontent + led;
	        newcontent = newcontent + burningProcess;
	        newcontent = newcontent + speaker;
	        newcontent = newcontent + leftMic;
	        newcontent = newcontent + rightMic;
	        newcontent = newcontent + camara;
	        newcontent = newcontent + gestureRecognition;
	        newcontent = newcontent + display;
	        newcontent = newcontent + touchKey;
	        newcontent = newcontent + rfid;
	        newcontent = newcontent + rotationFunction;
	        newcontent = newcontent + wifi;
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editData(' + i + ')" data-toggle="modal" data-target="#myModal-data"><i class="icon-edit bigger-120"></i></button><button onclick="deleteData(' + i + ')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button></td>';
	        newcontent = newcontent + '</tr>';
		    newcontent = newcontent + '<tr class="accordion-body collapse" >' +
			'<td colspan="16" style="background-color:#F7FCFC;" class="stepDetails">'+
			'  <table class="table tableNB" style="padding-top:20px;">'+
			'	<tr>'+
			'		<td>'+
			'			<span class="itemName itemDone">测试登记</span>'+
			'			<div class="itemNum itemNumDone"><span>1</span></div>'+
			'			<div class="itemMesg itemMesgDone">'+
			'				<span class="itemSponsor">登记人：'+testName+'</span>'+
			'				<span class="itemTime">时间：'+testTime+'</span>'+
			'			</div>'+
			'		</td>'+
			'		<td class="itemNumSingBox">'+
			'			<div class="itemNum itemNumSing"></div>'+
			'		</td>'+
			'		<td>'+
			'			<span class="itemName '+resultColor1+' ">部门审核</span>'+
			'			<div class="itemNum '+resultColor2+' "><span>2</span></div>'+
			'			<div class="itemMesg '+resultColor3+' ">'+
			'				<span class="itemSponsor">审核人：'+reviewName+'</span>'+
			'				<span class="itemTime">时间：'+reviewTime+'</span>'+
			'			</div>'+
			'		</td>'+
			'	</tr>'+
			'	'+
			'	<tr class="align-center stateLine">'+
			'		<td> </td>'+
			'		<td> </td>'+
			'		<td>'+
			resultContent +
			'		</td>'+
			'	</tr>'+
			'  </table>   '+
			'</td>'+
		    '</tr>';	
	      }
	      
	      dataListTab.append(newcontent);
	      
	}

    //初始化
    $(document).ready(function () {
		
        //更新界面
        updateUI();

    });


    //删除数据
    function deleteData(index) {
        if (confirm('你确定要删除数据吗？')) {
            //通过Ajax删除服务器上的数据，并更新界面
            var dataPar = 'id=' + jsonData[index].device.id+'&deviceNo='+jsonData[index].device.deviceNo;
            $.ajax({
                type: "POST",
                data: dataPar,
                dataType: "json",
                url: "<%=request.getContextPath() %>/api/deleteDevice",
                context: document.body,
                beforeSend: function () {
                        //这里是开始执行方法
                        var dataListTab = $('#dataListTab');
                        $("#dataListTab tr:not(:has(th))").remove();
                        dataListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");

                    },
                    complete: function () {

                    },
                    success: function (data) {
                        //数组更新
                        jsonData.remove(index);
                        //数据执行完成
                        updateUI();
                        updateTotalCount(-1);
                    }
            });

        }
    }
    
    
    //预审核
    function editReview(index){
    	if (index.length == '0') {
            $("#modifyReview").children("#deviceIndex").val("");
            $("#modifyReview").children("#deviceTestId").val("");
            $("#backResult").val("");
        } else {
        	var deviceTest = jsonData[index];
            $("#modifyReview").children("#deviceIndex").val(index);
            $("#modifyReview").children("#deviceTestId").val(deviceTest.id);
            $("#backResult").val("");
        }
    }
    
    //审核
    function updateReview(){
    	//隐藏modal
        $("#reviewModal-data").modal("hide");
        var id=$("#modifyReview").children("#deviceTestId").val();
        if(id==0){
        	alert("设备还未测试");
        	return;
        }
        var status= $("input:radio[name=\"status\"]:checked").val();
        var backResult=$("#backResult").val();
        //通过Ajax更新服务器上的数据，并更新界面
        var dataPar = 'id=' + id +'&status='+status+'&backResult='+backResult+'';
        //alert(dataPar);
        $.ajax({
            type: "POST",
            data: dataPar,
            dataType: "json",
            url: "<%=request.getContextPath() %>/api/changeDeviceTestStatus",
            context: document.body,
            beforeSend: function () {
                    //这里是开始执行方法
                    var dataListTab = $('#dataListTab');
                    $("#dataListTab tr:not(:has(th))").remove();
                    dataListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");

                },
                complete: function () {

                },
                success: function (data) {

                    var index = $("#modifyReview").children("#deviceIndex").val();
                    if (index.length == '0') {
                        //添加到最后
                        jsonData.push(data.data);
                        //更新总数
                        updateTotalCount(1);

                    } else {
                        //更新数组数据
                        jsonData[index].id = id;
                        jsonData[index].status = eval(status);
                        jsonData[index].backResult = backResult;
                        
                    }
                    //更新界面
                    updateUI();

                }
        });
    
    }


    //预添加或者修改设备测试信息
    function editData(index) {
        if (index.length == '0') {
            $("#modifyData").children("#deviceIndex").val("");
            $("#modifyData").children("#deviceTestId").val("");
        } else {
            //第一步，传输数组数据到弹出框
            var deviceTest = jsonData[index];
            $("#modifyData").children("#deviceIndex").val(index);
            $("#modifyData").children("#deviceTestId").val(deviceTest.id);
            document.getElementById("usbConnect"+deviceTest.usbConnect+"").checked=true;
            document.getElementById("led"+deviceTest.led+"").checked=true;
            document.getElementById("burningProcess"+deviceTest.burningProcess+"").checked=true;
            document.getElementById("speaker"+deviceTest.speaker+"").checked=true;
            document.getElementById("leftMic"+deviceTest.leftMic+"").checked=true;
            document.getElementById("rightMic"+deviceTest.rightMic+"").checked=true;
            document.getElementById("camara"+deviceTest.camara+"").checked=true;
            document.getElementById("gestureRecognition"+deviceTest.gestureRecognition+"").checked=true;
            document.getElementById("display"+deviceTest.display+"").checked=true;
            document.getElementById("touchKey"+deviceTest.touchKey+"").checked=true;
            document.getElementById("rfid"+deviceTest.rfid+"").checked=true;
            document.getElementById("rotationFunction"+deviceTest.rotationFunction+"").checked=true;
            document.getElementById("wifi"+deviceTest.wifi+"").checked=true;
        }
    }


    //修改设备测试信息
    function updateData() {
        //隐藏modal
        $("#myModal-data").modal("hide");
        var id=$("#modifyData").children("#deviceTestId").val();
        var index=$("#modifyData").children("#deviceIndex").val();
        var deviceTest = jsonData[index];
        var deviceNo=deviceTest.device.deviceNo;
        var epalId=deviceTest.device.epalId;
        var status=deviceTest.status;
        var usbConnect=$("input:radio[name=\"usbConnect\"]:checked").val();
        var led=$("input:radio[name=\"led\"]:checked").val();
        var burningProcess=$("input:radio[name=\"burningProcess\"]:checked").val();
        var speaker=$("input:radio[name=\"speaker\"]:checked").val();
        var leftMic=$("input:radio[name=\"leftMic\"]:checked").val();
        var rightMic=$("input:radio[name=\"rightMic\"]:checked").val();
        var camara=$("input:radio[name=\"camara\"]:checked").val();
        var gestureRecognition=$("input:radio[name=\"gestureRecognition\"]:checked").val();
        var display=$("input:radio[name=\"display\"]:checked").val();
        var touchKey=$("input:radio[name=\"touchKey\"]:checked").val();
        var rfid=$("input:radio[name=\"rfid\"]:checked").val();
        var rotationFunction=$("input:radio[name=\"rotationFunction\"]:checked").val();
        var wifi=$("input:radio[name=\"wifi\"]:checked").val();
        

        //通过Ajax更新服务器上的数据，并更新界面
        var dataPar = 'id=' + id +'&usbConnect='+usbConnect+'&led='+led
        +'&burningProcess='+burningProcess+'&speaker='+speaker+'&leftMic='+leftMic
        +'&rightMic='+rightMic+'&camara='+camara+'&gestureRecognition='+gestureRecognition
        +'&display='+display+'&touchKey='+touchKey+'&rfid='+rfid
        +'&rotationFunction='+rotationFunction+'&wifi='+wifi+'&deviceNo='+deviceNo+'&epalId='+epalId+'&status='+status+'';
        $.ajax({
            type: "POST",
            data: dataPar,
            dataType: "json",
            url: "<%=request.getContextPath() %>/api/modifyDeviceTest",
            context: document.body,
            beforeSend: function () {
                    //这里是开始执行方法
                    var dataListTab = $('#dataListTab');
                    $("#dataListTab tr:not(:has(th))").remove();
                    dataListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");

                },
                complete: function () {

                },
                success: function (data) {

                    var index = $("#modifyData").children("#deviceIndex").val();
                    if (index.length == '0') {
                        //添加到最后
                        jsonData.push(data.data);
                        //更新总数
                        updateTotalCount(1);

                    } else {
                        //更新数组数据
                        jsonData[index].id = data.data.id;
                        jsonData[index].status = status;
                        jsonData[index].deviceNo = deviceNo;
                        jsonData[index].epalId = epalId;
                        jsonData[index].usbConnect =usbConnect;
                        jsonData[index].led =led;
                        jsonData[index].burningProcess =burningProcess;
                        jsonData[index].speaker =speaker;
                        jsonData[index].leftMic =leftMic;
                        jsonData[index].rightMic =rightMic;
                        jsonData[index].camara =camara;
                        jsonData[index].gestureRecognition =gestureRecognition;
                        jsonData[index].display =display;
                        jsonData[index].touchKey =touchKey;
                        jsonData[index].rfid =rfid;
                        jsonData[index].rotationFunction =rotationFunction;
                        jsonData[index].wifi =wifi;
                        
                    }
                    //更新界面
                    updateUI();

                }
        });
    }
    
    
    
    
    
</script>

<div class="main-content">
	<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try {
				ace.settings.check('breadcrumbs', 'fixed');
			} catch (e) {
			}
		</script>

		<ul class="breadcrumb">
			<li><i class="icon-home home-icon"></i> <a href="main.htm">首页</a>
			</li>
			<li class="active">设备管理</li>
			<li class="active">设备信息列表</li>
		</ul>
		<!-- .breadcrumb -->

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/device/getDevicesInfo"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>设备唯一标示号</td>
						<td><input type="text" name="searchStr" value="${queryDto.searchStr}">
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
							</div>
						</td>
					</tr>


				</table>

				<div class="btn-group col-md-12">
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
										<th width="5%">设备唯一标示</th>
										<th width="5%">设备测试状态</th>
										<th width="5%">USB连接</th>
										<th width="5%">LED灯</th>
										<th width="5%">烧录程序</th>
										<th width="5%">SPEAKER</th>
										<th width="5%">LEFTMIC</th>
										<th width="5%">RIGHTMIC</th>
										<th width="5%">摄像头</th>
										<th width="5%">手势识别</th>
										<th width="5%">显示屏</th>
										<th width="5%">主机HOME键</th>
										<th width="5%">RFID</th>
										<th width="5%">旋转功能</th>
										<th width="5%">WIFI</th>
										<th width="25%">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./getDevicesInfo"
								params="<%=request.getParameterMap()%>" />

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- 模态弹出窗内容 -->
<form class="data" method="post"  id="modifyData" 
	enctype="multipart/form-data" action="">
	<input type="hidden" name="deviceIndex" id="deviceIndex" value="">
	<input type="hidden" name="deviceTestId" id="deviceTestId" value="">
	<div class="modal fade" id="myModal-data" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:1100px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">添加/修改设备测试信息</h4>
				</div>
				<div class="modal-body">


					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">USB连接</label>
						<div class="col-sm-9" style="line-height:26px;">
							<label class="inline">
								<input name="usbConnect" id="usbConnect0"  value="0" type="radio" class="ace" />
								<span class="lbl">不合格</span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="usbConnect" id="usbConnect1" value="1" type="radio" class="ace" />
								<span class="lbl">合格</span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">LED</label>
						<div class="col-sm-9" style="line-height:26px;">
							<label class="inline">
								<input name="led" id="led0"  value="0" type="radio" class="ace" />
								<span class="lbl">不合格</span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="led" id="led1" value="1" type="radio" class="ace" />
								<span class="lbl">合格</span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">烧录程序</label>
						<div class="col-sm-9" style="line-height:26px;">
							<label class="inline">
								<input name="burningProcess" id="burningProcess0"  value="0" type="radio" class="ace" />
								<span class="lbl">不合格</span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="burningProcess" id="burningProcess1" value="1" type="radio" class="ace" />
								<span class="lbl">合格</span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">SPEAKER</label>
						<div class="col-sm-9" style="line-height:26px;">
							<label class="inline">
								<input name="speaker" id="speaker0"  value="0" type="radio" class="ace" />
								<span class="lbl">不合格</span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="speaker" id="speaker1" value="1" type="radio" class="ace" />
								<span class="lbl">合格</span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">LEFTMIC</label>
						<div class="col-sm-9" style="line-height:26px;">
							<label class="inline">
								<input name="leftMic" id="leftMic0"  value="0" type="radio" class="ace" />
								<span class="lbl">不合格</span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="leftMic" id="leftMic1" value="1" type="radio" class="ace" />
								<span class="lbl">合格</span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">RIGHTMIC</label>
						<div class="col-sm-9" style="line-height:26px;">
							<label class="inline">
								<input name="rightMic" id="rightMic0"  value="0" type="radio" class="ace" />
								<span class="lbl">不合格</span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rightMic" id="rightMic1" value="1" type="radio" class="ace" />
								<span class="lbl">合格</span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">摄像头</label>
						<div class="col-sm-9" style="line-height:26px;">
							<label class="inline">
								<input name="camara" id="camara0"  value="0" type="radio" class="ace" />
								<span class="lbl">不合格</span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="camara" id="camara1" value="1" type="radio" class="ace" />
								<span class="lbl">合格</span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">手势识别</label>
						<div class="col-sm-9" style="line-height:26px;">
							<label class="inline">
								<input name="gestureRecognition" id="gestureRecognition0"  value="0" type="radio" class="ace" />
								<span class="lbl">不合格</span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="gestureRecognition" id="gestureRecognition1" value="1" type="radio" class="ace" />
								<span class="lbl">合格</span>
							</label>
							
						</div>
					</div>
					
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">显示屏</label>
						<div class="col-sm-9" style="line-height:26px;">
							<label class="inline">
								<input name="display" id="display0"  value="0" type="radio" class="ace" />
								<span class="lbl">不合格</span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="display" id="display1" value="1" type="radio" class="ace" />
								<span class="lbl">合格</span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">主机HOME键</label>
						<div class="col-sm-9" style="line-height:26px;">
							<label class="inline">
								<input name="touchKey" id="touchKey0"  value="0" type="radio" class="ace" />
								<span class="lbl">不合格</span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="touchKey" id="touchKey1" value="1" type="radio" class="ace" />
								<span class="lbl">合格</span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">RFID</label>
						<div class="col-sm-9" style="line-height:26px;">
							<label class="inline">
								<input name="rfid" id="rfid0"  value="0" type="radio" class="ace" />
								<span class="lbl">不合格</span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rfid" id="rfid1" value="1" type="radio" class="ace" />
								<span class="lbl">合格</span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">旋转功能</label>
						<div class="col-sm-9" style="line-height:26px;">
							<label class="inline">
								<input name="rotationFunction" id="rotationFunction0"  value="0" type="radio" class="ace" />
								<span class="lbl">不合格</span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rotationFunction" id="rotationFunction1" value="1" type="radio" class="ace" />
								<span class="lbl">合格</span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">WIFI</label>
						<div class="col-sm-9" style="line-height:26px;">
							<label class="inline">
								<input name="wifi" id="wifi0"  value="0" type="radio" class="ace" />
								<span class="lbl">不合格</span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="wifi" id="wifi1" value="1" type="radio" class="ace" />
								<span class="lbl">合格</span>
							</label>
							
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


<!-- 模态弹出窗内容 --> 
<form class="data" method="post"  id="modifyReview" 
	enctype="multipart/form-data" action="">
	<input type="hidden" name="deviceIndex" id="deviceIndex" value="" >
	<input type="hidden" name="deviceTestId" id="deviceTestId" value="" >
			<div class="modal  fade" id="reviewModal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">审核事件</h4>
						</div>
						<div class="modal-body">
							<div class="form-group" style="height:35px;">
							
								<div class="form-group" style="height:35px;">
									<label class="col-sm-3 control-label no-padding-right text-right">审核操作</label>
									<div class="col-sm-9" style="line-height:26px;">
										<label class="inline">
											<input name="status" id="status0" value="2" type="radio" class="ace" />
											<span class="lbl">通过</span>
										</label>

										&nbsp; &nbsp; &nbsp;
										<label class="inline">
											<input name="status" id="status1" value="1" type="radio" class="ace" />
											<span class="lbl">退回</span>
										</label>
									</div>
								</div>
								
								
								<div class="form-group" style="height:35px;">
									<label class="col-xs-3 control-label no-padding-right text-right">退回原因</label>
										<div class="col-xs-9">
										<div class="input-medium">
											<div class="input-group">
												<textarea name="backResult" id="backResult" style="max-width: 380px;max-height: 75px;"> </textarea>
											</div>
										</div>
									</div>
								</div>
								
							
							</div>
							<div style="clear: both;"> </div>		

						</div>
						<div class="modal-footer">
							<button type="button" onclick="updateReview()" class="btn btn-primary">提交</button>
							<button type="button"  class="btn btn-default" data-dismiss="modal">取消</button>
						</div>
					</div>
				</div>
			</div>
</form>

