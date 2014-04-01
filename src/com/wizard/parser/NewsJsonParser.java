package com.wizard.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.util.Log;

import com.wizard.adapter.LazyLoadingImageAdapter;
import com.wizard.constant.AppConstant;
import com.wizard.loader.AsyncImageLoader;

/**
 * 解析json数据
 * @author wizardyu
 *
 */
public class NewsJsonParser {
	
	private Bitmap  bgImgMobileTitle_bitmap ;
	private ArrayList<HashMap<String, Object>> listItem ;
	private AsyncImageLoader loader;
	
	public  NewsJsonParser(JSONArray dataJson,AsyncImageLoader loader){
		listItem = new ArrayList<HashMap<String, Object>>();    
		this.loader = loader;
		for(int i=0;i<dataJson.length();i++){
			try {
					HashMap<String, Object> map = new HashMap<String, Object>();    
					JSONObject fangtan = (JSONObject) dataJson.get(i);
					String fangtanTitle = (String) fangtan.get("fangtanTitle");
					String imgUrl =	AppConstant.GLOBAL_CONSTANTS_DOMAIN+ (String) fangtan.get("homepageUrl");
					this.loader.downloadImage(imgUrl, true/*false*/, new AsyncImageLoader.ImageCallback() {  
			        	 public void onImageLoaded(Bitmap bitmap, String imageUrl) {  
			        		 if(bitmap != null){  
			        			 bgImgMobileTitle_bitmap= bitmap;
			                 }else{  
			                 }  
			        	 }
			        }); 
				 	map.put("ItemImage", bgImgMobileTitle_bitmap);  
					map.put("ItemTitle", fangtanTitle);    
					listItem.add(map);
	                
				} catch (JSONException e) {
					Log.e("test", " onSuccess JSONException" +e.getMessage());
				}
		}
	}

	public  ArrayList<HashMap<String, Object>> getListItem(){
		return this.listItem;
	}

}


