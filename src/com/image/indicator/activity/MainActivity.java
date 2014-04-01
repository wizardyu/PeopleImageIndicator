package com.image.indicator.activity;

import com.image.indicator.R;
import com.image.indicator.utility.DimensionUtility;
import com.image.indicator.utility.ImageAnimation;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

/**
 * Androidʵ�־ֲ�ͼƬ����ָ��Ч��
 * @Description: ʵ�����¹��ܣ�
 * 1����������ͼƬ��������������
 * 2����ָ��
 * 3����������������ͼƬ��������ҳ�棬�����ͼ�����ݲ����� 
 * 4�����������ſͻ��˵Ĺ���

 * @File: MainActivity.java

 * @Package com.image.indicator

 * @Author Hanyonglu  
 * @author lilu Modified the code at 2012-11-7

 * @Date 2012-6-17 ����11:28:43

 * @Version V1.0
 */
@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup implements OnClickListener, OnCheckedChangeListener{
	private TextView mSelectedItem = null;
	private RelativeLayout mHeader = null;
	private RelativeLayout mNewsMainLayout = null;
	private LayoutParams params = null;
	private TextView mNetEaseTop = null;
	private TextView mNewsItem = null;
	private TextView mInfoItem = null;
	private TextView mBlogItem = null;
	private TextView mMagezineItem = null;
	private TextView mDomainItem = null;
	private TextView mMoreItem = null;
	
	private int mItemWidth = 0;
	private int startX = 0;
	private Intent mIntent = null;
	private View mNewsMain = null;
	private RadioGroup mRadioGroup;
	private ImageView mImageView;
	private RelativeLayout mButtomLayout;
	int startLeft;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);      
        initeViews();
    }
    
    private void initeViews(){
    	mNewsItem = (TextView) findViewById(R.id.tv_title_news);
    	mInfoItem = (TextView) findViewById(R.id.tv_title_info);
    	mBlogItem = (TextView) findViewById(R.id.tv_title_blog);
    	mMagezineItem = (TextView) findViewById(R.id.tv_title_magazine);
    	mDomainItem = (TextView) findViewById(R.id.tv_title_domain);
    	mMoreItem = (TextView) findViewById(R.id.tv_title_more);
    	
    	mRadioGroup = (RadioGroup)findViewById(R.id.radiogroup);
    	mRadioGroup.setOnCheckedChangeListener(this);
    	mButtomLayout = (RelativeLayout) findViewById(R.id.layout_bottom);
    	
    	mNewsItem.setOnClickListener(this);
    	mInfoItem.setOnClickListener(this);
    	mBlogItem.setOnClickListener(this);
    	mMagezineItem.setOnClickListener(this);
    	mDomainItem.setOnClickListener(this);
    	mMoreItem.setOnClickListener(this);

    	mSelectedItem = new TextView(this);
    	mSelectedItem.setText(R.string.title_news_category_tops);
    	mSelectedItem.setTextColor(Color.WHITE);
    	mSelectedItem.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
    	mSelectedItem.setGravity(Gravity.CENTER);
    	mSelectedItem.setWidth((getScreenWidth() - DimensionUtility.dip2px(this, 20)) / 6);
    	mSelectedItem.setBackgroundResource(R.drawable.slidebar);
    	RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
    			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    	param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
    	
    	mHeader = (RelativeLayout) findViewById(R.id.layout_title_bar);
    	mNetEaseTop = (TextView) findViewById(R.id.tv_netease_top);
    	
    	mHeader.addView(mSelectedItem, param);
    	
    	mIntent = new Intent(MainActivity.this, TopicNews.class);
    	mNewsMain = getLocalActivityManager().startActivity(
    			"TopicNews", mIntent).getDecorView();
    	params = new LayoutParams(
    			LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    	mNewsMainLayout = (RelativeLayout) findViewById(R.id.layout_news_main);
    	mNewsMainLayout.addView(mNewsMain, params);
    	
    	
    	mImageView = new ImageView(this);
    	mImageView.setImageResource(R.drawable.tab_front_bg);
    	mButtomLayout.addView(mImageView);
    }
    
    /**
     * @return
     */
    private int getScreenWidth(){
    	WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
//		Point point = new Point();
//		display.getSize(point);
//		int screenWidth = point.x; 
		int screenWidth = display.getWidth();
		return screenWidth;
    }
    
	@Override
	public void onClick(View v) {
		mItemWidth = findViewById(R.id.layout).getWidth();
		
		switch (v.getId()) {
		case R.id.tv_title_news:
			ImageAnimation.SetImageSlide(mSelectedItem, startX, 0, 0, 0);
			startX = 0;
			mSelectedItem.setText(R.string.title_news_category_tops);
			mNetEaseTop.setText(R.string.title_news_category_tops);
			
			mIntent.setClass(MainActivity.this, TopicNews.class);
			mNewsMain = getLocalActivityManager().startActivity("TopicNews", mIntent).getDecorView();
			break;
		case R.id.tv_title_info:
			ImageAnimation.SetImageSlide(mSelectedItem, startX, mItemWidth, 0, 0);
			startX = mItemWidth;
			mSelectedItem.setText(R.string.title_news_category_info);
			mNetEaseTop.setText(R.string.title_news_category_info);
			
			mIntent.setClass(MainActivity.this, InfoNews.class);
			mNewsMain = getLocalActivityManager().startActivity(
	    			"InfoNews", mIntent).getDecorView();
			break;
		case R.id.tv_title_blog:
			ImageAnimation.SetImageSlide(mSelectedItem, startX, mItemWidth * 2, 0, 0);
			startX = mItemWidth * 2;
			mSelectedItem.setText(R.string.title_news_category_blog);
			mNetEaseTop.setText(R.string.title_news_category_blog);
			
			mIntent.setClass(MainActivity.this, BlogNews.class);
			mNewsMain = getLocalActivityManager().startActivity("BlogNews", mIntent).getDecorView();
			break;
		case R.id.tv_title_magazine:
			ImageAnimation.SetImageSlide(mSelectedItem, startX, mItemWidth * 3, 0, 0);
			startX = mItemWidth * 3;
			mSelectedItem.setText(R.string.title_news_category_magazine);
			mNetEaseTop.setText(R.string.title_news_category_magazine);
			
			mIntent.setClass(MainActivity.this, MagazineNews.class);
			mNewsMain = getLocalActivityManager().startActivity("MagazineNews", mIntent).getDecorView();
			break;
		case R.id.tv_title_domain:
			ImageAnimation.SetImageSlide(mSelectedItem, startX, mItemWidth * 4, 0, 0);
			startX = mItemWidth * 4;
			mSelectedItem.setText(R.string.title_news_category_domain);
			mNetEaseTop.setText(R.string.title_news_category_domain);
			mIntent.setClass(MainActivity.this, DomainNews.class);
			mNewsMain = getLocalActivityManager().startActivity("DomainNews", mIntent).getDecorView();
			break;
		case R.id.tv_title_more:
			ImageAnimation.SetImageSlide(mSelectedItem, startX, mItemWidth * 5, 0, 0);
			startX = mItemWidth * 5;
			mSelectedItem.setText(R.string.title_news_category_more);
			mNetEaseTop.setText(R.string.title_news_category_more);
			
			mIntent.setClass(MainActivity.this, MoreNews.class);
			mNewsMain = getLocalActivityManager().startActivity("MoreNews", mIntent).getDecorView();
			break;
		default:
			break;
		}		
		mNewsMainLayout.removeAllViews();
		mNewsMainLayout.addView(mNewsMain, params);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {
		case R.id.radio_news:
			ImageAnimation.SetImageSlide(mImageView, startLeft, 0, 0, 0);
			startLeft = 0;
			break;
		case R.id.radio_topic:
			ImageAnimation.SetImageSlide(mImageView, startLeft, mImageView.getWidth(), 0, 0);
			startLeft = mImageView.getWidth();
		
			break;
		case R.id.radio_pic:
			ImageAnimation.SetImageSlide(mImageView, startLeft, mImageView.getWidth() * 2, 0, 0);
			startLeft = mImageView.getWidth() * 2;
			break;
		case R.id.radio_follow:
			ImageAnimation.SetImageSlide(mImageView, startLeft, mImageView.getWidth() * 3, 0, 0);
			startLeft = mImageView.getWidth() * 3;
			break;
		case R.id.radio_vote:
			ImageAnimation.SetImageSlide(mImageView, startLeft, mImageView.getWidth() * 4, 0, 0);
			startLeft = mImageView.getWidth() * 4;
			break;

		default:
			break;
		}
	
	}
}