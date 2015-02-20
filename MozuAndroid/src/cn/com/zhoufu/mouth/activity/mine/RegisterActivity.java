/**   
 * @Title: RegisterActivity.java 
 * @Package cn.com.zhoufu.mouth.activity.mine 
 * @Description: TODO(注册activity) 
 * @author 王小杰
 * @date 2014-2-10 下午12:31:43 
 * @version V1.0   
 */
package cn.com.zhoufu.mouth.activity.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseActivity;

import com.lidroid.xutils.view.annotation.ViewInject;

public class RegisterActivity extends BaseActivity implements
		OnCheckedChangeListener {

	@ViewInject(R.id.radioGroup)
	private RadioGroup radioGroup;

	private int tabIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(this, R.layout.activity_register);
		setTitle("用户注册");
		if (savedInstanceState == null)
			tabIndex = R.id.phone;
		else
			tabIndex = savedInstanceState.getInt("tabIndex", R.id.phone);
		initView();
		initDate();
	}

	public void initView() {
		radioGroup.setOnCheckedChangeListener(this);
	}

	public void initDate() {
		switch (tabIndex) {
		case R.id.phone:
			changeContent("Fragment1", PhoneFragment.class, null);
			break;
		case R.id.email:	
			 changeContent("Fragment2", EmailFragment.class, null);
			break;
		}
	}

	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.phone:
			tabIndex = R.id.phone;
			changeContent("Fragment1", PhoneFragment.class, null);
			break;
		case R.id.email:
			tabIndex = R.id.email;
			changeContent("Fragment2", EmailFragment.class, null);
			break;
		}
	}

	private void changeContent(String tag, Class<?> cls, Bundle bundle) {
		Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		if (fragment == null) {
			ft.replace(R.id.content,
					Fragment.instantiate(this, cls.getName(), bundle), tag);
		} else {
			ft.replace(R.id.content, fragment, tag);
		}
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
