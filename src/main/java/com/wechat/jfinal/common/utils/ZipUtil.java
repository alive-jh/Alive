package com.wechat.jfinal.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipOutputStream;
//import org.apache.tools.zip.ZipOutputStream;

/**
 * 压缩工具
 * @author zlisten
 */
public class ZipUtil {

    public static void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + fl[i].getName());
            }
        }else {
            out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
            FileInputStream in = new FileInputStream(f);
            int b;
            //System.out.println(base);
            while ( (b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
        }
    }
}
