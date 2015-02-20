/**   
 * @Title: PromotionActivity.java 
 * @Package cn.com.zhoufu.mouth.activity.home 
 * @Description: TODO(促销商品) 
 * @author 王小杰   
 * @date 2014-2-14 下午4:08:05
 * @version V1.0   
 */
package cn.com.zhoufu.mouth.activity.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseActivity;
import cn.com.zhoufu.mouth.activity.cart.ProductDetailActivity;
import cn.com.zhoufu.mouth.adapter.PromationAdapter;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.model.SearchInfo;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;
import cn.com.zhoufu.mouth.view.pullview.AbMultiColumnAdapterView;
import cn.com.zhoufu.mouth.view.pullview.AbMultiColumnAdapterView.OnItemClickListener;
import cn.com.zhoufu.mouth.view.pullview.AbMultiColumnListView;
import cn.com.zhoufu.mouth.view.pullview.AbOnListViewListener;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.view.annotation.ViewInject;

public class PromotionActivity extends BaseActivity implements
		AbOnListViewListener {

	@ViewInject(R.id.promationListView)
	private AbMultiColumnListView mListView;

	String promotion;

	private List<SearchInfo> list;

	private PromationAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(this, R.layout.activity_promotion);
		setTitle("促销商品");
		initView();

	}

	public void initView() {
		promotion = getIntent().getStringExtra("promotion");
		mAdapter = new PromationAdapter(mContext);
		list = new ArrayList<SearchInfo>();
		mAdapter.setList(list);
		mListView.setAdapter(mAdapter);
		promotion();
		mListView.setPullLoadEnable(false);
		mListView.setPullRefreshEnable(false);
		mListView.setAbOnListViewListener(this);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AbMultiColumnAdapterView<?> parent,
					View view, int position, long id) {
				Intent intent = new Intent(mContext,
						ProductDetailActivity.class);
				intent.putExtra("info",
						((SearchInfo) mAdapter.getItem((int) id)));
				if (application.getUser().getUser_id() != 0) {
					intent.putExtra("index", 0);// 会员
				} else {
					intent.putExtra("index", 1);
				}
				TextView tv = (TextView) view.findViewById(R.id.tvPrice);
				intent.putExtra("price", tv.getText().toString());
				startActivity(intent);
			}
		});
	}

	public void promotion() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("shopid_str", promotion);
		Log.e("", "promotion=" + promotion);
		showProgress("正在努力加载中");
		WebServiceUtils.callWebService(Constant.S_AdCommodity, map,
				new WebServiceCallBack() {
					public void callBack(Object result) {
						dismissProgress();
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							List<SearchInfo> list = JSON.parseArray(
									bean.getData(), SearchInfo.class);
							if (list.size() == 0)
								mActivity.showToast("暂无数据");
							mAdapter.addAll(list);
						} else {
							showToast("加载失败");
						}
					}
				});
	}

	public void onRefresh() {
	}

	public void onLoadMore() {

	}

}
