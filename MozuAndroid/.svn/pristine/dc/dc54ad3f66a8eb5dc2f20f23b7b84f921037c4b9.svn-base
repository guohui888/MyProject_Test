/**   
 * @Title: CartFragment.java 
 * @Package cn.com.zhoufu.mouth.activity.cart 
 * @Description: TODO(购物车fragment) 
 * @author 王小杰
 * @date 2014-2-11 下午1:56:56 
 * @version V1.0   
 */
package cn.com.zhoufu.mouth.activity.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseFragment;
import cn.com.zhoufu.mouth.activity.MainActivity;
import cn.com.zhoufu.mouth.adapter.CartAdapter;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.AddCartInfo;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.model.StockInfo;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;
import cn.com.zhoufu.mouth.utils.XMLParser;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class CartFragment extends BaseFragment {

	public static int sumNumber = 0;

	@ViewInject(R.id.cartListView)
	private ListView mListView;

	@ViewInject(R.id.right_button2)
	private Button right_button;

	@ViewInject(R.id.ly_empty)
	private LinearLayout ly_empty;

	@ViewInject(R.id.tvTotalPrice)
	private TextView tvTotalPrice;

	@ViewInject(R.id.accounts)
	private Button accounts;

	@ViewInject(R.id.table_Index_home)
	private Button table_Index_home;

	private List<AddCartInfo> list;

	private CartAdapter mAdapter;

	ArrayList<AddCartInfo> listSelect;

	ArrayList<AddCartInfo> listdelete;

	private double totalPrice;

	private AddCartInfo info;
	private int tag = 1;
	private int count = 0;

	private StockInfo stock;

	private LayoutInflater inflater;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		View view = inflater.inflate(R.layout.fragment_cart, container, false);
		ViewUtils.inject(this, view);
		initView();
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		tvTotalPrice.setText("￥0.0");
		list.clear();
		count = 0;
		listdelete.clear();
		listSelect.clear();
		Intent intent = new Intent(Constant.CART_CONTENT);
		mContext.sendBroadcast(intent);
		cartList();
	}

	public void initView() {
		listSelect = new ArrayList<AddCartInfo>();
		listdelete = new ArrayList<AddCartInfo>();
		list = new ArrayList<AddCartInfo>();
		mAdapter = new CartAdapter(mContext, listSelect, handler);
		mAdapter.setList(list);
		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ImageView imageView = (ImageView) arg1
						.findViewById(R.id.selected);
				if (imageView.getTag() == null
						&& imageView.getVisibility() == View.GONE) {
					imageView.setVisibility(View.VISIBLE);
					imageView.setTag(arg2);
					listSelect.add(list.get(arg2));
				} else {
					imageView.setVisibility(View.GONE);
					imageView.setTag(null);
					if (listSelect.contains(list.get(arg2))) {
						listSelect.remove(list.get(arg2));
					}
				}
				totalPrice = 0.0;
				if (listSelect.size() == 0) {
					for (int i = 0; i < list.size(); i++) {
						BigDecimal b1 = new BigDecimal(list.get(i)
								.getGoods_number()
								* Double.parseDouble(list.get(i)
										.getGoods_price()));
						double discount = b1.setScale(1,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						totalPrice += discount;
					}
				} else {
					for (int i = 0; i < listSelect.size(); i++) {
						BigDecimal b1 = new BigDecimal(listSelect.get(i)
								.getGoods_number()
								* Double.parseDouble(listSelect.get(i)
										.getGoods_price()));
						double discount = b1.setScale(1,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						totalPrice += discount;
					}
				}
				BigDecimal b1 = new BigDecimal(totalPrice);
				double discount = b1.setScale(1, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				tvTotalPrice.setText("￥" + discount);
			}
		});
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				double price = (Double) msg.obj;
				totalPrice = totalPrice - price;
				BigDecimal b1 = new BigDecimal(totalPrice);
				double discount = b1.setScale(1, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				tvTotalPrice.setText("￥" + discount);
				break;
			case 2:
				double prices = (Double) msg.obj;
				totalPrice += prices;
				BigDecimal b3 = new BigDecimal(totalPrice);
				double discount3 = b3.setScale(1, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				tvTotalPrice.setText("￥" + discount3);
				break;

			}
		};
	};

	//
	@OnClick(R.id.table_Index_home)
	public void Clickhome(View v) {
		mAct.sendBroadcast(new Intent(MainActivity.ACTION_CHANGE_RADIOBUTTON));
	}

	public void cartList() {
		if (!XMLParser.isNetworkAvailable(mContext)) {
			application.showToast("网络未连接");
			return;
		}
		showProgress("正在加载中");
		WebServiceUtils.callWebService(Constant.S_CartList,
				application.addData(), new WebServiceCallBack() {
					public void callBack(Object result) {
						dismissProgress();
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							list = JSON.parseArray(bean.getData(),
									AddCartInfo.class);
							mAdapter.setList(list);
							setTotalPrice();
						}
					}
				});
	}

	@OnClick(R.id.right_button2)
	public void clickDetele(View v) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < listSelect.size(); i++) {
			String goodsId = listSelect.get(i).getGoods_id() + "";
			if (listSelect.size() == 1) {
				sb.append(goodsId);
			} else {
				sb.append(goodsId).append(",");
			}
		}
		if (list.size() == 0) {
			application.showToast("购物车暂无商品");
			return;
		}
		if (listSelect.size() == 0) {
			application.showToast("请选择要删除的商品");
			return;
		}
		for (int i = 0; i < listSelect.size(); i++) {
			list.remove(listSelect.get(i));
			Log.e("", "==============" + i);
		}
		Log.e("", "tag===" + list.size() + "   " + listSelect.size());
		mAdapter.setList(list);
		setTotalPrice();
		deteleCart(sb.toString());
	}

	/**
	 * 计算总价
	 * 
	 */
	private void setTotalPrice() {

		Log.e("", "tag   " + list.size());
		if (list.size() == 0) {
			ly_empty.setVisibility(View.VISIBLE);
		} else {
			totalPrice = 0.0;
			for (int i = 0; i < list.size(); i++) {
				BigDecimal b1 = new BigDecimal(list.get(i).getGoods_number()
						* Double.parseDouble(list.get(i).getGoods_price()));
				double discount = b1.setScale(1, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				totalPrice += discount;
			}
			BigDecimal b1 = new BigDecimal(totalPrice);
			double discount = b1.setScale(1, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			tvTotalPrice.setText("￥" + discount);
		}
	}

	public void deteleCart(String sb) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Goods_ID", sb);
		map.put("UserID", application.getUser().getUser_id());
		if (application.getUser().getUser_id() == 0) {
			map.put("session_id", application.DEVICE_ID);
		} else {
			map.put("session_id", "");
		}
		Log.e("", "--" + map.toString());
		WebServiceUtils.callWebService(Constant.S_DeleteCart, map,
				new WebServiceCallBack() {
					public void callBack(Object result) {
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							Log.e("", "----" + bean.toString());
							if (bean.getState() == 1) {
								application.showToast("删除商品成功");
								listSelect = null;
								listSelect = new ArrayList<AddCartInfo>();
								tag = 2;
								// list = null;
								count = 0;
								// list = new ArrayList<AddCartInfo>();
								// mAdapter.removeAll();
								// cartList();
								final Intent intent = new Intent(
										Constant.CART_CONTENT);
								getActivity().sendBroadcast(intent);
							} else {
								application.showToast("删除商品失败");
							}
						}
					}
				});
	}

	@OnClick(R.id.accounts)
	public void clickAccounts(View v) {
		if (listSelect.size() == 0) {
			application.showToast("您还没有选择要结算的商品");
			return;
		}
		Intent intent = new Intent(mAct, SureOrderActivity.class);
		intent.putExtra("list", listSelect);
		intent.putExtra("totalPrice", totalPrice + "");
		startActivity(intent);
	}

}
