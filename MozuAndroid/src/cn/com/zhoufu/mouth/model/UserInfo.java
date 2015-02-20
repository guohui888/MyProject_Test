/**   
 * @Title: UserInfo.java 
 * @Package cn.com.zhoufu.mouth.model 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-14 上午11:55:04
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.model;

import java.io.Serializable;

public class UserInfo implements Serializable {

	private float credit_line;

	private int user_id;

	private String email;

	private String user_name;

	private int sex;

	private int address_id;

	private String mobile_phone;

	private String user_photo;

	private float user_money;

	private float frozen_money;

	private int isSignin;// 是否签到

	private int user_rank;// 登录等级

	public int getUser_rank() {
		return user_rank;
	}

	public void setUser_rank(int user_rank) {
		this.user_rank = user_rank;
	}

	public int getIsSignin() {
		return isSignin;
	}

	public void setIsSignin(int isSignin) {
		this.isSignin = isSignin;
	}

	public float getUser_money() {
		return user_money;
	}

	public float getCredit_line() {
		return credit_line;
	}

	public void setCredit_line(float credit_line) {
		this.credit_line = credit_line;
	}

	public void setUser_money(float user_money) {
		this.user_money = user_money;
	}

	public float getFrozen_money() {
		return frozen_money;
	}

	public void setFrozen_money(float frozen_money) {
		this.frozen_money = frozen_money;
	}

	public String getUser_photo() {
		return user_photo;
	}

	public void setUser_photo(String user_photo) {
		this.user_photo = user_photo;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getSex() {
		return sex;
	}

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getMobile_phone() {
		return mobile_phone;
	}

	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

	@Override
	public String toString() {
		return "UserInfo [user_id=" + user_id + ", email=" + email
				+ ", user_name=" + user_name + ", sex=" + sex
				+ ", mobile_phone=" + mobile_phone + ", user_photo="
				+ user_photo + "]";
	}

}
