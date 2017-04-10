package com.example.smartcloud;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpUploader {
	public static final int UPLOAD_SUCCESS = 0x123;
	public static final int UPLOAD_FAIL = 0x124;

	public HttpUploader() {

	}

	public int uploadFiles(String url, File file, String username) {
		HttpClient client = new DefaultHttpClient();// 开启一个客户端 HTTP 请求
		HttpPost post = new HttpPost(url);// 创建 HTTP POST 请求 POST 请求
		FileBody filebody = new FileBody(file);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addTextBody("username", username);
		builder.addTextBody("type", "android");
		builder.addPart("file", filebody);
		HttpEntity entity = builder.build();// 生成 HTTP POST 实体
		post.setEntity(entity);// 设置请求参数
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 发起请求 并返回请求的响应
		if (response.getStatusLine().getStatusCode() == 200) {
			String result = null;
			try {
				result = EntityUtils.toString(response.getEntity());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(result);
			return 1;
		}
		return -1;
	}
}