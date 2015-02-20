/**   
 * @Title: OrderAdapter.java 
 * @Package cn.com.zhoufu.mouth.adapter 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-19 下午4:33:22
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.mine.OrderDetailsActivity;
import cn.com.zhoufu.mouth.model.OrderInfo;

public class OrderAdapter extends BaseListAdapter<OrderInfo> {

	public OrderAdapter(Context context) {
		super(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listitem_order, null);
			viewHolder = new ViewHolder();
			viewHolder.order_sn = (TextView) convertView.findViewById(R.id.order_sn);
			viewHolder.order_money = (TextView) convertView.findViewById(R.id.order_money);
			viewHolder.order_addtime = (TextView) convertView.findViewById(R.id.order_addtime);
			viewHolder.order_status = (TextView) convertView.findViewById(R.id.order_status);
			viewHolder.orderDetailsBtn = (Button) convertView.findViewById(R.id.orderDetailsBtn);
			viewHolder.shopping_status = (TextView) convertView.findViewById(R.id.shopping_status);
			viewHolder.pay_status = (TextView) convertView.findViewById(R.id.pay_status);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final OrderInfo info = mList.get(position);
		viewHolder.order_sn.setText(info.getOrder_sn());
		viewHolder.order_money.setText("￥" + info.getGoods_amount());
		viewHolder.order_addtime.setText(info.getAdd_time());
		switch (info.getOrder_status()) {
		case 0:
			viewHolder.order_status.setText("未确认");
			break;
		case 1:
			viewHolder.order_status.setText("已确认");
			break;
		case 2:
			viewHolder.order_status.setText("已取消");
			break;
		case 3:
			viewHolder.order_status.setText("无效");
			break;
		case 4:
			viewHolder.order_status.setText("退货");
			break;
		}
		Log.e("", "=-=-=-=-   " + info.getShipping_status());
		switch (info.getShipping_status()) {
		case 0:
			viewHolder.shopping_status.setText("未发货");
			break;
		case 1:
			viewHolder.shopping_status.setText("已发货");
			break;
		case 2:
			viewHolder.shopping_status.setText("已收货");
			break;
		case 3:
			viewHolder.shopping_status.setText("备货中");
			break;
		}
		switch (info.getPay_status()) {
		case 0:
			viewHolder.pay_status.setText("未付款");
			break;
		case 1:
			viewHolder.pay_status.setText("付款中");
			break;
		case 2:
			viewHolder.pay_status.setText("已付款");
			break;
		}
		viewHolder.orderDetailsBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(mContext, OrderDetailsActivity.class);
				intent.putExtra("info", info);
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

	static class ViewHolder {
		TextView order_sn;
		TextView order_money;
		TextView order_addtime;
		Button orderDetailsBtn;
		TextView order_status, shopping_status, pay_status;
	}

}
