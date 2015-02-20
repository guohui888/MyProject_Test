/**   
 * @Title: ZFApplication.java 
 * @Package cn.com.zhoufu.mouth 
 * @Description: TODO(application) 
 * @author A18ccms A18ccms_gmail_com   
 * @date 2014-2-10 ����1:49:54 
 * @version V1.0   
 */
package cn.com.zhoufu.mouth;

import java.io.File;
import java.util.HashMap;
import java.util.Properties;
import java.util.Stack;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap.Config;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.CacheManager;
import android.widget.Toast;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.db.MouthSQLiteOpenHelper;
import cn.com.zhoufu.mouth.model.UserInfo;
import cn.com.zhoufu.mouth.utils.MethodsCompat;
import cn.com.zhoufu.mozu.R;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

public class ZFApplication extends Application {

	private static ZFApplication mInstance;

	public boolean m_bKeyRight = true;

	public static final String strKey = "t4VMNIeHr5GGlAmEzKxzIQXT";

	private Stack<Activity> stack = new Stack<Activity>();

	public SQLiteDatabase db;

	public MouthSQLiteOpenHelper mouthSQLiteOpenHelper;

	public BitmapUtils bitmapUtils;

	public UserInfo user;

	public String shareContent, picPath;

	public int tag;

	public String DEVICE_ID;
	public String longitude; // 经度
	public String latitude; // 纬度

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		init();
		bitmapUtils = new BitmapUtils(mInstance);
		bitmapUtils.configDefaultLoadingImage(R.drawable.default_bg);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.default_bg);
		bitmapUtils.configDefaultBitmapConfig(Config.RGB_565);

		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		DEVICE_ID = tm.getDeviceId();
	}

	public void init() {
		mouthSQLiteOpenHelper = new MouthSQLiteOpenHelper(this);
		db = mouthSQLiteOpenHelper.getWritableDatabase();
	}

	public static ZFApplication getInstance() {
		return mInstance;
	}

	/**
	 * 
	 * 添加联网参数
	 * 
	 * @return
	 */
	public HashMap<String, Object> addData() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("UserID", mInstance.getUser().getUser_id());
		if (user.getUser_id() == 0) {// 没有登录
			map.put("session_id", DEVICE_ID);
		} else {
			map.put("session_id", "");
		}
		Log.e("", "1111111111111  " + map.toString());
		return map;
	}

	public SQLiteDatabase getDb() {
		return db;
	}

	/**
	 * 添加到堆栈
	 * 
	 * @param activity
	 */
	public void addTask(Activity activity) {
		if (stack == null) {
			stack = new Stack<Activity>();
		}
		stack.add(activity);
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		Activity activity = stack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity() {
		Activity activity = stack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			stack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : stack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	// 判断实例中是否有当前activity
	public boolean hasActivity(Class<?> cls) {
		for (Activity activity : stack) {
			if (activity.getClass().equals(cls)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = stack.size(); i < size; i++) {
			if (null != stack.get(i)) {
				stack.get(i).finish();
			}
		}
		stack.clear();
	}

	public void finishOtherAct() {
		for (int i = 1; i < stack.size(); i++) {
			// if (stack.get(i) != stack.firstElement()) {
			//
			// }
			if (stack.get(i) != null && !stack.get(i).isFinishing()) {
				stack.get(i).finish();
			}
		}

	}

	/**
	 * 描述：Toast提示文本.
	 * 
	 * @param text
	 *            文本
	 */
	public void showToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 描述：Toast提示文本.
	 * 
	 * @param resId
	 *            文本的资源ID
	 */
	public void showToast(int resId) {
		Toast.makeText(this, this.getResources().getText(resId),
				Toast.LENGTH_SHORT).show();
	}

	public UserInfo getUser() {
		if (user == null) {
			SharedPreferences sp = getSharedPreferences(Constant.USER_PREF,
					Context.MODE_PRIVATE);
			user = new UserInfo();
			user.setUser_id(sp.getInt("user_id", 0));
			user.setUser_name(sp.getString("user_name", ""));
			user.setEmail(sp.getString("email", ""));
			user.setSex(sp.getInt("sex", 0));
			user.setMobile_phone(sp.getString("mobile_phone", ""));
			user.setUser_money(sp.getFloat("user_money", 0f));
			user.setFrozen_money(sp.getFloat("frozen_money", 0f));
			user.setCredit_line(sp.getFloat("credit_line", 0f));
			user.setUser_photo(sp.getString("user_photo", ""));
			Log.d("tag", "==========   " + sp.getFloat("user_money", 0f));
		}
		return user;
	}

	public void setUser(UserInfo user) {
		if (user != null) {
			SharedPreferences sp = getSharedPreferences(Constant.USER_PREF,
					Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putInt("user_id", user.getUser_id());
			editor.putString("email", user.getEmail());
			editor.putString("user_name", user.getUser_name());
			editor.putInt("sex", user.getSex());
			editor.putString("mobile_phone", user.getMobile_phone());
			editor.putFloat("user_money", user.getUser_money());
			editor.putString("user_photo", user.getUser_photo());
			editor.putFloat("frozen_money", user.getFrozen_money());
			editor.putFloat("credit_line", user.getCredit_line());
			editor.commit();
		}

		this.user = user;
	}

	/**
	 * 清空上次登录参数
	 */
	public void logout() {
		SharedPreferences sp = getSharedPreferences(Constant.USER_PREF,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
		user = null;
	}

	public String getShareContent() {
		return shareContent;
	}

	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	/**
	 * 清除app缓存
	 */
	public void clearAppCache() {
		// 清除webview缓存
		File file = CacheManager.getCacheFileBaseDir();
		if (file != null && file.exists() && file.isDirectory()) {
			for (File item : file.listFiles()) {
				item.delete();
			}
			file.delete();
		}
		deleteDatabase("webview.db");
		deleteDatabase("webview.db-shm");
		deleteDatabase("webview.db-wal");
		deleteDatabase("webviewCache.db");
		deleteDatabase("webviewCache.db-shm");
		deleteDatabase("webviewCache.db-wal");
		// 清除数据缓存
		clearCacheFolder(getFilesDir(), System.currentTimeMillis());
		clearCacheFolder(getCacheDir(), System.currentTimeMillis());
		// 2.2版本才有将应用缓存转移到sd卡的功能
		if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
			clearCacheFolder(MethodsCompat.getExternalCacheDir(this),
					System.currentTimeMillis());
		}
		// 清除编辑器保存的临时内容
		Properties props = getProperties();
		for (Object key : props.keySet()) {
			String _key = key.toString();
			if (_key.startsWith("temp"))
				removeProperty(_key);
		}
	}

	/**
	 * 清除缓存目录
	 * 
	 * @param dir
	 *            目录
	 * @param numDays
	 *            当前系统时间
	 * @return
	 */
	private int clearCacheFolder(File dir, long curTime) {
		int deletedFiles = 0;
		if (dir != null && dir.isDirectory()) {
			try {
				for (File child : dir.listFiles()) {
					if (child.isDirectory()) {
						deletedFiles += clearCacheFolder(child, curTime);
					}
					if (child.lastModified() < curTime) {
						if (child.delete()) {
							deletedFiles++;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deletedFiles;
	}

	/**
	 * 判断当前版本是否兼容目标版本的方法
	 * 
	 * @param VersionCode
	 * @return
	 */
	public static boolean isMethodsCompat(int VersionCode) {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		return currentVersion >= VersionCode;
	}

	public boolean containsProperty(String key) {
		Properties props = getProperties();
		return props.containsKey(key);
	}

	public void setProperties(Properties ps) {
		AppConfig.getAppConfig(this).set(ps);
	}

	public Properties getProperties() {
		return AppConfig.getAppConfig(this).get();
	}

	public void setProperty(String key, String value) {
		AppConfig.getAppConfig(this).set(key, value);
	}

	public String getProperty(String key) {
		return AppConfig.getAppConfig(this).get(key);
	}

	public void removeProperty(String... key) {
		AppConfig.getAppConfig(this).remove(key);
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}
}