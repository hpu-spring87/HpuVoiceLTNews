package org.lmw.lt.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragPagerAdapter extends FragmentPagerAdapter {
	private String[] titles;
	private List<Fragment> fList;
	public MyFragPagerAdapter(FragmentManager fm,List<Fragment> fList,String[] titles) {
		super(fm);
		this.titles=titles;
		this.fList=fList;
	}
	
	@Override
	public int getCount() {
		return titles.length;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}

	@Override
	public Fragment getItem(int position) {
		return fList.get(position);
	}
}
