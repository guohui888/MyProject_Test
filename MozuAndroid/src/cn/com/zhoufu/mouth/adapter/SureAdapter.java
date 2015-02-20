/**   
 * @Title: SureAdapter.java 
 * @Package cn.com.zhoufu.mouth.adapter 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-18 下午4:46:49
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.adapter;

import java.math.BigDecimal;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.model.AddCartInfo;

public class SureAdapter extends BaseListAdapter<AddCartInfo> {

	public SureAdapter(Context context) {
		super(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listitem_sure_order, null);
			viewHolder = new ViewHolder();
			viewHolder.goodsName = (TextView) convertView.findViewById(R.id.goodsName);
			viewHolder.goodsNumber = (TextView) convertView.findViewById(R.id.goodsNumber);
			viewHolder.goodsPrice = (TextView) convertView.findViewById(R.id.goodsPrice);
			viewHolder.goodsSumNumber = (TextView) convertView.findViewById(R.id.goodssumnumber);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final AddCartInfo info = mList.get(position);
		viewHolder.goodsName.setText(info.getGoods_name());
		viewHolder.goodsNumber.setText("数量：" + info.getGoods_number());
		BigDecimal b1 = new BigDecimal(info.getGoods_number() * Double.parseDouble(info.getGoods_price()));
		double discount = b1.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		viewHolder.goodsPrice.setText("价格：￥" + discount);
		Log.e("", "=====================");

		if (info.isIsshow()) {
			viewHolder.goodsSumNumber.setVisibility(View.VISIBLE);
			viewHolder.goodsSumNumber.setText("库存:" + info.getSumNumber());
		} else {
			viewHolder.goodsSumNumber.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	static class ViewHolder {
		TextView goodsName, goodsNumber, goodsPrice, goodsSumNumber;
	}

}
