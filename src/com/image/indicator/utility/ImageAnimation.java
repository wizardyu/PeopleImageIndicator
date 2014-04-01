package com.image.indicator.utility;

import android.view.View;
import android.view.animation.TranslateAnimation;

public class ImageAnimation {
	public static void SetImageSlide(View v, int startX, int toX, int startY, int toY) {
		TranslateAnimation anim = new TranslateAnimation(startX, toX, startY, toY);
		anim.setDuration(100);
		anim.setFillAfter(true);
		v.startAnimation(anim);
	}
}
