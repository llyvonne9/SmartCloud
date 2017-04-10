package com.example.smartcloud;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class UploadActivity extends Activity {

	private ListView fileLV;
	private TextView directoryTV;
	private Button homeB;
	File fileParent;
	File[] files;

	private Handler uploadHandler;
	private String username;
	private int result_upload = -1;
	private String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);

		fileLV = (ListView) findViewById(R.id.list);
		directoryTV = (TextView) findViewById(R.id.directoryText);
		homeB=(Button)findViewById(R.id.backToRootButton);
		
		Intent intent = getIntent();
		username = intent.getStringExtra("username");

		File root = new File("/mnt/sdcard/");
		if (root.exists()) {
			fileParent = root;
			files = root.listFiles();
			prepareAdapter(files);
		}
		fileLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				if (files[position].isFile()) {
					// Intent intent=new Intent();
					path = files[position].getAbsolutePath().toString();
					Toast.makeText(UploadActivity.this, path, Toast.LENGTH_SHORT).show();
					// intent.putExtra("path",path);
					// intent.setClass(UploadActivity.this, MainInterfceActivity.class);
					// startActivity(intent);
					new Thread(new Runnable() {
						@Override
						public void run() {
							HttpUploader uploader = new HttpUploader();
							File file = new File(path);
							result_upload = uploader.uploadFiles("http://congcongxyz.cn/files", file, username);
							Message m = uploadHandler.obtainMessage();
							uploadHandler.sendMessage(m);
						}
					}).start();

				} else {
					fileParent = files[position];
					files = files[position].listFiles();
					prepareAdapter(files);
				}

				// File[] folderChild=files[position].listFiles();
				// if(folderChild==null){
				//// Intent intent=new Intent();
				// String path=files[position].getAbsolutePath().toString();
				// Toast.makeText(UploadActivity.this, path+"case 2", Toast.LENGTH_SHORT).show();
				//// intent.putExtra("path",path);
				//// intent.setClass(UploadActivity.this, MainInterfceActivity.class);
				//// startActivity(intent);
				// }

				// fileParent=files[position];
				// files=files[position].listFiles();
				// prepareAdapter(files);

			}
		});
		
		homeB.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				File root=new File("/mnt/sdcard/");
				fileParent=root;
				files=root.listFiles();
				prepareAdapter(files);
			}
		});

		uploadHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (result_upload == 1) {
					Toast.makeText(UploadActivity.this, "upload success", Toast.LENGTH_SHORT).show();
				} else if (result_upload == -1) {
					Toast.makeText(UploadActivity.this, "upload fail", Toast.LENGTH_SHORT).show();
				}
				super.handleMessage(msg);
			}
		};
	}

	private void prepareAdapter(File[] files) {
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < files.length; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			if (files[i].isDirectory()) {
				item.put("icon", R.drawable.folder);
			} else {
				item.put("icon", R.drawable.singlefile);
			}
			item.put("filename", files[i].getName());
			items.add(item);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.list_item, new String[] { "icon", "filename" },
				new int[] { R.id.fileIconImageView, R.id.fileNameTextView });
		fileLV.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.upload, menu);
		return true;
	}

}
