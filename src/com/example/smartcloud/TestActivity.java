package com.example.smartcloud;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TestActivity extends Activity {
	private Button download;
	private Button upload;
	private Handler downloadHandler;
	private Handler uploadHandler;
	private int result_download = -1;
	private int result_upload = -1;
	private String username;
	private ArrayList<String> filelist=new ArrayList<String>();
	private ListView lt1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		download = (Button) findViewById(R.id.button1);
		upload = (Button) findViewById(R.id.button2);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		filelist=intent.getStringArrayListExtra("filelist");
		//System.out.println(filelist);
		System.out.println(filelist);
		System.out.println(getData());
		SimpleAdapter simplead = new SimpleAdapter(this, getData(),R.layout.simple, new String[] {"filename"},new int[] {R.id.filename});
		//access the list//
		lt1=(ListView)findViewById(R.id.listview1);
		//set adapter for the listView//
		lt1.setAdapter(simplead);
		//set listener for list//
		lt1.setOnItemClickListener(new OnItemClickListener() {
			//according to the click position to decide the value in intent and start another activity//
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Intent intent=new Intent();
//				Log.d("test", ""+position);
//				TextView module_day=(TextView) view.findViewById(R.id.day);
//				TextView start_time= (TextView) view.findViewById(R.id.start_time);
//				String day=module_day.getText().toString();
//				intent.putExtra("day", day);
//				intent.putExtra("start_time", start_time.getText().toString());
//				intent.setClass(ModuleList.this, Detail_info.class);
//				startActivity(intent);
//				finish();
			}
		});

		downloadHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (result_download == 1) {
					Toast.makeText(TestActivity.this, "file already exist", Toast.LENGTH_SHORT).show();
				} else if (result_download == 0) {
					Toast.makeText(TestActivity.this, "download success", Toast.LENGTH_SHORT).show();
				} else if (result_download == -1) {
					Toast.makeText(TestActivity.this, "download fail", Toast.LENGTH_SHORT).show();
				}
				super.handleMessage(msg);
			}
		};

		uploadHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (result_upload == 1) {
					Toast.makeText(TestActivity.this, "upload success", Toast.LENGTH_SHORT).show();
				} else if (result_upload == -1) {
					Toast.makeText(TestActivity.this, "upload fail", Toast.LENGTH_SHORT).show();
				}
				super.handleMessage(msg);
			}
		};

		download.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						HttpDownloader downloader = new HttpDownloader();
						result_download = downloader.downfile("http://192.168.56.1:80/test/" + username + "/picture.jpg",username + "/", "picture.jpg");
						System.out.println("http://192.168.56.1:80/test/" + username + "/picture.jpg");
						Message m = downloadHandler.obtainMessage();
						downloadHandler.sendMessage(m);
					}
				}).start();
			}
		});

		upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						HttpUploader uploader = new HttpUploader();
						File file = new File(Environment.getExternalStorageDirectory() + "/smartcloud/" + username + "/", "abc.zip");
						result_upload = uploader.uploadFiles("http://192.168.56.1:80/test/receive_file.php", file,username);
						Message m = uploadHandler.obtainMessage();
						uploadHandler.sendMessage(m);
					}
				}).start();
			}
		});

	}

	private List<Map<String,Object>> getData(){
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		int i=0;
		while (i<filelist.size()) {
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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}