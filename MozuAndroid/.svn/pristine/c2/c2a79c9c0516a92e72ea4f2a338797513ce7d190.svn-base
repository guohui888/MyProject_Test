/**   
 * @Title: BaseActivity.java 
 * @Package cn.com.zhoufu.mouth.activity 
 * @Description: TODO(父类activity) 
 * @author 王小杰  
 * @date 2014-2-10 上午11:53:13 
 * @version V1.0   
 */
package cn.com.zhoufu.mouth.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.ZFApplication;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.db.DBUtils;
import cn.com.zhoufu.mouth.view.MyProgressDialog;

import com.lidroid.xutils.ViewUtils;
import com.tencent.tauth.Tencent;

public class BaseActivity extends FragmentActivity {

	protected MyProgressDialog myProgressDialog;

	protected BaseActivity mActivity;

	public Context mContext;

	public ZFApplication application;

	public SharedPreferences sharedPreferences = null;

	protected Tencent mTencent;

	protected SQLiteDatabase db;

	protected DBUtils dbUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		application = (ZFApplication) getApplication();
		sharedPreferences = getSharedPreferences(Constant.USER_PREF,
				Context.MODE_PRIVATE);
		application.addTask(this);
		db = application.db;
		dbUtils = new DBUtils(db);
		init();
		mTencent = Tencent.createInstance(Constant.QQ_APP_ID, mContext);
	}

	protected void setLayout(BaseActivity activity, int layoutId) {
		mActivity = activity;
		setContentView(layoutId);
		ViewUtils.inject(mActivity);
	}

	public void onBack(View view) {
		finish();
	}

	public void setTitle(String str) {
		((TextView) findViewById(R.id.base_title)).setText(str);
	}

	/** 初始化自定义对话框 */
	private void init() {
		myProgressDialog = new MyProgressDialog(mContext);
		myProgressDialog.setMyCancelable(true);
		myProgressDialog.setMyTouchOutside(false);
	}

	/**
	 * @param 消息
	 */
	public void showProgress(String message) {
		myProgressDialog.setMyMessage(message);
		myProgressDialog.myShow();
	}

	public void showDefaultProgress() {
		myProgressDialog.setMyMessage("数据加载中，请稍候！");
		myProgressDialog.myShow();
	}

	/**
	 * @param 消息
	 */
	public void setMessage(String message) {
		myProgressDialog.setMyMessage(message);
	}

	public void dismissProgress() {
		if (myProgressDialog != null)
			myProgressDialog.mydismiss();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		application.finishActivity(this);
	}
}
