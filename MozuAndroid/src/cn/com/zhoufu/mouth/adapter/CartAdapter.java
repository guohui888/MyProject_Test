/**   
 * @Title: CartAdapter.java 
 * @Package cn.com.zhoufu.mouth.adapter 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-18 上午11:04:47
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
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
import cn.com.zhoufu.mouth.utils.AbViewUtil;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;
import cn.com.zhoufu.mouth.utils.XMLParser;

import com.alibaba.fastjson.JSON;

public class CartAdapter extends BaseListAdapter<AddCartInfo> {

	ArrayList<AddCartInfo> phone;

	private Handler handler;

	public CartAdapter(Context context, ArrayList<AddCartInfo> phone,
			Handler handler) {
		super(context);
		this.phone = phone;
		this.handler = handler;

		application.bitmapUtils
				.configDefaultLoadingImage(R.drawable.default_bg2);
		application.bitmapUtils
				.configDefaultLoadFailedImage(R.drawable.default_bg2);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listitem_cart, null);
			viewHolder.tvGoodName = (TextView) convertView
					.findViewById(R.id.tvGoodName);
			viewHolder.tvGoodNumber = (TextView) convertView
					.findViewById(R.id.tvGoodNumber);
			viewHolder.tvShopPrice = (TextView) convertView
					.findViewById(R.id.tvShopPrice);
			viewHolder.selected = (ImageView) convertView
					.findViewById(R.id.selected);
			viewHolder.down_product = (Button) convertView
					.findViewById(R.id.down_product);
			viewHolder.add_product = (Button) convertView
					.findViewById(R.id.add_product);
			viewHolder.goodsImage = (ImageView) convertView
					.findViewById(R.id.goodsImage);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final AddCartInfo info = mList.get(position);
		Log.e("tag", info.toString());
		viewHolder.tvGoodName.setText(info.getGoods_name());

		viewHolder.tvGoodNumber.setText(info.getGoods_number() + "");

		application.bitmapUtils.display(viewHolder.goodsImage,
				Constant.IMAGE_URL + info.getGoods_img());

		viewHolder.tvShopPrice.setText(AbViewUtil.changTVsize("价格：￥"
				+ info.getGoods_price()));
		if (phone != null && phone.contains(info)) {
			viewHolder.selected.setVisibility(View.VISIBLE);
		} else {
			viewHolder.selected.setVisibility(View.GONE);
		}
		viewHolder.down_product.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (!XMLParser.isNetworkAvailable(mContext)) {
					application.showToast("网络未连接");
					return;
				}
				int num = info.getGoods_number();
				if (num - 1 == 0 || num == 0) {
					application.showToast("不能小于0");
					return;
				}
				info.setGoods_number(--num);
				viewHolder.tvGoodNumber.setText(num + "");
				viewHolder.add_product.setClickable(true);
				Message m = new Message();
				m.obj = Double.parseDouble(info.getGoods_price());
				m.what = 1;
				handler.sendMessage(m);
				update(info.getGoods_id(), info.getGoods_number(),
						Double.parseDouble(info.getGoods_price()));
			}
		});
		viewHolder.add_product.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!XMLParser.isNetworkAvailable(mContext)) {
					application.showToast("网络未连接");
					return;
				}
				int num = info.getGoods_number();
				info.setGoods_number(++num);
				viewHolder.tvGoodNumber.setText(num + "");
				Message m = new Message();
				m.obj = Double.parseDouble(info.getGoods_price());
				m.what = 2;
				handler.sendMessage(m);
				update(info.getGoods_id(), info.getGoods_number(),
						Double.parseDouble(info.getGoods_price()));

			}
		});
		return convertView;
	}

	public void deteleCart(String sb) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Goods_ID", sb);
		map.put("UserID", application.getUser().getUser_id());
		WebServiceUtils.callWebService(Constant.S_DeleteCart, map,
				new WebServiceCallBack() {
					public void callBack(Object result) {
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							if (bean.getState() == 1) {
								CartAdapter.this.notifyDataSetChanged();
							} else {
								application.showToast("删除商品失败");
							}
						}
					}
				});
	}

	static class ViewHolder {
		public TextView tvGoodName, tvGoodNumber, tvShopPrice;
		ImageView selected, goodsImage;
		Button down_product, add_product;
	}

	public void update(int goodsId, int number, final double price) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Good_ID", goodsId);
		map.put("UserID", application.getUser().getUser_id());
		map.put("Number", number);
		WebServiceUtils.callWebService(Constant.S_UpdateCartNumber, map,
				new WebServiceCallBack() {
					public void callBack(Object result) {
						Log.i("info", result.toString());
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							if (bean.getState() == 1) {
								final Intent intent = new Intent(
										Constant.CART_CONTENT);
								mContext.sendBroadcast(intent);
							}
						}
					}
				});
	}
}
