package com.example.smartcloud;

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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MyFilesActivity extends Activity {
	private String filename;
	private String username;
	private ArrayList<String> filelist = new ArrayList<String>();
	private ListView lt1;
	private Handler downloadHandler;
	private int result_download = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_files);

		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		filelist = intent.getStringArrayListExtra("filelist");
		System.out.println(filelist);
		SimpleAdapter simplead = new SimpleAdapter(this, getData(), R.layout.simple, new String[] { "filename" }, new int[] { R.id.filename });
		// access the list//
		lt1 = (ListView) findViewById(R.id.listview1);
		// set adapter for the listView//
		lt1.setAdapter(simplead);
		// set listener for list//
		lt1.setOnItemClickListener(new OnItemClickListener() {
			// according to the click position to decide the value in intent and start another activity//
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView filenameview = (TextView) view.findViewById(R.id.filename);
				filename = filenameview.getText().toString();
				Toast.makeText(MyFilesActivity.this, filename, Toast.LENGTH_SHORT).show();
				// TextView start_time= (TextView) view.findViewById(R.id.start_time);
				// String day=module_day.getText().toString();
				// intent.putExtra("day", day);
				// intent.putExtra("start_time", start_time.getText().toString());
				// intent.setClass(ModuleList.this, Detail_info.class);
				// startActivity(intent);
				// finish();
				new Thread(new Runnable() {

					@Override
					public void run() {
						HttpDownloader downloader = new HttpDownloader();
						result_download = downloader.downfile("http://congcongxyz.cn/uploads/" + username + "/ison/" + filename, username + "/", filename);
						// System.out.println("http://192.168.56.1:80/test/" + username + "/"+filename);
						Message m = downloadHandler.obtainMessage();
						downloadHandler.sendMessage(m);
					}
				}).start();

			}
		});

		downloadHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (result_download == 1) {
					Toast.makeText(MyFilesActivity.this, "file already exist", Toast.LENGTH_SHORT).show();
				} else if (result_download == 0) {
					Toast.makeText(MyFilesActivity.this, "download success", Toast.LENGTH_SHORT).show();
				} else if (result_download == -1) {
					Toast.makeText(MyFilesActivity.this, "download fail", Toast.LENGTH_SHORT).show();
				}
				super.handleMessage(msg);
			}
		};
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		int i = 0;
		while (i < filelist.size()) {
			Map<String, Object> list = new HashMap<String, Object>();
			String filename = filelist.get(i);
			list.put("filename", filename);
			lists.add(list);
			i++;
		}
		return lists;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_files, menu);
		return true;
	}

}
