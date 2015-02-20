/**   
 * @Title: AttributeAdapter.java 
 * @Package cn.com.zhoufu.mouth.adapter 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-26 下午3:10:41
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.model.AttributeInfo;

public class AttributeAdapter extends BaseListAdapter<AttributeInfo> {

	public AttributeAdapter(Context context) {
		super(context);
	}

	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listitem_attribute, null);
			viewHolder = new ViewHolder();
			viewHolder.attr_name = (TextView) convertView.findViewById(R.id.attr_name);
			viewHolder.attr_value = (TextView) convertView.findViewById(R.id.attr_value);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		AttributeInfo info = mList.get(position);
		viewHolder.attr_name.setText(info.getAttr_name());
		viewHolder.attr_value.setText(info.getAttr_value());
		return convertView;
	}

	static class ViewHolder {
		TextView attr_name;
		TextView attr_value;
	}

}
