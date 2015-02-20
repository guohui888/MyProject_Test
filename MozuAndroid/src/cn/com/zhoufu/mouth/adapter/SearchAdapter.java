/**   
 * @Title: SearchAdapter.java 
 * @Package cn.com.zhoufu.mouth.adapter 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-13 下午5:36:09
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.adapter;

import java.util.List;

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
import cn.com.zhoufu.mouth.model.AddCartInfo;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.model.SearchInfo;
import cn.com.zhoufu.mouth.utils.AbViewUtil;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;
import cn.com.zhoufu.mouth.utils.XMLParser;

import com.alibaba.fastjson.JSON;

public class SearchAdapter extends BaseListAdapter<SearchInfo> {

	private Handler handler;

	ViewHolder viewHolder;

	private int pos;

	private SearchInfo info;

	private String price;
	public int currenCount;

	public SearchAdapter(Handler handler, Context context) {
		super(context);
		this.handler = handler;
		application.bitmapUtils
				.configDefaultLoadingImage(R.drawable.default_bg2);
		application.bitmapUtils
				.configDefaultLoadFailedImage(R.drawable.default_bg2);
		application.bitmapUtils.configDefaultBitmapConfig(Config.RGB_565);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
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
		info = mList.get(position);
		viewHolder.tvGoods_name.setText(info.getGoods_name());
		viewHolder.market_price.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		viewHolder.market_price.setText("￥" + info.getMarket_price());

		viewHolder.deleteBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				info = mList.get(position);
				pos = position;
				Message message = new Message();
				message.what = 0;
				message.arg1 = pos;
				message.obj = mList.get(position).getPrice();
				handler.sendMessage(message);
			}
		});
		Log.e("", "--" + info.getGoods_img());
		application.bitmapUtils.display(viewHolder.ivImageUrl,
				Constant.IMAGE_URL + info.getGoods_img());
		Log.e("",
				(info.getPresenttime() > info.getPromote_start_date()) + "---"
						+ info.getPromote_end_date() + "--"
						+ info.getPresenttime());
		kk();
		return convertView;
	}

	private void kk() {
		if ((info.getPresenttime() > info.getPromote_start_date())
				&& (info.getPresenttime() < info.getPromote_end_date())
				&& info.getIs_promote() == 1) {// 是促销商品，并且在促销时间之类
			if (info.getPromote_num() == 0) {// 促销商品为0表示不限量
				info.setPrice(info.getPromote_price()); // 不管是不是会员都可以以促销价格购买
				viewHolder.shop_price.setText(AbViewUtil.changTVsize("￥"
						+ info.getPromote_price()));
			} else {// 促销商品限量，只有会员能以促销价格购买
				if (application.getUser().getUser_id() != 0) {
					info.setPrice(info.getPromote_price());
					viewHolder.shop_price.setText(AbViewUtil.changTVsize("￥"
							+ info.getPromote_price()));
				} else {
					info.setPrice(info.getShop_price() + "");
					viewHolder.shop_price.setText(AbViewUtil.changTVsize("￥"
							+ info.getShop_price()));
				}
			}
		} else {
			info.setPrice(info.getShop_price() + "");
			viewHolder.shop_price.setText(AbViewUtil.changTVsize("￥"
					+ info.getShop_price()));
		}
	}

	public void cartList(final SearchInfo info1) {
		if (!XMLParser.isNetworkAvailable(mContext)) {
			application.showToast("网络未连接");
			return;
		}
		WebServiceUtils.callWebService(Constant.S_CartList,
				application.addData(), new WebServiceCallBack() {
					public void callBack(Object result) {
						Log.i("info", result.toString());
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							List<AddCartInfo> list = JSON.parseArray(
									bean.getData(), AddCartInfo.class);
							Log.e("", list.size()
									+ "   11111111111==================="
									+ currenCount);
							for (int i = 0; i < list.size(); i++) {
								AddCartInfo add = list.get(i);
								if (info1.getGoods_id() == add.getGoods_id()) {
									Log.e("",
											"===========   "
													+ add.getGoods_number());
									currenCount = add.getGoods_number();
								} else {
									currenCount = 0;
								}
							}
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				});
	}

	static class ViewHolder {
		TextView tvGoods_name;
		TextView shop_price;
		TextView market_price;
		Button deleteBtn;
		ImageView ivImageUrl;
	}

}
