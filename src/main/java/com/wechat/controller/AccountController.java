package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.Account;
import com.wechat.entity.Keyword;
import com.wechat.service.AccountService;
import com.wechat.service.KeywordService;
import com.wechat.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@RequestMapping("account")
public class AccountController {

	@Resource
	private AccountService accountService;

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@Resource
	private KeywordService KeywordService;

	public KeywordService getKeywordService() {
		return KeywordService;
	}

	public void setKeywordService(KeywordService KeywordService) {
		this.KeywordService = KeywordService;
	}
	
	

	@RequestMapping(value="/accountAdmin")
	public String accountAdmin(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
	
	
		Account account = new Account();
		account = this.accountService.getAccount("0");
		request.setAttribute("account", account);
		
		HashMap map = new HashMap();
		map.put("page", "1");
		map.put("rowsPerPage", "100");


		Page resultPage = this.KeywordService.searchKeyword(map);
		request.setAttribute("resultPage", resultPage);
		
		if(account.getKeywordId()!= 0 )
		{
			
			Keyword keyword = new Keyword();
			keyword.setId(account.getKeywordId());
			keyword = this.KeywordService.getKeyword(keyword);
			request.setAttribute("keywordName", keyword.getKeyword());
		}
		return "account/accountAdmin";
	}
	
	
	
	@RequestMapping(value="/updateKeyword")
	public String updateKeyword(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
	
	
		this.accountService.updateAccountKeywordId(request.getParameter("accountId"), request.getParameter("keywordId"));
		return "redirect:accountAdmin";
	}
	
}
