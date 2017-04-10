package com.example.smartcloud;

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
import org.apache.http.util.EntityUtils;

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

public class RegisterActivity extends Activity {
	private Button registerButton;
	private EditText usernameEditView, passwordEditView, passwordConfirmEditView, emailEditView;
	private String username, email, password, passwordConfirm;
	private CheckBox isAgreeCheckBox;// isAgree=0 for not being checked, 1 for
										// checked
	private int isAgree;

	private Handler handler;
	private String result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		registerButton = (Button) findViewById(R.id.buttonRegister);
		usernameEditView = (EditText) findViewById(R.id.usernameLogEditTextRegister);
		passwordEditView = (EditText) findViewById(R.id.passwoedRegisterEditText);
		passwordConfirmEditView = (EditText) findViewById(R.id.passwoedRegisterEditTextConfirm);
		emailEditView = (EditText) findViewById(R.id.emailLogEditTextRegister);
		isAgreeCheckBox = (CheckBox) findViewById(R.id.isAgreeCheckBox);
		// username=usernameEditView.getText().toString();
		// password=passwordEditView.getText().toString();
		// passwordConfirm=passwordConfirmEditView.getText().toString();
		// email=emailEditView.getText().toString();

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (result.equals("success")) {
					usernameEditView.setText("");
					passwordEditView.setText("");
					passwordConfirmEditView.setText("");
					emailEditView.setText("");
					Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
					startActivity(intent);
				} else {
					usernameEditView.setText("");
					passwordEditView.setText("");
					passwordConfirmEditView.setText("");
					emailEditView.setText("");
					Toast.makeText(RegisterActivity.this, "register fail", Toast.LENGTH_SHORT).show();
				}
				super.handleMessage(msg);
			}
		};

		registerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (usernameEditView.getText().toString().equals("") || passwordEditView.getText().toString().equals("")
						|| passwordConfirmEditView.getText().toString().equals("") || emailEditView.getText().toString().equals("")) {
					Toast.makeText(RegisterActivity.this, "please enter the all information", Toast.LENGTH_SHORT).show();
				} else if (!passwordEditView.getText().toString().equals(passwordConfirmEditView.getText().toString())) {
					Toast.makeText(RegisterActivity.this, "password is not same", Toast.LENGTH_SHORT).show();
				} else if (!isAgreeCheckBox.isChecked()) {
					Toast.makeText(RegisterActivity.this, "please agree with the items to finish register", Toast.LENGTH_SHORT).show();
				} else {
					new Thread(new Runnable() {

						@Override
						public void run() {
							register();
							Message m = handler.obtainMessage();
							handler.sendMessage(m);
						}
					}).start();
				}
			}
		});
		// if(isAgreeCheckBox.isChecked()){
		// isAgree=0;
		// }else{
		// isAgree=1;
		// }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	public void register() {
		String target = "http://congcongxyz.cn/register";
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpRequest = new HttpPost(target);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("type", "android"));
		params.add(new BasicNameValuePair("username", usernameEditView.getText().toString()));
		params.add(new BasicNameValuePair("psw1", passwordEditView.getText().toString()));
		params.add(new BasicNameValuePair("email", emailEditView.getText().toString()));
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(httpResponse.getEntity());
				System.out.println(result);
			} else {
				result = "request fail";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}