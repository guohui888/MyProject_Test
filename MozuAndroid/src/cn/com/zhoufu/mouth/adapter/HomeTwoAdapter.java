package cn.com.zhoufu.mouth.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.HomeTwoModel;
import cn.com.zhoufu.mouth.utils.Util;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

public class HomeTwoAdapter extends BaseListAdapter<HomeTwoModel> {
	BitmapUtils bitmapUtils;

	public HomeTwoAdapter(Context context) {
		super(context);

		bitmapUtils = new BitmapUtils(context);
		bitmapUtils.configDefaultLoadingImage(R.drawable.home_two_bg);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.home_two_bg);
		bitmapUtils.configDefaultBitmapConfig(Config.RGB_565);

	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		H h;
		if (v == null) {
			h = new H();
			v = mInflater.inflate(R.layout.item_home_gridview_two, null);
			h.img = (ImageView) v.findViewById(R.id.item_home_two_img);
			v.setTag(h);
		} else {
			h = (H) v.getTag();
		}
		HomeTwoModel model = mList.get(position);
		// bitmapUtils
		// .display(h.img, Constant.HOME_IMAGE_URL + model.getAd_code());
		bitmapUtils.display(h.img,
				Constant.HOME_IMAGE_URL + model.getAd_code(),
				new BitmapLoadCallBack<View>() {

					@Override
					public void onLoadCompleted(View arg0, String arg1,
							Bitmap arg2, BitmapDisplayConfig arg3,
							BitmapLoadFrom arg4) {
						ImageView v = (ImageView) arg0;
						LinearLayout.LayoutParams params = new LayoutParams(
								LayoutParams.MATCH_PARENT, Util.dip2px(
										mContext, 100));
						v.setLayoutParams(params);
						v.setImageBitmap(arg2);
					}

					@Override
					public void onLoadFailed(View arg0, String arg1,
							Drawable arg2) {
						ImageView v = (ImageView) arg0;
						LinearLayout.LayoutParams params = new LayoutParams(
								LayoutParams.MATCH_PARENT, Util.px2dip(
										mContext, 160));
						v.setLayoutParams(params);
					}
				});

		return v;

	}

	static class H {
		ImageView img;
	}

}
