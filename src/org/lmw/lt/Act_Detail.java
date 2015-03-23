package org.lmw.lt;

import org.lmw.lt.comm.API;
import org.lmw.lt.comm.App;
import org.lmw.lt.comm.BaseActivity;
import org.lmw.lt.comm.BaseHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lmw.lt.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class Act_Detail extends BaseActivity implements Callback{
	
	TextView title;
	WebView wv;
	Handler hd;
	String href = "http://m.baidu.com/";
	String base_url = "";
	String ck="";//cacke_key
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_detail);
		initView();
	}
	
	private void initView() {
		hd = new Handler(this);
		href = getIntent().getExtras().getString("href");
		ck=href.split("/")[href.split("/").length-1].replace("?sp=true", "");
		
		title=(TextView)findViewById(R.id.textView2);
		title.setText(getIntent().getExtras().getString("title"));
		wv = (WebView) findViewById(R.id.webView);
		wv.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		String data=App.mCache.getAsString(ck);
		
		showDialog();
		if(null==data){
			API.doGet(href, hd, WHAT_LOAD_NET);	
		}else{
			hd.obtainMessage(WHAT_LOAD_LOCAL, data).sendToTarget();
		}
	}

	private void loadData(String result,int what) {
		String body = "";
		if(what==WHAT_LOAD_LOCAL){
			body=result;
		}
		if(what==WHAT_LOAD_NET){
		Document doc = Jsoup.parse(result);
		Elements imgs = doc.getElementsByTag("img");
		for (Element img : imgs) {
			String absolute_path = base_url + img.attr("src");
			img.attr("src", absolute_path);
		}
		Element pic = doc.getElementById("articlePhoto");
		String content = doc.getElementById("resizeableText").html();
		
		if (pic != null) {
			body = App.WEB_STYLE + pic.html() + content;
		} else {
			body = App.WEB_STYLE + content;
		}
		// 过滤掉 img标签的width,height属性
		body = body.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
		body = body.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");
		
		App.mCache.put(ck, body);
		}
		wv.loadDataWithBaseURL(null, body, "text/html", "utf-8", null);
		disDialog();
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
		disDialog();
		return false;
	}
}
