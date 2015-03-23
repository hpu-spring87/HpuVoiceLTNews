package org.lmw.lt.comm;


import android.os.Handler;
import android.os.Message;

public class BaseHandler extends Handler{
	
	public void handleMessage(Message msg) {
		if(msg.what==-2){
			App.showToast("网络不可用");
		}
	}
}
