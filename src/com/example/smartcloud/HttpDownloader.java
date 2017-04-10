package com.example.smartcloud;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpDownloader {
	private URL url = null;
	FileUtils fileUtils = new FileUtils();

	public int downfile(String url, String userspace, String fileName) {
		if (fileUtils.isFileExist(userspace + fileName)) {
			return 1;
		} else {
			try {
				InputStream input = null;
				input = getInputStream(url);
				File resultFile = fileUtils.write2SDFromInput(userspace, fileName, input);
				if (resultFile == null) {
					return -1;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	// 由于得到一个InputStream对象是所有文件处理前必须的操作，所以将这个操作封装成了一个方法
	public InputStream getInputStream(String urlStr) throws IOException {
		InputStream is = null;
		try {
			url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			is = urlConn.getInputStream();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;
	}
}