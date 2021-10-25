<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.wechat.util.RedisUtil"%>
<%@ page import="java.util.ArrayList"%>




<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>

				<div class="sidebar" id="sidebar">
					
					<ul class="nav nav-list" id="labalName">
					<!-- 
					<li>
						<a href="#">
							<i class="icon-dashboard"></i>
							<span class="menu-text"> 控制台 </span>
						</a>
					</li>
					 -->
									

					<c:forEach items="<%=(ArrayList)RedisUtil.getModulaByCookie(request)%>" var ="parentModular">
							<c:if test="${parentModular.parentId ==0}">
								<li class="list">
								<a href="#" class="dropdown-toggle">
									<i class="icon-cog"></i>
									<span class="menu-text">${parentModular.name} </span>
									<b class="arrow icon-angle-down"></b>
								</a>
								<ul class="submenu">
								<c:forEach items="<%=(ArrayList)RedisUtil.getModulaByCookie(request)%>" var ="tempModulars">
									<c:if test="${tempModulars.parentId == parentModular.id}">
									<li>
										<a href="<%=request.getContextPath()%>${tempModulars.url}">
											<i class="icon-double-angle-right" ></i>
											${tempModulars.name}
										</a>
									</li>
									</c:if>
								</c:forEach>
								</ul>
								</li>
					</c:if>
					</c:forEach>
					</ul>
					<div class="sidebar-collapse" id="sidebar-collapse">
						<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
					</div>

					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'collapsed');}catch(e){}
						
						$('.list:eq(${sessionScope.labelIndex}) ul').css("display","block");
						
						$('.list').on("click",function(){
							$.post("<%=request.getContextPath()%>/api/saveLabelIndex",
									{index : $(this).index()},
									function(success){},"json");
						});
					</script>
				</div>