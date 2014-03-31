package com.wizard.task;

import com.wizard.view.PorterDuffView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ActAsyncTask extends Activity implements OnClickListener {
	private PorterDuffView pViewA, pViewB, pViewC, pViewD;
	public static final String[] STRING_ARR = {//
	"http://developer.android.com/images/home/android-jellybean.png",//
			"http://developer.android.com/images/home/design.png",//
			"http://developer.android.com/images/home/google-play.png",//
			"http://developer.android.com/images/home/google-io.png" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onClick(View v) {
		if (v instanceof PorterDuffView) {
			PorterDuffView pdView = (PorterDuffView) v;
			if (pdView.isLoading() == false) {
				DownloadImgTask task = new DownloadImgTask(pdView);
				task.execute(STRING_ARR[pdView.getId() % STRING_ARR.length]);
				pdView.setPorterDuffMode(true);
				pdView.setLoading(true);
				pdView.setProgress(0);
				pdView.invalidate();
			}
		}
	}
}