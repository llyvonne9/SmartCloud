package com.example.smartcloud;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainInterfceActivity extends Activity {
	
	private LocalActivityManager localManager;
	private MyAdapter myAdapter;
	private OnPageChangeListener pageChageListener;
	
	private ImageView filePic,transferPic,chatPic,uploadPic,personPic;
	private ViewPager myViewPager;
	private TextView usernameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_maininterface);
        
        filePic=(ImageView)findViewById(R.id.filesIcon);
        transferPic=(ImageView)findViewById(R.id.transferIcon);
        chatPic=(ImageView)findViewById(R.id.chatIcon);
        uploadPic=(ImageView)findViewById(R.id.personalInfoIcon);
        personPic=(ImageView)findViewById(R.id.personIcon);
        usernameTV=(TextView)findViewById(R.id.loggedUser);
        
        
        
        usernameTV.setClickable(true);
        usernameTV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				//intent.putExtra("path",path);
				intent.setClass(MainInterfceActivity.this, PersonalInfoActivity.class);
				startActivity(intent);
				
			}
		});
        
        localManager = new LocalActivityManager(this, true);
        localManager.dispatchCreate(savedInstanceState);

        myViewPager = (ViewPager) findViewById(R.id.viewpager);
        
        filePic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				myViewPager.setCurrentItem(0);
			}
		});
        uploadPic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				myViewPager.setCurrentItem(1);
			}
		});
        transferPic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				myViewPager.setCurrentItem(2);
			}
		});
        chatPic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				myViewPager.setCurrentItem(3);
			}
		});
        personPic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				myViewPager.setCurrentItem(4);
			}
		});
        initialPage();
        
    }
    
    public void initialPage(){
    	pageChageListener=new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				switch(arg0){
				case 0:
					filePic.setImageResource(R.drawable.file);
					transferPic.setImageResource(R.drawable.transfer);
					chatPic.setImageResource(R.drawable.chat);
					uploadPic.setImageResource(R.drawable.personinfo);
					personPic.setImageResource(R.drawable.personinfo);
                    break;
				case 1:
					filePic.setImageResource(R.drawable.file);
					transferPic.setImageResource(R.drawable.transfer);
					chatPic.setImageResource(R.drawable.chat);
					uploadPic.setImageResource(R.drawable.personinfo);
					personPic.setImageResource(R.drawable.personinfo);
                    break;
				case 2:
					filePic.setImageResource(R.drawable.file);
					transferPic.setImageResource(R.drawable.transfer);
					chatPic.setImageResource(R.drawable.chat);
					uploadPic.setImageResource(R.drawable.personinfo);
					personPic.setImageResource(R.drawable.personinfo);
                    break;
				case 3:
					filePic.setImageResource(R.drawable.file);
					transferPic.setImageResource(R.drawable.transfer);
					chatPic.setImageResource(R.drawable.chat);
					uploadPic.setImageResource(R.drawable.personinfo);
					personPic.setImageResource(R.drawable.personinfo);
                    break;
				}
			}

    	};
    
        List<View> mViews = new ArrayList<View>();
        Intent intent = getIntent();

        intent.setClass(this, MyFilesActivity.class);
        intent.putExtra("id", 1);
        mViews.add(getView("Files", intent));
        
//        intent = new Intent();
        intent.setClass(this, UploadActivity.class);
        intent.putExtra("id", 2);
        mViews.add(getView("PersonalInfo", intent));

//        intent = new Intent();
        intent.setClass(this, TransferActivity.class);
        intent.putExtra("id", 3);
        mViews.add(getView("TransferList", intent));

//        intent = new Intent();
        intent.setClass(this, ChatActivity.class);
        intent.putExtra("id", 4);
        mViews.add(getView("ChatWithFriends", intent));

//      intent = new Intent();
        intent.setClass(this, PersonalInfoActivity.class);
        intent.putExtra("id", 5);
        mViews.add(getView("PersonalInfoChange", intent));

        myAdapter = new MyAdapter(mViews);
        myViewPager.setAdapter(myAdapter);
    	
    	
    	
    }
    
    private View getView(String id, Intent intent) {
        return localManager.startActivity(id,intent).getDecorView();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
