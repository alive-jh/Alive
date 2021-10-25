//1、LOGO标题部分
var navbarHead='<a href="{homeLink}" class="navbar-brand">'+
			'<small>'+
				'<i class="" style="background-image: url({logoURL});background-size:100%;display: inline-block;width:40px;height:20px;"></i>'+
				'{bigTitle}'+
			'</small>'+
		'</a><!-- /.brand -->';
navbarHead=navbarHead.replace(/\{bigTitle\}/g,data[0].title);
navbarHead=navbarHead.replace(/\{logoURL\}/g,data[0].logoURL);
navbarHead=navbarHead.replace(/\{homeLink\}/g,data[0].link);
$('#navbar-container .navbar-header')[0].innerHTML=navbarHead;
//LOGO标题部分end---------------------------------------------
//2、消息部分
var banner=$('.ace-nav')[0];
//2.1消息1-任务栏部分
var html_item='<li class="{iconColor}">'+
				'<a data-toggle="dropdown" class="dropdown-toggle" href="#">'+
					'<i class="{itemType}"></i>'+
					'<span class="badge {badgeColor}">{numLab}</span>'+
				'</a>'+

				'<ul class="pull-right dropdown-navbar {titleBag} dropdown-menu dropdown-caret dropdown-close">'+
					'<li class="dropdown-header">'+
						'<i class="{titleIcon}"></i>'+
						'{titleContent}'+
					'</li>'+

					'{taskList}'+
					'<li>'+
						'<a href={link}>'+
							'{content}'+
							'<i class="{iconType}"></i>'+
						'</a>'+
					'</li>'+
				'</ul>'+
			'</li>';
//2.1.1任务栏列表部分
var taskLists='<li>'+
						'<a href="{itemLink1}">'+
							'<div class="clearfix">'+
								'<span class="pull-left">{taskTitle}</span>'+
								'<span class="pull-right">{taskProgress}</span>'+
							'</div>'+

							'<div class="progress progress-mini {progressMiniType}">'+
								'<div style="{minProgress}" class="{progresBarType}"></div>'+
							'</div>'+
						'</a>'+
					'</li>'
var html_item2=new Array();
for(var i=1;i<data.length;i++){
	html_item2[i]=html_item.replace(/\{iconColor\}/g,data[i].iconColor).replace(/\{titleBag\}/g,data[i].titleBag).replace(/\{itemType\}/g,data[i].itemType).replace(/\{numLab\}/g,data[i].numLab).replace(/\{titleIcon\}/g,data[i].titleIcon).replace(/\{titleContent\}/g,data[i].titleContent).replace(/\{badgeColor\}/g,data[i].badgeColor);
	html_item2[i]=html_item2[i].replace(/\{link\}/g,data[i].link).replace(/\{content\}/g,data[i].content).replace(/\{iconType\}/g,data[i].iconType);
}
//alert(html_item2[1]);
var taskLists2='';
for(var i=0;i<taskList.length;i++)
{
	taskLists2+=taskLists.replace(/\{itemLink1\}/g,taskList[i].itemLink).replace(/\{progresBarType\}/g,taskList[i].progresBarType).replace(/\{taskTitle\}/g,taskList[i].taskTitle).replace(/\{taskProgress\}/g,taskList[i].taskProgress).replace(/\{minProgress\}/g,taskList[i].minProgress).replace(/\{progressMiniType\}/g,taskList[i].progressMiniType);
}
//任务栏列表部分end--------------------------------------------------------

html_item2[1]=html_item2[1].replace(/\{taskList\}/g,taskLists2);
//消息1-任务栏部分end--------------------------------------------



//2.1.2消息铃响列表部分
var noticeLists='<li>'+
						'<a href={itemLink}>'+
							'<div class="clearfix">'+
								'<span class="pull-left">'+
									'<i class="btn btn-xs {iconStyle}"></i>'+
									'{itemContent}'+
								'</span>'+
								'<span class="pull-right badge {itemNumStyle}">{itemNum}</span>'+
							'</div>'+
						'</a>'+
					'</li>';
var noticeLists2='';
for(var i=0;i<noticeList.length;i++)
{
	noticeLists2+=noticeLists.replace(/\{itemLink\}/g,noticeList[i].itemLink).replace(/\{iconStyle\}/g,noticeList[i].iconStyle).replace(/\{itemContent\}/g,noticeList[i].itemContent).replace(/\{itemNumStyle\}/g,noticeList[i].itemNumStyle).replace(/\{itemNum\}/g,noticeList[i].itemNum);
}
html_item2[2]=html_item2[2].replace(/\{taskList\}/g,noticeLists2);
//消息铃响列表部分end----------------------------------------------------------
//2.1.3消息2-消息信封列表部分
var messageLists='<li>'+
						'<a href={itemLink}>'+
							'<img src={imageSrc} class="msg-photo" alt={alt} />'+
							'<span class="msg-body">'+
								'<span class="msg-title">'+
									'<span class="blue">{name}</span>'+
									'{itemContent}'+
								'</span>'+

								'<span class="msg-time">'+
									'<i class="icon-time"></i>'+
									'<span>{itemTiem}</span>'+	
								'</span>'+
							'</span>'+
						'</a>'+
					'</li>';
var messageLists2='';
for(var i=0;i<messageList.length;i++){
	messageLists2+=messageLists.replace(/\{itemLink\}/g,messageList[i].itemLink).replace(/\{imageSrc\}/g,messageList[i].imageSrc).
	replace(/\{alt\}/g,messageList[i].alt).replace(/\{name\}/g,messageList[i].name).replace(/\{itemContent\}/g,messageList[i].itemContent).
	replace(/\{itemTiem\}/g,messageList[i].itemTiem);
}
html_item2[3]=html_item2[3].replace(/\{taskList\}/g,messageLists2);
//消息2-消息信封列表部分end---------------------------------------------------
//3个人中心
var personalHeads='<li class={bgColor}>'+
				'<a data-toggle="dropdown" href="#" class="dropdown-toggle">'+
					'<img class="nav-user-photo" src={imgScr} alt={alt} />'+
					'<span class="user-info">'+
						'<small>欢迎光临,</small>'+
						'{name}'+
					'</span>'+

					'<i class={downIcon}></i>'+	
				'</a>'+

				'<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">'+
				'{list}'+
				'</ul>'+
			'</li>'
personalHeads=personalHeads.replace(/\{bgColor\}/g,personalHead[0].bgColor).replace(/\{imgScr\}/g,personalHead[0].imgScr).
			  replace(/\{alt\}/g,personalHead[0].alt).replace(/\{name\}/g,personalHead[0].name).
			  replace(/\{downIcon\}/g,personalHead[0].downIcon);
//3.2个人中心菜单列表	
var pMenuLists='<li>'+
						'<a href={itemLink}>'+
							'<i class={iconType}></i>'+
							'{name}'+
						'</a>'+
					'</li>';
var pMenuLists2='';
for(var i=0;i<pMenuList.length;i++){
	if(i==2){
		pMenuLists2+='<li class="divider"></li>';
	}
	pMenuLists2+=pMenuLists.replace(/\{itemLink\}/g,pMenuList[i].itemLink).replace(/\{iconType\}/g,pMenuList[i].iconType).replace(/\{name\}/g,pMenuList[i].name);
}
personalHeads=personalHeads.replace(/\{list\}/g,pMenuLists2);
//个人中心菜单列表end--------------------------------------------		  
//3个人中心end------------------------------------------------------------
html_item=html_item2[1]+html_item2[2]+html_item2[3]+personalHeads;
banner.innerHTML=html_item;
//消息部分end--------------------------------------------------------

//*******导航条部分
function navInit(aim){
	//面包屑
	var breadcrumbs=$('#breadcrumbs .breadcrumb')[0];
	var html_breadcrumb='<li class="active">'+
							'<i class="{itemIcon} home-icon" style="font-size:17px;top:0px;"></i>'+
							'{itemText}'+
						'</li>';
	var html_breadcrumbs='<li>'+
							'<i class="icon-home home-icon"></i>'+
							'<a href="main.htm">首页</a>'+
						'</li>'
	
	//高亮显示当前菜单
	firstMenuList[aim[0]].listActive='active';
	html_breadcrumbs+=html_breadcrumb.replace(/\{itemIcon\}/g,firstMenuList[aim[0]].itemIcon).replace(/\{itemText\}/g,firstMenuList[aim[0]].itemText);
	if(aim.length==3){
		for(var i=0;i<secondMenuList.length;i++)
		{
			if(secondMenuList[i].location[0]==aim[0]&&secondMenuList[i].location[1]==aim[1])
			{
				secondMenuList[i].listActive='active';
				html_breadcrumbs+=html_breadcrumb.replace(/\{itemIcon\}/g,'').replace(/\{itemText\}/g,secondMenuList[i].itemText);
			}
		}
		for(var j=0;j<thirdMenuList.length;j++)
		{
			if(thirdMenuList[j].location[0]==aim[0]&&thirdMenuList[j].location[1]==aim[1]&&thirdMenuList[j].location[2]==aim[2])
			{
				thirdMenuList[j].listActive='active';
				
				html_breadcrumbs+=html_breadcrumb.replace(/\{itemIcon\}/g,'').replace(/\{itemText\}/g,thirdMenuList[j].itemText);
			}
		}
	}else if(aim.length==2){
		for(var i=0;i<secondMenuList.length;i++)
		{
			if(secondMenuList[i].location[0]==aim[0]&&secondMenuList[i].location[1]==aim[1])
			{
				secondMenuList[i].listActive='active';
				html_breadcrumbs+=html_breadcrumb.replace(/\{itemIcon\}/g,'').replace(/\{itemText\}/g,secondMenuList[i].itemText);
			}
		}
	}
	breadcrumbs.innerHTML=html_breadcrumbs;
	//高亮显示当前菜单end------------------------------------------------------------
var nav=$('#sidebar .nav-list')[0];
var html_nav='';
var html_menuFirst='<li class={listActive}>'+
							'<a href={itemLink} class="{linkClass}">'+
								'<i class={itemIcon}></i>'+
								'<span class="menu-text">{itemText} </span>'+
								'{downArrow}'+
							'</a>'+
							'{subMenu}'+
						'</li>';
var html_menuSeconds=[];
var html_menuSecond='';
var html_menuThirds=[];
var html_menuThird='';
var html_menuFirsts=[];
for(var i=0;i<firstMenuList.length;i++){//i为一级菜单的下标号
	html_menuSecond='';//清空上次循环时变量中保留的值
	html_menuFirsts[i]=html_menuFirst.replace(/\{listActive\}/g,firstMenuList[i].listActive).replace(/\{itemLink\}/g,firstMenuList[i].itemLink).
	replace(/\{linkClass\}/g,firstMenuList[i].linkClass).replace(/\{itemIcon\}/g,firstMenuList[i].itemIcon).
	replace(/\{itemText\}/g,firstMenuList[i].itemText).replace(/\{downArrow\}/g,firstMenuList[i].downArrow);
	
		//alert(firstMenuList[i].subMenu==='');
	if(firstMenuList[i].subMenu==='')
	{
		html_menuFirsts[i]=html_menuFirsts[i].replace(/\{subMenu\}/g,firstMenuList[i].subMenu);
	}
	else{
		//alert(firstMenuList[i].son);
		for(var j=0;j<firstMenuList[i].son;j++)//j为二级菜单元素的个数
		{	html_menuThird='';//清空上次循环时变量中保留的值
			var k=j+firstMenuList[i].subMenu;//k为二级菜单下标号
			html_menuSeconds[j]=html_menuFirst.replace(/\{listActive\}/g,secondMenuList[k].listActive).replace(/\{itemLink\}/g,secondMenuList[k].itemLink).
			replace(/\{linkClass\}/g,secondMenuList[k].linkClass).replace(/\{itemIcon\}/g,secondMenuList[k].itemIcon).
			replace(/\{itemText\}/g,secondMenuList[k].itemText).replace(/\{downArrow\}/g,secondMenuList[k].downArrow);
			if(secondMenuList[k].subMenu==='')
			{
				html_menuSeconds[j]=html_menuSeconds[j].replace(/\{subMenu\}/g,secondMenuList[k].subMenu);
			}
			else{
				for(var m=0;m<secondMenuList[k].son;m++){//m为三级菜单元素的个数
					var n=m+secondMenuList[k].subMenu;//n为三级菜单的下标号
					html_menuThirds[m]=html_menuFirst.replace(/\{listActive\}/g,thirdMenuList[n].listActive).replace(/\{itemLink\}/g,thirdMenuList[n].itemLink).
					replace(/\{linkClass\}/g,thirdMenuList[n].linkClass).replace(/\{itemIcon\}/g,thirdMenuList[n].itemIcon).
					replace(/\{itemText\}/g,thirdMenuList[n].itemText).replace(/\{downArrow\}/g,thirdMenuList[n].downArrow).
					replace(/\{subMenu\}/g,thirdMenuList[n].subMenu);
					html_menuThird+=html_menuThirds[m];
				}
				html_menuSeconds[j]=html_menuSeconds[j].replace(/\{subMenu\}/g,'<ul class="submenu">'+html_menuThird+'</ul>');
			}
			html_menuSecond+=html_menuSeconds[j];
		}
		html_menuFirsts[i]=html_menuFirsts[i].replace(/\{subMenu\}/g,'<ul class="submenu">'+html_menuSecond+'</ul>');
	}
	html_nav+=html_menuFirsts[i];
}
nav.innerHTML=html_nav;
//*******导航条部分end--------------------------------------------------
}