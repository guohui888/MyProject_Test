/**   
 * @Title: HomeActivity.java 
 * @Package cn.com.zhoufu.mouth.activity.home 
 * @Description: TODO(父类fragment) 
 * @author 王小杰
 * @date 2014-2-10 
 * @version V1.0   
 */
package cn.com.zhoufu.mouth.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import cn.com.zhoufu.mouth.ZFApplication;
import cn.com.zhoufu.mouth.db.DBUtils;
import cn.com.zhoufu.mouth.view.MyProgressDialog;

public class BaseFragment extends Fragment {

	protected ZFApplication application;

	protected Context mContext;

	protected MyProgressDialog myProgressDialog;
	public SharedPreferences sharedPreferences = null;
	protected DBUtils dbUtils;

	protected SQLiteDatabase db;

	public BaseActivity mAct;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mAct = (BaseActivity) activity;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (ZFApplication) getActivity().getApplication();
		mContext = getActivity();
		db = application.getDb();
		dbUtils = new DBUtils(db);

		init();
	}

	private void init() {
		myProgressDialog = new MyProgressDialog(mContext);
		myProgressDialog.setMyCancelable(true);
		myProgressDialog.setMyTouchOutside(false);
	}

	public void showProgress(String message) {
		myProgressDialog.setMyMessage(message);
		myProgressDialog.myShow();
	}

	public void showDefaultProgress() {
		myProgressDialog.setMyMessage("数据加载中");
		myProgressDialog.myShow();
	}

	public void setMessage(String message) {
		myProgressDialog.setMyMessage(message);
	}

	public void dismissProgress() {
		if (myProgressDialog != null) {
			myProgressDialog.mydismiss();
		}
	}

	public boolean isShow() {
		if (myProgressDialog.isShowing()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 描述：Toast提示文本.
	 * 
	 * @param text
	 *            文本
	 */
	public void showToast(String message) {
		Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 描述：Toast提示文本.
	 * 
	 * @param resId
	 *            文本的资源ID
	 */
	public void showToast(int resId) {
		Toast.makeText(mContext, this.getResources().getText(resId),
				Toast.LENGTH_SHORT).show();
	}
}
