package com.wechat.spider;

import com.google.gson.Gson;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
public class Getinfo {
  
	public static JSONObject getInfo(String ISBN) throws Exception{
		Amazon amazon = new Amazon();
		DangDang dangdang = new DangDang();
		JSONObject info = new JSONObject();
		data result = dangdang.getinfo(ISBN);
		Gson gson=new Gson();
		String json=gson.toJson(result);
		JSONObject result_info = new JSONObject(json);
		info.put("msg","ok");
		JSONArray data = new JSONArray();
		data.put(result_info);
		info.put("data", data);
		return info; 

	}
	public static void set(String content,int row1,int coloum) throws Exception{

	    try {
	        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
	        		"F://333.xls"));
	        HSSFSheet sheet = workbook.getSheet("Sheet1");
	
            HSSFRow row = sheet.getRow((short) row1);

            HSSFCell cell = row.getCell((short) coloum);
	        cell.setCellValue(content);


	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
    }
	
    public static void main(String[] args) throws Exception{
    	
    	InputStream is = new FileInputStream("F://333.xls");   
    	  
        Workbook rwb = Workbook.getWorkbook(is); 
        Sheet st = rwb.getSheet(0);
        for(int i=1;i<2047;i++){
            Cell c00 = st.getCell(3,i);  
            String strc00 = c00.getContents();  
            
            
            
	        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
	        		"F://3333.xls"));
	        HSSFSheet sheet = workbook.getSheet("sheet1");
	        try{
	        	JSONObject result = getInfo(strc00);
	        	
	        	JSONArray data = result.getJSONArray("data");
	        	

	        	String author = data.getJSONObject(0).getString("uAuthor");
//	            HSSFCell cell4 = row.getCell((short) 4);

	            
//	            //System.out.println(cell4);
//	            cell4.setCellValue(author);
//	        	
	        	String uTranslator = data.getJSONObject(0).getString("uTranslator");
//	            HSSFCell cell5 = row.getCell((short) 5);
//	            cell5.setCellValue(uTranslator);
	        	
	        	String uPublish = data.getJSONObject(0).getString("uPublish");
//	            HSSFCell cell6 = row.getCell((short) 6);
//	            cell6.setCellValue(uPublish);
	        	
	        	String uSeries =  data.getJSONObject(0).getString("uSeries");
//	            HSSFCell cell7 = row.getCell((short) 7);
//	            cell7.setCellValue(uSeries);
	        	
	        	String uPage = data.getJSONObject(0).getString("uPage");
//	            HSSFCell cell8 = row.getCell((short) 8);
//	            cell8.setCellValue(uPage);
//	        	
	        	
	        	String uCataLog = data.getJSONObject(0).getString("uCataLog");
//	            HSSFCell cell9 = row.getCell((short) 9);
//		        cell9.setCellValue(uCataLog);
	        	
	        
	        	String uContent = data.getJSONObject(0).getString("uContent");
//	            HSSFCell cell10 = row.getCell((short) 10);
//		        cell10.setCellValue(uContent);
	        	
	        	Cell c0 = st.getCell(0,i);  
	        
	        	Cell c1 = st.getCell(1,i);  
	        	
	        	Cell c2 = st.getCell(2,i);  
	        	
	        	Cell c3 = st.getCell(3,i);  
	        	//System.out.print(c0.getContents());
	            //System.out.print("\t");
	        	//System.out.print(c1.getContents());
	            //System.out.print("\t");
	        	//System.out.print(c2.getContents());
	            //System.out.print("\t");
	        	//System.out.print(c3.getContents());
	            //System.out.print("\t");
	            //System.out.print(author);
	            //System.out.print("\t");
	            //System.out.print(uTranslator);
	            //System.out.print("\t");
	            //System.out.print(uPublish);
	            //System.out.print("\t");
	            //System.out.print(uSeries);
	            //System.out.print("\t");
	            //System.out.print(uPage);
	            //System.out.print("\t");
	            //System.out.print(uCataLog);
	            //System.out.print("\t");
	            //System.out.print(uContent);
	            //System.out.print("\n");
		        
	        }catch(IOException e){
	        	e.printStackTrace();
	        	continue;
	        }
        	



        }

        

    }
}
