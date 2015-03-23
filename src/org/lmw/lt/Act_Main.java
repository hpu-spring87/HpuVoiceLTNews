package org.lmw.lt;

import java.util.ArrayList;
import java.util.List;

import org.lmw.lt.adapter.MyFragPagerAdapter;
import org.lmw.lt.widget.PagerSlidingTabStrip;
import org.lmw.lt.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
public class Act_Main extends FragmentActivity {
	PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private List<Fragment> fragList=new ArrayList<Fragment>();
	private MyFragPagerAdapter adapter;
	String[] titles={"热点推荐","中国财经","国际财经"};
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_main);
		initFragList();
		initVeiw();
	}
	
	private void initVeiw(){
		FragmentManager fm = getSupportFragmentManager();
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setOffscreenPageLimit(fragList.size());
		adapter = new MyFragPagerAdapter(fm, fragList,titles);
		pager.setAdapter(adapter);
		tabs.setViewPager(pager);
	}
	
	private void initFragList(){
		
		fragList.add(new Frag_Hot());
		fragList.add(new Frag_CN());
		fragList.add(new Frag_INTL());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		startActivity(new Intent(getApplicationContext(), Act_About.class));
		return super.onOptionsItemSelected(item);
	}
}
