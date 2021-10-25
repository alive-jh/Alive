package com.wechat.entity;


import java.awt.image.BufferedImage;

public class TwoDimensionCodeImage   {
	  
    BufferedImage bufImg;  
      
    public TwoDimensionCodeImage(BufferedImage bufImg) {  
        this.bufImg = bufImg;  
    }  
      
	public int getHeight() {
        return bufImg.getHeight();  
    }  
  
   
	public int getPixel(int x, int y) {
        return bufImg.getRGB(x, y);  
    }  
  
   
	public int getWidth() {
        return bufImg.getWidth();  
    }  
  
}  
