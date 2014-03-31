package com.wizard.loader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 
 * @author Administrator
 * @desc �첽����ͼƬ������
 *
 */
public class LoaderImpl {
	//�ڴ��е���Ӧ�û���
	private Map<String, SoftReference<Bitmap>> imageCache;
	
	//�Ƿ񻺴�ͼƬ�������ļ�
	private boolean cache2FileFlag = false;
	
	//����Ŀ¼,Ĭ����/data/data/package/cache/Ŀ¼
	private String cachedDir;
	
	public LoaderImpl(Map<String, SoftReference<Bitmap>> imageCache){
		this.imageCache = imageCache;
	}
	
	/**
	 * �Ƿ񻺴�ͼƬ���ⲿ�ļ�
	 * @param flag 
	 */
	public void setCache2File(boolean flag){
		cache2FileFlag = flag;
	}
	
	/**
	 * ���û���ͼƬ���ⲿ�ļ���·��
	 * @param cacheDir
	 */
	public void setCachedDir(String cacheDir){
		this.cachedDir = cacheDir;
	}
	
	/**
	 * �����������ͼƬ
	 * @param url ����ͼƬ��URL��ַ
	 * @param cache2Memory �Ƿ񻺴�(�������ڴ���)
	 * @return bitmap ͼƬbitmap�ṹ
	 * 
	 */
	public Bitmap getBitmapFromUrl(String url, boolean cache2Memory){
		Bitmap bitmap = null;
		try{
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)u.openConnection();  
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			
			if(cache2Memory){
				//1.����bitmap���ڴ���������
				imageCache.put(url, new SoftReference<Bitmap>(bitmap));
				if(cache2FileFlag){
					//2.����bitmap��/data/data/packageName/cache/�ļ�����
					String fileName = getMD5Str(url);
					String filePath = this.cachedDir + "/" +fileName;
					FileOutputStream fos = new FileOutputStream(filePath);
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
				}
			}
			
			is.close();
			conn.disconnect();
			return bitmap;
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * ���ڴ滺���л�ȡbitmap
	 * @param url
	 * @return bitmap or null.
	 */
	public Bitmap getBitmapFromMemory(String url){
		Bitmap bitmap = null;
		if(imageCache.containsKey(url)){
			synchronized(imageCache){
				SoftReference<Bitmap> bitmapRef = imageCache.get(url);
				if(bitmapRef != null){
					bitmap = bitmapRef.get();
					return bitmap;
				}
			}
		}
		//���ⲿ�����ļ���ȡ
		if(cache2FileFlag){
			bitmap = getBitmapFromFile(url);
			if(bitmap != null)
				imageCache.put(url, new SoftReference<Bitmap>(bitmap));
		}
		
		return bitmap;
	}
	
	/**
	 * ���ⲿ�ļ������л�ȡbitmap
	 * @param url
	 * @return
	 */
	private Bitmap getBitmapFromFile(String url){
		Bitmap bitmap = null;
		String fileName = getMD5Str(url);
		if(fileName == null)
			return null;
		
		String filePath = cachedDir + "/" + fileName;
		
		try {
			FileInputStream fis = new FileInputStream(filePath);
			bitmap = BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			bitmap = null;
		}
		return bitmap;
	}
	
	
	/**  
     * MD5 ����  
     */   
    private static String getMD5Str(String str) {   
        MessageDigest messageDigest = null;   
        try {   
            messageDigest = MessageDigest.getInstance("MD5");   
            messageDigest.reset();   
            messageDigest.update(str.getBytes("UTF-8"));   
        } catch (NoSuchAlgorithmException e) {   
            System.out.println("NoSuchAlgorithmException caught!");   
            return null;
        } catch (UnsupportedEncodingException e) {   
            e.printStackTrace();
            return null;
        }   
   
        byte[] byteArray = messageDigest.digest();   
        StringBuffer md5StrBuff = new StringBuffer();   
        for (int i = 0; i < byteArray.length; i++) {               
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)   
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));   
            else   
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));   
        }   
   
        return md5StrBuff.toString();   
    }  

	/**  
     * MD5 ����  
    private static String getMD5Str(Object...objects){
    	StringBuilder stringBuilder=new StringBuilder();
    	for (Object object : objects) {
			stringBuilder.append(object.toString());
		}
    	return getMD5Str(stringBuilder.toString());
    }*/ 
}

