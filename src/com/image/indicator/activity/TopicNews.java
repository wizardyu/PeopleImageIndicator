package com.image.indicator.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.image.indicator.R;
import com.image.indicator.layout.SlideImageLayout;
import com.image.indicator.parser.NewsXmlParser;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.wizard.adapter.LazyLoadingAdapter;
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
	
	private ProgressDialog pDialog;

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
		
		// 圆点图片区域
		mParser = new NewsXmlParser();
		int length = mParser.getSlideImages().length;
		mImageCircleViews = new ImageView[length];
		mImageCircleView = (ViewGroup) mMainView.findViewById(R.id.layout_circle_images);
		mSlideLayout = new SlideImageLayout(TopicNews.this);
		mSlideLayout.setCircleImageLayout(length);
		
		for(int i = 0; i < length; i++){
			mImagePageViewList.add(mSlideLayout.getSlideImageLayout(mParser.getSlideImages()[i]));
			mImageCircleViews[i] = mSlideLayout.getCircleImageLayout(i);
			mImageCircleView.addView(mSlideLayout.getLinearLayout(mImageCircleViews[i], 10, 10));
		}
		
		// 设置默认的滑动标题
		mSlideTitle = (TextView) mMainView.findViewById(R.id.tvSlideTitle);
		mSlideTitle.setText(mParser.getSlideTitles()[0]);
		
		ListView list = (ListView) mMainView.findViewById(R.id.ListView01);
		
		loadListView(list);
		
		setContentView(mMainView);
		
		// 设置ViewPager
        mViewPager.setAdapter(new SlideImageAdapter());  
        mViewPager.setOnPageChangeListener(new ImagePageChangeListener());
	}
	
	private void loadListView(ListView list){
//			String urlString = "http://10.100.4.99/api/fangtanApi.do?action=topicNews";
//			listItem = new ArrayList<HashMap<String, Object>>();    
//			HttpUtil.get(urlString, new JsonHttpResponseHandler() {
//				public void onSuccess(JSONArray dataJson){
//					HashMap<String, Object> map = new HashMap<String, Object>();    
//					for(int i=0;i<dataJson.length();i++){
//						try {
//								JSONObject fangtan = (JSONObject) dataJson.get(i);
//								String fangtanTitle = (String) fangtan.get("fangtanTitle");
//							 	map.put("ItemImage", R.drawable.c);//图像资源的ID    
//								map.put("ItemTitle", fangtanTitle);    
//				                map.put("LastImage", R.drawable.lastimage);   
//							} catch (JSONException e) {
//								Log.e("test", " onSuccess JSONException" +e.getMessage());
//							}
//					}
//					listItem.add(map);
//				};
//				public void onFailure(Throwable arg0) {
//	                Log.e("test", " onFailure" + arg0.toString());
//	            };
//	            public void onFinish() {
//	                Log.i("test", "onFinish");
//	            };
//			});
			LazyLoadingAdapter lazyLoading = new LazyLoadingAdapter(this,listItem,// 数据源     
	                R.layout.list_items,//ListItem的XML实现    
	                //动态数组与ImageItem对应的子项            
	                new String[] {"ItemImage","ItemTitle", "LastImage"},     
	                //ImageItem的XML文件里面的一个ImageView,两个TextView ID    
	                new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.last}
			);
//	        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,// 数据源     
//                R.layout.list_items,//ListItem的XML实现    
//                //动态数组与ImageItem对应的子项            
//                new String[] {"ItemImage","ItemTitle", "LastImage"},     
//                //ImageItem的XML文件里面的一个ImageView,两个TextView ID    
//                new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.last}    
//            );    
            //添加并且显示    
            list.setAdapter(lazyLoading); 
	}
	
	// 滑动图片数据适配器
    private class SlideImageAdapter extends PagerAdapter {  
        @Override  
        public int getCount() { 
            return mImagePageViewList.size();  
        }  
  
        @Override  
        public boolean isViewFromObject(View view, Object object) {  
            return view == object;  
        }  
  
        @Override  
        public int getItemPosition(Object object) {  
            return super.getItemPosition(object);  
        }  
  
        @Override  
        public void destroyItem(View view, int arg1, Object arg2) {  
            ((ViewPager) view).removeView(mImagePageViewList.get(arg1));  
        }  
  
        @Override  
        public Object instantiateItem(View view, int position) {  
        	((ViewPager) view).addView(mImagePageViewList.get(position));
            
            return mImagePageViewList.get(position);  
        }  
  
        @Override  
        public void restoreState(Parcelable arg0, ClassLoader arg1) {  
  
        }  
  
        @Override  
        public Parcelable saveState() {  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View arg0) {  
        }  
  
        @Override  
        public void finishUpdate(View arg0) {  
        }  
    }
    
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
