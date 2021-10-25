var DataSourceTree = function(options) {
	this._data 	= options.data;
	this._delay = options.delay;
}

DataSourceTree.prototype.data = function(options, callback) {
	var self = this;
	var $data = null;

	if(!("name" in options) && !("type" in options)){
		$data = this._data;//the root tree
		callback({ data: $data });
		return;
	}
	else if("type" in options && options.type == "folder") {
		if("additionalParameters" in options && "children" in options.additionalParameters)
			$data = options.additionalParameters.children;
		else $data = {}//no data
	}
	
	if($data != null)//this setTimeout is only for mimicking some random delay
		setTimeout(function(){callback({ data: $data });} , parseInt(Math.random() * 500) + 200);

	//we have used static data here
	//but you can retrieve your data dynamically from a server using ajax call
	//checkout examples/treeview.html and examples/treeview.js for more info
};

var tree_data = {
	'最近联系人' : {name: '最近联系人', type: 'folder'}	,
	'管理员组1' : {name: '管理员组1', type: 'folder'}	,
	'管理员组2' : {name: '管理员组2', type: 'folder'}	,
	'管理员组3' : {name: '管理员组3', type: 'folder'}	,
	'其他' : {name: '其他', type: 'folder'}	
}
tree_data['最近联系人']['additionalParameters'] = {
	'children' : {
		'李某' : {name: '<span class="icon-user blue"></span>李某', type: 'item'},
		'张某' : {name: '<span class="icon-user blue"></span>张某', type: 'item'},
		'刘某' : {name: '<span class="icon-user blue"></span>刘某', type: 'item'},
		'金某' : {name: '<span class="icon-user blue"></span>金某', type: 'item'},
		'史某' : {name: '<span class="icon-user blue"></span>史某', type: 'item'},
		'田某' : {name: '<span class="icon-user blue"></span>田某', type: 'item'},
		'周某' : {name: '<span class="icon-user blue"></span>周某', type: 'item'}
	}
}
tree_data['管理员组1']['additionalParameters'] = {
	'children' : {
		'田某' : {name: '<span class="icon-user blue"></span>田某', type: 'item'},
		'周某' : {name: '<span class="icon-user blue"></span>周某', type: 'item'}
	}
}
tree_data['管理员组2']['additionalParameters'] = {
	'children' : {
		'张某' : {name: '<span class="icon-user blue"></span>张某', type: 'item'},
		'刘某' : {name: '<span class="icon-user blue"></span>刘某', type: 'item'},
		'金某' : {name: '<span class="icon-user blue"></span>金某', type: 'item'},
		'史某' : {name: '<span class="icon-user blue"></span>史某', type: 'item'},
		'田某' : {name: '<span class="icon-user blue"></span>田某', type: 'item'},
		'周某' : {name: '<span class="icon-user blue"></span>周某', type: 'item'}
	}
}

tree_data['管理员组3']['additionalParameters'] = {
	'children' : {
		'史某' : {name: '<span class="icon-user blue"></span>史某', type: 'item'},
		'田某' : {name: '<span class="icon-user blue"></span>田某', type: 'item'},
		'周某' : {name: '<span class="icon-user blue"></span>周某', type: 'item'}
	}
}
tree_data['其他']['additionalParameters'] = {
	'children' : {
		'张某' : {name: '<span class="icon-user blue"></span>张某', type: 'item'},
		'刘某' : {name: '<span class="icon-user blue"></span>刘某', type: 'item'},
		'金某' : {name: '<span class="icon-user blue"></span>金某', type: 'item'},
		'史某' : {name: '<span class="icon-user blue"></span>史某', type: 'item'}
	}
}

var treeDataSource = new DataSourceTree({data: tree_data});

var tree_data_2 = {
	'系统管理员' : {name: '系统管理员', type: 'folder', 'icon-class':'icon-group red'}	,
	'管理组1' : {name: '管理组1', type: 'folder', 'icon-class':'icon-group orange'}	,
	'管理组2' : {name: '管理组2', type: 'folder', 'icon-class':'icon-group blue'}	,
	'管理组3' : {name: '管理组3', type: 'folder', 'icon-class':'icon-group green'}	,
	'经理' : {name: '<i class="icon-group blue"></i> 经理', type: 'item'},
	'店长' : {name: '<i class="icon-group blue"></i> 店长', type: 'item'},
	'员工' : {name: '<i class="icon-group blue"></i> 员工', type: 'item'}
}
tree_data_2['管理组1']['additionalParameters'] = {
	'children' : [
		{name: '<i class="icon-user green"></i> 刘某某', type: 'item'}
	]
}
tree_data_2['管理组2']['additionalParameters'] = {
	'children' : [
		{name: '<i class="icon-user green"></i> 刘某某', type: 'item'}
	]
}
tree_data_2['系统管理员']['additionalParameters'] = {
	'children' : {
		'行业1' : {name: '行业1', type: 'folder', 'icon-class':'icon-group pink'},
		'行业2' : {name: '行业2', type: 'folder', 'icon-class':'icon-group pink'},
		'行业3' : {name: '行业3', type: 'folder', 'icon-class':'icon-group pink'}
	}
}
tree_data_2['系统管理员']['additionalParameters']['children']['行业1']['additionalParameters'] = {
	'children' : {
		'单位1' : {name: '单位1', type: 'folder', 'icon-class':'icon-group pink'},
		'单位2' : {name: '单位2', type: 'folder', 'icon-class':'icon-group pink'}
	}
}
tree_data_2['系统管理员']['additionalParameters']['children']['行业1']['additionalParameters']['children']['单位1']['additionalParameters'] = {
	'children' : {
		'经理' : {name: '经理', type: 'folder', 'icon-class':'icon-group pink'},
		'店长' : {name: '店长', type: 'folder', 'icon-class':'icon-group pink'},
		'员工' : {name: '员工', type: 'folder', 'icon-class':'icon-group pink'}
	}
}
tree_data_2['系统管理员']['additionalParameters']['children']['行业1']['additionalParameters']['children']['单位1']['additionalParameters']['children']['经理']['additionalParameters'] = {
	'children' : [
		{name: '<i class="icon-user green"></i> 李某某', type: 'item'}
	]
}
tree_data_2['系统管理员']['additionalParameters']['children']['行业1']['additionalParameters']['children']['单位1']['additionalParameters']['children']['店长']['additionalParameters'] = {
	'children' : [
		{name: '<i class="icon-user green"></i> 刘某某', type: 'item'}
	]
}
tree_data_2['系统管理员']['additionalParameters']['children']['行业1']['additionalParameters']['children']['单位1']['additionalParameters']['children']['员工']['additionalParameters'] = {
	'children' : [
		{name: '<i class="icon-user green"></i> 周某某', type: 'item'},
		{name: '<i class="icon-user green"></i> 陈某某', type: 'item'},
		{name: '<i class="icon-user green"></i> 刘某某', type: 'item'}
	]
}
tree_data_2['系统管理员']['additionalParameters']['children']['行业1']['additionalParameters']['children']['单位2']['additionalParameters'] = {
	'children' : [
		{name: '<i class="icon-user green"></i> 刘某某', type: 'item'}
	]
}
tree_data_2['系统管理员']['additionalParameters']['children']['行业2']['additionalParameters'] = {
	'children' : [
		{name: '<i class="icon-user green"></i> 刘某某', type: 'item'}
	]
}
tree_data_2['系统管理员']['additionalParameters']['children']['行业3']['additionalParameters'] = {
	'children' : [
		{name: '<i class="icon-user green"></i> 刘某某', type: 'item'}
	]
}


tree_data_2['管理组3']['additionalParameters'] = {
	'children' : [
		{name: '<i class="icon-user green"></i> 刘某某', type: 'item'}
	]
}

var treeDataSource2 = new DataSourceTree({data: tree_data_2});