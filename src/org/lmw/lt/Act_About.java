package org.lmw.lt;
import org.lmw.lt.comm.BaseActivity;
import org.lmw.lt.comm.FileUtils;
import org.lmw.lt.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class Act_About extends BaseActivity {
	TextView tv;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_setting);
		
		tv=(TextView)findViewById(R.id.tv_cache);
		tv.setText(getCacheSize());
		tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				cleanCache();
			}
		});
	}
	
	public String getCacheSize(){
		double x=FileUtils.getDirSize(getCacheDir());
		double y=1024*1024;
		return "缓存："+String.format("%.2f",x/y)+" MB";
		
	}
	
	private void cleanCache(){
		final Handler mHandler = new Handler(new Handler.Callback() {
	        @Override
	        public boolean handleMessage(Message msg) {
				if (msg.what == 1) {
					showToast("成功清除"+msg.arg1+"个缓存文件");
				} else {
					showToast("缓存清除失败");
				}
				tv.setText(getCacheSize());
	            return false;
	        }
	    });
		new Thread() {
			public void run() {
				Message msg = mHandler.obtainMessage();
				try {
					msg.arg1=FileUtils.clearCacheFolder(getCacheDir(), System.currentTimeMillis());
					msg.what = 1;
				} catch (Exception e) {
					e.printStackTrace();
					msg.arg1=0;
					msg.what = -1;
				}
				mHandler.sendMessage(msg);
			}
		}.start();
	}
	

	
}
