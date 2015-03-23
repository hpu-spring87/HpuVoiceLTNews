package org.lmw.lt.comm;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BaseFragment extends Fragment {
	public Toast mToast = null;
	public static Gson gson=new Gson();
	public static Type type = new TypeToken<List<Map<String, String>>>(){}.getType();
	public static final int WHAT_LOAD_LOCAL=1;
	public static final int WHAT_LOAD_NET=2;
	public static final int WHAT_NO_NET=-2;
	//
	public void showLog(String tag,String msg){
		if (App.IS_DEBUG)
			Log.e(tag,msg);
	}
	
	//
	public void showToast(String msg) {
		if(getActivity()==null){return;}
		if (mToast == null) {
			mToast = Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}
	//
	public void back(View v){
		getActivity().onBackPressed();
	}
}
