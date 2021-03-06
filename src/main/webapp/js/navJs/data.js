//banner部分--logo、标题、任务、消息、通知、个人中心
var data = [];
//logo和标题
data.push({
    'title'  : '深圳机场运营管理系统',
    'link':'main.htm',
    'logoURL':'assets/images/sysLogo.png'
})
//任务
data.push({
    'iconColor'  : 'grey',
    'titleBag':'',
    'itemType':'icon-tasks',
    'badgeColor':'badge-grey',
    'numLab':7,
    'titleIcon':'icon-ok',
    'titleContent':'还有7个任务未完成',
	'link':'toDoList.htm',
	'content':'查看任务详情',
	'iconType':'icon-arrow-right'
})
//消息
data.push({
    'iconColor'  : 'purple',
    'titleBag':'navbar-pink',
    'itemType':'icon-bell-alt icon-animated-bell',
    'badgeColor':'badge-important',
    'numLab':8,
    'titleIcon':'icon-warning-sign',
    'titleContent':'8条通知',
	'link':'blank.htm',
	'content':'查看所有通知',
	'iconType':'icon-arrow-right'
})
//通知
data.push({
    'iconColor'  : 'green',
    'titleBag':'',
    'itemType':'icon-envelope icon-animated-vertical',
    'badgeColor':'badge-success',
    'numLab':5,
    'titleIcon':'icon-envelope-alt',
    'titleContent':'5条消息',
	'link':'blank.htm',
	'content':'查看所有消息',
	'iconType':'icon-arrow-right'
})
//个人中心
var personalHead=[];
personalHead.push({
	'bgColor':'light-blue',
	'imgScr':'assets/avatars/user.jpg',
	'alt':'Jason\'s Photo',
	'name':'Jason',
	'downIcon':'icon-caret-down'
})
//任务列表
var taskList = [];
taskList.push({
    'itemLink':'toDoList.htm',
    'taskTitle':'审核事件',
    'progressMiniType':'',
    'progresBarType':'progress-bar',
    'taskProgress':'3',
    'minProgress':'width:100%'	
})
taskList.push({
    'itemLink':'toDoList.htm',
    'taskTitle':'培训报名',
    'progressMiniType':'',
    'progresBarType':'progress-bar progress-bar-danger',
    'taskProgress':'2',
    'minProgress':'width:100%'	
})
taskList.push({
    'itemLink':'toDoList.htm',
    'taskTitle':'考核报名',
    'progressMiniType':'',
    'progresBarType':'progress-bar progress-bar-warning',
    'taskProgress':'2',
    'minProgress':'width:100%'	
})
taskList.push({
    'itemLink':'toDoList.htm',
    'taskTitle':'其他',
    'progressMiniType':'',
    'progresBarType':'progress-bar progress-bar-success',
    'taskProgress':'0',
    'minProgress':'width:100%'	
})
//消息列表
var noticeList=[];
noticeList.push({
	'itemLink':'blank.htm',
	'iconStyle':'no-hover btn-pink icon-comment',
	'itemContent':'新闻评论',
	'itemNumStyle':'badge-info',
	'itemNum':'+12'
})
noticeList.push({
	'itemLink':'blank.htm',
	'iconStyle':'btn-primary icon-user',
	'itemContent':'切换为编辑登录..',
	'itemNumStyle':'badge-info',
	'itemNum':''
})
noticeList.push({
	'itemLink':'blank.htm',
	'iconStyle':'no-hover btn-success icon-shopping-cart',
	'itemContent':'新订单',
	'itemNumStyle':'badge-success',
	'itemNum':'+8'
})

noticeList.push({
	'itemLink':'blank.htm',
	'iconStyle':'no-hover btn-info icon-twitter',
	'itemContent':'粉丝',
	'itemNumStyle':'badge-info',
	'itemNum':'+11'
})
//通知列表
var messageList=[];
messageList.push({
	'itemLink':'blank.htm',
	'imageSrc':'assets/avatars/avatar.png',
	'alt':'Alex\'s Avatar',
	'name':'Alex:',
	'itemContent':'不知道写啥 ...',
	'itemTiem':'1分钟以前'
})
messageList.push({
	'itemLink':'blank.htm',
	'imageSrc':'assets/avatars/avatar3.png',
	'alt':'Susan\'s Avatar',
	'name':'Susan:',
	'itemContent':'不知道翻译...',
	'itemTiem':'20分钟以前'
})
messageList.push({
	'itemLink':'blank.htm',
	'imageSrc':'assets/avatars/avatar4.png',
	'alt':'Bob\'s Avatar',
	'name':'Bob:',
	'itemContent':'到底是不是英文 ...',
	'itemTiem':'下午3:15'
})
//个人中心菜单
var pMenuList=[];
pMenuList.push({
	'itemLink':'blank.htm',
	'iconType':'icon-cog',
	'name':'设置'
})
pMenuList.push({
	'itemLink':'blank.htm',
	'iconType':'icon-user',
	'name':'个人中心'
})
pMenuList.push({
	'itemLink':'index-login.html',
	'iconType':'icon-off',
	'name':'退出'
})
//banner部分--logo、标题、任务、消息、通知、个人中心end----------------------------------------------------


//菜单部分  共有三级菜单

//三级菜单
	//人员信息-餐饮单位2个
var thirdMenuList=[];
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'userModify.htm',
	'itemIcon':'icon-plus',
	'itemText':'添加人员',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[1,0,0]
})//0
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'userManager.htm',
	'itemIcon':'icon-eye-open',
	'itemText':'查看人员',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[1,0,1]
})//1
	//人员信息-零售单位2个
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'userModify2.htm',
	'itemIcon':'icon-plus',
	'itemText':'添加人员',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[1,1,0]
})//2
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'userManager2.htm',
	'itemIcon':'icon-eye-open',
	'itemText':'查看人员',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[1,1,1]
})//3
	//单位信息-餐饮单位2个
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'unitModify.htm',
	'itemIcon':'icon-plus',
	'itemText':'添加单位',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[2,0,0]
})//4
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'unitManager.htm',
	'itemIcon':'icon-eye-open',
	'itemText':'查看单位',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[2,0,1]
})//5
	//单位信息-零售单位2个
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'unitModify2.htm',
	'itemIcon':'icon-plus',
	'itemText':'添加单位',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[2,1,0]
})//6
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'unitManager2.htm',
	'itemIcon':'icon-eye-open',
	'itemText':'查看单位',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[2,1,1]
})//7
	//征信信息-单位征信4个
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'creditManager11.htm',
	'itemIcon':'icon-eye-open',
	'itemText':'违规处罚',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[3,0,0]
})//8
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'creditManager12.htm',
	'itemIcon':'icon-eye-open',
	'itemText':'改进整改',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[3,0,1]
})//9
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'creditManager13.htm',
	'itemIcon':'icon-eye-open',
	'itemText':'停业整顿',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[3,0,2]
})//10
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'creditManager14.htm',
	'itemIcon':'icon-eye-open',
	'itemText':'荣誉信息',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[3,0,3]
})//11
	//征信信息-人员征信4个
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'creditManager21.htm',
	'itemIcon':'icon-eye-open',
	'itemText':'停职学习',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[3,1,0]
})//12
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'creditManager22.htm',
	'itemIcon':'icon-eye-open',
	'itemText':'违规处罚',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[3,1,1]
})//13
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'creditManager23.htm',
	'itemIcon':'icon-eye-open',
	'itemText':'撤职开除',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[3,1,2]
})//14
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'creditManager24.htm',
	'itemIcon':'icon-eye-open',
	'itemText':'荣誉信息',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[3,1,3]
})//15
	//培训管理-培训信息2个
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'trainDemand.htm',
	'itemIcon':'icon-plus',
	'itemText':'培训需求',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[4,0,0]
})//16
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'trainManager.htm',
	'itemIcon':'icon-eye-open',
	'itemText':'培训经历',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[4,0,1]
})//17
	//培训管理-考核信息2个
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'assessmentModify.htm',
	'itemIcon':'icon-plus',
	'itemText':'考核需求',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[4,1,0]
})//18
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'assessmentManager.htm',
	'itemIcon':'icon-eye-open',
	'itemText':'考核结果',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[4,1,1]
})//19
	//培训管理-岗位资质2个
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'qualificationManager.htm',
	'itemIcon':'icon-plus',
	'itemText':'岗前资质',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[4,2,0]
})//20
thirdMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'certificatesManager.htm',
	'itemIcon':'icon-eye-open',
	'itemText':'岗位证件',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[4,2,1]
})//21
//三级菜单end--------------------------------------------------------------------

//二级菜单
   //人员信息2个
var secondMenuList=[];
secondMenuList.push({
	'listActive':'',
	'linkClass':'dropdown-toggle',
	'itemLink':'#',
	'itemIcon':'icon-double-angle-right',
	'itemText':'餐饮单位',
	'downArrow':'<b class="arrow icon-angle-down"></b>',
	'son':2,
	'subMenu':0,
	'location':[1,0]
})//0
secondMenuList.push({
	'listActive':'',
	'linkClass':'dropdown-toggle',
	'itemLink':'#',
	'itemIcon':'icon-double-angle-right',
	'itemText':'零售单位',
	'downArrow':'<b class="arrow icon-angle-down"></b>',
	'son':2,
	'subMenu':2,
	'location':[1,1]
})//1
   //单位信息2个
secondMenuList.push({
	'listActive':'',
	'linkClass':'dropdown-toggle',
	'itemLink':'#',
	'itemIcon':'icon-double-angle-right',
	'itemText':'餐饮单位',
	'downArrow':'<b class="arrow icon-angle-down"></b>',
	'son':2,
	'subMenu':4,
	'location':[2,0]
})//2

secondMenuList.push({
	'listActive':'',
	'linkClass':'dropdown-toggle',
	'itemLink':'#',
	'itemIcon':'icon-double-angle-right',
	'itemText':'零售单位',
	'downArrow':'<b class="arrow icon-angle-down"></b>',
	'son':2,
	'subMenu':6,
	'location':[2,1]
})//3
	//单位征信2个
secondMenuList.push({
	'listActive':'',
	'linkClass':'dropdown-toggle',
	'itemLink':'#',
	'itemIcon':'icon-double-angle-right',
	'itemText':'单位征信',
	'downArrow':'<b class="arrow icon-angle-down"></b>',
	'son':4,
	'subMenu':8,
	'location':[3,0]
})//4
secondMenuList.push({
	'listActive':'',
	'linkClass':'dropdown-toggle',
	'itemLink':'#',
	'itemIcon':'icon-double-angle-right',
	'itemText':'人员征信',
	'downArrow':'<b class="arrow icon-angle-down"></b>',
	'son':4,
	'subMenu':12,
	'location':[3,1]
})//5
	//培训管理5个
secondMenuList.push({
	'listActive':'',
	'linkClass':'dropdown-toggle',
	'itemLink':'#',
	'itemIcon':'icon-double-angle-right',
	'itemText':'培训信息',
	'downArrow':'<b class="arrow icon-angle-down"></b>',
	'son':2,
	'subMenu':16,
	'location':[4,0]
})//6
secondMenuList.push({
	'listActive':'',
	'linkClass':'dropdown-toggle',
	'itemLink':'#',
	'itemIcon':'icon-double-angle-right',
	'itemText':'考核信息',
	'downArrow':'<b class="arrow icon-angle-down"></b>',
	'son':2,
	'subMenu':18,
	'location':[4,1]
})//7
secondMenuList.push({
	'listActive':'',
	'linkClass':'dropdown-toggle',
	'itemLink':'#',
	'itemIcon':'icon-double-angle-right',
	'itemText':'岗位资质',
	'downArrow':'<b class="arrow icon-angle-down"></b>',
	'son':2,
	'subMenu':20,
	'location':[4,2]
})//8
secondMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'educationManager.htm',
	'itemIcon':'icon-double-angle-right',
	'itemText':'教育经历',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[4,3]
})//9
secondMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'inductionManager.htm',
	'itemIcon':'icon-double-angle-right',
	'itemText':'就职经历',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[4,4]
})//10
	//通知公告2个
secondMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'noticeModify.htm',
	'itemIcon':'icon-double-angle-right',
	'itemText':'发送通知',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[5,0]
})//11
secondMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'noticeManager.htm',
	'itemIcon':'icon-double-angle-right',
	'itemText':'通知列表',//<span class="badge badge-important">5</span>
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[5,1]
})//12
	//系统管理4个
secondMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'userInfo.htm',
	'itemIcon':'icon-double-angle-right',
	'itemText':'个人设置',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[6,0]
})//13
secondMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'userPwd.htm',
	'itemIcon':'icon-double-angle-right',
	'itemText':'修改密码',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[6,1]
})//14
secondMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'userSystemManage.htm',
	'itemIcon':'icon-double-angle-right',
	'itemText':'用户管理',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[6,2]
})//15
secondMenuList.push({
	'listActive':'',
	'linkClass':'',
	'itemLink':'userSystem.htm',
	'itemIcon':'icon-double-angle-right',
	'itemText':'权限管理',
	'downArrow':'',
	'son':0,
	'subMenu':'',
	'location':[6,3]
})//17
//二级菜单end--------------------------------------------------------
//一级菜单
var firstMenuList=[];
firstMenuList.push({
	'listActive':'',//标记菜单是否为当前状态
	'linkClass':'',//li的样式，决定是否有下拉菜单
	'itemLink':'main.htm',//跳转链接
	'itemIcon':'icon-dashboard',//菜单图标
	'itemText':'控制台',//菜单名称
	'downArrow':'',//向下箭头，决定是否有下拉菜单
	'son':0,//子菜单的个数
	'subMenu':'',//子菜单在二级菜单数字中的起始位置
	'location':[0]//三级菜单的三维数组下标
})//0
firstMenuList.push({
	'listActive':'',
	'linkClass':'dropdown-toggle',
	'itemLink':'#',
	'itemIcon':'icon-group',
	'itemText':'人员信息',
	'downArrow':'<b class="arrow icon-angle-down"></b>',
	'son':2,
	'subMenu':0,
	'location':[1]
})//1
firstMenuList.push({
	'listActive':'',
	'linkClass':'dropdown-toggle',
	'itemLink':'#',
	'itemIcon':'icon-building',
	'itemText':'单位信息',
	'downArrow':'<b class="arrow icon-angle-down"></b>',
	'son':2,
	'subMenu':2,
	'location':[2]
})//2
firstMenuList.push({
	'listActive':'',
	'linkClass':'dropdown-toggle',
	'itemLink':'#',
	'itemIcon':'icon-credit-card',
	'itemText':'征信信息',
	'downArrow':'<b class="arrow icon-angle-down"></b>',
	'son':2,
	'subMenu':4,
	'location':[3]
})//3
firstMenuList.push({
	'listActive':'',
	'linkClass':'dropdown-toggle',
	'itemLink':'#',
	'itemIcon':'icon-bolt',
	'itemText':'培训管理',
	'downArrow':'<b class="arrow icon-angle-down"></b>',
	'son':5,
	'subMenu':6,
	'location':[4]
})//4
firstMenuList.push({
	'listActive':'',
	'linkClass':'dropdown-toggle',
	'itemLink':'#',
	'itemIcon':'icon-envelope',
	'itemText':'通知公告',
	'downArrow':'<b class="arrow icon-angle-down"></b>',
	'son':2,
	'subMenu':11,
	'location':[5]
})//5
firstMenuList.push({
	'listActive':'',
	'linkClass':'dropdown-toggle',
	'itemLink':'#',
	'itemIcon':'icon-cog',
	'itemText':'系统管理',
	'downArrow':'<b class="arrow icon-angle-down"></b>',
	'son':4,
	'subMenu':13,
	'location':[6]
})//6
//一级菜单end-------------------------------------------------------
//菜单部分  共有三级菜单end----------------------------------------------------------