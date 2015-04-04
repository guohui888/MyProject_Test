/**   
 * @Title: SureOrderActivity.java 
 * @Package cn.com.zhoufu.mouth.activity.cart 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-18 下午3:50:01
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.activity.cart;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseActivity;
import cn.com.zhoufu.mouth.activity.MainActivity;
import cn.com.zhoufu.mouth.activity.cart.pay.Keys;
import cn.com.zhoufu.mouth.activity.cart.pay.Result;
import cn.com.zhoufu.mouth.activity.cart.pay.Rsa;
import cn.com.zhoufu.mouth.activity.mine.AddressActivity;
import cn.com.zhoufu.mouth.adapter.SureAdapter;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.AddCartInfo;
import cn.com.zhoufu.mouth.model.AddOrderInfo;
import cn.com.zhoufu.mouth.model.AddreInfo;
import cn.com.zhoufu.mouth.model.AddressInfo;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.model.GoodsListInfo;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;
import cn.com.zhoufu.mouth.view.MyListView;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnCheckedChange;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SureOrderActivity extends BaseActivity {

	@ViewInject(R.id.tvTotalPrice)
	private TextView tvTotalPrice;

	@ViewInject(R.id.sureListView)
	private MyListView mListView;

	@ViewInject(R.id.ly_address)
	private LinearLayout ly_address;

	@ViewInject(R.id.radioGroup)
	private RadioGroup radioGroup;

	@ViewInject(R.id.radioGroup2)
	private RadioGroup radioGroup2;

	@ViewInject(R.id.radioButton5)
	private RadioButton radiobutton5;

	@ViewInject(R.id.tvAddress)
	private TextView tvAddress;

	@ViewInject(R.id.tvAddressName)
	private TextView tvAddressName;

	@ViewInject(R.id.tvAddressTel)
	private TextView tvAddressTel;

	@ViewInject(R.id.edTime)
	private EditText edTime;
	
	@ViewInject(R.id.et_number)
	private EditText etNumber;//数量
	
	@ViewInject(R.id.btn_add)
	private Button btnAdd;
	
	@ViewInject(R.id.btn_plus)
	private Button btnPlus;
	int num=1;//数量

	@ViewInject(R.id.submitOrderBtn)
	private Button submitOrderBtn;

	private float totalPrice;

	private List<AddCartInfo> list;

	private List<AddressInfo> list1;

	private SureAdapter mAdapter;

	private AddressInfo info;

	private AddreInfo info1;

	private int pay_id = 1, shipping_id = 6;

	private String pay_name = "货到付款", shipping_name = "模组速递";

	private String best_time = "尽快发货";

	private static final int RQF_PAY = 1;

	private static final int RQF_LOGIN = 2;

	String orderNo;

	private boolean isshow;

	private Bean bean;

	private SharedPreferences sp;
	private String order_sn;
	private String OrderID;

	private List<Bean> erroeBeans = new ArrayList<Bean>();
	int Goods_Number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(this, R.layout.activity_sure_order);
		setTitle("确认订单");
		info = new AddressInfo();
		info1 = new AddreInfo();
		initView();
	}

	@SuppressWarnings("unchecked")
	public void initView() {
		Intent intent = getIntent();
		list = (List<AddCartInfo>) intent.getSerializableExtra("list");
		Log.e("list", list.toString());
		String monet = getIntent().getStringExtra("totalPrice");
		String money_user = monet.substring(0, (monet.lastIndexOf(".") + 2));
		totalPrice = Float.parseFloat(money_user);
		if (application.getUser().getUser_id() == 0) {
			submitOrderBtn.setText("立即支付");
		}
		tvTotalPrice.setText("￥" + totalPrice);
		update();
		tvAddress.setText("您还没有选择收货地址");
		sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
		if (application.getUser().getUser_id() == 0) {
			radiobutton5.setVisibility(View.GONE);
		} else {
			radiobutton5.setVisibility(View.GONE);
		}
		mAdapter = new SureAdapter(mContext);
		mAdapter.setList(list);
		mListView.setAdapter(mAdapter);
		btnAdd.setTag("-");
		btnPlus.setTag("+");
		
		//设置输入类型为数字
		etNumber.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
		etNumber.setText(String.valueOf(num));
		setViewListener();
	}
	
	/**
	 * 设置文本变化相关监听事件
	 */
	private void setViewListener() {
		btnAdd.setOnClickListener(new OnButtonClickListener());
		btnPlus.setOnClickListener(new OnButtonClickListener());
		etNumber.addTextChangedListener(new OnTextChangeListener());
	}

	/**
	 * 加减按钮事件监听器
	 * 
	 * 
	 */
	class OnButtonClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			String numString = etNumber.getText().toString();
			if (numString == null || numString.equals("")) {
				num = 1;
				etNumber.setText("1");
				tvTotalPrice.setText("￥" + totalPrice*num);
			} else {
				if (v.getTag().equals("-")) {
					if (++num < 1) // 先加，再判断
					{
						num--;
						btnPlus.setClickable(false);
//						Toast.makeText(MainActivity.this, "请输入一个大于0的数字",
//								Toast.LENGTH_SHORT).show();
					} else {
						etNumber.setText(String.valueOf(num));
						tvTotalPrice.setText("￥" + totalPrice*num);
					}
				} else if (v.getTag().equals("+")) {
					if (--num < 1) // 先减，再判断
					{
						num++;
					} else {
						etNumber.setText(String.valueOf(num));
						tvTotalPrice.setText("￥" + totalPrice*num);
					}
				}
			}
		}
	}

	/**
	 * EditText输入变化事件监听器
	 */
	class OnTextChangeListener implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
			String numString = s.toString();
			if (numString == null || numString.equals("")) {
				num = 0;
			} else {
				int numInt = Integer.parseInt(numString);
				if (numInt < 0) {
					btnPlus.setClickable(false);
				} else {
					// 设置EditText光标位置 为文本末端
					etNumber.setSelection(etNumber.getText().toString().length());
					num = numInt;
				}
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

	}

	public void update() {
		list1 = new ArrayList<AddressInfo>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("UserID", application.getUser().getUser_id());
		showProgress("正在加载中");
		WebServiceUtils.callWebService(Constant.S_ShippingAddressList, map,
				new WebServiceCallBack() {
					public void callBack(Object result) {
						dismissProgress();
						Bean bean = JSON.parseObject(result.toString(),
								Bean.class);
						int state = bean.getState();
						switch (state) {
						case 1:
							list1 = JSON.parseArray(bean.getData(),
									AddressInfo.class);
							if (list1 == null)
								return;
							AddressInfo add = getAddressInfo();
							info = add;
							if (add != null) {
								String addressname = add.getConsignee();
								String Tel = add.getTel();
								tvAddress.setText(add.getPrivoceName() + "省"
										+ add.getCityName() + "市"
										+ add.getDinstrictName()
										+ add.getAddress());
								tvAddressName.setText(addressname);
								tvAddressTel.setText(Tel);
							}
							if (add == null) {
							}
							break;
						default:
							break;
						}
					}
				});

	}

	private AddressInfo getAddressInfo() {
		for (int i = 0; i < list1.size(); i++) {
			if (list1.get(i).getAddressid() == list1.get(i).getAddress_id()) {
				return list1.get(i);
			}
		}
		return list1.get(0);
	}

	@OnClick(R.id.ly_address)
	public void clickAddress(View v) {
		Intent intent = new Intent(mContext, AddressActivity.class);
		intent.putExtra("tag", "1");
		startActivityForResult(intent, 10);
	}

	@OnCheckedChange(R.id.radioGroup)
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radioButton1:
			AlertDialog();
			pay_id = 1;
			pay_name = "货到付款";
			break;
		case R.id.radioButton2:
			pay_id = 3;
			pay_name = "支付宝";
			break;
		case R.id.radioButton5:
			pay_id = 4;
			pay_name = "余额支付";
			break;
		}
	}

	@OnCheckedChange(R.id.radioGroup2)
	public void onCheckedChanged2(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radioButton3:
			edTime.setVisibility(View.GONE);
			best_time = "尽快发货";
			break;
		case R.id.radioButton4:
			best_time = edTime.getText().toString().trim();
			edTime.setVisibility(View.VISIBLE);
			break;
		}
	}

	public void AlertDialog() {
		new AlertDialog.Builder(mActivity).setTitle("模组折扣")
				.setMessage("如有疑问请咨询客服 0755-83838000")
				.setNeutralButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setPositiveButton("咨询", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						Intent intent = new Intent();
						intent.setAction("android.intent.action.CALL");
						intent.setData(Uri.parse("tel:075583838000"));
						startActivity(intent);

					}
				}).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 10) {
			if (resultCode == RESULT_OK) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					if (application.getUser().getUser_id() != 0) {
						info = (AddressInfo) extras.getSerializable("info");
						tvAddress.setText(info.getPrivoceName() + "省"
								+ info.getCityName() + "市"
								+ info.getDinstrictName() + info.getAddress());
						tvAddressName.setText(info.getConsignee());
						tvAddressTel.setText(info.getTel());
					} else {
						info1 = (AddreInfo) extras.getSerializable("info1");
						tvAddress.setText(info1.getProviceName() + "省"
								+ info1.getCityName() + "市"
								+ info1.getPlacename() + info1.getAddress());
						Log.e("", "=====" + info1.toString());
						tvAddressName.setText(info1.getConsignee());
						tvAddressTel.setText(info1.getTel());
					}
				}
			}
		}
	}

	public void submit() {
		if (application.getUser().getUser_id() != 0) {
			if ("".equals(info.getConsignee()) || info.getConsignee() == null) {
				application.showToast("请选择收货地址");
				return;
			}
		} else {
			if ("".equals(info1.getConsignee()) || info1.getConsignee() == null) {
				application.showToast("请选择收货地址");
				return;
			}
		}
		if (edTime.getVisibility() == View.VISIBLE
				&& ("".equals(edTime.getText().toString().trim()) || edTime
						.getText().toString().trim() == null)) {
			application.showToast("请输入最佳送货时间");
			return;
		}
		AddOrderInfo addOrderInfo = new AddOrderInfo();
		if (application.getUser().getUser_id() != 0) {
			addOrderInfo.setUser_id(application.getUser().getUser_id() + "");
			addOrderInfo.setConsignee(info.getConsignee());
			addOrderInfo.setCountry(info.getCountry());
			addOrderInfo.setProvince(info.getProvince());
			addOrderInfo.setCity(info.getCity());
			addOrderInfo.setDistrict(info.getDistrict());
			addOrderInfo.setAddress(info.getAddress());
			addOrderInfo.setZipcode(info.getZipcode());
			addOrderInfo.setTel(info.getTel());
			addOrderInfo.setMobile(info.getMobile());
			addOrderInfo.setEmail(info.getEmail());
			addOrderInfo.setBest_time(best_time);
			addOrderInfo.setSign_building(info.getSign_building());
			addOrderInfo.setPostscript("");
			addOrderInfo.setShipping_id(shipping_id + "");
			addOrderInfo.setShipping_name(shipping_name);
			addOrderInfo.setPay_id(pay_id + "");
			addOrderInfo.setPay_name(pay_name);
			addOrderInfo.setHow_oos("等待所有商品备齐后再发");
			addOrderInfo.setGoods_amount(totalPrice*num + "");
			addOrderInfo.setShipping_fee("0");
			addOrderInfo.setOrder_amount(totalPrice*num + "");
			addOrderInfo.setSession_id("");
		} else {
			addOrderInfo.setUser_id("0");
			addOrderInfo.setConsignee(info1.getConsignee());
			addOrderInfo.setCountry("1");
			addOrderInfo.setProvince(info1.getProvince());
			// if (info1.getCity() == null) {
			// addOrderInfo.setCity("惠济区".equals(info1.getCityName()) ? "3409"
			// : "149");
			// } else {
			addOrderInfo.setCity(info1.getCity());
			// }
			addOrderInfo.setDistrict(info1.getDistrict());
			addOrderInfo.setAddress(info1.getAddress());
			addOrderInfo.setZipcode(info1.getZipcode());
			addOrderInfo.setTel(info1.getTel());
			addOrderInfo.setMobile(info1.getMobile());
			addOrderInfo.setEmail(info1.getEmail());
			addOrderInfo.setBest_time(best_time);
			addOrderInfo.setSign_building(info1.getSign_building());
			addOrderInfo.setPostscript("");
			addOrderInfo.setShipping_id(shipping_id + "");
			addOrderInfo.setShipping_name(shipping_name);
			addOrderInfo.setPay_id(pay_id + "");
			addOrderInfo.setPay_name(pay_name);
			addOrderInfo.setHow_oos("等待所有商品备齐后再发");
			addOrderInfo.setGoods_amount(totalPrice*num + "");
			addOrderInfo.setShipping_fee("0");
			addOrderInfo.setSession_id(application.DEVICE_ID);
			addOrderInfo.setOrder_amount(totalPrice*num + "");
		}
		List<GoodsListInfo> goodsList = new ArrayList<GoodsListInfo>();
		for (int i = 0; i < list.size(); i++) {
			AddCartInfo add = list.get(i);
			GoodsListInfo info = new GoodsListInfo();
			info.setGoods_id(add.getGoods_id() + "");
			info.setGoods_name(add.getGoods_name() + "");
			info.setGoods_sn(add.getGoods_sn());
			info.setGoods_number(add.getGoods_number() + "");
			info.setMarket_price(add.getMarket_price());
			info.setGoods_price(add.getGoods_price());
			info.setGoods_attr("");
			info.setGoods_txm(add.getPre_barcode());
			goodsList.add(info);
		}
		addOrderInfo.setGoodsList(goodsList);
		Log.e("", "--" + addOrderInfo.toString());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("StrJosn", addOrderInfo.toString());
		showProgress("正在提交订单");
		WebServiceUtils.callWebService(Constant.S_AddOrder, map,
				new WebServiceCallBack() {
					public void callBack(Object result) {
						dismissProgress();
						if (result != null) {
							bean = JSON.parseObject(result.toString(),
									Bean.class);
							Log.e("", "-----===  " + result.toString());
							if (bean.getState() == 1) {
								com.alibaba.fastjson.JSONObject json = JSON
										.parseObject(bean.getData());
								order_sn = json.getString("order_sn");
								OrderID = json.getString("order_id");
								if (application.getUser().getUser_id() != 0) {
									if (pay_id == 1) {// 货到付款
										application.showToast("添加订单成功");
										// startActivity(new Intent(mContext,
										// MainActivity.class));
										// finish();
										application.finishOtherAct();
									} else if (pay_id == 3) {
										// orderNo = JSON.parseObject(
										// bean.getData()).getString(
										// "order_sn");
										pay_test(order_sn);

									} else if (pay_id == 4) {
										float money = application.getUser()
												.getUser_money()
												+ application.getUser()
														.getCredit_line()
												- application.getUser()
														.getFrozen_money();
										Log.e("total", totalPrice + "");
										if (money < totalPrice*num) {
											application
													.showToast("您的余额不足,请尽快充值");
										} else {
											updateStatue(order_sn, 1);
											application.getUser()
													.setUser_money(
															money - totalPrice*num);
										}
									}
								} else {
									if (pay_id == 1) {// 货到付款
										application.showToast("您的订单已提交,请注意查收");
										finish();
									} else if (pay_id == 3) {// 支付宝
										// orderNo = JSON.parseObject(
										// bean.getData()).getString(
										// "order_sn");
										pay_test(orderNo);
									}
								}

							} else {
								application.showToast("添加订单失败");
								try {
									JSONObject jb = new JSONObject(bean
											.getData());
									JSONArray array = jb.getJSONArray("_data");
									for (int i = 0; i < array.length(); i++) {
										JSONObject object = array
												.getJSONObject(i);
										Bean b = new Bean();
										b.setData(object.getString("goods_id"));
										b.setState(object
												.getInt("goods_number"));
										finderror(b.getData(), b.getState());
										erroeBeans.add(b);
									}
									mAdapter.notifyDataSetChanged();
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						} else {
							mActivity.showToast("请检查收货地址是否正确。");
						}
					}
				});
	}

	private void finderror(String goods_id, int sum) {
		for (int i = 0; i < list.size(); i++) {
			AddCartInfo info = list.get(i);
			if (goods_id.equals(info.getGoods_id() + "")) {
				info.setIsshow(true);
				info.setSumNumber(sum);
			}
		}
	}

	@OnClick(R.id.submitOrderBtn)
	public void submitOrder(View v) {
		submit();
	}

	/***
	 * 余额支付
	 */
	// public void BalancePay(String uid, String umoney, final String OrderID) {
	// HashMap<String, Object> map = new HashMap<String, Object>();
	// map.put("UserID", uid);
	// map.put("User_Money", umoney);
	// map.put("OrderID", OrderID);
	// WebServiceUtils.callWebService(Constant.S_UpdateAccount, map,
	// new WebServiceCallBack() {
	// public void callBack(Object result) {
	// if (result != null) {
	// Bean bean = JSON.parseObject(result.toString(),
	// Bean.class);
	// int state = bean.getState();
	// switch (state) {
	// case 1:
	// application.showToast("支付成功");
	// float nowmoney = application.getUser()
	// .getUser_money() - totalPrice;
	// application.getUser().setUser_money(nowmoney);
	// updateStatue(OrderID, 1);
	// finish();
	// break;
	// case 0:
	// application.showToast("支付失败");
	// break;
	// default:
	// break;
	// }
	// }
	// }
	// });
	//
	// }
	// resultSoapObject={"data":[{"pre_barcode":"6907992507385","promote_start_date":"0","promote_end_date":"0","is_promote":"0","promote_num":"0","goods_thumb":"images/201312/thumb_img/2206_thumb_G_1387848472689.jpg","goods_img":"images/201312/goods_img/2206_G_1387848472310.jpg","original_img":"images/201312/source_img/2206_G_1387848472509.JPG","goods_id":"2206","goods_sn":"DZB002206","goods_name":"金典纯牛奶250ml*12盒","market_price":"65.00","promote_price":"0.00","goods_price":"60.00","goods_number":"0","presenttime":"1402558936"}],"msg":"成功","state":"1"}

	private void pay_test(String orderNo) {
		try {
			Log.i("ExternalPartner", "onItemClick");
			String info = getNewOrderInfo(orderNo);
			String sign = Rsa.sign(info, Keys.PRIVATE);
			sign = URLEncoder.encode(sign);
			info += "&sign=\"" + sign + "\"&" + getSignType();
			Log.i("ExternalPartner", "start pay");
			// start the pay.
			final String orderInfo = info;
			new Thread() {
				public void run() {
					PayTask alipay = new PayTask(SureOrderActivity.this);
//					AliPay alipay = new AliPay(SureOrderActivity.this, mHandler);
					// 设置为沙箱模式，不设置默认为线上环境
					// alipay.setSandBox(true);
					String result = alipay.pay(orderInfo);
					Message msg = new Message();
					msg.what = RQF_PAY;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			}.start();

		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

	private String getNewOrderInfo(String orderNo1) {
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(Keys.DEFAULT_PARTNER);
		sb.append("\"&out_trade_no=\"");
		sb.append(orderNo1);
		sb.append("\"&subject=\"");
		sb.append(list.get(0).getGoods_name());
		sb.append("\"&body=\"");
		sb.append(list.get(0).getGoods_name());
		sb.append("\"&total_fee=\"");
		sb.append(totalPrice);
		sb.append("\"&notify_url=\"");

		// 网址需要做URL编码
		sb.append(URLEncoder.encode("http://notify.java.jpxx.org/index.jsp"));
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		sb.append("\"&return_url=\"");
		sb.append(URLEncoder.encode("http://m.alipay.com"));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(Keys.DEFAULT_SELLER);

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		sb.append("\"&it_b_pay=\"1m");
		sb.append("\"");

		return new String(sb);
	}

	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Result result = new Result((String) msg.obj);
			switch (msg.what) {
			case RQF_PAY:
				Log.e("", "------" + result.getResultStatus());
				if ("9000".equals(result.getResultStatus())) {
					showToast("支付成功");
					updateStatue(order_sn, 0);
					finish();
				} else {
					showToast("支付失败");
					finish();
				}
			case RQF_LOGIN: {
				Toast.makeText(SureOrderActivity.this, result.getResult(),
						Toast.LENGTH_SHORT).show();
			}
				break;
			default:
				break;
			}
		};
	};

	public void updateStatue(String orderNo, int PaymentType) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Order_Sn", orderNo);
		map.put("OrderID", OrderID);
		map.put("Order_Status", 5);
		map.put("PaymentType", PaymentType);
		map.put("PaymentMoney", totalPrice + "");
		if (application.getUser().getUser_id() != 0)
			map.put("UserID", application.getUser().getUser_id());
		else
			map.put("UserID", 0);
		Log.e("", "tag   S_UpdateOrdersState " + map.toString());
		WebServiceUtils.callWebService(Constant.S_UpdateOrdersState, map,
				new WebServiceCallBack() {
					public void callBack(Object result) {
						Log.e("", "tag============S_UpdateOrdersState  "
								+ result);
						Bean bean = JSON.parseObject(result.toString(),
								Bean.class);
						if (bean.getState() == 1) {
							startActivity(new Intent(mContext,
									MainActivity.class));
							finish();
						}
					}
				});
	}
}
