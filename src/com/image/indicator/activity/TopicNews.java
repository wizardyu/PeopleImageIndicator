package com.image.indicator.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.image.indicator.R;
import com.image.indicator.layout.SlideImageLayout;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.wizard.adapter.LazyLoadingImageAdapter;
import com.wizard.adapter.SlideImageAdapter;
import com.wizard.constant.AppConstant;
import com.wizard.listener.FangtanListListener;
import com.wizard.loader.AsyncImageLoader;
import com.wizard.parser.NewsXmlParser;
import com.wizard.util.ACache;
import com.wizard.util.HttpUtil;

/**
 * 头条新闻Activity
 * @Description: 头条新闻Activity

 * @File: TopicNews.java

 * @Package com.image.indicator.activity

 * @Author Hanyonglu

 * @Date 2012-6-18 上午08:24:36

 * @Version V1.0
 */
public class TopicNews extends Activity{
	// 滑动图片的集合
	private ArrayList<View> mImagePageViewList = null;
	private ViewGroup mMainView = null;
	private ViewPager mViewPager = null;
	// 当前ViewPager索引
//	private int pageIndex = 0; 
	
	// 包含圆点图片的View
	private ViewGroup mImageCircleView = null;
	private ImageView[] mImageCircleViews = null; 
	
	// 滑动标题
	private TextView mSlideTitle = null;
	
	// 布局设置类
	private SlideImageLayout mSlideLayout = null;
	// 数据解析类
	private NewsXmlParser mParser = null; 
	//列表输出
	private ArrayList<HashMap<String, Object>> listItem ;
	private LazyLoadingImageAdapter listItemAdapter;
	private AsyncImageLoader loader;
	private Bitmap  bgImgMobileTitle_bitmap ;
	private ACache aCache ;
	
	/**
	 * 访谈交互地址
	 */
	private String mdomain = AppConstant.GLOBAL_CONSTANTS_DOMAIN;
	
	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_Translucent_NoTitleBar);		
		// 初始化
		initeViews();
	}
	
	/**
	 * 初始化
	 */
	private void initeViews(){
		// 滑动图片区域
		mImagePageViewList = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater();  
		mMainView = (ViewGroup)inflater.inflate(R.layout.page_topic_news, null);
		mViewPager = (ViewPager) mMainView.findViewById(R.id.image_slide_page);  
		aCache = ACache.get(this);
		// 圆点图片区域
		mParser = new NewsXmlParser(AppConstant.GLOBAL_CONSTANTS_DOMAIN+AppConstant.FANGTAN_XML_HOMEPAGEFANGTANS,this);
		int length = mParser.getSlideImages().length;
		mImageCircleViews = new ImageView[length];
		mImageCircleView = (ViewGroup) mMainView.findViewById(R.id.layout_circle_images);
		mSlideLayout = new SlideImageLayout(TopicNews.this);
		mSlideLayout.setCircleImageLayout(length);
		for(int i = 0; i < length; i++){
			mImagePageViewList.add(mSlideLayout.getSlideImageLayout(mParser.getSlideImages()[i],this.getResources()));
			mImageCircleViews[i] = mSlideLayout.getCircleImageLayout(i);
			mImageCircleView.addView(mSlideLayout.getLinearLayout(mImageCircleViews[i], 10, 10));
		}
		
		// 设置默认的滑动标题
		mSlideTitle = (TextView) mMainView.findViewById(R.id.tvSlideTitle);
		mSlideTitle.setText(mParser.getSlideTitles()[0]);
		
		list = (ListView) mMainView.findViewById(R.id.ListView01);
		loadListView();
		setContentView(mMainView);
		
		// 设置ViewPager
        mViewPager.setAdapter(new SlideImageAdapter(mImagePageViewList));  
        mViewPager.setOnPageChangeListener(new ImagePageChangeListener());
        list.setOnItemClickListener(new FangtanListListener(this));
	}
	
	private void loadListView(){
			String urlString = mdomain+AppConstant.FANGTAN_XML_FEATUREDFANGTANS;
			listItem = new ArrayList<HashMap<String, Object>>();    
	        listItemAdapter = new LazyLoadingImageAdapter(this,listItem,// 数据源     
                R.layout.list_items,//ListItem的XML实现    
                //动态数组与ImageItem对应的子项            
                new String[] {"ItemImage","ItemTitle"},     
                //ImageItem的XML文件里面的一个ImageView,两个TextView ID    
                new int[] {R.id.ItemImage,R.id.ItemTitle}    
            );    
	         //添加并且显示    
	        list.setAdapter(listItemAdapter);  
	        loader = new AsyncImageLoader(getApplicationContext());  
	        loader.setCache2File(true);
	        loader.setCachedDir(this.getCacheDir().getAbsolutePath());  
	        JSONArray dataJson = aCache.getAsJSONArray(AppConstant.ACACHE_KEY_LATESTFANGTAN);
	        if(dataJson==null){
	        	HttpUtil.get(urlString, new JsonHttpResponseHandler() {
					public void onSuccess(JSONArray dataJson){
						loadListViewDate(dataJson);
						aCache.put(AppConstant.ACACHE_KEY_LATESTFANGTAN, dataJson);
					}
					public void onFailure(Throwable arg0) {
		            };
		            public void onFinish() {
		            	listItemAdapter.notifyDataSetChanged();
		            };
				});
	        }else{
	        	loadListViewDate(dataJson);
	        }
	    	
	}
	
	/**
	 * 解析
	 * @param dataJson
	 */
	private void loadListViewDate(JSONArray dataJson) {
		for(int i=0;i<dataJson.length();i++){
			try {
					HashMap<String, Object> map = new HashMap<String, Object>();    
					JSONObject fangtan = (JSONObject) dataJson.get(i);
					String fangtanTitle = (String) fangtan.get("fangtanTitle");
					String imgUrl =	mdomain+ (String) fangtan.get("homepageUrl");
					loader.downloadImage(imgUrl, true/*false*/, new AsyncImageLoader.ImageCallback() {  
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
		listItemAdapter.notifyDataSetChanged();
	};
    // 滑动页面更改事件监听器
    private class ImagePageChangeListener implements OnPageChangeListener {
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
        }  
  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
        }  
  
        @Override  
        public void onPageSelected(int index) {  
//        	pageIndex = index;
        	mSlideLayout.setPageIndex(index);
        	mSlideTitle.setText(mParser.getSlideTitles()[index]);
        	
            for (int i = 0; i < mImageCircleViews.length; i++) {  
            	mImageCircleViews[index].setBackgroundResource(R.drawable.dot_selected);
                
                if (index != i) {  
                	mImageCircleViews[i].setBackgroundResource(R.drawable.dot_none);  
                }  
            }
        }  
    }
}
