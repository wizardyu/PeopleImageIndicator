package com.image.indicator.layout;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.image.indicator.R;
import com.wizard.constant.AppConstant;
import com.wizard.parser.NewsXmlParser;

public class SlideImageLayout {
	private ArrayList<ImageView> mImageList = null;
	private Context mContext = null;
	private ImageView[] mImageViews = null; 
	private ImageView mImageView = null;
	private NewsXmlParser mParser = null;
	private int pageIndex = 0;
	
	public SlideImageLayout(Context context) {
		this.mContext = context;
		mImageList = new ArrayList<ImageView>();
		mParser = new NewsXmlParser(AppConstant.GLOBAL_CONSTANTS_DOMAIN+AppConstant.FANGTAN_XML_HOMEPAGEFANGTANS,context);
	}
	
	/**
	 * @param id
	 * @return
	 */
	@SuppressLint("NewApi")
	public View getSlideImageLayout(Bitmap id,Resources res){
		LinearLayout imageLinerLayout = new LinearLayout(mContext);
		LinearLayout.LayoutParams imageLinerLayoutParames = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT,
				1);
		
		ImageView iv = new ImageView(mContext);
		//		iv.setBackgroundResource(id);
		iv.setBackground(new BitmapDrawable(res,id));
		iv.setOnClickListener(new ImageOnClickListener());
		imageLinerLayout.addView(iv,imageLinerLayoutParames);
		mImageList.add(iv);
		
		return imageLinerLayout;
	}
	
	/**
	 * @param view
	 * @param width
	 * @param height
	 * @return
	 */
	public View getLinearLayout(View view,int width,int height){
		LinearLayout linerLayout = new LinearLayout(mContext);
		LinearLayout.LayoutParams linerLayoutParames = new LinearLayout.LayoutParams(
				width, 
				height,
				1);
		linerLayout.setPadding(10, 0, 10, 0);
		linerLayout.addView(view, linerLayoutParames);
		
		return linerLayout;
	}
	
	/**
	 * @param size
	 */
	public void setCircleImageLayout(int size){
		mImageViews = new ImageView[size];
	}
	
	/**
	 * @param index
	 * @return
	 */
	public ImageView getCircleImageLayout(int index){
		mImageView = new ImageView(mContext);  
		mImageView.setLayoutParams(new LayoutParams(10,10));
        mImageView.setScaleType(ScaleType.FIT_XY);
        
        mImageViews[index] = mImageView;
         
        if (index == 0) {  
            mImageViews[index].setBackgroundResource(R.drawable.dot_selected);  
        } else {  
            mImageViews[index].setBackgroundResource(R.drawable.dot_none);  
        }  
         
        return mImageViews[index];
	}
	
	/**
	 * @param index
	 */
	public void setPageIndex(int index){
		pageIndex = index;
	}

	private class ImageOnClickListener implements OnClickListener{
    	@Override
    	public void onClick(View v) {
    		Toast.makeText(mContext, mParser.getSlideTitles()[pageIndex], Toast.LENGTH_SHORT).show();
    		Toast.makeText(mContext, mParser.getSlideUrls()[pageIndex], Toast.LENGTH_SHORT).show();
    	}
    }
}
