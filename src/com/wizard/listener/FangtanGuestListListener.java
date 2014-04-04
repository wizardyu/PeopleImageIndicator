package com.wizard.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.image.indicator.activity.FangtanGuestDetai;

/**
 * 访谈嘉宾列表页面 点击事件
 * @author wizardyu
 *
 */
public class FangtanGuestListListener implements OnClickListener{

	private Activity activity;
	
	public FangtanGuestListListener(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		Intent it = new Intent(activity, FangtanGuestDetai.class);
	}

}
