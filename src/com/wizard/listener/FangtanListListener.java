package com.wizard.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.image.indicator.activity.FangtanDetai;

/**
 * 访谈嘉宾列表页面 点击事件
 * @author wizardyu
 *
 */
public class FangtanListListener implements OnItemClickListener {

	private Activity activity;
	
	public FangtanListListener(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent it = new Intent(activity, FangtanDetai.class);
		activity.startActivity(it);
	}

}
