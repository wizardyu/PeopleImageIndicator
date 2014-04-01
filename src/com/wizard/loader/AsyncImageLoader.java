package com.wizard.loader;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

public class AsyncImageLoader {
	private static HashSet<String> sDownloadingSet;
	private static Map<String,SoftReference<Bitmap>> sImageCache; 
	private static LoaderImpl impl;
	private static ExecutorService sExecutorService;
	
	private Handler handler; 
	
	/**
	 */
	public interface ImageCallback{
		/**
		 * �ص�����
		 * @param bitmap: may be null!
		 * @param imageUrl 
		 */
		public void onImageLoaded(Bitmap bitmap, String imageUrl);
	}
	
	static{
		sDownloadingSet = new HashSet<String>();
		sImageCache = new HashMap<String,SoftReference<Bitmap>>();
		impl = new LoaderImpl(sImageCache);
	}

	public AsyncImageLoader(Context context){
		handler = new Handler();
		startThreadPoolIfNecessary();
		
		String defaultDir = context.getCacheDir().getAbsolutePath();
		setCachedDir(defaultDir);
	}
	
	/**
	 */
	public void setCache2File(boolean flag){
		impl.setCache2File(flag);
	}
	
	/**
	 */
	public void setCachedDir(String dir){
		impl.setCachedDir(dir);
	}

	public static void startThreadPoolIfNecessary(){
		if(sExecutorService == null || sExecutorService.isShutdown() || sExecutorService.isTerminated()){
			sExecutorService = Executors.newFixedThreadPool(3);
			//sExecutorService = Executors.newSingleThreadExecutor();
		}
	}
	
	/**
	 * @param url	
	 * @param callback	see ImageCallback interface
	 */
	public void downloadImage(final String url, final ImageCallback callback){
		downloadImage(url, true, callback);
	}
	
	/**
	 * 
	 * @param url
	 * @param cache2Memory �Ƿ񻺴���memory��
	 * @param callback
	 */
	public void downloadImage(final String url, final boolean cache2Memory, final ImageCallback callback){
		if(sDownloadingSet.contains(url)){
			Log.i("AsyncImageLoader", "###��ͼƬ�������أ������ظ����أ�");
			return;
		}
		
		Bitmap bitmap = impl.getBitmapFromMemory(url);
		if(bitmap != null){
			if(callback != null){
				callback.onImageLoaded(bitmap, url);
			}
		}else{
			sDownloadingSet.add(url);
			sExecutorService.submit(new Runnable(){
				@Override
				public void run() {
					final Bitmap bitmap = impl.getBitmapFromUrl(url, cache2Memory);
					handler.post(new Runnable(){
						@Override
						public void run(){
							if(callback != null)
								callback.onImageLoaded(bitmap, url);
							sDownloadingSet.remove(url);
						}
					});
				}
			});
		}
	}
	
	/**
	 * @param url 
	 */
	public void preLoadNextImage(final String url){
		downloadImage(url, null);
	}
	
}
