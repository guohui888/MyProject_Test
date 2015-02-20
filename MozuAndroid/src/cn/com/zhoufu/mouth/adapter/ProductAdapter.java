/**   
 * @Title: ProductAdapter.java 
 * @Package cn.com.zhoufu.mouth.adapter 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-17 下午3:38:11
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.ZFApplication;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.CommodityInfo;

public class ProductAdapter extends PagerAdapter {

	List<CommodityInfo> list;

	Context context;

	ZFApplication application;

	public static String url;

	public ProductAdapter(Context context, List<CommodityInfo> list) {
		this.list = list;
		this.context = context;
		this.application = (ZFApplication) context.getApplicationContext();
		application.bitmapUtils
				.configDefaultLoadingImage(R.drawable.default_bg);
		application.bitmapUtils
				.configDefaultLoadFailedImage(R.drawable.default_bg);
		application.bitmapUtils.configDefaultBitmapConfig(Config.RGB_565);
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
	}

	@Override
	public void finishUpdate(View arg0) {

	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object instantiateItem(View container, final int arg1) {
		View view = View.inflate(context, R.layout.viewpager_item, null);
		((ViewPager) container).addView(view);
		ImageView imageView = (ImageView) view.findViewById(R.id.image);
		application.bitmapUtils.display(imageView, Constant.IMAGE_URL
				+ list.get(arg1).getImg_url());
		url = Constant.IMAGE_URL + list.get(arg1).getImg_url();
		Log.i("info", "url=" + url);
		return view;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {

	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {

	}

}
