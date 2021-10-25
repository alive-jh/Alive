

var PPage=(function(window){
	//标签ID、当前页、总页数、函数名 、提醒是否开启 、 每页记录数
	return function(pid,curNum,maxNum,funcName,elevator,pageSize){
		//直达页面UI是否出现 , 为true 时出现
		elevator=elevator || false;
		
		function getAHtml(num){
			return '<a href="javascript:;" onclick="'+funcName+'('+num+','+maxNum+','+pageSize+');return false;">'+num+'</a>';
		};
		
		var page=typeof pid=="string" ? document.getElementById(pid) : pid,
			sPrev='',
			sNext='',
			sResult='',
			sp='',
			sn='',
			sCur='<em>'+curNum+'</em>',
			gd='<span>...</span>',
			prevNum,
			nextNum,
			i,
			elevatorHtml='',
			selectHtml = '';
		
		if(curNum==1){
			//sPrev='<span>«</span>';
			sPrev='';
		}else{
			prevNum=curNum-1;
			sPrev='<a href="javascript:;" onclick="'+funcName+'('+prevNum+','+maxNum+','+pageSize+');return false;" title="上一页">«</a>';
		}
		
		if(curNum==maxNum){
			//sNext='<span>»</span>';
			sNext='';
		}else{
			nextNum=Number(curNum)+Number(1);
			sNext='<a href="javascript:;" onclick="'+funcName+'('+nextNum+','+maxNum+','+pageSize+');return false;" title="下一页">»</a>';
		}
		
		if(maxNum<=6){
			for(i=1;i<curNum;i++){
				sp+=getAHtml(i);
			}
			
			for(i=Number(curNum)+Number(1);i<=maxNum;i++){
				sn+=getAHtml(i);
			}
			
			sResult=sPrev+sp+sCur+sn+sNext;
		}else{
			if (curNum <= 4) {
				for (i = 1; i < curNum; i++) {
					sp += getAHtml(i);
				}

				for (i = Number(curNum) + Number(1); i <= 5; i++) {
					sn += getAHtml(i);
				}
				
				sNext=getAHtml(maxNum)+sNext;
				
				sResult=sPrev+sp+sCur+sn+gd+sNext;
			}else{
				sPrev=sPrev+getAHtml(1);
				
				if(curNum<maxNum-3){
					for (i = curNum-2; i < curNum; i++) {
						sp += getAHtml(i);
					}
				
					for (i = Number(curNum) + Number(1); i <= Number(curNum)+Number(2); i++) {
						sn += getAHtml(i);
					}
					
					sNext=getAHtml(maxNum)+sNext;
					
					sResult=sPrev+gd+sp+sCur+sn+gd+sNext;
				}else{
					for (i = maxNum-4; i < curNum; i++) {
						sp += getAHtml(i);
					}
					
					for (i = Number(curNum) + Number(1); i <= maxNum; i++) {
						sn += getAHtml(i);
					}
					
					sResult=sPrev+gd+sp+sCur+sn+sNext;
				}
			}
		}
		
		if(elevator===true){
			var n1=+new Date(),
				n2=parseInt(Math.random()*1000),
				pagetTextId="j-page-num"+n1+n2,
				pageWarningId="j-page-elevator-warning"+n1+n2,
				
				timeout="time"+n1+n2,
				time=2000,
			
				f=funcName.replace(/\./g,"_");
			
			window['PPage_elevator_'+f]=function(v,max,psize){
				v=+v;
				
				if(!v || typeof v!=="number" || v>max || v<0){
					var pageWarningElem=document.getElementById(pageWarningId);
					
					clearTimeout(timeout);
				
					pageWarningElem.style.display="block";
	
					timeout=setTimeout(function(){
						pageWarningElem.style.display="none";
					},time);
					
					return;
				} 
				
				eval(funcName+'('+v+','+max+','+psize+')');
			};
			
			elevatorHtml='<span class="page-elevator-wrap"><div id="'+pageWarningId+'" class="page-elevator-warning" style="display:none;"><span>最大页数 '+maxNum+'</span><b></b><i></i></div><input class="page-txt" type="text" id="'+pagetTextId+'" autocomplete="off" style="ime-mode:disabled" title="请输入页码，最大页数：'+maxNum+'" /><button class="page-btn" onclick="PPage_elevator_'+f+'(document.getElementById(\''+pagetTextId+'\').value,'+maxNum+','+pageSize+');return false;">确定</button></span>';
		}
		if(pageSize != undefined && pageSize != null){
			selectHtml = '<div class="page-select" ><span>每页条数:&nbsp;</span><select id="setPageSize" onChange="onChangePageSize(this)" style="margin-top:0px;">';
			var arrayPageSize = new Array("20","50","100");
			for(var i=0; i < arrayPageSize.length; i++){
				var selected = "";
				if(arrayPageSize[i] == pageSize)
					selected = "selected";
				selectHtml += ' <option value="'+arrayPageSize[i]+'" '+selected+'>'+arrayPageSize[i]+'</option>';
			}
			selectHtml += '</select></div>';
		}
		page.innerHTML=sResult+elevatorHtml+selectHtml;
	};
})(window);

function onChangePageSize(selectEl){
	var pageSize = selectEl.value;
	var createPage = document.createElement("input");
	createPage.setAttribute("type","hidden");
	createPage.setAttribute("id","page");
	createPage.setAttribute("name","page");
	createPage.setAttribute("value",1);
	var createPageSize = document.createElement("input");
	createPageSize.setAttribute("type","hidden");
	createPageSize.setAttribute("id","pageSize");
	createPageSize.setAttribute("name","pageSize");
	createPageSize.setAttribute("value",pageSize);
	document.forms[0].appendChild(createPage);
	document.forms[0].appendChild(createPageSize);
	document.forms[0].submit();
}
