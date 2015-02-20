package cn.com.zhoufu.mouth.activity.home;

import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseActivity;
import cn.com.zhoufu.mouth.adapter.HomeCenterAdapter;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.model.HomeCenterInfo;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

public class HotActivity extends BaseActivity implements OnItemClickListener {
	private HomeCenterAdapter mAdapter;
	@ViewInject(R.id.hot_Listview)
	private ListView mlistview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot);
		ViewUtils.inject(this);
		setTitle("热门活动");
		mAdapter = new HomeCenterAdapter(mContext);
		mlistview.setAdapter(mAdapter);
		showProgress("正在加载数据...");
		homeCenter();
	}

	public void homeCenter() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("isapp_position", 1);
		WebServiceUtils.callWebService(Constant.S_Ad, map,
				new WebServiceCallBack() {
					public void callBack(Object result) {
						dismissProgress();
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							List<HomeCenterInfo> list = JSON.parseArray(
									bean.getData(), HomeCenterInfo.class);
							mAdapter.setList(list);
						}
					}
				});
	}

	@Override
	@OnItemClick({ R.id.hot_Listview })
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(mContext, PromotionActivity.class);
		intent.putExtra("promotion", mAdapter.getList().get(arg2)
				.getShopid_str());
		startActivity(intent);

	}
}
