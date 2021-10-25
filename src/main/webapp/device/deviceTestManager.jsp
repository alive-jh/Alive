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
    //json字符串转json数组
    var jsonData = $.parseJSON('${jsonData}').data;

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
        
	     for(var i=0;i<jsonData.length;i++){
	        var deviceTest=jsonData[i];
	        var testName= deviceTest.registMen;
	        var testTime= deviceTest.registTime;
	        var reviewName = deviceTest.reviewMen;
	        var reviewTime = deviceTest.reviewTime;
	        var backResult=deviceTest.backResult;
	        var reviewColor='';
	        var reviewText='';
	        var resultColor1='';
	        var resultColor2='';
	        var resultColor3='';
	        var reviewButton='<button class="btn btn-danger" onclick="editReview(' + i + ')"  style="border:none;border-radius: 3px;"  data-toggle="modal" data-target="#reviewModal-data">审核</button>';
	        var resultContent='<b class="itemState blue">维修记录:</b><span class="itemReason blue" style="text-align:center;">'+backResult+'</span>';
	        switch (deviceTest.status) {
			case 0:
			      reviewColor='btn-warning';
	          	  reviewText='待测试';
	          	  resultColor1='itemWarn';
	          	  resultColor2='itemNumWarn';
	          	  resultColor3='itemMesgWarn';
				break;
			case 1:
			  	 reviewColor='btn-danger';
	          	 reviewText='不合格';
	          	 resultColor1='itemError';
	          	 resultColor2='itemNumError';
	          	 resultColor3='itemMesgError';
				break;
			case 2:
			  	 reviewColor='btn-success';
	          	 reviewText='已合格';
	          	 resultColor1='itemDone';
	          	 resultColor2='itemNumDone';
	          	 resultColor3='itemMesgDone';
				break;
			case 3:
				 reviewColor='btn-info';
	          	 reviewText='已维修';
	          	 resultColor1='itemRepair';
	          	 resultColor2='itemNumRepair';
	          	 resultColor3='itemMesgRepair';
				break;
			}
			
			//usb连接状态
	        var usbConnect= getStatus(deviceTest.usbConnect); 
	     	//led灯
	     	var led= getStatus(deviceTest.led); 
	     	//烧录程序
	     	var burningProcess= getStatus(deviceTest.burningProcess); 
	     	//speaker
	     	var speaker= getStatus(deviceTest.speaker); 
			//leftmic
	     	var leftMic= getStatus(deviceTest.leftMic); 
	     	//rightmic
	     	var rightMic=getStatus(deviceTest.rightMic); 
	     	//摄像头
	     	var camara= getStatus(deviceTest.camara); 
	     	//手势识别
	     	var gestureRecognition= getStatus(deviceTest.gestureRecognition); 
	     	//显示屏
	     	var display= getStatus(deviceTest.display); 
	     	//主机HOME键
	     	var touchKey= getStatus(deviceTest.touchKey); 
	     	//rfid
	     	var rfid= getStatus(deviceTest.rfid); 
	     	//旋转功能
	     	var rotationFunction= getStatus(deviceTest.rotationFunction); 
	     	//wifi
	     	var wifi= getStatus(deviceTest.wifi); 
	     	//开机启动
	     	var power=getStatus(deviceTest.power); 
	     	//测试程序
	     	var testProcess=getStatus(deviceTest.testProcess); 
	     	//左喇叭
	     	var leftHorn=getStatus(deviceTest.leftHorn); 
	     	//右喇叭
	     	var rightHorn=getStatus(deviceTest.rightHorn); 
	     	//左耳机
	     	var leftEar=getStatus(deviceTest.leftEar); 
	     	//右耳机
	     	var rightEar=getStatus(deviceTest.rightEar); 
	     	//耳机MIC
	     	var earMic=getStatus(deviceTest.earMic); 
	     	//顶部Touch
	     	var topTouch=getStatus(deviceTest.topTouch); 
	     	//左Touch
	     	var leftTouch=getStatus(deviceTest.leftTouch); 
	     	//右Touch
	     	var rightTouch=getStatus(deviceTest.rightTouch); 
	     	//底座充电
	     	var footCharge=getStatus(deviceTest.footCharge); 
	     	//USB充电
	     	var usbCharge=getStatus(deviceTest.usbCharge); 
	     	
	     	newcontent = newcontent + '<tr>';
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" >' + deviceTest.deviceNo + '</td>';
	        newcontent = newcontent + '<td><span class="btn btn-xs '+reviewColor+' " style="cursor:default;" onclick="$(this).parent().parent().next().toggle(200);" title="点击查看状态详情"><i class="">'+reviewText+'</i></span></td>';
	        newcontent = newcontent + usbConnect;
	        newcontent = newcontent + led;
	        newcontent = newcontent + power;
	        newcontent = newcontent + leftMic;
	        newcontent = newcontent + rightMic;
	        newcontent = newcontent + camara;
	        newcontent = newcontent + gestureRecognition;
	        newcontent = newcontent + display;
	        newcontent = newcontent + rfid;
	        newcontent = newcontent + rotationFunction;
	        newcontent = newcontent + wifi;
	        newcontent = newcontent + testProcess;
	        newcontent = newcontent + leftHorn;
	        newcontent = newcontent + rightHorn;
	        newcontent = newcontent + leftEar;
	        newcontent = newcontent + rightEar;
	        newcontent = newcontent + earMic;
	        newcontent = newcontent + topTouch;
	        newcontent = newcontent + leftTouch;
	        newcontent = newcontent + rightTouch;
	        newcontent = newcontent + footCharge;
	        newcontent = newcontent + usbCharge;
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editData(' + i + ')" data-toggle="modal" data-target="#myModal-data"><i class="icon-edit bigger-120"></i></button><button onclick="deleteData(' + i + ')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button></td>';
	        newcontent = newcontent + '</tr>';
		    newcontent = newcontent + '<tr class="accordion-body collapse" >' +
			'<td colspan="25" style="background-color:#F7FCFC;" class="stepDetails">'+
			'  <table class="table tableNB" style="padding-top:20px;">'+
			'	<tr>'+
			'		<td>'+
			'			<span class="itemName itemDefault ">测试登记</span>'+
			'			<div class="itemNum itemNumDefault"><span>1</span></div>'+
			'			<div class="itemMesg itemMesgDefault">'+
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
			'		<td>'
			+ resultContent +
			'       </td>'+
			'		<td> </td>'+
			'		<td>' 
			+ reviewButton +
			'		</td>' +
			'	</tr>'+
			'  </table>   '+
			'</td>'+
		    '</tr>';	
	      }
	      
	      dataListTab.append(newcontent);
	      
	}
	
	
	function getStatus(status){
		var htmSta='';
		switch (status) {
		case 0:
			htmSta='<td><span class="btn btn-xs btn-warning " style="cursor:default;" >&nbsp;&nbsp;&nbsp</span></td>';
			break;
		case 1:
			htmSta='<td><span class="btn btn-xs btn-danger " style="cursor:default;" >&nbsp;&nbsp;&nbsp;</span></td>';
			break;
		case 2:
			htmSta='<td><span class="btn btn-xs btn-success " style="cursor:default;" >&nbsp;&nbsp;&nbsp;</span></td>';
			break;
		case 3:
			htmSta='<td><span class="btn btn-xs btn-info " style="cursor:default;" >&nbsp;&nbsp;&nbsp;</span></td>';
			break;
		default:
			break;
		}
		return htmSta;
		
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
            var dataPar = 'deviceNo='+jsonData[index].deviceNo;
            $.ajax({
                type: "POST",
                data: dataPar,
                dataType: "json",
                url: "<%=request.getContextPath() %>/api/deleteDeviceTestByDeviceNo",
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
       	var deviceTest = jsonData[index];
        $("#modifyReview").children("#index").val(index);
        $("#modifyReview").children("#id").val(deviceTest.id);
        $("#reviewMen").val(deviceTest.reviewMen);
        $("#backResult").val(deviceTest.backResult);
        document.getElementById("review"+deviceTest.status+"").checked=true;
        
    }
    
    //审核
    function updateReview(){
    	//隐藏modal
        $("#reviewModal-data").modal("hide");
        var id=$("#modifyReview").children("#id").val();
        var index = $("#modifyReview").children("#index").val();
        var status= $("input:radio[name=\"status\"]:checked").val();
        var backResult=$("#backResult").val();
        var reviewMen=$("#reviewMen").val();
        //通过Ajax更新服务器上的数据，并更新界面
        var dataPar = 'id=' + id +'&status='+status+'&backResult='+backResult+'&reviewMen='+reviewMen+'';
        $.ajax({
            type: "POST",
            data: dataPar,
            dataType: "json",
            url: "<%=request.getContextPath() %>/api/changeDeviceTestStatusWeb",
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
                    
                    //更新数组数据
                    jsonData[index]=data.data;
                    //更新界面
                    updateUI();

                }
        });
    
    }

    //预添加或者修改设备测试信息
    function editData(index) {
        if (index.length == '0') {
            $("#modifyData").children("#index").val("");
            $("#modifyData").children("#id").val("");
        } else {
            //第一步，传输数组数据到弹出框
            var deviceTest = jsonData[index];
            $("#modifyData").children("#index").val(index);
            $("#modifyData").children("#id").val(deviceTest.id);
            document.getElementById("usbConnect"+deviceTest.usbConnect+"").checked=true;
            document.getElementById("led"+deviceTest.led+"").checked=true;
            document.getElementById("power"+deviceTest.power+"").checked=true;
            document.getElementById("leftMic"+deviceTest.leftMic+"").checked=true;
            document.getElementById("rightMic"+deviceTest.rightMic+"").checked=true;
            document.getElementById("camara"+deviceTest.camara+"").checked=true;
            document.getElementById("gestureRecognition"+deviceTest.gestureRecognition+"").checked=true;
            document.getElementById("display"+deviceTest.display+"").checked=true;
            document.getElementById("rfid"+deviceTest.rfid+"").checked=true;
            document.getElementById("rotationFunction"+deviceTest.rotationFunction+"").checked=true;
            document.getElementById("wifi"+deviceTest.wifi+"").checked=true;
            document.getElementById("testProcess"+deviceTest.testProcess+"").checked=true;
            document.getElementById("leftHorn"+deviceTest.leftHorn+"").checked=true;
            document.getElementById("rightHorn"+deviceTest.rightHorn+"").checked=true;
            document.getElementById("leftEar"+deviceTest.leftEar+"").checked=true;
            document.getElementById("rightEar"+deviceTest.rightEar+"").checked=true;
            document.getElementById("earMic"+deviceTest.earMic+"").checked=true;
            document.getElementById("topTouch"+deviceTest.topTouch+"").checked=true;
            document.getElementById("leftTouch"+deviceTest.leftTouch+"").checked=true;
            document.getElementById("rightTouch"+deviceTest.rightTouch+"").checked=true;
            document.getElementById("footCharge"+deviceTest.footCharge+"").checked=true;
            document.getElementById("usbCharge"+deviceTest.usbCharge+"").checked=true;
            
        }
    }


    //修改设备测试信息
    function updateData() {
        //隐藏modal
        $("#myModal-data").modal("hide");
        var id=$("#modifyData").children("#id").val();
        var index=$("#modifyData").children("#index").val();
        var deviceTest = jsonData[index];
        var deviceNo=deviceTest.deviceNo;
        var epalId=deviceTest.epalId;
        var status=deviceTest.status;
        var usbConnect=$("input:radio[name=\"usbConnect\"]:checked").val();
        var led=$("input:radio[name=\"led\"]:checked").val();
        var power=$("input:radio[name=\"power\"]:checked").val();
        var leftMic=$("input:radio[name=\"leftMic\"]:checked").val();
        var rightMic=$("input:radio[name=\"rightMic\"]:checked").val();
        var camara=$("input:radio[name=\"camara\"]:checked").val();
        var gestureRecognition=$("input:radio[name=\"gestureRecognition\"]:checked").val();
        var display=$("input:radio[name=\"display\"]:checked").val();
        var rfid=$("input:radio[name=\"rfid\"]:checked").val();
        var rotationFunction=$("input:radio[name=\"rotationFunction\"]:checked").val();
        var wifi=$("input:radio[name=\"wifi\"]:checked").val();
        var testProcess=$("input:radio[name=\"testProcess\"]:checked").val();
        var leftHorn=$("input:radio[name=\"leftHorn\"]:checked").val();
        var rightHorn=$("input:radio[name=\"rightHorn\"]:checked").val();
        var leftEar=$("input:radio[name=\"leftEar\"]:checked").val();
        var rightEar=$("input:radio[name=\"rightEar\"]:checked").val();
        var earMic=$("input:radio[name=\"earMic\"]:checked").val();
        var topTouch=$("input:radio[name=\"topTouch\"]:checked").val();
        var leftTouch=$("input:radio[name=\"leftTouch\"]:checked").val();
        var rightTouch=$("input:radio[name=\"rightTouch\"]:checked").val();
        var footCharge=$("input:radio[name=\"footCharge\"]:checked").val();
        var usbCharge=$("input:radio[name=\"usbCharge\"]:checked").val();
        

        //通过Ajax更新服务器上的数据，并更新界面
        var dataPar = 'id=' + id +'&usbConnect='+usbConnect+'&led='+led
        +'&power='+power+'&leftMic='+leftMic
        +'&rightMic='+rightMic+'&camara='+camara+'&gestureRecognition='+gestureRecognition
        +'&display='+display+'&rfid='+rfid+'&rotationFunction='+rotationFunction+'&wifi='+wifi
        +'&testProcess='+testProcess+'&leftHorn='+leftHorn+'&rightHorn='+rightHorn
        +'&leftEar='+leftEar+'&rightEar='+rightEar+'&earMic='+earMic
        +'&topTouch='+topTouch+'&leftTouch='+leftTouch+'&rightTouch='+rightTouch
        +'&footCharge='+footCharge+'&usbCharge='+ usbCharge +'&deviceNo='+deviceNo+'&epalId='+epalId+'&status='+status+'';
        $.ajax({
            type: "POST",
            data: dataPar,
            dataType: "json",
            url: "<%=request.getContextPath() %>/api/saveDeviceTest",
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

                    var index = $("#modifyData").children("#index").val();
                    if (index.length == '0') {
                        //添加到最后
                        jsonData.push(data.data);
                        //更新总数
                        updateTotalCount(1);

                    } else {
                        //更新数组数据
                        jsonData[index]=data.data;
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
		
		<div class="button-group">
			<button class="btn btn-warning" onclick="window.location='<%=request.getContextPath()%>/device/getDeviceTestsInfo?status=0'">待测试</button>
			<button class="btn btn-danger" onclick="window.location='<%=request.getContextPath()%>/device/getDeviceTestsInfo?status=1'">不合格</button>
			<button class="btn btn-success" onclick="window.location='<%=request.getContextPath()%>/device/getDeviceTestsInfo?status=2'">已合格</button>
			<button class="btn btn-info" onclick="window.location='<%=request.getContextPath()%>/device/getDeviceTestsInfo?status=3'">已维修</button>
		</div>
	

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/device/getDeviceTestsInfo"
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
										<th width="1%">设备唯一标示号</th>
										<th width="1%">测试状态</th>
										<th width="1%">usb连接</th>
										<th width="1%">led灯</th>
										<th width="1%">开机启动</th>
										<th width="1%">leftmic</th>
										<th width="1%">rightmic</th>
										<th width="1%">camera</th>
										<th width="1%">手势识别</th>
										<th width="1%">显示屏</th>
										<th width="1%">rfid</th>
										<th width="1%">旋转功能</th>
										<th width="1%">wifi</th>
										<th width="1%">测试程序</th>
										<th width="1%">左喇叭</th>
										<th width="1%">右喇叭</th>
										<th width="1%">左耳机</th>
										<th width="1%">右耳机</th>
										<th width="1%">耳机mic</th>
										<th width="1%">顶部touch</th>
										<th width="1%">左touch</th>
										<th width="1%">右touch</th>
										<th width="1%">底座充电</th>
										<th width="1%">usb充电</th>
										<th width="5%">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./getDeviceTestsInfo"
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
	<input type="hidden" name="index" id="index" value="">
	<input type="hidden" name="id" id="id" value="">
	<div class="modal fade" id="myModal-data" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:650px;">
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
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="usbConnect" id="usbConnect1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="usbConnect" id="usbConnect2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="usbConnect" id="usbConnect3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">LED</label>
						<div class="col-sm-9" style="line-height:26px;">
						
							<label class="inline">
								<input name="led" id="led0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="led" id="led1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="led" id="led2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="led" id="led3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">开机启动</label>
						<div class="col-sm-9" style="line-height:26px;">
							
							<label class="inline">
								<input name="power" id="power0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="power" id="power1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="power" id="power2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="power" id="power3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">LEFTMIC</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="leftMic" id="leftMic0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="leftMic" id="leftMic1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="leftMic" id="leftMic2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="leftMic" id="leftMic3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">RIGHTMIC</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="rightMic" id="rightMic0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rightMic" id="rightMic1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rightMic" id="rightMic2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rightMic" id="rightMic3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">摄像头</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="camara" id="camara0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="camara" id="camara1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="camara" id="camara2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="camara" id="camara3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">手势识别</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="gestureRecognition" id="gestureRecognition0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="gestureRecognition" id="gestureRecognition1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="gestureRecognition" id="gestureRecognition2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="gestureRecognition" id="gestureRecognition3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">显示屏</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="display" id="display0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="display" id="display1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="display" id="display2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="display" id="display3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">RFID</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="rfid" id="rfid0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rfid" id="rfid1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rfid" id="rfid2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rfid" id="rfid3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">旋转功能</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="rotationFunction" id="rotationFunction0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rotationFunction" id="rotationFunction1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rotationFunction" id="rotationFunction2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rotationFunction" id="rotationFunction3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">WIFI</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="wifi" id="wifi0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="wifi" id="wifi1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="wifi" id="wifi2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="wifi" id="wifi3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">测试程序</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="testProcess" id="testProcess0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="testProcess" id="testProcess1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="testProcess" id="testProcess2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="testProcess" id="testProcess3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">左喇叭</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="leftHorn" id="leftHorn0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="leftHorn" id="leftHorn1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="leftHorn" id="leftHorn2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="leftHorn" id="leftHorn3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">右喇叭</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="rightHorn" id="rightHorn0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rightHorn" id="rightHorn1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rightHorn" id="rightHorn2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rightHorn" id="rightHorn3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">左耳机</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="leftEar" id="leftEar0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="leftEar" id="leftEar1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="leftEar" id="leftEar2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="leftEar" id="leftEar3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>

					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">右耳机</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="rightEar" id="rightEar0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rightEar" id="rightEar1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rightEar" id="rightEar2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rightEar" id="rightEar3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">耳机mic</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="earMic" id="earMic0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="earMic" id="earMic1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="earMic" id="earMic2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="earMic" id="earMic3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">顶部touch</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="topTouch" id="topTouch0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="topTouch" id="topTouch1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="topTouch" id="topTouch2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="topTouch" id="topTouch3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">左touch</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="leftTouch" id="leftTouch0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="leftTouch" id="leftTouch1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="leftTouch" id="leftTouch2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="leftTouch" id="leftTouch3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">右touch</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="rightTouch" id="rightTouch0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rightTouch" id="rightTouch1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rightTouch" id="rightTouch2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="rightTouch" id="rightTouch3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">底座充电</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="footCharge" id="footCharge0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="footCharge" id="footCharge1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="footCharge" id="footCharge2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="footCharge" id="footCharge3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
							</label>
							
						</div>
					</div>
					
					<div class="form-group" style="height:35px;">
						<label class="col-sm-3 control-label no-padding-right text-right">USB充电</label>
						<div class="col-sm-9" style="line-height:26px;">
											
							<label class="inline">
								<input name="usbCharge" id="usbCharge0"  value="0" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-warning " style="cursor:default;" >待测试</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="usbCharge" id="usbCharge1" value="1" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-danger " style="cursor:default;" >不合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="usbCharge" id="usbCharge2" value="2" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-success " style="cursor:default;" >已合格</span></span>
							</label>
							
							&nbsp; &nbsp; &nbsp;
							<label class="inline">
								<input name="usbCharge" id="usbCharge3" value="3" type="radio" class="ace" />
								<span class="lbl"><span class="btn btn-xs btn-info " style="cursor:default;" >已维修</span></span>
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
	<input type="hidden" name="index" id=index value="" >
	<input type="hidden" name="id" id="id" value="" >
			<div class="modal fade" id="reviewModal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width:500px;">
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
											<input name="status" id="review3" value="3" type="radio" class="ace" />
											<span class="lbl">已维修</span>
										</label>

										&nbsp; &nbsp; &nbsp;
										<label class="inline">
											<input name="status" id="review2" value="2" type="radio" class="ace" />
											<span class="lbl">已合格</span>
										</label>
										&nbsp; &nbsp; &nbsp;
										<label class="inline">
											<input name="status" id="review1" value="1" type="radio" class="ace" />
											<span class="lbl">不合格</span>
										</label>
										&nbsp; &nbsp; &nbsp;
										<label class="inline">
											<input name="status" id="review0" value="0" type="radio" class="ace" />
											<span class="lbl">待测试</span>
										</label>
									</div>
								</div>
								
								<div class="form-group" style="height:35px;">
									<label class="col-xs-3 control-label no-padding-right text-right">审核人</label>
									<div class="col-xs-9">
									<div class="input-medium">
										<div class="input-group" style="width:200px;">
											<input class="form-control"  name="reviewMen" id="reviewMen"
												type="text" data-rule="审核人:required;"
												style="max-width:200px;width:100%;" />
										</div>
									</div>
									</div>
								</div>
								
								
								<div class="form-group" style="height:35px;">
									<label class="col-xs-3 control-label no-padding-right text-right">修改记录</label>
										<div class="col-xs-9">
										<div class="input-medium">
											<div class="input-group">
												<textarea name="backResult" id="backResult" style="max-width: 380px;max-height: 150px;"> </textarea>
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

