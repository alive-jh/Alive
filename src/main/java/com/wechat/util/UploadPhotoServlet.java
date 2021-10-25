package com.wechat.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;


@SuppressWarnings("serial")
public class UploadPhotoServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UploadPhotoServlet() {
		super();
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//String savePath = this.getServletConfig().getServletContext().getRealPath("");
		////System.out.print("-----"+savePath);
		//savePath = savePath+"/uploads/";
		String savePath = "d:/data/wechatImages/";
		File f1 = new File(savePath);
		if(!f1.exists()){
			f1.mkdirs();
		}
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List fileList = null;
		try {
			fileList = upload.parseRequest(req);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		if(fileList==null){
			resp.getWriter().print("No file upload!");
			return;
		}
		Iterator<FileItem> it = fileList.iterator();
		String name = "";
		String extName = "";
		while(it.hasNext()){
			FileItem item = it.next();
			if(!item.isFormField()){//isFormField����:true=�?���� ��false=�ļ��ϴ���
				name = item.getName();
				//long size = item.getSize();
				//String type = item.getContentType();
				if(name == null || name.trim().equals("")){
					continue;
				}
				//��չ���ʽ
				//System.out.println(" name = "+name);
				
				if(name.lastIndexOf(".")>=0){
					extName = name.substring(name.lastIndexOf("."));
				}
				File file = null;
				do{
					//����ļ���
					//name = UUID.randomUUID().toString();
					file = new File(savePath+name+extName);
				}while(file.exists());
				
				File saveFile = new File(savePath+name);
				try {
					item.write(saveFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		resp.getWriter().print(name+extName);
		
	}

}
