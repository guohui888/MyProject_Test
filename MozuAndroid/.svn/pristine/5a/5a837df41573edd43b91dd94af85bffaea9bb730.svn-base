/**   
 * @Title: MessageFragment.java 
 * @Package cn.com.zhoufu.mouth.activity.mine 
 * @Description: TODO(找回密码) 
 * @author A18ccms A18ccms_gmail_com   
 * @date 2014-2-11 上午10:44:45 
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.activity.mine;

import java.util.HashMap;

import android.os.Bundle;
import android.os.CountDownTimer;
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

public class ResetMessageFragment extends BaseFragment {

	@ViewInject(R.id.edPhone)
	private EditText mEtPhone;

	@ViewInject(R.id.btn_get_check_code)
	private Button mBtnGetCheckCode;

	@ViewInject(R.id.edCheckNumber)
	private EditText mEtCheckCode;

	@ViewInject(R.id.btn_find_pwd)
	private Button mBtnFindPwd;

	@ViewInject(R.id.edPasswords)
	private EditText edPasswords;

	@ViewInject(R.id.edsurePassword)
	private EditText edsurePassword;

	private String userId, verificationcode;

	private MyCount mycount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_reset_message,
				container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@OnClick(R.id.btn_get_check_code)
	public void onGetCheckCodeClick(View v) {
		String phone = mEtPhone.getText().toString().trim();
		if (AbStrUtil.isEmpty(phone)) {
			showToast(R.string.error_phone);
			mEtPhone.setFocusable(true);
			mEtPhone.requestFocus();
			return;
		}
		if (!AbStrUtil.isMobileNo(phone)) {
			showToast(R.string.error_phone_expr);
			mEtPhone.setFocusable(true);
			mEtPhone.requestFocus();
			return;
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("User_Name", phone);
		map.put("Type", "mobile");
		showToast("正在获取验证码");
		if (mycount == null)
			mycount = new MyCount(120000, 1000);
		mycount.start();
		WebServiceUtils.callWebService(Constant.S_GetBackPassword, map,
				new WebServiceCallBack() {
					public void callBack(Object result) {
						dismissProgress();
						Bean bean = JSON.parseObject(result.toString(),
								Bean.class);
						int state = bean.getState();
						switch (state) {
						case 2:
							userId = JSON.parseObject(bean.getData())
									.getString("user_id");
							verificationcode = JSON.parseObject(bean.getData())
									.getString("verificationcode");
							break;
						default:
							break;
						}
					}
				});
	}

	@OnClick(R.id.btn_find_pwd)
	public void onFindPwdClick(View v) {
		updatePassword();
	}

	public void updatePassword() {
		String pass = edPasswords.getText().toString().trim();
		String pass2 = edsurePassword.getText().toString().trim();
		String checkCode2 = mEtCheckCode.getText().toString().trim();
		if ("".equals(pass)) {
			application.showToast("请输入密码");
			return;
		}
		if ("".equals(pass2)) {
			application.showToast("请输入确认密码");
			return;
		}
		if (!pass.equals(pass2)) {
			application.showToast("两次输入的密码不一致");
			return;
		}
		if (!pass.equals(pass2)) {
			application.showToast("两次输入的密码不一致");
			return;
		}
		if (AbStrUtil.isEmpty(checkCode2)) {
			showToast(R.string.error_check_code);
			mEtCheckCode.setFocusable(true);
			mEtCheckCode.requestFocus();
			return;
		}
		if (!verificationcode.equals(checkCode2)) {
			showToast(R.string.error_check_code_match);
			return;
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("User_ID", userId);
		map.put("Password", pass2);
		showProgress("正在修改密码");
		WebServiceUtils.callWebService(Constant.UPDATE_PASSWORD, map,
				new WebServiceCallBack() {

					public void callBack(Object result) {
						dismissProgress();
						Log.i("info", "" + result.toString());
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							if (bean.getState() == 1) {
								application.showToast("修改密码成功");
								getActivity().finish();
							} else {
								application.showToast("修改密码失败");
							}
						}
					}
				});
	}

	/* 定义一个倒计时的内部类 */
	class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			mBtnGetCheckCode.setEnabled(true);
			mBtnGetCheckCode.setText("获取验证码");
		}

		@Override
		public void onTick(long millisUntilFinished) {
			mBtnGetCheckCode.setEnabled(false);
			mBtnGetCheckCode.setText("重新获取(" + millisUntilFinished / 1000
					+ ")秒");
		}
	}
}
