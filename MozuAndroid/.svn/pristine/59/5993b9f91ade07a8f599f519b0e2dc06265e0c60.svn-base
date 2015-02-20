/**   
 * @Title: KeyWordAdapter.java 
 * @Package cn.com.zhoufu.mouth.adapter 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-14 上午10:00:03
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.model.KeywordInfo;

public class KeyWordAdapter extends BaseListAdapter<KeywordInfo> {

	public KeyWordAdapter(Context context) {
		super(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listitem_keyword, null);
			viewHolder = new ViewHolder();
			viewHolder.cat_name = (TextView) convertView.findViewById(R.id.keyname);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		KeywordInfo info = mList.get(position);
		viewHolder.cat_name.setText(info.getKeyword());
		return convertView;
	}

	static class ViewHolder {
		TextView cat_name;
	}

}
