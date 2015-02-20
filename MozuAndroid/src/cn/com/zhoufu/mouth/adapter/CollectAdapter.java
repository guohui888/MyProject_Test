/**   
 * @Title: CollectAdapter.java 
 * @Package cn.com.zhoufu.mouth.adapter 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-17 下午5:58:02
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.adapter;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.model.SearchInfo;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;

import com.alibaba.fastjson.JSON;

public class CollectAdapter extends BaseListAdapter<SearchInfo> {

	Handler handler;

	public CollectAdapter(Context context, Handler handler) {
		super(context);
		application.bitmapUtils
				.configDefaultLoadingImage(R.drawable.default_bg2);
		application.bitmapUtils
				.configDefaultLoadFailedImage(R.drawable.default_bg2);
		application.bitmapUtils.configDefaultBitmapConfig(Config.RGB_565);
		this.handler = handler;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listitem_collect, null);
			viewHolder = new ViewHolder();
			viewHolder.tvGoods_name = (TextView) convertView
					.findViewById(R.id.tvGoods_name);
			viewHolder.shop_price = (TextView) convertView
					.findViewById(R.id.shop_price);
			viewHolder.market_price = (TextView) convertView
					.findViewById(R.id.market_price);
			viewHolder.deleteBtn = (Button) convertView
					.findViewById(R.id.deleteBtn);
			viewHolder.ivImageUrl = (ImageView) convertView
					.findViewById(R.id.ivImageUrl);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final SearchInfo info = mList.get(position);
		viewHolder.tvGoods_name.setText(info.getGoods_name());
		viewHolder.market_price.getPaint()
				.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		viewHolder.market_price.getPaint().setAntiAlias(true);
		viewHolder.shop_price.setText("￥" + info.getShop_price());
		viewHolder.market_price.setText("￥" + info.getMarket_price());
		application.bitmapUtils.display(viewHolder.ivImageUrl,
				Constant.IMAGE_URL + info.getGoods_img());
		viewHolder.deleteBtn.setText("取消收藏");
		viewHolder.deleteBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				collect(Constant.S_CallOffCollectGoods, info);
			}
		});
		return convertView;
	}

	static class ViewHolder {
		TextView tvGoods_name;
		TextView shop_price;
		TextView market_price;
		Button deleteBtn;
		ImageView ivImageUrl;
	}

	public void collect(String url, SearchInfo info) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("UserID", application.getUser().getUser_id());
		map.put("Goods_ID", info.getGoods_id());
		map.put("Is_attention", 1);
		Log.i("info", application.getUser().getUser_id() + "");
		WebServiceUtils.callWebService(url, map, new WebServiceCallBack() {

			public void callBack(Object result) {
				if (result != null) {
					Bean bean = JSON.parseObject(result.toString(), Bean.class);
					if (bean.getState() == 1) {
						application.showToast("取消收藏成功");
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);
					} else {
						application.showToast("取消收藏失败");
					}
				}
			}
		});
	}

}
