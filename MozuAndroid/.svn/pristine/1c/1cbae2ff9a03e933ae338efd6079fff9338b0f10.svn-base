package cn.com.zhoufu.mouth.activity.cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseFragment;
import cn.com.zhoufu.mouth.adapter.AttributeAdapter;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.AttributeInfo;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;
import cn.com.zhoufu.mouth.view.MyListView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class GoodsAttributeFragment extends BaseFragment {

	@ViewInject(R.id.listviews)
	private MyListView mListView;

	String goodsId;

	private AttributeAdapter mAdapter;

	private List<AttributeInfo> list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_goods_attribute,
				container, false);
		ViewUtils.inject(this, view);
		Bundle bundle = getArguments();
		goodsId = bundle.getString("id");
		initView();
		return view;
	}

	public void initView() {
		list = new ArrayList<AttributeInfo>();
		mAdapter = new AttributeAdapter(getActivity());
		mAdapter.setList(list);
		mListView.setAdapter(mAdapter);
		attribute();
	}

	public void attribute() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Goods_ID", goodsId);

		Log.e("Goodsid", goodsId + "");
		WebServiceUtils.callWebService(Constant.S_Attribute, map,
				new WebServiceCallBack() {

					public void callBack(Object result) {
						Log.i("S_Attribute", result.toString());
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							if (bean.getState() == 1) {
								List<AttributeInfo> list = JSON.parseArray(
										bean.getData(), AttributeInfo.class);
								mAdapter.addAll(list);
							}
						}
					}
				});
	}
}
