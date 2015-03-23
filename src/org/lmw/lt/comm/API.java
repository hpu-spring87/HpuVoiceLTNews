package org.lmw.lt.comm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class API {
	 static HttpUtils http = new HttpUtils();
	 static Gson gson=new Gson();
	    /**
	     * post方法
	     * @param postUrl
	     * @param params
	     * @param hd
	     * @param flag 
	     */
	    public static void doPost(final String url,final String paramStr, final Handler hd,final int waht) {
		if (App.getNetworkType()==0) {
			hd.obtainMessage(-2).sendToTarget();
			return;
		}	
		http.configTimeout(1000*5);
	    http.configRequestRetryCount(3);
		RequestParams params=new RequestParams();
		params.addBodyParameter("syncTransferData", paramStr);
		App.showLog("请求参数："+paramStr);
		
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
		    @Override
		    public void onSuccess(ResponseInfo<String> arg0) {
			App.showLog("\n返回结果是"+ arg0.result);
			hd.obtainMessage(waht, arg0.result).sendToTarget();
		    }

		    @Override
		    public void onFailure(HttpException arg0, String arg1) {
			App.showLog("错误原因：" + arg1);
			hd.obtainMessage(-1).sendToTarget();
		    }
		});

	    }
	    
	    
	    /**
	     * get方法
	     */
	public static void doGet(final String getUrl,final Handler hd,final int waht){
		if (App.getNetworkType()==0) {
			hd.obtainMessage(-2).sendToTarget();
			return;
		}	
	    http.configTimeout(5000);
	    http.send(HttpMethod.GET, getUrl, new RequestCallBack<String>() {
	    	
	    	@Override
	    	public void onSuccess(ResponseInfo<String> arg0) {
	    		App.showLog("\n返回结果是"+ arg0.result);
	    		hd.obtainMessage(waht, arg0.result).sendToTarget();
	    	}
	    	
	    	@Override
	    	public void onFailure(HttpException arg0, String arg1) {
	    		App.showLog(("错误原因：" + arg1));
	    		hd.obtainMessage(-1).sendToTarget();
	    	}
		});
	   }
	    
	    
	    //模拟数据
	    public static void doTestGet(final int what,final Handler hd){
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(3000);
						List<Map<String, String>> list=new ArrayList<Map<String,String>>();
						for (int i = 0; i < 10; i++) {
							list.add(null);
						}
						Message msg=new Message();
						msg.what=what;
						msg.obj= gson.toJson(list);
						hd.sendMessage(msg);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
	    }
	    

}
