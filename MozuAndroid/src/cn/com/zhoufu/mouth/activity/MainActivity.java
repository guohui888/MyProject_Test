/**   
 * @Title: HomeActivity.java 
 * @Package cn.com.zhoufu.mouth.activity.home 
 * @Description: TODO(首页activity) 
 * @author 王小杰
 * @date 2014-2-10 
 * @version V1.0   
 */
package cn.com.zhoufu.mouth.activity;

import java.util.HashMap;
import java.util.List;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.WelcomeActivity;
import cn.com.zhoufu.mouth.activity.cart.CartFragment;
import cn.com.zhoufu.mouth.activity.category.CategoryFragment;
import cn.com.zhoufu.mouth.activity.home.HomeFragment;
import cn.com.zhoufu.mouth.activity.mine.MineFragment;
import cn.com.zhoufu.mouth.activity.search.SearchFragment;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.AddCartInfo;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.model.HomeCenterInfo;
import cn.com.zhoufu.mouth.utils.AbFileUtil;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;
import cn.com.zhoufu.mouth.utils.XMLParser;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnCheckedChange;

public class MainActivity extends BaseActivity {

	@ViewInject(R.id.tab_group)
	private RadioGroup tabGroup;

	@ViewInject(R.id.classroom)
	private RadioButton classroom;

	@ViewInject(R.id.product_count)
	private TextView product_count;

	private int tabIndex;

	private long exitTime = 0;

	private OnForResultListener listener;

	// public static int agency_id = -100;

	Bundle bundle;

	private BroadcastReceiver receiver;

	private IntentFilter filter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(this, R.layout.activity_main);
		bundle = savedInstanceState;
		if (savedInstanceState == null)
			tabIndex = R.id.home;
		else
			tabIndex = savedInstanceState.getInt("tabIndex", R.id.home);
		filter = new IntentFilter(Constant.CART_CONTENT);
		filter.addAction(ACTION_CHANGE_RADIOBUTTON);
		filter.addAction(ACTION_CHANGE_RADIOBUTTON_CART);
		filter.addAction(ACTION_CHANGE_SEARCH);
		receiver = new ChangeReceiver();

		initData();
		checkBackGround();
	}

	private void initData() {
		switch (tabIndex) {
		case R.id.home:
			changeContent("home", HomeFragment.class, null);
			break;
		case R.id.search:
			changeContent("search", SearchFragment.class, null);
			break;
		case R.id.category:
			changeContent("category", CategoryFragment.class, null);
			break;
		case R.id.classroom:
			changeContent("cart", CartFragment.class, null);
			break;
		case R.id.personal:
			changeContent("personal", MineFragment.class, null);
			break;
		default:
			break;
		}
	}

	public interface OnForResultListener {
		public void onActivityResult(int requestCode, int resultCode,
				Intent data);
	}

	public void setListener(OnForResultListener listener) {
		this.listener = listener;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (listener != null) {
			listener.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// if (application.getTag() == 1) {
		// if (bundle == null)
		// tabIndex = R.id.cart;
		// else
		// tabIndex = bundle.getInt("tabIndex", R.id.cart);
		// cart.setChecked(true);
		// application.setTag(2);
		// } else {
		// if (bundle == null)
		// tabIndex = R.id.home;
		// else {
		// tabIndex = bundle.getInt("tabIndex", R.id.home);
		// }
		// application.setTag(2);
		// }
		cartList();
	}

	@OnCheckedChange(R.id.tab_group)
	public void onTabRroupChecked(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.home:
			tabIndex = R.id.home;
			changeContent("home", HomeFragment.class, null);
			break;
		case R.id.search:
			tabIndex = R.id.search;
			changeContent("search", SearchFragment.class, null);
			break;
		case R.id.category:
			tabIndex = R.id.category;
			changeContent("category", CategoryFragment.class, null);
			break;
		case R.id.classroom:
			tabIndex = R.id.classroom;
			changeContent("cart", CartFragment.class, null);
			break;
		case R.id.personal:
			tabIndex = R.id.personal;
			changeContent("personal", MineFragment.class, null);
			break;
		default:
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
		ft.commitAllowingStateLoss();
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tabIndex", tabIndex);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - exitTime >= 2000) {
				application.showToast("再按一次退出程序");
				exitTime = currentTime;
			} else {
				finish();
				System.exit(0);// 退出程序

			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}



	public void cartList() {
		if (!XMLParser.isNetworkAvailable(mContext)) {
			product_count.setVisibility(View.GONE);
			return;
		}
		// HashMap<String, Object> map = new HashMap<String, Object>();
		// map.put("UserID", application.getUser().getUser_id());
		WebServiceUtils.callWebService(Constant.S_CartList,
				application.addData(), new WebServiceCallBack() {
					public void callBack(Object result) {
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							List<AddCartInfo> list = JSON.parseArray(
									bean.getData(), AddCartInfo.class);
							if (list.size() == 0) {
								product_count.setVisibility(View.GONE);
							} else {
								int number = 0;
								for (int i = 0; i < list.size(); i++) {
									number += list.get(i).getGoods_number();
								}
								product_count.setText(number + "");
								product_count.setVisibility(View.VISIBLE);
								Log.e("name", number + "");
							}
						}
					}
				});
	}

	class ChangeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			// 监听接收到消息的广播
			if (action.equals(Constant.CART_CONTENT)) {
				cartList();
			}
			if (action.equals(ACTION_CHANGE_RADIOBUTTON)) {
				tabIndex = R.id.home;
				changeContent("cart", HomeFragment.class, null);
				((RadioButton) tabGroup.getChildAt(0)).setChecked(true);
			}
			if (action.equals(ACTION_CHANGE_RADIOBUTTON_CART)) {
				tabIndex = R.id.classroom;
				changeContent("cart", CartFragment.class, null);
				((RadioButton) tabGroup.getChildAt(3)).setChecked(true);
			}
			if (action.equals(ACTION_CHANGE_SEARCH)) {
				tabIndex = R.id.search;
				changeContent("search", SearchFragment.class, null);
				((RadioButton) tabGroup.getChildAt(1)).setChecked(true);
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (receiver.isOrderedBroadcast()) {
			unregisterReceiver(receiver);
		}
	}

	// Error receiving broadcast Intent {
	// act=cn.com.zhoufu.mouth.ACTION_CHANGE_RADIOBUTTON_CART flg=0x10 } in
	// cn.com.zhoufu.mouth.activity.MainActivity$ChangeReceiver@43832258

	@Override
	protected void onStart() {
		super.onStart();
		if (receiver != null)
			registerReceiver(receiver, filter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	// 检测更换主界面欢迎图
	public void checkBackGround() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("isapp_position", 3);
		WebServiceUtils.callWebService(Constant.S_Ad, map,
				new WebServiceCallBack() {
					public void callBack(Object result) {
						Log.e("info", result.toString());
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							List<HomeCenterInfo> list = JSON.parseArray(
									bean.getData(), HomeCenterInfo.class);
							if (list.size() == 0) {
								return;
							}
							HomeCenterInfo update = list.get(0);
							String url = Constant.HOME_IMAGE_URL
									+ update.getAd_code();
							String filepath = sharedPreferences.getString(
									WelcomeActivity.splashImageUrl, null);
							Log.e("", "---------111" + filepath);
							if (TextUtils.isEmpty(filepath)) {
								filepath = AbFileUtil.downFileToSD(url,
										update.getAd_code());
								Log.e("", "---------222" + filepath);
								sharedPreferences
										.edit()
										.putString(
												WelcomeActivity.splashImageUrl,
												filepath).commit();
							}
						}
					}
				});

	}

	public static final String KEY_CACHE_HOME_TOP = "KEY_CACHE_HOME_TOP";
	public static final String KEY_CACHE_HOME_BUTTOM = "KEY_CACHE_HOME_CENTER_1";
	public static final String KEY_CACHE_HOME_CENTER_1 = "KEY_CACHE_HOME_CENTER_2";
	public static final String KEY_CACHE_SERACH = "KEY_CACHE_SERACH";
	public static final String KEY_CACHE_Category = "KEY_CACHE_Category";
	public static final String KEY_CACHE_CART = "KEY_CACHE_CART";

	public static final String ACTION_CHANGE_RADIOBUTTON = "cn.com.zhoufu.mouth.ACTION_CHANGE_RADIOBUTTON";
	public static final String ACTION_CHANGE_RADIOBUTTON_CART = "cn.com.zhoufu.mouth.ACTION_CHANGE_RADIOBUTTON_CART";
	public static final String ACTION_CHANGE_SEARCH = "cn.com.zhoufu.mouth.ACTION_CHANGE_SEARCH";

}
