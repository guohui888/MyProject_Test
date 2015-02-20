/**   
 * @Title: HomeCenterAdapter.java 
 * @Package cn.com.zhoufu.mouth.adapter 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-14 下午3:38:17
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.adapter;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.HomeCenterInfo;
import cn.com.zhoufu.mouth.utils.AbDateUtil;

public class HomeCenterAdapter extends BaseListAdapter<HomeCenterInfo> {

	public HomeCenterAdapter(Context context) {
		super(context);
		application.bitmapUtils
				.configDefaultLoadingImage(R.drawable.default_bg);
		application.bitmapUtils
				.configDefaultLoadFailedImage(R.drawable.default_bg);
		application.bitmapUtils.configDefaultBitmapConfig(Config.RGB_565);
	}

	@SuppressWarnings("deprecation")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.listitem_home_center, null);
			viewHolder = new ViewHolder();
			viewHolder.tvName = (TextView) convertView
					.findViewById(R.id.tvName);
			viewHolder.tvTime = (TextView) convertView
					.findViewById(R.id.tvTime);
			viewHolder.img_url = (ImageView) convertView
					.findViewById(R.id.img_url);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		HomeCenterInfo info = mList.get(position);
		viewHolder.tvName.setText(info.getAd_name());
		String time = info.getEnd_time() + "000";
		viewHolder.tvTime.setText(AbDateUtil.getStringByFormat(time,
				"yyyy-MM-dd HH:mm:ss"));
		application.bitmapUtils.display(viewHolder.img_url,
				Constant.HOME_IMAGE_URL + info.getAd_code());
		return convertView;
	}

	static class ViewHolder {
		TextView tvName, tvTime;
		ImageView img_url;
	}

}