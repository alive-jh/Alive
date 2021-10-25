package com.wechat.controller;

import com.wechat.util.QiniuLogTask;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Timer;

public class QiniuLogServer extends HttpServlet {
	private Timer timer = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		timer = new Timer(true);
		timer.schedule(new QiniuLogTask(), 0, 1000 * 60 * 30);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public void destroy() {
	}
}
