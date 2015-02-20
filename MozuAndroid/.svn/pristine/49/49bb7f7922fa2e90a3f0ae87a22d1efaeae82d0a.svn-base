/**   
 * @Title: LoginActivity.java 
 * @Package cn.com.zhoufu.mouth.activity.mine 
 * @Description: TODO(登录activity) 
 * @author A18ccms A18ccms_gmail_com   
 * @date 2014-2-10 下午12:31:26 
 * @version V1.0   
 */
package cn.com.zhoufu.mouth.activity.mine;

import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseActivity;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.utils.AbStrUtil;
import cn.com.zhoufu.mouth.utils.Util;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;
import cn.com.zhoufu.mouth.view.SlipButton;
import cn.com.zhoufu.mouth.view.SlipButton.OnChangedListener;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.weibo.sdk.android.Oauth2AccessToken;

public class LoginActivity extends BaseActivity implements OnChangedListener {

	@ViewInject(R.id.right_button2)
	private Button right_button;

	@ViewInject(R.id.reset_password)
	private TextView reset_password;

	@ViewInject(R.id.edUserName)
	private EditText edUserName;

	@ViewInject(R.id.edUserPassword)
	private EditText edPassword;

	@ViewInject(R.id.loginBtns)
	private Button loginBtn;

	@ViewInject(R.id.qq_login)
	private TextView qq_login;

	@ViewInject(R.id.sina_login)
	private TextView sina_login;

	@ViewInject(R.id.slipButton)
	private SlipButton slipButton;

	String user_id, channel_id, appId;

	private Oauth2AccessToken oauth2;
	public static QQAuth mQQAuth;
	private UserInfo mInfo;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(this, R.layout.activity_login);
		setTitle("登录");
		mQQAuth = QQAuth.createInstance(Constant.QQ_APP_ID, this);
		initView();
	}

	public void initView() {
		right_button.setVisibility(View.VISIBLE);
		sharedPreferences = getSharedPreferences(Constant.PUSH_PREF,
				Context.MODE_PRIVATE);
		user_id = sharedPreferences.getString("user_id", "");
		channel_id = sharedPreferences.getString("channel_id", "");
		sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
		String User_Name = sp.getString("User_Name", "");
		String User_Pwd = sp.getString("User_Pwd", "");
		edUserName.setText(User_Name);
		if ("1".equals(sp.getString("isCheck", ""))) {
			slipButton.setChecked(true);
			edPassword.setText(User_Pwd);
		} else {
			Editor editor = sp.edit();
			editor.putString("User_Pwd", "").commit();
			slipButton.setChecked(false);
		}
		slipButton.setOnChangedListener(this);
	}

	@OnClick(R.id.right_button2)
	public void registerClick(View v) {
		startActivity(new Intent(mContext, RegisterActivity.class));
	}

	@OnClick(R.id.reset_password)
	public void resetpasswordClick(View v) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("拨打客服电话");
		dialog.setMessage("0755-83838000");
		dialog.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// 用intent启动拨打电话
				dialog.dismiss();
			}
		});
		dialog.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:0755-83838000"));
				startActivity(intent);
			}
		});
		dialog.show();
		// startActivity(new Intent(mContext, ResetPasswordActivity.class));
	}

	@OnClick(R.id.qq_login)
	public void qqloginClick(View v) {
		onClickLogin();
	}

	@OnClick(R.id.loginBtns)
	public void loginClick(View v) {
		login();
	}

	public void login() {
		final String name = edUserName.getText().toString().trim();
		final String password = edPassword.getText().toString().trim();
		if (AbStrUtil.isEmpty(name)) {
			showToast(R.string.error_user_name);
			edUserName.setFocusable(true);
			edUserName.requestFocus();
			return;
		}
		if (AbStrUtil.isEmpty(password)) {
			showToast(R.string.error_pwd);
			edPassword.setFocusable(true);
			edPassword.requestFocus();
			return;
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("UserName", name);
		map.put("PassWord", password);
		map.put("Device_Type", 3);
		map.put("User_ID", user_id);
		map.put("Channel_ID", channel_id);
		showProgress("正在登录...");

		Log.e("", "map=" + map.toString());
		WebServiceUtils.callWebService(Constant.S_Login, map,
				new WebServiceCallBack() {
					public void callBack(Object result) {
						if (myProgressDialog.isShowing()) {
							dismissProgress();
						}
						Log.i("info", result.toString());
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							int state = bean.getState();
							switch (state) {
							case 1:
								cn.com.zhoufu.mouth.model.UserInfo user = JSON.parseObject(
										bean.getData(),
										cn.com.zhoufu.mouth.model.UserInfo.class);
								application.setUser(user);
								Log.e("tag", application.getUser()
										+ "===========mjmm");
								if ("1".equals(sp.getString("isCheck", ""))) {
									Editor editor = sp.edit();
									editor.putString("User_Name", name);
									editor.putString("User_Pwd", password);
									editor.commit();
								} else {
									Editor editor = sp.edit();
									editor.putString("User_Name", name);
									editor.putString("User_Pwd", password);
									editor.commit();
								}
								finish();
								break;
							case 0:
								showToast("登录失败！");
								break;
							default:
								break;
							}
						}
					}
				});

	}

	private void onClickLogin() {
		if (!mQQAuth.isSessionValid()) {
			IUiListener listener = new BaseUiListener() {
				@Override
				protected void doComplete(Object values) {
					try {
						JSONTokener jsonParser = new JSONTokener(
								values.toString());
						JSONObject obj = (JSONObject) jsonParser.nextValue();
						appId = obj.getString("access_token");
					} catch (Exception e) {
					}
				}
			};
			mTencent.login(this, "all", listener);
		} else {
			mTencent.logout(this);
		}
	}

	private class BaseUiListener implements IUiListener {
		@Override
		public void onComplete(Object arg0) {
			// TODO Auto-generated method stub
			application.showToast("登录成功");
			doComplete(arg0);
		}

		protected void doComplete(Object values) {
		}

		public void onError(UiError e) {
			Util.toastMessage(LoginActivity.this, "onError: " + e.errorDetail);
			Util.dismissDialog();
		}

		public void onCancel() {
			Util.toastMessage(LoginActivity.this, "onCancel: ");
			Util.dismissDialog();
		}

	}

	public void onChange(boolean CheckState) {
		Editor editor = sp.edit();
		if (CheckState) {
			editor.putString("isCheck", "1").commit();
			Log.i("info", "选中");
		} else {
			editor.putString("isCheck", "0").commit();
			Log.i("info", "没选中");
		}
	}
}
