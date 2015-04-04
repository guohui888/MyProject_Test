/**   
 * @Title: SearchInfo.java 
 * @Package cn.com.zhoufu.mouth.model 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-13 下午5:36:52
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.model;

import java.io.Serializable;

import org.json.JSONObject;

public class SearchInfo implements Serializable {

	private int goods_id;

	private String goods_name;

	private double market_price;

	private double shop_price;

	private String promote_price;

	private String goods_sn;

	private String goods_thumb;

	private String brand_name;

	private int goods_number;

	private int is_promote;

	private int promote_num;

	private long presenttime;
	private long promote_start_date;
	private long promote_end_date;

	private String price;

	private String member_price;// 会员价格

	// "presenttime": "1398219698",
	// "promote_start_date": "1398168000",
	// "promote_end_date": "1398171600",
	// "is_promote": "1",
	// "promote_num": "3",
	// "goods_sn": "DZB002480",
	// "goods_type": "21",
	// "goods_id": "2480",
	// "goods_name": "恒大冰泉长白山天然矿泉水500ml",
	// "market_price": "4.50",
	// "shop_price": "4.50",
	// "promote_price": "3.60",
	// "goods_thumb": "images/201404/thumb_img/2480_thumb_G_1397126748571.jpg",
	// "goods_img": "",
	// "original_img": "",
	// "brand_name": ""

	public String getMember_price() {
		return member_price;
	}

	public void setMember_price(String member_price) {
		this.member_price = member_price;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public long getPresenttime() {
		return presenttime;
	}

	public void setPresenttime(long presenttime) {
		this.presenttime = presenttime;
	}

	public long getPromote_start_date() {
		return promote_start_date;
	}

	public void setPromote_start_date(long promote_start_date) {
		this.promote_start_date = promote_start_date;
	}

	public long getPromote_end_date() {
		return promote_end_date;
	}

	public void setPromote_end_date(long promote_end_date) {
		this.promote_end_date = promote_end_date;
	}

	public int getPromote_num() {
		return promote_num;
	}

	public void setPromote_num(int promote_num) {
		this.promote_num = promote_num;
	}

	public int getIs_promote() {
		return is_promote;
	}

	public void setIs_promote(int is_promote) {
		this.is_promote = is_promote;
	}

	public int getGoods_number() {
		return goods_number;
	}

	public void setGoods_number(int goods_number) {
		this.goods_number = goods_number;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getGoods_sn() {
		return goods_sn;
	}

	public void setGoods_sn(String goods_sn) {
		this.goods_sn = goods_sn;
	}

	private String goods_img;

	private String original_img;

	public int getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public double getMarket_price() {
		return market_price;
	}

	public void setMarket_price(double market_price) {
		this.market_price = market_price;
	}

	public double getShop_price() {
		return shop_price;
	}

	public void setShop_price(double shop_price) {
		this.shop_price = shop_price;
	}

	public String getPromote_price() {
		return promote_price;
	}

	public void setPromote_price(String promote_price) {
		this.promote_price = promote_price;
	}

	public String getGoods_thumb() {
		return goods_thumb;
	}

	public void setGoods_thumb(String goods_thumb) {
		this.goods_thumb = goods_thumb;
	}

	public String getGoods_img() {
		return goods_img;
	}

	public void setGoods_img(String goods_img) {
		this.goods_img = goods_img;
	}

	public String getOriginal_img() {
		return original_img;
	}

	public void setOriginal_img(String original_img) {
		this.original_img = original_img;
	}

	@Override
	public String toString() {
		return "SearchInfo [goods_id=" + goods_id + ", goods_name="
				+ goods_name + ", market_price=" + market_price
				+ ", shop_price=" + shop_price + ", promote_price="
				+ promote_price + ", goods_sn=" + goods_sn + ", goods_thumb="
				+ goods_thumb + ", brand_name=" + brand_name
				+ ", goods_number=" + goods_number + ", is_promote="
				+ is_promote + ", promote_num=" + promote_num
				+ ", presenttime=" + presenttime + ", promote_start_date="
				+ promote_start_date + ", promote_end_date=" + promote_end_date
				+ ", goods_img=" + goods_img + ", original_img=" + original_img
				+ "]";
	}

}
