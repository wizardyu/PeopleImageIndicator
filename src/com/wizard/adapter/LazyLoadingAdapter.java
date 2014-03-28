package com.wizard.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.image.indicator.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.wizard.util.HttpUtil;

public class LazyLoadingAdapter extends SimpleAdapter{
	
	private ArrayList<HashMap<String, Object>> listItem ;
	private int[] mTo;
    private String[] mFrom;
    private ViewBinder mViewBinder;
    private List<? extends Map<String, ?>> mData;
    private int mResource;
    private LayoutInflater mInflater;
    private int mDropDownResource;
    
	public LazyLoadingAdapter(Context context, List<? extends Map<String, ?>> data,int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		mData = data;
        mResource = mDropDownResource = resource;
        mFrom = from;
        mTo = to;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		String urlString = "http://10.100.4.99/api/fangtanApi.do?action=topicNews";
		loadListView(urlString);
		return null;
	}
	
	private void loadListView(String url){
        new LoadListView().execute();
    }

	 class LoadListView extends AsyncTask<Void, Void, ArrayList>{
		@Override
		protected ArrayList doInBackground(Void... params) {
			String urlString = "http://10.100.4.99/api/fangtanApi.do?action=topicNews";
			listItem = new ArrayList<HashMap<String, Object>>();    
			HttpUtil.get(urlString, new JsonHttpResponseHandler() {
				public void onSuccess(JSONArray dataJson){
					HashMap<String, Object> map = new HashMap<String, Object>();    
					for(int i=0;i<dataJson.length();i++){
						try {
								JSONObject fangtan = (JSONObject) dataJson.get(i);
								String fangtanTitle = (String) fangtan.get("fangtanTitle");
							 	map.put("ItemImage", R.drawable.c);//Í¼Ïñ×ÊÔ´µÄID    
								map.put("ItemTitle", fangtanTitle);    
				                map.put("LastImage", R.drawable.lastimage);   
							} catch (JSONException e) {
								Log.e("test", " onSuccess JSONException" +e.getMessage());
							}
					}
					listItem.add(map);
					Log.e("test", " listItem.add(map) over");
				};
				public void onFailure(Throwable arg0) {
	                Log.e("test", " onFailure" + arg0.toString());
	            };
	            public void onFinish() {
	                Log.i("test", "onFinish");
	            };
			});
			return listItem;
		}
		
		@Override
        protected void onPostExecute(ArrayList result) {
            if(result!=null&&result.size()>0){
            	Log.e("test", "onPostExecute");
                notifyDataSetChanged();
            }
        }
	 }
	      
}
