<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="footer-nav">
				<ul class="clearfix fixed-block">
					<li>
						<a href="<%=request.getContextPath()%>/mall/mallMobileManager?memberId=${memberId}">
							<span></span>
							<p>首页</p>
						</a>
					</li>
					<li>
						<a href="<%=request.getContextPath()%>/member/memberOrderManager?memberId=${memberId}">
							<span></span>
							<p>订单</p>
						</a>
					</li>
					<li>
						<a href="#">
							<span></span>
							<p>我的</p>
						</a>
					</li>
					<li>
						<a href="#">
							<span></span>
							<p>更多</p>
						</a>
					</li>
				</ul>
			</div>