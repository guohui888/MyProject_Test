/**   
 * @Title: HomeFragment.java 
 * @Package cn.com.zhoufu.mouth.activity.home 
 * @Description: TODO(首页fragment) 
 * @author 王小杰
 * @date 2014-2-10 下午13:09:00 
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.activity.home;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseFragment;
import cn.com.zhoufu.mouth.activity.MainActivity;
import cn.com.zhoufu.mouth.activity.category.SearchActivity;
import cn.com.zhoufu.mouth.activity.mine.LoginActivity;
import cn.com.zhoufu.mouth.adapter.HomeButtomAdapter;
import cn.com.zhoufu.mouth.adapter.HomeTopAdapter;
import cn.com.zhoufu.mouth.adapter.HomeTwoAdapter;
import cn.com.zhoufu.mouth.adapter.MainHomeAdapter;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.model.CategaryInfo;
import cn.com.zhoufu.mouth.model.HomeButtomModel;
import cn.com.zhoufu.mouth.model.HomeCenterInfo;
import cn.com.zhoufu.mouth.model.HomeTwoModel;
import cn.com.zhoufu.mouth.model.isstoryInfo;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;
import cn.com.zhoufu.mouth.utils.XMLParser;
import cn.com.zhoufu.mouth.view.MyGridView;
import cn.com.zhoufu.mouth.view.MyListView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

public class HomeFragment extends BaseFragment implements OnPageChangeListener,
		OnClickListener {
	@ViewInject(R.id.main_home_gridView)
	private GridView mGridView;

	private int drawableID[] = { R.drawable.class_1, R.drawable.class_3,
			R.drawable.class_4, R.drawable.class_5 };

	private String classStr[] = { "特价专区", "P4", "P5", "P6" };
	@ViewInject(R.id.main_home_2_gridView)
	private MyGridView mTwoGridView;

	private HomeTwoAdapter mTwoAdapter;

	// private HomeTwoAdapter mTwoAdapter1;

	@ViewInject(R.id.main_home_2_listview)
	private MyListView mListView;

	@ViewInject(R.id.main_home_3_listview)
	private MyListView mListView2;

	private HomeButtomAdapter btmAdapter;

	// ---------------------------------------

	@ViewInject(R.id.btn_scan)
	private Button btnScan;

	@ViewInject(R.id.tvAddress)
	public static TextView tvAddress;

	@ViewInject(R.id.ly_search)
	private LinearLayout ly_search;

	@ViewInject(R.id.adv_pager)
	private ViewPager mViewPager;

	@ViewInject(R.id.viewGroup)
	private LinearLayout viewGroup;

	private ImageView[] imageViews = null;

	private ImageView imageView = null;

	private ScheduledExecutorService scheduledExecutorService;

	private int currentItem = 0; // 当前图片的索引号

	private List<HomeCenterInfo> list;

	// private HomeCenterAdapter mAdapter;

	double latitude = 0.0; // 纬度
	double longitude = 0.0; // 经度

	InputStream inputStream = null;// 输入流对象

	String city, district;

	String disString;

	String serverCode;

	String androidUrl;

	private Dialog dialog;

	public static Boolean gpsEnabled;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sharedPreferences = mAct.getSharedPreferences(Constant.USER_PREF,
				Context.MODE_PRIVATE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	private void dataInit() {
		mGridView.setAdapter(new MainHomeAdapter(mAct, drawableID, classStr));
		mTwoAdapter = new HomeTwoAdapter(mAct);
		// mTwoAdapter1 = new HomeTwoAdapter(mAct);
		mTwoGridView.setAdapter(mTwoAdapter);
		// mListView.setAdapter(mTwoAdapter1);
		btmAdapter = new HomeButtomAdapter(mContext);
		mListView2.setAdapter(btmAdapter);
	}

	// 加载中间数据
	private void loadTwoData() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		WebServiceUtils.callWebService(Constant.S_Index2, map,
				new WebServiceCallBack() {

					public void callBack(Object result) {
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							if (bean.getState() == 1) {
								mAct.sharedPreferences
										.edit()
										.putString(
												MainActivity.KEY_CACHE_HOME_CENTER_1,
												bean.getData()).commit();
								pullTwoData(bean.getData());
								Log.e("",
										"tag================================="
												+ bean.getData());
							} else {
								showToast(bean.getMsg());
							}
						}
					}
				});
	}

	/**
	 * 
	 * 解析中间数据
	 * 
	 * @param str
	 */
	private void pullTwoData(String str) {
		com.alibaba.fastjson.JSONObject jo = com.alibaba.fastjson.JSONObject
				.parseObject(str);
		String str1 = jo.getString("data1");
		List<HomeTwoModel> twoList1 = JSON.parseArray(str1, HomeTwoModel.class);
		mTwoAdapter.setList(twoList1);
		String str2 = jo.getString("data2");
		List<HomeTwoModel> twoList2 = JSON.parseArray(str2, HomeTwoModel.class);
		// mTwoAdapter1.setList(twoList2);
		Log.e("", "jj  " + twoList1.size() + "   " + twoList2.size());
	}

	/**
	 * 
	 * 加载底部数据
	 */
	private void loadButtomData() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		WebServiceUtils.callWebService(Constant.S_Index3, map,
				new WebServiceCallBack() {

					public void callBack(Object result) {
						mAct.dismissProgress();
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							if (bean.getState() == 1) {
								mAct.sharedPreferences
										.edit()
										.putString(
												MainActivity.KEY_CACHE_HOME_BUTTOM,
												bean.getData()).commit();
								btmAdapter.setList(pullButtomData(bean
										.getData()));
							} else {
								showToast(bean.getMsg());
							}
						}
					}
				});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		dataInit();
		getTopcache();
		getCentercache();
		getButtomcache();
		if (!XMLParser.isNetworkAvailable(mContext)) {
			application.showToast("网络未连接");
			return;
		}
		new Thread(new updateTask()).start();
	}

	/**
	 * 
	 * 解析底部数据
	 * 
	 * @param data
	 * @return
	 */
	private List<HomeButtomModel> pullButtomData(String data) {

		List<HomeButtomModel> btmList = new ArrayList<HomeButtomModel>();
		for (int i = 0; i < 4; i++) {
			HomeButtomModel btmModel = new HomeButtomModel();
			com.alibaba.fastjson.JSONObject o = com.alibaba.fastjson.JSONObject
					.parseObject(data);
			String data1 = o.getString("data" + (i + 1));
			com.alibaba.fastjson.JSONObject o1 = com.alibaba.fastjson.JSONObject
					.parseObject(data1);
			String data_1 = o1.getString("data1");
			List<HomeTwoModel> list1 = JSON.parseArray(data_1,
					HomeTwoModel.class);
			if (list1.size() > 0) {
				btmModel.setImgLarge(list1.get(0).getAd_code());
				btmModel.setShopid_str_left(list1.get(0).getShopid_str());
			}
			String data_2 = o1.getString("data2");
			List<HomeTwoModel> list2 = JSON.parseArray(data_2,
					HomeTwoModel.class);
			if (list2.size() > 1) {
				btmModel.setImgSmall_1(list2.get(0).getAd_code());
				btmModel.setShopid_str_r1(list2.get(0).getShopid_str());
				btmModel.setImgSmall_2(list2.get(1).getAd_code());
				btmModel.setShopid_str_r2(list2.get(1).getShopid_str());
			}
			String data_3 = o1.getString("data3");
			List<CategaryInfo> list3 = JSON.parseArray(data_3,
					CategaryInfo.class);
			Log.e("", "tag444   list3=" + list3.size());
			String name[] = new String[list3.size()];
			int id[] = new int[list3.size()];
			for (int j = 0; j < list3.size(); j++) {
				name[j] = list3.get(j).getCat_name();
				id[j] = list3.get(j).getCat_id();
				Log.e("", "tag444   name[j]=" + name[j]);
			}
			btmModel.setCat_id(id);
			btmModel.setCategoryName(name);
			btmList.add(btmModel);
		}
		return btmList;
	}

	private class updateTask implements Runnable {

		public void run() {
			try {
				update();
			} catch (Exception e) {
			}
		}

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cancel:
			dialog.dismiss();
			break;
		case R.id.no_update_btn:
			dialog.dismiss();
			break;
		case R.id.update_btn:
			Constant.SERVER_CONFIG_LOADPATH = androidUrl;
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse(Constant.SERVER_CONFIG_LOADPATH)));
			dialog.dismiss();
			break;
		}
	}

	@OnClick(R.id.tvAddress)
	public void addressClick(View v) {

		Intent intent = new Intent();
		intent.putExtra("city", city);
		if (MapsActivity.name == null) {
			intent.putExtra("district", disString);
		} else {
			intent.putExtra("district", MapsActivity.name);
		}
		intent.setClass(mContext, MapsActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.btn_scan)
	public void scanClick(View v) {

		startActivity(new Intent(mContext, MipcaActivityCapture.class));

	}

	@OnClick(R.id.ly_search)
	public void searchClick(View v) {
		mAct.sendBroadcast(new Intent(MainActivity.ACTION_CHANGE_SEARCH));
	}

	private void getButtomcache() {
		String str = mAct.sharedPreferences.getString(
				MainActivity.KEY_CACHE_HOME_BUTTOM, "");
		if ("".equals(str) || str == null) {
			showDefaultProgress();
			loadButtomData();
		} else {
			btmAdapter.setList(pullButtomData(str));
			loadButtomData();
		}
	}

	private void getTopcache() {
		String str = mAct.sharedPreferences.getString(
				MainActivity.KEY_CACHE_HOME_TOP, "");

		Log.e("top", str);
		if ("".equals(str) || str == null) {
			homeTop();
		} else {
			Bean bean = JSON.parseObject(str, Bean.class);
			List<HomeCenterInfo> list = JSON.parseArray(bean.getData(),
					HomeCenterInfo.class);
			initViewPager(list);
			homeTop();
		}
	}

	private void getCentercache() {
		String str = mAct.sharedPreferences.getString(
				MainActivity.KEY_CACHE_HOME_CENTER_1, "");
		if ("".equals(str) || str == null) {
			loadTwoData();
		} else {
			pullTwoData(str);
			loadTwoData();
		}
	}

	public void homeTop() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		WebServiceUtils.callWebService(Constant.S_Index, map,
				new WebServiceCallBack() {

					public void callBack(Object result) {
						mAct.sharedPreferences
								.edit()
								.putString(MainActivity.KEY_CACHE_HOME_TOP,
										result.toString()).commit();
						Log.e("top111   ", result.toString());
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							List<HomeCenterInfo> list = JSON.parseArray(
									bean.getData(), HomeCenterInfo.class);
							initViewPager(list);
						}
					}
				});
	}

	private void initViewPager(List<HomeCenterInfo> topList) {
		if (imageViews != null) {
			viewGroup.removeAllViews();
		}
		imageViews = new ImageView[topList.size()];
		for (int i = 0; i < topList.size(); i++) {
			imageView = new ImageView(getActivity());
			imageView.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.setMargins(10, 5, 0, 5);
			imageView.setLayoutParams(lp);
			imageViews[i] = imageView;
			if (i == 0) {
				imageViews[i].setBackgroundResource(R.drawable.dot_focus);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.dot_normal);
			}
			viewGroup.addView(imageViews[i]);
		}
		mViewPager.setAdapter(new HomeTopAdapter(mContext, topList));
		mViewPager.setOnPageChangeListener(this);
	}

	public void onPageScrollStateChanged(int arg0) {

	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	public void onPageSelected(int arg0) {
		for (int i = 0; i < imageViews.length; i++) {
			imageViews[arg0].setBackgroundResource(R.drawable.dot_focus);
			if (arg0 != i) {
				imageViews[i].setBackgroundResource(R.drawable.dot_normal);
			}
		}
	}

	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mViewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};

	@Override
	public void onStart() {
		super.onStart();
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 5,
				TimeUnit.SECONDS);
	};

	@Override
	public void onStop() {
		// 当Activity不可见的时候停止切换
		scheduledExecutorService.shutdown();
		super.onStop();
	}

	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (mViewPager) {
				currentItem = (currentItem + 1) % imageViews.length;
				handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}
	}

	@OnItemClick({ R.id.main_home_2_gridView, R.id.main_home_2_listview,
			R.id.main_home_gridView })
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		if (!XMLParser.isNetworkAvailable(mContext)) {
			application.showToast("网络未连接");
			return;
		}
		switch (arg0.getId()) {
		case R.id.main_home_2_gridView: {
			if (arg2 == 0 || arg2 == 1) {
				return;
			}
			Intent intent = new Intent(mContext, PromotionActivity.class);
			intent.putExtra("promotion", mTwoAdapter.getList().get(arg2)
					.getShopid_str());
			startActivity(intent);
		}
			break;
		case R.id.main_home_2_listview: {
			// Intent intent = new Intent(mContext, PromotionActivity.class);
			// intent.putExtra("promotion", mTwoAdapter1.getList().get(arg2)
			// .getShopid_str());
			// startActivity(intent);
		}
			break;
		case R.id.main_home_gridView:
			if (arg2 == 0) {
				startActivity(7);
			} else {
				startActivity(arg2 + 3);
			}
			// else if (arg2 == 5) {
			// mAct.startActivity(new Intent(mAct, SwingActivity.class));
			// } else if (arg2 == 6) {
			// mAct.startActivity(new Intent(mAct, HotActivity.class));
			// } else if (arg2 == 7) {
			// if (!XMLParser.isNetworkAvailable(mContext)) {
			// application.showToast("网络未连接");
			// return;
			// }
			// if (application.getUser().getUser_id() == 0) {
			// startActivity(new Intent(mContext, LoginActivity.class));
			// return;
			// }
			// Log.e("", "-----------------------"
			// + application.getUser().getIsSignin());
			// if (application.getUser().getIsSignin() > 0) {
			// showToast("您今天已经签到。");
			// return;
			// }
			// signData();
			// }
			break;
		}

	}

	private void startActivity(int position) {
		Intent intent = new Intent(mContext, PrefectureActivity.class);
		intent.putExtra("ad", position);
		startActivity(intent);
	}

	private void signData() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("UserID", application.getUser().getUser_id());
		Log.e("", "====================" + application.getUser().getUser_id());
		WebServiceUtils.callWebService(Constant.S_Signin, map,
				new WebServiceCallBack() {

					@Override
					public void callBack(Object result) {
						Bean bean = JSON.parseObject(result.toString(),
								Bean.class);
						showToast(bean.getMsg());
					}
				});

	}

	// -------------------------------------------------

	/**
	 * 是否有仓库
	 * 
	 * @param S_GpsEntrepot
	 */
	public void isstore(String S_GpsEntrepot, int id) {
		Log.e("Region_Name", S_GpsEntrepot);
		Log.e("region_id", id + "");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Region_Name", S_GpsEntrepot);
		map.put("region_id", id);
		WebServiceUtils.callWebService(Constant.S_GpsEntrepot, map,
				new WebServiceCallBack() {
					@Override
					public void callBack(Object result) {
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							if (bean.getState() == 1) {
								List<isstoryInfo> list = JSON.parseArray(
										bean.getData(), isstoryInfo.class);

								int agency_id = list.get(0).getAgency_id();
								sharedPreferences.edit()
										.putInt("Agency_id", agency_id)
										.commit();
							}
						}

					}
				});

	}

	// 更新
	public void update() {
		// String bugrank;
		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(Constant.UPDATE_VERSION_URL);
		Log.e("xml", xml);
		HashMap<String, String> map = (HashMap<String, String>) parser
				.ParseUpdateInfo(xml);
		serverCode = map.get("androidversion");
		androidUrl = map.get("androidurl");
		// bugrank = map.get("bugrank");
		int versionCode = 0;
		try {
			versionCode = XMLParser.getVersionCode(mContext);
			Log.e("", "tag-----------------" + versionCode + "   " + serverCode);
			if (Integer.parseInt(serverCode) > versionCode) {
				Message msg = new Message();
				msg.what = 1;
				// msg.obj = bugrank;
				mHandler.sendMessage(msg);
				Log.e("", "tag==============");
			} else {
				Message msg = new Message();
				msg.what = 2;
				mHandler.sendMessage(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if (myProgressDialog != null) {
					dismissProgress();
				}
				// if (msg.obj == null) {
				// return;
				// }
				updateDialog();
				break;
			case 2:
				if (myProgressDialog != null) {
					dismissProgress();
				}
				break;
			}
		};
	};

	public void updateDialog() {
		if (dialog == null) {
			dialog = new Dialog(mContext, R.style.dialog);
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
			dialog.setContentView(R.layout.dialog_update);

			Button btnTakePhoto = (Button) dialog.findViewById(R.id.update_btn);
			Button btnPickPhoto = (Button) dialog
					.findViewById(R.id.no_update_btn);
			Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
			// if (bugrank.equals("1")) {
			// btnCancel.setVisibility(View.GONE);
			// btnPickPhoto.setVisibility(View.GONE);
			// dialog.setOnDismissListener(new OnDismissListener() {
			// @Override
			// public void onDismiss(DialogInterface dialog) {
			// if (HomeFragment.gpsEnabled) {
			// }
			// mAct.finish();
			// System.exit(0);// 退出程序
			// }
			// });
			// }
			btnTakePhoto.setOnClickListener(this);
			btnPickPhoto.setOnClickListener(this);
			btnCancel.setOnClickListener(this);

			WindowManager m = ((Activity) mContext).getWindowManager();
			Display d = m.getDefaultDisplay();
			WindowManager.LayoutParams params = dialog.getWindow()
					.getAttributes();
			params.width = (int) (d.getWidth() * 0.99);
			params.height = (int) (d.getHeight() * 0.5);
			Window window = dialog.getWindow();
			window.setGravity(Gravity.BOTTOM);
		}
		dialog.show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
