package com.wechat.util;

import com.wechat.entity.BookCategory;
import com.wechat.entity.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class CategoryUtil {
	
	
	public ArrayList getCategoryNolevel(String table,Page bData,int parentId,int level,int currentId,ArrayList cats){
       
        List data= bData.getItems(); 
        for(int i=0;i<data.size();i++){
            ProductCategory p=new ProductCategory();
            p=(ProductCategory) data.get(i); 
            //如果p.getCat_id()!=currentId 那么它和它下面所有的子类都不会递归出来
            if(p.getParent_id()==parentId&&p.getCat_id()!=currentId){
                p.setMark(strRepeat("",level));
                cats.add(p);
                getCategoryNolevel(table,bData,p.getCat_id(),level+1,currentId,cats);
            }

        }
        return cats;
    }
	


	//选择分类时，不能选择自己的子类作为自己的父分类
	public ArrayList getCategoryNolevelBook(String table,Page bData,int parentId,int level,int currentId,ArrayList cats){
        List data= bData.getItems(); 
        for(int i=0;i<data.size();i++){
            BookCategory p=new BookCategory();
            p=(BookCategory) data.get(i); 
            //如果p.getCat_id()!=currentId 那么currentId和currentId下面所有的子类都不会递归出来
            if(p.getParent_id()==parentId&&p.getCat_id()!=currentId){
                p.setMark(strRepeat("",level));
                cats.add(p);
                getCategoryNolevelBook(table,bData,p.getCat_id(),level+1,currentId,cats);
            }

        }
        return cats;
    }
	
	
	public ArrayList getBookCategoryList(Page bData,int parentId,int level,ArrayList cats){
        List data= bData.getItems(); 
        for(int i=0;i<data.size();i++){
            BookCategory p=new BookCategory();
            p=(BookCategory) data.get(i); 
            if(p.getParent_id()==parentId){
                p.setMark(strRepeat("",level));
                cats.add(p);
                getBookCategoryList(bData,p.getCat_id(),level+1,cats);
            }
        }
        return cats;
    }
	
	 /**
     * +----------------------------------------------------------
     * 获取当前分类下所有子分类
     * +----------------------------------------------------------
     * table 数据表名
     * bData 数据
     * childIds 子类ID临时存储器
     * +----------------------------------------------------------
     */
    public StringBuffer getChildIds(String table,Page bData,int parentId,StringBuffer childIds) {
    	List data= bData.getItems(); 
        for(int i=0;i<data.size();i++){
            ProductCategory p=new ProductCategory();
            p=(ProductCategory) data.get(i); 
            if(p.getParent_id()==parentId){
                childIds=childIds.append(p.getCat_id()+",");
                getChildIds(table,bData,p.getCat_id(),childIds);
            }
        }
        return childIds;
    }
    
    /**
     * +----------------------------------------------------------
     * 获取当前分类下所有子分类
     * +----------------------------------------------------------
     * table 数据表名
     * bData 数据
     * childIds 子类ID临时存储器
     * +----------------------------------------------------------
     */
    public StringBuffer getChildIdsBook(String table,Page bData,int parentId,StringBuffer childIds) {
    	List data= bData.getItems(); 
        for(int i=0;i<data.size();i++){
            BookCategory p=new BookCategory();
            p=(BookCategory) data.get(i); 
            if(p.getParent_id()==parentId){
                childIds=childIds.append(p.getCat_id()+",");
                getChildIdsBook(table,bData,p.getCat_id(),childIds);
            }
        }
        return childIds;
    }
    
    
    
	
    public String strRepeat(String mark,int level){
    	if(level==0){
    		return "";
    	}
    	for(int i=0;i<level;i++){
    		mark+=" - ";	
    	}
    	return mark;
    }

}
