package cn.com.zhoufu.mouth.activity.mine;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseActivity;
import cn.com.zhoufu.mouth.activity.cart.SureOrderActivity;
import cn.com.zhoufu.mouth.activity.cart.pay.Keys;
import cn.com.zhoufu.mouth.activity.cart.pay.Result;
import cn.com.zhoufu.mouth.activity.cart.pay.Rsa;
import cn.com.zhoufu.mouth.adapter.OrderDetailsAdapter;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.model.OrderDetailsInfo;
import cn.com.zhoufu.mouth.model.OrderInfo;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;
import cn.com.zhoufu.mouth.view.MyListView;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class OrderDetailsActivity extends BaseActivity {

	OrderInfo info;

	@ViewInject(R.id.orderdetailListView)
	private MyListView mListView;

	@ViewInject(R.id.right_button2)
	private Button right_button2;

	@ViewInject(R.id.order_status)
	private TextView order_status;

	@ViewInject(R.id.order_sn)
	private TextView order_sn;

	@ViewInject(R.id.totalPrice)
	private TextView totalPrice;

	@ViewInject(R.id.totalPrice2)
	private TextView totalPrice2;

	@ViewInject(R.id.orderName)
	private TextView orderName;

	@ViewInject(R.id.orderTel)
	private TextView orderTel;

	@ViewInject(R.id.orderAddress)
	private TextView orderAddress;

	@ViewInject(R.id.pay_type)
	private TextView pay_type;

	@ViewInject(R.id.peisong_type)
	private TextView peisong_type;

	@ViewInject(R.id.shopping_status)
	private TextView shopping_status;

	@ViewInject(R.id.pay_status)
	private TextView pay_status;

	@ViewInject(R.id.clearBtn)
	private Button clearBtn;

	private OrderDetailsAdapter mAdapter;

	private List<OrderDetailsInfo> list;

	private static final int RQF_PAY = 1;

	private static final int RQF_LOGIN = 2;

	String orderNo;

	String orderid;

	String statusStr = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(this, R.layout.activity_order_details);
		setTitle("订单详情");
		if (application.getUser().getUser_id() != 0) {
			initView();
		}
	}

	public void initView() {
		info = (OrderInfo) getIntent().getSerializableExtra("info");
		orderNo = info.getOrder_sn();
		orderid = info.getOrder_id();
		order_sn.setText(orderNo);
		totalPrice.setText("￥" + info.getGoods_amount());
		totalPrice2.setText("￥" + info.getGoods_amount());
		orderName.setText(info.getConsignee());
		orderTel.setText(info.getMobile());
		orderAddress.setText(info.getPrivoceName() + "省"
				+ info.getRegion_name() + "市" + info.getRegion_name1()
				+ info.getAddress());
		if (info.getPay_name().contains("货到")) {
			pay_type.setText("线下支付");
		} else {
			pay_type.setText(info.getPay_name());
		}
		peisong_type.setText(info.getShipping_name());
		switch (info.getOrder_status()) {
		case 0:
			order_status.setText("未确认");
			break;
		case 1:
			order_status.setText("已确认");
			break;
		case 2:
			order_status.setText("已取消");
			clearBtn.setVisibility(View.GONE);
			break;
		case 3:
			order_status.setText("无效");
			clearBtn.setVisibility(View.GONE);
			break;
		case 4:
			order_status.setText("退货");
			clearBtn.setVisibility(View.GONE);
			break;
		}
		switch (info.getShipping_status()) {
		case 0:
			shopping_status.setText("未发货");
			break;
		case 1:
			shopping_status.setText("已发货");
			break;
		case 2:
			shopping_status.setText("已收货");
			clearBtn.setVisibility(View.GONE);
			break;
		case 3:
			shopping_status.setText("备货中");
			break;
		}
		switch (info.getPay_status()) {
		case 0:
			pay_status.setText("未付款");
			break;
		case 1:
			pay_status.setText("付款中");
			break;
		case 2:
			pay_status.setText("已付款");
			break;
		}

		if (info.getShipping_status() == 1) {
			// 已发货状态，显示确认收货
			statusStr = "确认收货";
		} else {
			// 当条件为：货到付款，且未发货，且订单(未确认或已确认),显示取消订单
			if ("货到付款".equals(info.getPay_name())
					&& (info.getOrder_status() == 0 || info.getOrder_status() == 1)
					&& info.getShipping_status() == 0) {
				statusStr = "取消订单";
			}
			// 当条件为：不是货到付款，且未发货，且订单(未确认或已确认),显示取消订单
			if (!"货到付款".equals(info.getPay_name())
					&& (info.getOrder_status() == 0 || info.getOrder_status() == 1)
					&& info.getPay_status() == 0) {
				statusStr = "取消订单";
			}
		}

		clearBtn.setText(statusStr);

		// 当条件为：不是货到付款，且未支付，且订单(未确认或已确认),显示付款按钮
		if (!"货到付款".equals(info.getPay_name())
				&& (info.getOrder_status() == 0 || info.getOrder_status() == 1)
				&& info.getPay_status() == 0) {
			right_button2.setVisibility(View.VISIBLE);
			right_button2.setText("付款");
		}
		list = new ArrayList<OrderDetailsInfo>();
		mAdapter = new OrderDetailsAdapter(mContext);
		mAdapter.setList(list);
		mListView.setAdapter(mAdapter);
		order();
	}

	public void order() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("OrderID", info.getOrder_id());
		showProgress("正在加载中");
		WebServiceUtils.callWebService(Constant.S_OrdersDetail, map,
				new WebServiceCallBack() {

					public void callBack(Object result) {
						dismissProgress();
						Log.i("info", result.toString());
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							if (bean.getState() == 1) {
								List<OrderDetailsInfo> list = JSON.parseArray(
										bean.getData(), OrderDetailsInfo.class);
								mAdapter.addAll(list);
							}
						}
					}
				});
	}

	@OnClick(R.id.clearBtn)
	public void ClickSure(View v) {
		if ("确认收货".equals(statusStr)) {
			updateStatue(6, orderNo);
		} else {
			updateStatue(2, orderNo);
		}
	}

	@OnClick(R.id.right_button2)
	public void ClickPay(View v) {
		pay_test(orderNo);

	}

	private void pay_test(String orderNo) {

		try {

			String info = getNewOrderInfo(orderNo);
			String sign = Rsa.sign(info, Keys.PRIVATE);
			sign = URLEncoder.encode(sign);
			info += "&sign=\"" + sign + "\"&" + getSignType();
			final String orderInfo = info;

			new Thread() {
				@Override
				public void run() {
//					AliPay aliPay = new AliPay(OrderDetailsActivity.this,
//							mHandler);
					PayTask alipay = new PayTask(OrderDetailsActivity.this);
					String result = alipay.pay(orderInfo);
					Message msg = new Message();
					msg.what = RQF_PAY;
					msg.obj = result;
					mHandler.sendMessage(msg);
					super.run();
				}

			}.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			application.showToast("Failure calling remote service");
		}
	}

	private String getNewOrderInfo(String orderNo) {
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(Keys.DEFAULT_PARTNER);
		sb.append("\"&out_trade_no=\"");
		sb.append(orderNo);
		sb.append("\"&subject=\"");
		sb.append(list.get(0).getGoods_name());
		sb.append("\"&body=\"");
		sb.append(list.get(0).getGoods_name());
		sb.append("\"&total_fee=\"");
		sb.append(info.getGoods_amount());//
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
				if ("9000".equals(result.getResultStatus())) {
					updateStatue(5, orderNo);
				} else {
					application.showToast("支付失败");
				}
				break;
			case RQF_LOGIN: {
				Toast.makeText(OrderDetailsActivity.this, result.getResult(),
						Toast.LENGTH_SHORT).show();
			}
				break;
			default:
				break;
			}
		};
	};

	public void updateStatue(final int orderStatus, String orderNo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Order_Status", orderStatus);
		map.put("Order_Sn", orderNo);
		map.put("OrderID", orderid);
		map.put("PaymentType", -1);
		map.put("PaymentMoney", info.getGoods_amount() + "");
		if (application.getUser().getUser_id() != 0)
			map.put("UserID", application.getUser().getUser_id());
		else
			map.put("UserID", 0);
		WebServiceUtils.callWebService(Constant.S_UpdateOrdersState, map,
				new WebServiceCallBack() {

					public void callBack(Object result) {
						Log.i("info", result.toString());
						Bean bean = JSON.parseObject(result.toString(),
								Bean.class);
						if (bean.getState() == 1) {
							if (orderStatus == 2) {
								order_status.setText("已取消");
								application.showToast("取消订单成功");
							} else {
								shopping_status.setText("已收货");
								application.showToast("确认收货成功");
							}
							clearBtn.setVisibility(View.GONE);
							right_button2.setVisibility(View.GONE);
						} else {
							application.showToast("操作失败，请重试");
						}
					}
				});
	}
}