/**   
 * @Title: EmailFragment.java 
 * @Package cn.com.zhoufu.mouth.activity.mine 
 * @Description: TODO(邮箱注册fragment) 
 * @author 王小杰   
 * @date 2014-2-11 上午10:28:18 
 * @version V1.0   
 */
package cn.com.zhoufu.mouth.activity.mine;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
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
import cn.com.zhoufu.mouth.utils.CheckTextBox;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class EmailFragment extends BaseFragment {

	@ViewInject(R.id.edEmail)
	private EditText edEmail;

	@ViewInject(R.id.edPassword)
	private EditText edPassword;

	@ViewInject(R.id.edsurePassword)
	private EditText edsurePassword;

	@ViewInject(R.id.registerBtn)
	private Button registerBtn;

	String user_id, channel_id;

	public SharedPreferences sharedPreferences;

	private SharedPreferences sp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_emails_register,
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

	@OnClick(R.id.registerBtn)
	public void registerClick(View v) {
		register();
	}

	public void register() {
		final String email = edEmail.getText().toString().trim();
		final String password = edPassword.getText().toString().trim();
		String passwords = edsurePassword.getText().toString().trim();
		if (AbStrUtil.isEmpty(email)) {
			showToast(R.string.error_email);
			edEmail.setFocusable(true);
			edEmail.requestFocus();
			return;
		}
		if (CheckTextBox.isEmail(email) == false) {
			showToast(R.string.error_email_expr);
			edEmail.setFocusable(true);
			edEmail.requestFocus();
			return;
		}
		if (AbStrUtil.isEmpty(password)) {
			showToast(R.string.error_pwd);
			edPassword.setFocusable(true);
			edPassword.requestFocus();
			return;
		}
		if (password.length() < 6) {
			showToast(R.string.error_pwd_length1);
			edPassword.setFocusable(true);
			edPassword.requestFocus();
			return;
		}
		if (password.length() > 20) {
			showToast(R.string.error_pwd_length2);
			edPassword.setFocusable(true);
			edPassword.requestFocus();
			return;
		}
		if (AbStrUtil.isEmpty(passwords)) {
			showToast(R.string.error_confirm_pwd);
			edsurePassword.setFocusable(true);
			edsurePassword.requestFocus();
			return;
		}
		if (passwords.length() < 6) {
			showToast(R.string.error_confirm_pwd_length1);
			edsurePassword.setFocusable(true);
			edsurePassword.requestFocus();
			return;
		}
		if (passwords.length() > 20) {
			showToast(R.string.error_confirm_pwd_length2);
			edsurePassword.setFocusable(true);
			edsurePassword.requestFocus();
			return;
		}
		if (!password.equals(passwords)) {
			showToast(R.string.error_pwd_match);
			edsurePassword.setFocusable(true);
			edsurePassword.requestFocus();
			return;
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("UserName", email);
		map.put("PassWord", password);
		map.put("Device_Type", 3);
		map.put("User_ID", user_id);
		map.put("Channel_ID", channel_id);
		map.put("RegisterType", "email");

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
						Log.e("state", state + "");
						switch (state) {
						case 1:
							showToast("注册成功!");
							if ("1".equals(sp.getString("isCheck", ""))) {
								Editor editor = sp.edit();
								editor.putString("User_Name", email);
								editor.putString("User_Pwd", password);
								editor.commit();
							} else {
								Editor editor = sp.edit();
								editor.putString("User_Name", email);
								editor.commit();
							}
							getActivity().finish();
							break;
						default:
							showToast("注册失败!");
							break;
						}
					}
				});
	}
}