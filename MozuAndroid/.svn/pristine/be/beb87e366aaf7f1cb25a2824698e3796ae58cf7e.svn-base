package cn.com.zhoufu.mouth.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.mine.AddAddressActivity;
import cn.com.zhoufu.mouth.model.AddreInfo;

public class AddreAdapter extends BaseListAdapter<AddreInfo> {

	public AddreAdapter(Context context) {
		super(context);
	}

	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listitem_addre, null);
			viewHolder = new ViewHolder();
			viewHolder.address = (TextView) convertView
					.findViewById(R.id.address);
			viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			viewHolder.mobile = (TextView) convertView
					.findViewById(R.id.mobile);
			viewHolder.edit = (ImageView) convertView
					.findViewById(R.id.btn_edit_address);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final AddreInfo info = mList.get(position);
		viewHolder.address.setText(info.getProviceName() + "省"
				+ info.getCityName() + "市" + info.getPlacename()
				+ info.getAddress());
		viewHolder.name.setText(info.getConsignee());
		viewHolder.mobile.setText(info.getTel());
		viewHolder.edit.setTag(info);
		viewHolder.edit.setOnClickListener(editOnClickListener);
		return convertView;
	}

	private View.OnClickListener editOnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			AddreInfo info = (AddreInfo) v.getTag();
			Intent intent = new Intent(mContext, AddAddressActivity.class);
			intent.putExtra("tag", "2");
			intent.putExtra("info", info);
			mContext.startActivity(intent);

		}
	};

	static class ViewHolder {
		TextView address;
		TextView name;
		TextView mobile;
		ImageView edit;
	}

}
