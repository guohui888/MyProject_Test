/**   
 * @Title: OrderDetailsAdapter.java 
 * @Package cn.com.zhoufu.mouth.adapter 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-20 下午3:58:25
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.OrderDetailsInfo;

public class OrderDetailsAdapter extends BaseListAdapter<OrderDetailsInfo> {

	public OrderDetailsAdapter(Context context) {
		super(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listitem_order_details, null);
			viewHolder = new ViewHolder();
			viewHolder.goodNumber = (TextView) convertView.findViewById(R.id.goodNumber);
			viewHolder.goodName = (TextView) convertView.findViewById(R.id.goodName);
			viewHolder.imageUrl = (ImageView) convertView.findViewById(R.id.imageUrl);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		OrderDetailsInfo info = mList.get(position);
		viewHolder.goodName.setText(info.getGoods_name());
		viewHolder.goodNumber.setText("数量：" + info.getGoods_number() + "个");
		application.bitmapUtils.display(viewHolder.imageUrl, Constant.IMAGE_URL+info.getGoods_img());
		return convertView;
	}

	static class ViewHolder {
		TextView goodName, goodNumber;
		ImageView imageUrl;
	}

}
