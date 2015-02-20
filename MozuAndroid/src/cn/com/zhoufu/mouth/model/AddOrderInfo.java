/**   
 * @Title: AddOrderInfo.java 
 * @Package cn.com.zhoufu.mouth.model 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-19 上午11:58:18
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.model;

import java.io.Serializable;
import java.util.List;

public class AddOrderInfo implements Serializable {

	private String user_id;// 用户ID

	private String consignee;// 收货人姓名

	private String country;// 收货人省份ID

	private String province;// 城市ID

	private String city;// 区县Id

	private String district;// 大学或社区ID

	private String address;// 收货人详细地址

	private String zipcode;// 邮编

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	private String tel;// 收货人座机电话

	private String mobile;// 收货人手机号码

	private String email;// 收获人邮箱

	private String best_time;// 最佳送货时间

	private String sign_building;// 收货人地址标志性建筑

	private String postscript;// 订单留言

	private String shipping_id;// 订单配货方式ID

	private String shipping_name;// 订单配送方式名称

	private String pay_id;// 支付方式ID

	private String pay_name;// 订单支付名称

	private String how_oos;// 缺货处理方式,等待所有商品备齐后再发； 取消订单；与店主协商

	private String goods_amount;// 商品总金额

	private String shipping_fee;// 商品配送费用

	private String order_amount;// 应付款金额

	private String session_id;

	private List<GoodsListInfo> goodsList;// 商品明细json

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBest_time() {
		return best_time;
	}

	public void setBest_time(String best_time) {
		this.best_time = best_time;
	}

	public String getSign_building() {
		return sign_building;
	}

	public void setSign_building(String sign_building) {
		this.sign_building = sign_building;
	}

	public String getPostscript() {
		return postscript;
	}

	public void setPostscript(String postscript) {
		this.postscript = postscript;
	}

	public String getShipping_id() {
		return shipping_id;
	}

	public void setShipping_id(String shipping_id) {
		this.shipping_id = shipping_id;
	}

	public String getShipping_name() {
		return shipping_name;
	}

	public void setShipping_name(String shipping_name) {
		this.shipping_name = shipping_name;
	}

	public String getPay_id() {
		return pay_id;
	}

	public void setPay_id(String pay_id) {
		this.pay_id = pay_id;
	}

	public String getPay_name() {
		return pay_name;
	}

	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}

	public String getHow_oos() {
		return how_oos;
	}

	public void setHow_oos(String how_oos) {
		this.how_oos = how_oos;
	}

	public String getGoods_amount() {
		return goods_amount;
	}

	public void setGoods_amount(String goods_amount) {
		this.goods_amount = goods_amount;
	}

	public String getShipping_fee() {
		return shipping_fee;
	}

	public void setShipping_fee(String shipping_fee) {
		this.shipping_fee = shipping_fee;
	}

	public String getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}

	public List<GoodsListInfo> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<GoodsListInfo> goodsList) {
		this.goodsList = goodsList;
	}

	@Override
	public String toString() {
		StringBuilder json = new StringBuilder();
		json.append("[{\"user_id\":\"" + user_id + "\",");
		json.append("\"consignee\":\"" + consignee + "\",");
		json.append("\"country\":\"" + country + "\",");
		json.append("\"province\":\"" + province + "\",");
		json.append("\"city\":\"" + city + "\",");
		json.append("\"district\":\"" + district + "\",");
		json.append("\"address\":\"" + address + "\",");
		json.append("\"zipcode\":\"" + zipcode + "\",");
		json.append("\"tel\":\"" + tel + "\",");
		json.append("\"mobile\":\"" + mobile + "\",");
		json.append("\"email\":\"" + email + "\",");
		json.append("\"best_time\":\"" + best_time + "\",");
		json.append("\"sign_building\":\"" + sign_building + "\",");
		json.append("\"postscript\":\"" + postscript + "\",");
		json.append("\"shipping_id\":\"" + shipping_id + "\",");
		json.append("\"shipping_name\":\"" + shipping_name + "\",");
		json.append("\"pay_id\":\"" + pay_id + "\",");
		json.append("\"pay_name\":\"" + pay_name + "\",");
		json.append("\"how_oos\":\"" + how_oos + "\",");
		json.append("\"goods_amount\":\"" + goods_amount + "\",");
		json.append("\"shipping_fee\":\"" + shipping_fee + "\",");
		json.append("\"order_amount\":\"" + order_amount + "\",");
		json.append("\"session_id\":\"" + session_id + "\",");
		json.append("\"GoodsList\":" + goodsList + "}]");
		return json.toString();
	}

}
