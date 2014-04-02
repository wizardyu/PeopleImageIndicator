package com.wizard.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.image.indicator.R;
import com.image.indicator.activity.RadioGuestActivity;
import com.wizard.loader.ImageLoader;

public class NetMusicAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader; // 用来下载图片的类，后面有介绍

	public NetMusicAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.radio_guest_item, null);
		TextView title = (TextView) vi.findViewById(R.id.title); // 标题
		TextView artist = (TextView) vi.findViewById(R.id.artist); // 歌手名
		TextView duration = (TextView) vi.findViewById(R.id.duration); // 时长
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image); // 缩略图
		HashMap<String, String> song = new HashMap<String, String>();
		song = data.get(position); // 设置ListView的相关值
		title.setText(song.get(RadioGuestActivity.KEY_USERNICK));
		artist.setText(song.get(RadioGuestActivity.KEY_USERTITLE));
		duration.setText(song.get(RadioGuestActivity.KEY_LASTFANGTANDATE));
		imageLoader.DisplayImage(song.get(RadioGuestActivity.KEY_USERIMAGE_URL), thumb_image);
		return vi;
	}
}
