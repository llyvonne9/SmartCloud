package com.example.smartcloud;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button loginButton, turnToRegisterButton;
	private EditText usernameEditView, passwordEditView;
	private CheckBox isRememberCheckBox;
	private Handler handler;
	private String state;
	private String username;
	private JSONArray files;
	private ArrayList<String> filelist = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loginButton = (Button) findViewById(R.id.loginButton);
		usernameEditView = (EditText) findViewById(R.id.usernameLogEditText);
		passwordEditView = (EditText) findViewById(R.id.passwoedLogEditText);
		isRememberCheckBox = (CheckBox) findViewById(R.id.RememberCheckBox);
		turnToRegisterButton = (Button) findViewById(R.id.buttonRegisterInLogin);
		// if(isRememberCheckBox.isChecked()){
		// isRemember=0;
		// }else{
		// isRemember=1;
		// }

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (state.equals("success")) {
					usernameEditView.setText("");
					passwordEditView.setText("");
					Intent intent = new Intent(MainActivity.this, MainInterfceActivity.class);
					intent.putExtra("username", username);
					intent.putStringArrayListExtra("filelist", filelist);
					startActivity(intent);
					filelist.clear();
				} else {
					usernameEditView.setText("");
					passwordEditView.setText("");
					Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
				}
				super.handleMessage(msg);
			}
		};

		turnToRegisterButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, RegisterActivity.class);
				startActivity(intent);

			}
		});
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (usernameEditView.getText().toString().equals("") || passwordEditView.getText().toString().equals("")) {
					Toast.makeText(MainActivity.this, "please enter the username and password", Toast.LENGTH_SHORT).show();
				} else {
					new Thread(new Runnable() {

						@Override
						public void run() {
							login();
							Message m = handler.obtainMessage();
							handler.sendMessage(m);
						}
					}).start();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void login() {
		String target = "http://congcongxyz.cn/login";
		StringBuilder strbuilder = new StringBuilder();
		JSONObject jsonObject;
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpRequest = new HttpPost(target);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("type", "android"));
		params.add(new BasicNameValuePair("accountforlogin", usernameEditView.getText().toString()));
		params.add(new BasicNameValuePair("passwordforlogin", passwordEditView.getText().toString()));
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
				for (String s = reader.readLine(); s != null; s = reader.readLine()) {
					strbuilder.append(s);
				}
				jsonObject = new JSONObject(strbuilder.toString());
				state = jsonObject.getString("state");
				System.out.println("abc" + state);
				username = jsonObject.getString("username");
				files = jsonObject.getJSONArray("files");
				if (files.length() == 0) {
					System.out.println("this is the error case");
				}
				for (int i = 0; i < files.length(); i++) {
					String tempname = files.getString(i);
					tempname = tempname.substring(tempname.lastIndexOf("/") + 1);
					filelist.add(tempname);
				}
			} else {
				// result="request fail";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
