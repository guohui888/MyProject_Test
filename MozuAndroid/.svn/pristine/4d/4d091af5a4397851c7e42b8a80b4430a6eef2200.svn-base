package cn.com.zhoufu.mouth.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;

public class MainHomeAdapter extends BaseAdapter {

	private int ImageID[];

	private String title[];

	private LayoutInflater inflater;

	private Resources resource;

	public MainHomeAdapter(Context context, int ImageID[], String title[]) {
		this.ImageID = ImageID;
		this.title = title;
		resource = context.getResources();
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ImageID.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return title[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {

		Holder h = null;
		if (v == null) {
			v = inflater.inflate(R.layout.main_home_item, null);
			h = new Holder();
			h.tv = (TextView) v.findViewById(R.id.mian_home_item_tv);
			v.setTag(h);
		} else {
			h = (Holder) v.getTag();
		}
		h.tv.setText(title[position]);
		h.tv.setCompoundDrawablesWithIntrinsicBounds(null,
				resource.getDrawable(ImageID[position]), null, null);
		return v;
	}

	static class Holder {
		public TextView tv;
	}

}
