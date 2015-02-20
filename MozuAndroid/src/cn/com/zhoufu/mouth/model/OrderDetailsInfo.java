/**   
 * @Title: OrderDetailsInfo.java 
 * @Package cn.com.zhoufu.mouth.model 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-20 下午3:59:11
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.model;

import java.io.Serializable;

public class OrderDetailsInfo implements Serializable {

	private String goods_thumb;

	private String goods_img;

	private String original_img;

	private String goods_name;

	private String goods_sn;

	private int goods_number;

	private double market_price;

	private String goods_attr;

	private int send_number;

	private int is_real;

	private String goods_txm;

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

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_sn() {
		return goods_sn;
	}

	public void setGoods_sn(String goods_sn) {
		this.goods_sn = goods_sn;
	}

	public int getGoods_number() {
		return goods_number;
	}

	public void setGoods_number(int goods_number) {
		this.goods_number = goods_number;
	}

	public double getMarket_price() {
		return market_price;
	}

	public void setMarket_price(double market_price) {
		this.market_price = market_price;
	}

	public String getGoods_attr() {
		return goods_attr;
	}

	public void setGoods_attr(String goods_attr) {
		this.goods_attr = goods_attr;
	}

	public int getSend_number() {
		return send_number;
	}

	public void setSend_number(int send_number) {
		this.send_number = send_number;
	}

	public int getIs_real() {
		return is_real;
	}

	public void setIs_real(int is_real) {
		this.is_real = is_real;
	}

	public String getGoods_txm() {
		return goods_txm;
	}

	public void setGoods_txm(String goods_txm) {
		this.goods_txm = goods_txm;
	}

}
