/**   
 * @Title: CategaryAdapter.java 
 * @Package cn.com.zhoufu.mouth.adapter 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-13 下午1:59:40
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.CategaryInfo;

public class CategaryAdapter extends BaseListAdapter<CategaryInfo> {

	public CategaryAdapter(Context context) {
		super(context);
		application.bitmapUtils
				.configDefaultLoadingImage(R.drawable.default_bg2);
		application.bitmapUtils
				.configDefaultLoadFailedImage(R.drawable.default_bg2);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listitem_cate, null);
			viewHolder = new ViewHolder();
			viewHolder.cat_name = (TextView) convertView
					.findViewById(R.id.cat_name);
			viewHolder.keywords = (TextView) convertView
					.findViewById(R.id.keywords);
			viewHolder.cat_img = (ImageView) convertView
					.findViewById(R.id.cat_img);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		CategaryInfo info = mList.get(position);
		viewHolder.cat_name.setText(info.getCat_name());
		viewHolder.keywords.setText(info.getKeywords().replace(" ", "/"));
		switch (position) {
		case 0:
			viewHolder.cat_img.setImageResource(R.drawable.categary_1);
			break;
		case 1:
			viewHolder.cat_img.setImageResource(R.drawable.categary_2);
			break;
		case 2:
			viewHolder.cat_img.setImageResource(R.drawable.categary_3);
			break;
		case 3:
			viewHolder.cat_img.setImageResource(R.drawable.categary_4);
			break;
		case 4:
			viewHolder.cat_img.setImageResource(R.drawable.categary_5);
			break;
		case 5:
			viewHolder.cat_img.setImageResource(R.drawable.categary_6);
			break;
		case 6:
			viewHolder.cat_img.setImageResource(R.drawable.categary_7);
			break;
		}

		return convertView;
	}

	static class ViewHolder {
		TextView cat_name;
		TextView keywords;

		ImageView cat_img;

	}

}
