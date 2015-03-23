package org.lmw.lt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lmw.lt.R;
import org.lmw.lt.adapter.ArtAdapter;
import org.lmw.lt.comm.API;
import org.lmw.lt.comm.App;
import org.lmw.lt.comm.BaseFragment;
import org.lmw.lt.comm.BaseHandler;
import org.lmw.lt.widget.xlist.XListView;
import org.lmw.lt.widget.xlist.XListView.IXListViewListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

//路透_国际
public class Frag_INTL extends BaseFragment implements OnItemClickListener, IXListViewListener,Callback{
	
	View v;
	String URL=App.API_INTL_week;
	String CK=App.ck_intl;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.frag_news, null);
		return v;
	}
	
	private XListView lv;
	private ArtAdapter adapter;
	private List<Map<String, String>> rs = new ArrayList<Map<String, String>>();
	private Handler hd;
	int pageIndex = 1;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		hd = new Handler(this);
		lv = (XListView) v.findViewById(R.id.listView);
		adapter = new ArtAdapter(getActivity(), rs);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
		lv.setXListViewListener(this);
		
		lv.pullRefreshing();
		//缓存
		String data=App.mCache.getAsString(CK);
		if(null==data){
			API.doGet(URL, hd, WHAT_LOAD_NET);
		}else{
			hd.obtainMessage(WHAT_LOAD_LOCAL, data).sendToTarget();
		}
	}

	private void loadData(String result,int what) {
		rs.clear();
		if(what==WHAT_LOAD_LOCAL){
			//解析 缓存
			List<Map<String, String>> temp=gson.fromJson(result, type);
			rs.addAll(temp);
		}
		if(what==WHAT_LOAD_NET){
		Document doc = Jsoup.parse(result);
		Elements es = doc.select("div.headlineMed+div.standalone");
		Map<String, String> m;
		for (Element e : es) {
			m = new HashMap<String, String>();
			Element title = e.getElementsByTag("a").get(0);
			m.put("title", title.text());
			m.put("href", App.API_HOST + title.attr("href") + "?sp=true");

			String time = e.getElementsByTag("span").get(1).text();

			if (time.length() == 9) {
				time = "今天  " + time.substring(0, 5);
			} else {
				time = time.substring(5, 12).replace(" ", "") + time.substring(16,23);
			}

			m.put("time", time);
			rs.add(m);
		}
			App.mCache.put(CK, gson.toJson(rs));
		}
		lv.stopRefresh();
		adapter.notifyDataSetChanged();
	}



	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent it=new Intent(getActivity(), Act_Detail.class);
		it.putExtra("title", rs.get(position-1).get("title"));
		it.putExtra("href", rs.get(position-1).get("href"));
		startActivity(it);
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case WHAT_LOAD_NET:
		case WHAT_LOAD_LOCAL:
			loadData(msg.obj.toString(),msg.what);
			break;
		case WHAT_NO_NET:
			showToast("网络不可用");
			break;
		default:
			showToast("找不到服务器");
			break;
		}
		lv.stopRefresh();
		return false;
	}
	
	@Override
	public void onRefresh() {
		API.doGet(URL, hd, WHAT_LOAD_NET);
	}
	@Override
	public void onLoadMore() {
	} 

}
