package cn.com.zhoufu.mouth.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.category.SearchActivity;
import cn.com.zhoufu.mouth.activity.home.PromotionActivity;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.HomeButtomModel;

import com.lidroid.xutils.BitmapUtils;

public class HomeButtomAdapter extends BaseListAdapter<HomeButtomModel> {
	BitmapUtils bitmapUtils;
	BitmapUtils bitmapUtils1;
	Resources res;

	public HomeButtomAdapter(Context context) {
		super(context);
		res = context.getResources();
		bitmapUtils = new BitmapUtils(context);
		bitmapUtils.configDefaultLoadingImage(R.drawable.defult_small);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.defult_small);
		// bitmapUtils.configDefaultBitmapConfig(Config.RGB_565);
		bitmapUtils1 = new BitmapUtils(context);
		// bitmapUtils1.configDefaultLoadingImage(R.drawable.defult_);
		// bitmapUtils1.configDefaultLoadFailedImage(R.drawable.defult_nol);
		// bitmapUtils1.configDefaultBitmapConfig(Config.RGB_565);
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		Holder h = null;
		if (v == null) {
			h = new Holder();
			v = mInflater.inflate(R.layout.item_home_buttom, null);
			h.title = (TextView) v.findViewById(R.id.item_home_buttom_title);
			h.left = (ImageView) v.findViewById(R.id.item_home_buttom_large);
			h.left.setTag(R.id.item_home_buttom_large, position);
			h.left.setOnClickListener(onclick);
			h.right1 = (ImageView) v
					.findViewById(R.id.item_home_buttom_small_1);
			h.right1.setTag(R.id.item_home_buttom_small_1, position);
			h.right1.setOnClickListener(onclick);
			h.right2 = (ImageView) v
					.findViewById(R.id.item_home_buttom_small_2);
			h.right2.setTag(R.id.item_home_buttom_small_2, position);
			h.right2.setOnClickListener(onclick);
			// ----------------------------
			h.line = (ImageView) v.findViewById(R.id.item_home_buttom_lin);
			h.type1 = (TextView) v.findViewById(R.id.item_buttom_type1);
			h.type1.setTag(R.id.item_buttom_type1, position);
			h.type1.setOnClickListener(onclick);
			h.type2 = (TextView) v.findViewById(R.id.item_buttom_type2);
			h.type2.setTag(R.id.item_buttom_type2, position);
			h.type2.setOnClickListener(onclick);
			h.type3 = (TextView) v.findViewById(R.id.item_buttom_type3);
			h.type3.setTag(R.id.item_buttom_type3, position);
			h.type3.setOnClickListener(onclick);
			h.type4 = (TextView) v.findViewById(R.id.item_buttom_type4);
			h.type4.setTag(R.id.item_buttom_type4, position);
			h.type4.setOnClickListener(onclick);
			h.type5 = (TextView) v.findViewById(R.id.item_buttom_type5);
			h.type5.setTag(R.id.item_buttom_type5, position);
			h.type5.setOnClickListener(onclick);
			v.setTag(h);
		} else {
			h = (Holder) v.getTag();
		}
		HomeButtomModel model = mList.get(position);
		bitmapUtils1.display(h.left,
				Constant.HOME_IMAGE_URL + model.getImgLarge());

		bitmapUtils.display(h.right1,
				Constant.HOME_IMAGE_URL + model.getImgSmall_1());
		bitmapUtils.display(h.right2,
				Constant.HOME_IMAGE_URL + model.getImgSmall_2());
		if (model.getCategoryName().length > 0) {
			h.type1.setVisibility(0);
			h.type1.setText(model.getCategoryName()[0]);
		} else {
			h.type1.setVisibility(8);
		}
		if (model.getCategoryName().length > 1) {
			h.type2.setVisibility(0);
			h.type2.setText(model.getCategoryName()[1]);
		} else {
			h.type2.setVisibility(8);
		}
		if (model.getCategoryName().length > 2) {
			h.type3.setVisibility(0);
			h.type3.setText(model.getCategoryName()[2]);
		} else {
			h.type3.setVisibility(8);
		}
		if (model.getCategoryName().length > 3) {
			h.type4.setVisibility(0);
			h.type4.setText(model.getCategoryName()[3]);
		} else {
			h.type4.setVisibility(8);
		}
		if (model.getCategoryName().length > 4) {
			h.type5.setVisibility(0);
			h.type5.setText(model.getCategoryName()[4]);
		} else {
			h.type5.setVisibility(8);
		}
		switch (position) {
		case 0:
			h.title.setText("户内表贴");
			h.title.setTextColor(res.getColor(R.color.home_buttom_1));
			h.title.setCompoundDrawablesWithIntrinsicBounds(
					res.getDrawable(R.drawable.home_buttom_d1), null, null,
					null);
			h.line.setBackgroundResource(R.drawable.home_lin1);
			break;
		case 1:
			h.title.setText("户外全彩");
			h.title.setTextColor(res.getColor(R.color.home_buttom_2));
			h.title.setCompoundDrawablesWithIntrinsicBounds(
					res.getDrawable(R.drawable.home_buttom_d2), null, null,
					null);
			h.line.setBackgroundResource(R.drawable.home_lin2);
			break;
		case 2:
			h.title.setText("控制系统");
			h.title.setTextColor(res.getColor(R.color.home_buttom_3));
			h.title.setCompoundDrawablesWithIntrinsicBounds(
					res.getDrawable(R.drawable.home_buttom_d3), null, null,
					null);
			h.line.setBackgroundResource(R.drawable.home_lin3);
			break;
		case 3:
			h.title.setText("显示屏周边");
			h.title.setTextColor(res.getColor(R.color.home_buttom_4));
			h.title.setCompoundDrawablesWithIntrinsicBounds(
					res.getDrawable(R.drawable.home_buttom_d4), null, null,
					null);
			h.line.setBackgroundResource(R.drawable.home_lin4);
			break;
		}
		return v;
	}

	static class Holder {
		TextView title;
		ImageView left;
		ImageView right1;
		ImageView right2;
		ImageView line;
		TextView type1, type2, type3, type4, type5;
	}

	OnClickListener onclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.item_home_buttom_large: {
				int index = (Integer) v.getTag(R.id.item_home_buttom_large);
				Log.e("", "position=" + 1111);
				Intent intent = new Intent(mContext, PromotionActivity.class);
				intent.putExtra("promotion", mList.get(index)
						.getShopid_str_left());
				mContext.startActivity(intent);
			}
				break;
			case R.id.item_home_buttom_small_1: {
				int index = (Integer) v.getTag(R.id.item_home_buttom_small_1);
				Intent intent = new Intent(mContext, PromotionActivity.class);
				intent.putExtra("promotion", mList.get(index)
						.getShopid_str_r1());
				mContext.startActivity(intent);
			}
				break;
			case R.id.item_home_buttom_small_2: {
				int index = (Integer) v.getTag(R.id.item_home_buttom_small_2);
				Intent intent = new Intent(mContext, PromotionActivity.class);
				intent.putExtra("promotion", mList.get(index)
						.getShopid_str_r2());
				mContext.startActivity(intent);
			}
				break;
			case R.id.item_buttom_type1: {
				int index = (Integer) v.getTag(R.id.item_buttom_type1);
				IndexClick(mList.get(index).getCat_id()[0] + "");
			}
				break;
			case R.id.item_buttom_type2: {
				int index = (Integer) v.getTag(R.id.item_buttom_type2);
				IndexClick(mList.get(index).getCat_id()[1] + "");
			}
				break;
			case R.id.item_buttom_type3: {
				int index = (Integer) v.getTag(R.id.item_buttom_type3);
				IndexClick(mList.get(index).getCat_id()[2] + "");
			}
				break;
			case R.id.item_buttom_type4: {
				int index = (Integer) v.getTag(R.id.item_buttom_type4);
				IndexClick(mList.get(index).getCat_id()[3] + "");
			}
				break;
			case R.id.item_buttom_type5: {
				int index = (Integer) v.getTag(R.id.item_buttom_type5);
				IndexClick(mList.get(index).getCat_id()[4] + "");
			}
				break;
			}

		}
	};

	private void IndexClick(String id) {
		Intent intent = new Intent(mContext, SearchActivity.class);
		intent.putExtra("id", id);
		mContext.startActivity(intent);
	}

}
