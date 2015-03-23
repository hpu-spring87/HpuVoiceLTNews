package org.lmw.lt.comm;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.lmw.lt.widget.LoadingDialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BaseActivity extends FragmentActivity {
	private Toast mToast = null;
	public LoadingDialog dialog=null;
	public SharedPreferences.Editor editor_setting;
	public SharedPreferences sp_setting;
	public Context appContext;
	
	public static Gson gson=new Gson();
	public static Type type = new TypeToken<List<Map<String, String>>>(){}.getType();
	public static final int WHAT_LOAD_LOCAL=1;
	public static final int WHAT_LOAD_NET=2;
	public static final int WHAT_NO_NET=-2;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		appContext=App.appContext;
		AppManager.getAppManager().addActivity(this);
	}
	
	//
	public void showLog(String tag,String msg){
		if (App.IS_DEBUG)
			Log.i(tag,msg);
	}
	
	//
	public void showToast(String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(getApplicationContext(), msg,
					Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}
	
	// 环形加载进度条
	public void showDialog() {
		if(dialog==null){
		dialog = new LoadingDialog(this);
		}
		dialog.show();
	}
	public void disDialog() {
		
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}
	
	//
	public void back(View v) {
		onBackPressed();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//结束Activity从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}
}
