package com.example.smartcloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalInfoActivity extends ListActivity {
	private ListView personalInfoLV;
	
	private TextView changeTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_info);

		personalInfoLV=(ListView)findViewById(android.R.id.list);
		changeTV=(TextView)findViewById(R.id.fieldContentTextView);
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		 
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Username");
        map.put("info", "I am username");
        list.add(map);
 
        map = new HashMap<String, Object>();
        map.put("title", "Password");
        map.put("info", "I am password");
        list.add(map);
 
        map = new HashMap<String, Object>();
        map.put("title", "Phone number");
        map.put("info", "110");
        list.add(map);
		final SimpleAdapter adapter=new SimpleAdapter(this,list,R.layout.personalinfo_list_item,new String[]{"title","info"},new int[]{R.id.fieldNameTextView,R.id.fieldContentTextView});
		setListAdapter(adapter);
		personalInfoLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
				final EditText modifyET=new EditText(PersonalInfoActivity.this);
				ListView listView = (ListView)parent;
				final HashMap<String, String> mapItem = (HashMap<String, String>) listView.getItemAtPosition(position);
				AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
				builder.setTitle("Modify");
				builder.setMessage("Change "+mapItem.get("title").toString());
				builder.setView(modifyET);
				modifyET.setText(mapItem.get("info").toString());
				builder.setPositiveButton("Ok",new OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						mapItem.put("info", modifyET.getText().toString());
						adapter.notifyDataSetChanged();
						
						
						
					}
					
				});
				builder.setNegativeButton("Cancel", new OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
					
				});
				builder.show();
				 
				 
				
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.personal_info, menu);
		return true;
	}

}
