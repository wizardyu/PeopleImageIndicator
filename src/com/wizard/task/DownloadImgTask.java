package com.wizard.task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import com.wizard.view.PorterDuffView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * @author Sodino E-mail:sodinoopen@hotmail.com
 * @version Time��2012-7-5 ����03:34:58
 */
public class DownloadImgTask extends AsyncTask<String, Float, Bitmap> {
	private PorterDuffView pdView;

	public DownloadImgTask(PorterDuffView pdView) {
		this.pdView = pdView;
	}

	/** ����׼����������UI�߳��е��á� */
	protected void onPreExecute() {
	}

	/** ִ�����ء��ڱ����̵߳��á� */
	protected Bitmap doInBackground(String... params) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(params[0]);
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			printHttpResponse(httpResponse);
			HttpEntity httpEntity = httpResponse.getEntity();
			long length = httpEntity.getContentLength();
			is = httpEntity.getContent();
			if (is != null) {
				baos = new ByteArrayOutputStream();
				byte[] buf = new byte[128];
				int read = -1;
				int count = 0;
				while ((read = is.read(buf)) != -1) {
					baos.write(buf, 0, read);
					count += read;
					publishProgress(count * 1.0f / length);
				}
				byte[] data = baos.toByteArray();
				Bitmap bit = BitmapFactory.decodeByteArray(data, 0, data.length);
				return bit;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/** �������ؽ�ȡ���UI�̵߳��á�onProgressUpdate */
	protected void onProgressUpdate(Float... progress) {
		// LogOut.out(this, "onProgressUpdate");
		pdView.setProgress(progress[0]);
	}

	/** ֪ͨ����������ɡ���UI�̵߳��á� */
	protected void onPostExecute(Bitmap bit) {
		pdView.setPorterDuffMode(false);
		pdView.setLoading(false);
		pdView.setImageBitmap(bit);
	}

	protected void onCancelled() {
		super.onCancelled();
	}

	private void printHttpResponse(HttpResponse httpResponse) {
		Header[] headerArr = httpResponse.getAllHeaders();
		for (int i = 0; i < headerArr.length; i++) {
			Header header = headerArr[i];
		}
		HttpParams params = httpResponse.getParams();
	}
}
