package cn.com.zhoufu.mouth.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.mine.AddAddressActivity;
import cn.com.zhoufu.mouth.activity.mine.AddressActivity;
import cn.com.zhoufu.mouth.model.AddressInfo;

public class AddressAdapter extends BaseListAdapter<AddressInfo> {

	public int currentIndex = -1;

	private Handler handler;

	public AddressAdapter(Context context, Handler handler) {
		super(context);
		this.handler = handler;

	}

	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listitem_address, null);
			viewHolder = new ViewHolder();
			viewHolder.address = (TextView) convertView
					.findViewById(R.id.address);
			viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			viewHolder.mobile = (TextView) convertView
					.findViewById(R.id.mobile);
			viewHolder.edit = (ImageView) convertView
					.findViewById(R.id.btn_edit_address);
			viewHolder.cb = (ImageView) convertView.findViewById(R.id.checkid);
			viewHolder.ib = (ImageView) convertView.findViewById(R.id.check_ib);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final AddressInfo info = mList.get(position);
		viewHolder.address.setText(info.getPrivoceName() + info.getCityName()
				+ info.getDinstrictName() + info.getAddress());
		viewHolder.name.setText(info.getConsignee());
		viewHolder.mobile.setText(info.getTel());
		viewHolder.edit.setTag(info);
		viewHolder.edit.setOnClickListener(editOnClickListener);
		viewHolder.ib.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeIndex(position);
			}
		});
		if (position == currentIndex) {
			viewHolder.cb.setVisibility(View.VISIBLE);
			Message message = new Message();
			message.what = 1;
			message.obj = info;
			handler.sendMessage(message);
		} else {
			viewHolder.cb.setVisibility(View.GONE);
		}

		return convertView;
	}

	private View.OnClickListener editOnClickListener = new View.OnClickListener() {

		public void onClick(View v) {
			AddressInfo info = (AddressInfo) v.getTag();
			Intent intent = new Intent(mContext, AddAddressActivity.class);
			intent.putExtra("tag", "2");
			intent.putExtra("info", info);
			mContext.startActivity(intent);
			AddressActivity.index = 0;
		}
	};

	public void changeIndex(int position) {
		if (currentIndex != position) {
			currentIndex = position;
		} else {
			currentIndex = -1;
		}
		notifyDataSetChanged();
	}

	static class ViewHolder {
		TextView address;
		TextView name;
		TextView mobile;
		ImageView edit;
		ImageView cb;
		ImageView ib;
	}

}
