package com.wechat.jfinal.common.utils;

import com.jfinal.render.Render;
import com.wechat.jfinal.common.utils.util.ContentTypeKit;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.net.URLEncoder;

/**
 * 用来render图片文件
 * Created by konbluesky zlisten
 * Date : 14-8-24 上午2:34
 */
public class MimeTypeRender extends Render {
    private String mimetype;
    private String filename;
    private String filePath;
    private ByteArrayInputStream is;
    private ByteArrayOutputStream os;

    public MimeTypeRender(String mimetype, String filename ,String filePath) {
        this.mimetype=mimetype;
        this.filename=filename;
        this.filePath = filePath;
    }

    public  MimeTypeRender(String mimetype, String filename, ByteArrayInputStream is) {
        this.mimetype=mimetype;
        this.filename=filename;
        this.is = is;
    }
    public  MimeTypeRender(String mimetype, String filename, ByteArrayOutputStream os) {
        this.mimetype=mimetype;
        this.filename=filename;
        this.os = os;
    }


    public void render() {

        ServletOutputStream sos = null;
        try {
            File file = new File(filePath);
            //设置头信息,内容处理的方式,attachment以附件的形式打开,就是进行下载,并设置下载文件的命名
            response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(filename, "UTF-8"));
//            response.setHeader("Content-Disposition","attachment;filename="+file.getName());
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType(ContentTypeKit.getMime(mimetype));
            sos=response.getOutputStream();
            // 创建文件输入流
            InputStream nis ;
            if(is != null)
                nis = is;
            else if(os != null){
                nis = new ByteArrayInputStream(os.toByteArray());
            }else if(filePath != null){
                nis = new FileInputStream(file);
            }else return;
//            else
//                nis = new FileInputStream(file);
            // 创建缓冲区
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = nis.read(buffer)) != -1) {
                sos.write(buffer, 0, len);
            }
            nis.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (sos != null)
                try {
                    sos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }


}