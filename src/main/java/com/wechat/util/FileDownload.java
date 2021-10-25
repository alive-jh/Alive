package com.wechat.util;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileDownload extends HttpServlet{
	
	/**   * httpServletID号   */
	private static final long serialVersionUID = 101L;
	public void service(HttpServletRequest req,HttpServletResponse res) throws IOException{
		String filepath=req.getParameter("filepath");
		String filename=req.getParameter("filename");
		if(filepath!=null){
			File file=new File(filepath);
			if(!file.exists()){
				res.setContentType("text/html;charset=UTF-8");
				res.getWriter().print("指定文件不存在");
				return;
			}else{
				ServletOutputStream out=res.getOutputStream();
				res.setHeader("Content-disposition", "attachment;filename="+new String(filename.getBytes("utf-8"),"iso8859-1"));//重新编码，不然可能会出现乱码现象s     
				res.addHeader("Content-Length", ""+file.length());
				BufferedInputStream bis=null;
				BufferedOutputStream bos = null;
				try{
					bis = new BufferedInputStream(new FileInputStream(filepath));
					bos = new BufferedOutputStream(out);
					byte[] buff = new byte[2048];
					int bytesRead;
					while(-1!=(bytesRead = bis.read(buff,0,buff.length))){
						bos.write(buff,0,bytesRead);
					}
				}catch(Exception e){
					e.printStackTrace();
					res.setContentType("text/html;charset=UTF-8");
					res.getWriter().print("文件已找到，下载失败。");
					return;
				}finally{
					if(bis!=null){
						bis.close();
					}
					if(bos!=null){
						bos.close();
					}
				}
			}
		}
	}

}
