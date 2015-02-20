/**   
 * @Title: PhoneFragment.java 
 * @Package cn.com.zhoufu.mouth.activity.mine 
 * @Description: TODO(手机注册fragment) 
 * @author 王小杰   
 * @date 2014-2-11 上午10:28:27 
 * @version V1.0   
 */
package cn.com.zhoufu.mouth.activity.mine;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseFragment;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.utils.AbStrUtil;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class PhoneFragment extends BaseFragment {

	@ViewInject(R.id.edPhone)
	private EditText edPhone;

	@ViewInject(R.id.checkBtn)
	private Button checkBtn;

	@ViewInject(R.id.edCheckNumber)
	private EditText edCheckNumber;

	@ViewInject(R.id.edPasswords)
	private EditText edPasswords;

	@ViewInject(R.id.edsurePassword)
	private EditText edsurePassword;

	@ViewInject(R.id.registerPhoneBtn)
	private Button registerPhoneBtn;

	String user_id, channel_id;

	public SharedPreferences sharedPreferences;

	private String checkCode = "";

	private SharedPreferences sp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_phone_register,
				container, false);
		ViewUtils.inject(this, view);
		sharedPreferences = getActivity().getSharedPreferences(
				Constant.PUSH_PREF, Context.MODE_PRIVATE);
		user_id = sharedPreferences.getString("user_id", "");
		channel_id = sharedPreferences.getString("channel_id", "");
		sp = getActivity().getSharedPreferences("user_info",
				Context.MODE_PRIVATE);
		return view;
	}

	@OnClick(R.id.checkBtn)
	public void getCheckCode(View v) {
		checkUser();
	}

	@OnClick(R.id.registerPhoneBtn)
	public void register(View v) {
		register();
	}

	public void checkUser() {
		String phone = edPhone.getText().toString().trim();
		if (AbStrUtil.isEmpty(phone)) {
			showToast(R.string.error_phone);
			edPhone.setFocusable(true);
			edPhone.requestFocus();
			return;
		}
		if (!AbStrUtil.isMobileNo(phone)) {
			showToast(R.string.error_phone_expr);
			edPhone.setFocusable(true);
			edPhone.requestFocus();
			return;
		}
		showProgress("获取验证码中，请稍候！");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Mobile", phone);
		WebServiceUtils.callWebService(Constant.S_CheckUser, map,
				new WebServiceCallBack() {

					public void callBack(Object result) {
						Log.e("", "    tag  " + result.toString());
						if (myProgressDialog.isShowing()) {
							dismissProgress();
						}
						Bean bean = JSON.parseObject(result.toString(),
								Bean.class);
						int state = bean.getState();
						switch (state) {
						case 2:
							showToast(bean.getMsg());
							checkCode = bean.getData();
							break;

						default:
							showToast(bean.getMsg());
							break;
						}
					}
				});
	}

	public void register() {
		final String phone = edPhone.getText().toString().trim();
		// String check_code = edCheckNumber.getText().toString().trim();
		final String login_password = edPasswords.getText().toString().trim();
		String confirm_password = edsurePassword.getText().toString().trim();

		if (AbStrUtil.isEmpty(phone)) {
			showToast(R.string.error_phone);
			edPhone.setFocusable(true);
			edPhone.requestFocus();
			return;
		}
		if (!AbStrUtil.isMobileNo(phone)) {
			showToast(R.string.error_phone_expr);
			edPhone.setFocusable(true);
			edPhone.requestFocus();
			return;
		}
		// if (AbStrUtil.isEmpty(check_code)) {
		// showToast(R.string.error_check_code);
		// edCheckNumber.setFocusable(true);
		// edCheckNumber.requestFocus();
		// return;
		// }

		// if (!check_code.equals(checkCode)) {
		// showToast(R.string.error_check_code_match);
		// edCheckNumber.setFocusable(true);
		// edCheckNumber.requestFocus();
		// return;
		// }

		if (AbStrUtil.isEmpty(login_password)) {
			showToast(R.string.error_pwd);
			edPasswords.setFocusable(true);
			edPasswords.requestFocus();
			return;
		}
		if (AbStrUtil.strLength(login_password) < 6) {
			showToast(R.string.error_pwd_length1);
			edPasswords.setFocusable(true);
			edPasswords.requestFocus();
			return;
		}

		if (AbStrUtil.strLength(login_password) > 20) {
			showToast(R.string.error_pwd_length2);
			edPasswords.setFocusable(true);
			edPasswords.requestFocus();
			return;
		}

		if (TextUtils.isEmpty(confirm_password)) {
			showToast(R.string.error_confirm_pwd);
			edsurePassword.setFocusable(true);
			edsurePassword.requestFocus();
			return;
		}
		if (AbStrUtil.strLength(confirm_password) < 6) {
			showToast(R.string.error_confirm_pwd_length1);
			edsurePassword.setFocusable(true);
			edsurePassword.requestFocus();
			return;
		}
		if (AbStrUtil.strLength(confirm_password) > 20) {
			showToast(R.string.error_confirm_pwd_length2);
			edsurePassword.setFocusable(true);
			edsurePassword.requestFocus();
			return;
		}
		if (!confirm_password.equals(login_password)) {
			showToast(R.string.error_pwd_match);
			edsurePassword.setFocusable(true);
			edsurePassword.requestFocus();
			return;
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("UserName", phone);
		map.put("PassWord", login_password);
		map.put("Device_Type", 3);
		map.put("User_ID", user_id);
		map.put("Channel_ID", channel_id);
		map.put("RegisterType", "mobile_phone");
		Log.i("info", user_id + "......" + channel_id);
		showProgress("正在注册，请稍候！");
		WebServiceUtils.callWebService(Constant.S_Register, map,
				new WebServiceCallBack() {

					public void callBack(Object result) {
						if (myProgressDialog.isShowing()) {
							dismissProgress();
						}
						Bean bean = JSON.parseObject(result.toString(),
								Bean.class);
						int state = bean.getState();
						switch (state) {
						case 1:
							showToast("注册成功!");
							if ("1".equals(sp.getString("isCheck", ""))) {
								Editor editor = sp.edit();
								editor.putString("User_Name", phone);
								editor.putString("User_Pwd", login_password);
								editor.commit();
							} else {
								Editor editor = sp.edit();
								editor.putString("User_Name", phone);
								editor.commit();
							}
							getActivity().finish();
							break;
						default:
							break;
						}
					}
				});
	}
}
