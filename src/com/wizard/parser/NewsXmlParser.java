package com.wizard.parser;

import java.io.InputStream;
//import java.util.HashMap;
//import java.util.List;






import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.Xml;

import com.image.indicator.R;
//import com.image.indicator.entity.News;
import com.image.indicator.utility.FileAccess;
import com.wizard.constant.AppConstant;
import com.wizard.loader.ImageLoader;

public class NewsXmlParser{
	
	ImageLoader imageLoader;
	
	public NewsXmlParser(String url,Context context){
		 imageLoader = new ImageLoader(context);
		 XmlParser parser = new XmlParser();
		 String xml = parser.getXmlFromUrl(url);
		 Document doc = parser.getDomElement(xml); // 获取 DOM 节点
         NodeList nl = doc.getElementsByTagName(AppConstant.KEY_ITEM);
         List<String> titles = new ArrayList<String>();
         List<Bitmap> images = new ArrayList<Bitmap>();
         List<String> ids= new ArrayList<String>();
         for (int i = 0; i < nl.getLength(); i++) {
        	 Element e = (Element) nl.item(i);
        	 images.add(	imageLoader.getBitmap(parser.getValue(e, AppConstant.KEY_FANGTANIMAGE)));
        	 ids.add( parser.getValue(e, AppConstant.KEY_ID));
        	 titles.add( parser.getValue(e, AppConstant.KEY_FANGTANTITLE));
        
         }
         slideTitles =titles.toArray(new String[0]);
         slideUrls  = ids.toArray(new String[0]);
         slideImages = images.toArray(new Bitmap[0]);
	}
	
	private Bitmap[] slideImages = {
	};
	
	private String[] slideTitles = {
	};
	
	private String[] slideUrls = {
	};
	
	public Bitmap[] getSlideImages(){
		return slideImages;
	}
	
	public String[] getSlideTitles(){
		return slideTitles;
	}
	
	public String[] getSlideUrls(){
		return slideUrls;
	}
	
	/**
	 * @param result
	 * @return
	 */
	private XmlPullParser getXmlPullParser(String result){
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = FileAccess.String2InputStream(result);
		
		try {
			parser.setInput(inputStream, "UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return parser;
	}
	
	public int getNewsListCount(String result){
		int count = -1;
		
		try {
			XmlPullParser parser = getXmlPullParser(result);
	        int event = parser.getEventType();
	        
	        while(event != XmlPullParser.END_DOCUMENT){
	        	switch(event){
	        	case XmlPullParser.START_DOCUMENT:
	        		break;
	        	case XmlPullParser.START_TAG:
	        		if("count".equals(parser.getName())){
	        			count = Integer.parseInt(parser.nextText());
	                }
	        		
	        		break;
	        	case XmlPullParser.END_TAG:
//	        		if("count".equals(parser.getName())){
//	        			count = Integer.parseInt(parser.nextText());
//	                }
	        		
	        		break;
	        	}
            
	        	event = parser.next();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return count;
	}
}
