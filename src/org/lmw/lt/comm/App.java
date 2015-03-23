package org.lmw.lt.comm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class App extends Application {
	static boolean DEBUG=false;
	public static Context appContext;
	public static final boolean IS_DEBUG=true;
	private static Toast mToast = null;
	/** 全局web样式 */
	// 链接样式文件，代码块高亮的处理
	public final static String linkCss = "<script type=\"text/javascript\" src=\"file:///android_asset/shCore.js\"></script>"
			+ "<script type=\"text/javascript\" src=\"file:///android_asset/brush.js\"></script>"
			+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shThemeDefault.css\">"
			+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shCore.css\">"
			+ "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>";
	public final static String WEB_STYLE = linkCss + "<style>* {font-size:14px;line-height:20px;} p {color:#333;} a {color:#3E62A6;} img {max-width:310px;} "
			+ "img.alignleft {float:left;max-width:120px;margin:0 10px 5px 0;border:1px solid #ccc;background:#fff;padding:2px;} "
			+ "pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;overflow: auto;} "
			+ "a.tag {font-size:15px;text-decoration:none;background-color:#bbd6f3;border-bottom:2px solid #3E6D8E;border-right:2px solid #7F9FB6;color:#284a7b;margin:2px 2px 2px 0;padding:2px 4px;white-space:nowrap;}</style>";
	
	public static String replace_str_01="<p>路透全新邮件产品服务——“每日财经荟萃”，让您在每日清晨收到路透全球财经资讯精华和最新投资动向。请点击此处(<a href=\"https://commerce.cn.reuters.com/profile/pages/newsletter/begin.do\">here</a>)开通此服务。</p>";
	
	public static int PAGE_SIZE=30;
	
	//api url
	public static ACache mCache;
	public static String API_HOST="http://cn.reuters.com";
	public static String API_HOT=API_HOST+"/news/rankings";
	public static String API_CHINA=API_HOST+"/news/china";
	public static String API_INTL=API_HOST+"/news/internationalbusiness";
	
	public static String API_CHINA_week=API_HOST+"/news/archive/chinaNews?date=weekOverview";
	public static String API_INTL_week=API_HOST+"/news/archive/CNIntlBizNews?date=weekOverview";
	
	//cache
	public static String ck_hot="ck_hot";
	public static String ck_china="ck_china";
	public static String ck_intl="ck_intl";
	
	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x03;
	@Override
	public void onCreate() {
		super.onCreate();
		appContext=getApplicationContext();
		mCache=ACache.get(getCacheDir());
	}
	
	/**
	 * 检测网络是否可用
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
	
	/**
	 * 获取当前网络类型
	 * @return 0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络
	 */
	public static int getNetworkType() {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager)appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}		
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if(!StringUtils.isEmpty(extraInfo)){
				if (extraInfo.toLowerCase().equals("cmnet")) {
					netType = NETTYPE_CMNET;
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}
	
	public static String dateToStr(String style){
		SimpleDateFormat format=new SimpleDateFormat(style,Locale.CHINA);
		return format.format(new Date());
	}
	
	public static void showToast(String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(appContext, msg,Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}
	
    public static void showLog(String tag,String msg){
    	if(DEBUG)
    	Log.i(tag, msg);
    }
    public static void showLog(String msg){
    	if(DEBUG)
    	Log.i("", msg);
    }
}
