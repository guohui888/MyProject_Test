/**   
 * @Title: SearchResultActivity.java 
 * @Package cn.com.zhoufu.mouth.activity.search 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-14 上午11:01:16
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.activity.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseActivity;
import cn.com.zhoufu.mouth.activity.cart.ProductDetailActivity;
import cn.com.zhoufu.mouth.adapter.SearchAdapter;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.AddCartInfo;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.model.SearchInfo;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;
import cn.com.zhoufu.mouth.utils.XMLParser;
import cn.com.zhoufu.mouth.view.pullview.AbOnListViewListener;
import cn.com.zhoufu.mouth.view.pullview.AbPullListView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnCheckedChange;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

public class SearchResultActivity extends BaseActivity implements
		AbOnListViewListener {

	@ViewInject(R.id.radioGroup)
	private RadioGroup radioGroup;

	@ViewInject(R.id.searchListView)
	private AbPullListView mListView;

	@ViewInject(R.id.edKeyword)
	private EditText edKeyword;

	@ViewInject(R.id.ly_search_top)
	private LinearLayout ly_search_top;

	@ViewInject(R.id.searchsBtn)
	private Button searchsBtn;

	@ViewInject(R.id.re_top)
	private RelativeLayout re_top;

	private SearchAdapter mAdapter;

	private List<SearchInfo> list;

	String keyword, sort;

	int pageIndex = 1, pageSize = 20;

	SearchInfo info;

	AddCartInfo addCartInfo;

	private String price;// 当前的价格

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(this, R.layout.activity_search_result);
		initView();
	}

	public void initView() {
		keyword = getIntent().getStringExtra("keyword");
		ly_search_top.setVisibility(View.VISIBLE);
		re_top.setVisibility(View.GONE);
		edKeyword.setText(keyword);
		sort = "click_count";
		mListView.setEmptyView(findViewById(R.id.myText));
		mAdapter = new SearchAdapter(handler, mContext);
		list = new ArrayList<SearchInfo>();
		mAdapter.setList(list);

		mListView.setAdapter(mAdapter);
		mListView.setPullLoadEnable(true);
		mListView.setPullRefreshEnable(true);
		mListView.setAbOnListViewListener(this);
		if (keyword != null) {
			search(pageIndex, pageSize, sort);
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				price = (String) msg.obj;
				Log.e("", "------------" + price);
				info = (SearchInfo) mAdapter.getItem(msg.arg1);
				addcartinfo();
				mAdapter.cartList(info);
				break;
			case 1:
				if (application.getUser().getUser_id() != 0) {
					if (info.getPresenttime() > info.getPromote_start_date()
							&& info.getPresenttime() < info
									.getPromote_end_date()
							&& info.getIs_promote() == 1) {
						if (info.getPromote_num() > 0
								&& mAdapter.currenCount < info.getPromote_num())
							addCart();
						else
							mActivity.showToast("已达到限购数量");
					} else {
						addCart();
					}
				} else {
					addCart();
				}
				break;
			}
		};
	};

	public void addcartinfo() {
		addCartInfo = new AddCartInfo();
		if (application.getUser().getUser_id() != 0) {
			addCartInfo.setUser_id(application.getUser().getUser_id());
			addCartInfo.setGoods_id(info.getGoods_id());
			addCartInfo.setGoods_sn(info.getGoods_sn());
			addCartInfo.setGoods_number(1);
			addCartInfo.setGoods_name(info.getGoods_name());
			addCartInfo.setMarket_price(info.getMarket_price() + "");
			addCartInfo.setGoods_price(price);
		} else {
			addCartInfo.setUser_id(0);
			addCartInfo.setGoods_id(info.getGoods_id());
			addCartInfo.setGoods_sn(info.getGoods_sn());
			addCartInfo.setGoods_number(1);
			addCartInfo.setGoods_name(info.getGoods_name());
			addCartInfo.setMarket_price(info.getMarket_price() + "");
			addCartInfo.setGoods_price(price);
			addCartInfo.setSession_id(application.DEVICE_ID);
		}
	}

	public void addCart() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("json", addCartInfo.toString());
		Log.e("LOG", addCartInfo.toString());
		showProgress("正在加入购物车");
		WebServiceUtils.callWebService(Constant.S_AddCart, map,
				new WebServiceCallBack() {
					public void callBack(Object result) {
						dismissProgress();
						Log.i("info", result.toString());
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							if (bean.getState() == 1) {
								application.showToast("添加到购物车成功");
								// cartList();
							} else {
								application.showToast("添加到购物车失败");
							}
						}
					}
				});
	}

	public void search(int pageIndex, int pageSize, String sort) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Log.e("info", keyword);
		map.put("TradeName", keyword);
		map.put("Page", pageIndex);
		map.put("Count", pageSize);
		map.put("Sort", sort);
		map.put("Sortord", "desc");
		// {Sort=click_count, TradeName=矿泉水, Count=20, Page=1, Sortord=asc}
		Log.e("----", map.toString());
		showProgress("正在努力加载中");
		WebServiceUtils.callWebService(Constant.S_SearchGoods, map,
				new WebServiceCallBack() {

					public void callBack(Object result) {
						dismissProgress();
						Log.i("info", result.toString());
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							List<SearchInfo> list = JSON.parseArray(
									bean.getData(), SearchInfo.class);
							for (int i = 0; i < list.size(); i++) {
								Log.e("tag", list.get(i).getPresenttime()
										+ "  "
										+ list.get(i).getPromote_end_date()
										+ "  "
										+ list.get(i).getPromote_start_date());
							}
							mAdapter.addAll(list);
						}
					}
				});
	}

	@OnClick(R.id.searchsBtn)
	public void ClickSearchBtn(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edKeyword.getApplicationWindowToken(), 0);
		keyword = edKeyword.getText().toString().trim();
		pageIndex = 1;
		mAdapter.removeAll();
		search(pageIndex, pageSize, sort);
	}

	@OnItemClick(R.id.searchListView)
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (!XMLParser.isNetworkAvailable(mContext)) {
			application.showToast("网络未连接");
			return;
		}
		Log.e("tag", "...");
		TextView tv = (TextView) arg1.findViewById(R.id.shop_price);
		Intent intent = new Intent(mContext, ProductDetailActivity.class);
		intent.putExtra("info", (SearchInfo) mAdapter.getItem((int) (arg3)));
		intent.putExtra("index", 1);
		intent.putExtra("price", tv.getText().toString());
		startActivity(intent);
	}

	@OnCheckedChange(R.id.radioGroup)
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radioButton1:
			sort = "click_count";
			pageIndex = 1;
			mAdapter.removeAll();
			search(pageIndex, pageSize, sort);
			break;
		case R.id.radioButton2:
			sort = "shop_price";
			pageIndex = 1;
			mAdapter.removeAll();
			search(pageIndex, pageSize, sort);
			break;
		case R.id.radioButton3:
			sort = "last_update";
			pageIndex = 1;
			mAdapter.removeAll();
			search(pageIndex, pageSize, sort);
			break;
		case R.id.radioButton4:
			sort = "add_time";
			pageIndex = 1;
			mAdapter.removeAll();
			search(pageIndex, pageSize, sort);
			break;
		}
	}

	public void onRefresh() {
		if (keyword == null || "".equals(keyword)) {
			return;
		}
		pageIndex = 1;
		mAdapter.removeAll();
		search(pageIndex, pageSize, sort);
		mListView.stopRefresh();
	}

	public void onLoadMore() {
		if (keyword == null || "".equals(keyword)) {
			return;
		}
		pageIndex++;
		search(pageIndex, pageSize, sort);
		mListView.stopLoadMore();
	}
}
