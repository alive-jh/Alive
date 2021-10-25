package com.wechat.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class UploadFileServlet  extends HttpServlet { 

	
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
         //获得磁盘文件条目工厂    
        DiskFileItemFactory factory = new DiskFileItemFactory();    
        //获取文件缓存文件夹的路径      
        String tempPath = request.getRealPath("/temp");      
        //如果没以下两行设置的话，上传大的 文件 会占用 很多内存，    
        //设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同    
        /**  
         * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上，   
         * 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的   
         * 然后再将其真正写到 对应目录的硬盘上  
         */    
        File tempFolder = new File(tempPath);   
        if(tempFolder.exists()==false){  
           tempFolder.mkdirs();  
        }  
        //设置缓存文件夹  
        factory.setRepository(new File("/data/wechatImages/"));    
        //设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室    
        factory.setSizeThreshold(1024*1024) ;    
            
        //高水平的API文件上传处理    
        ServletFileUpload upload = new ServletFileUpload(factory);   
        List<FileItem> list = null;  
        try {  
            list = (List<FileItem>)upload.parseRequest(request);  
        } catch (FileUploadException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }    
        for(FileItem item : list)    
        {    
            //获取表单的属性名字    
            String name = item.getFieldName();  //这个name就是<input>标签中的name属性，是很重要与服务器通信的方式  
             
            //如果获取的 表单信息是普通的 文本 信息    
            if(item.isFormField())    
            {                       
               //System.out.println(item);  
            }    
            //对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些    
            else    
            {    
                /**  
                 * 以下三步，主要获取 上传文件的名字  
                 */    
                //获取路径名    
            	
                String value = item.getName() ;    
                ////System.out.println("test = " + value.subSequence(value.indexOf("."),value.length()));
                //索引到最后一个反斜杠    
                int start = value.lastIndexOf("\\");    
                //截取 上传文件的 字符串名字，加1是 去掉反斜杠，    
                String filename = value.substring(start+1);   
                //将文件写入服务器，第二个参数是文件名（不要加扩展名），第三个参数是要写入的文件夹 ，一般在这里文件名用随机数生成比较好  
                writeFile(item, filename, "/data/wechatImages/");  
                  
            }              
        }  
        PrintWriter out = response.getWriter();  
        out.print("{error:'test'}");//返回空json字符串代表上传成功，同时在浏览器收到后会出现绿色的对勾，如果失败就传输一个"{error:'错误信息'}",这样的话进图条就走不到100%并且还会锁死  
        out.flush();  
        out.close();  
    }  
      
    /** 
     * 传入一个fileitem，并且按照给出的文件名前缀，文件名后缀（索引），文件存储相对目录来写入从互联网的到的文件 
     * @param fileItem 传来的文件对象 
     * @param firstName 文件名前缀 
     * @param parentFolder 相对存储目录，如："imgs" 根目录C:\apache-tomcat-7.0.61\webapps\项目名\+parentFolder\ 
     * @return 
     * @throws IOException 
     */  
    public boolean writeFile(FileItem fileItem,String firstName,String parentFolder) throws IOException{  
        //用原来的文件名做文件名，用项目目录的绝对地址/attachment作为目录地址  
          
        File file1 = new File(parentFolder,firstName);
        if(file1.getParentFile().exists()==false){  
            file1.getParentFile().mkdirs();  
        }  
        file1.createNewFile();  
          
        InputStream ins = fileItem.getInputStream();  
        OutputStream ous = new FileOutputStream(file1);  
          
        try{  
            byte[] buffer = new byte[1024];  
            //System.out.println("开始写文件");  
            int len = 0;  
            while((len = ins.read(buffer)) > -1)  
                ous.write(buffer,0,len);  
            //System.out.println("已保存的文件"+file1.getAbsolutePath());  
        }finally{  
            ous.close();  
            ins.close();  
        }  
        return true;  
    }  
	
}
