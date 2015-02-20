/**   
 * @Title: OrderInfo.java 
 * @Package cn.com.zhoufu.mouth.constants 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-19 下午4:34:44
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.model;

import java.io.Serializable;

public class OrderInfo implements Serializable {

	private int pay_status;

	private String order_id;

	private double goods_amount;

	private String add_time;

	private String order_sn;

	private int order_status;

	private int shipping_status;

	private String region_name;

	private String region_name1;

	private String consignee;

	private String address;

	private String zipcode;

	private String tel;

	private String mobile;

	private String email;

	private String best_time;

	private String sign_building;

	private String shipping_name;

	private String pay_name;

	private String how_oos;

	private String referer;

	private String privoceName;

	public String getPrivoceName() {
		return privoceName;
	}

	public void setPrivoceName(String privoceName) {
		this.privoceName = privoceName;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public String getRegion_name1() {
		return region_name1;
	}

	public void setRegion_name1(String region_name1) {
		this.region_name1 = region_name1;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
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

	public String getShipping_name() {
		return shipping_name;
	}

	public void setShipping_name(String shipping_name) {
		this.shipping_name = shipping_name;
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

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public int getPay_status() {
		return pay_status;
	}

	public void setPay_status(int pay_status) {
		this.pay_status = pay_status;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public double getGoods_amount() {
		return goods_amount;
	}

	public void setGoods_amount(double goods_amount) {
		this.goods_amount = goods_amount;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public int getShipping_status() {
		return shipping_status;
	}

	public void setShipping_status(int shipping_status) {
		this.shipping_status = shipping_status;
	}

}
