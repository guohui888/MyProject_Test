package cn.com.zhoufu.mouth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.model.CascadeInfo;

public class CascadeAdapter extends BaseListAdapter<CascadeInfo> {

	public static String name;

	public CascadeAdapter(Context context) {
		super(context);
	}

	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listitem_cascade, null);
			viewHolder = new ViewHolder();
			viewHolder.item_type = (TextView) convertView.findViewById(R.id.categoryName);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		CascadeInfo info = mList.get(position);
		viewHolder.item_type.setText(info.getRegion_name());
		name = info.getRegion_name();
		return convertView;
	}

	static class ViewHolder {
		TextView item_type;
	}
}