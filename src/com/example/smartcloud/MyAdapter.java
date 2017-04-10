package com.example.smartcloud;

import java.io.Serializable;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

@SuppressWarnings("serial")
public class MyAdapter  extends PagerAdapter implements Serializable {

	private List<View> pages;
	
	public MyAdapter(List<View> pages){
		this.pages=pages;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pages.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pages.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(pages.get(position), 0);
        return pages.get(position);
    }

}
