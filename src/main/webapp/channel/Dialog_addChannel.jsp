<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<form class="data" method="post" name="modifyData" id="modifyData"
	enctype="multipart/form-data" action="">
	<input type="hidden" name="index" id="index" value=""> 
	<input type="hidden" name="id" id="id" value=""> 
	<div class="modal fade" id="myModal-data" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:900px;height: 1200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">频道添加</h4>
				</div>
				<div class="modal-body">
				
					<div id="divInput" class="form-group" style="height:35px;display: none;">
						<label class="col-sm-2 control-label no-padding-right text-right">序号</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="id" id="id"
										type="text"
										style="max-width:650px;width:100%;" readonly/>
								</div>
							</div>
						</div>
					</div>
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">频道ID</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="channelId" id="channelId"
										type="text" 
										style="max-width:650px;width:80%;" />&nbsp;&nbsp;&nbsp;&nbsp;
									<button onclick="AutomaticFilling()" style="max-width:650px;width:15%;">自动获取</button>
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">频道名称</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="name" id="name"
										type="text"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">频道简介</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="intro" id="intro"
										type="text"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">频道海报</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="image" id="image"
										type="text"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">粉丝数</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="fans" id="fans"
										type="text"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">状态</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="status" id="status"
										type="text" 
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">等级</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="level" id="level"
										type="text" 
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					<div style="display:none;">
						<input id="nextUpdateTime" name="nextUpdateTime">
					</div>
					
					<div style="display:none;">
						<input id="lastUpdateTime" name="lastUpdateTime">
					</div>
					
					<div style="display:none;">
						<input id="updateCycle" name="updateCycle">
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