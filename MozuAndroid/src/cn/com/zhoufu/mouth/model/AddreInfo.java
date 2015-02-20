package cn.com.zhoufu.mouth.model;

import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

@Table(name = "AddreInfo")
public class AddreInfo implements Serializable {
	@Id
	private int id;
	private String consignee;
	private String email;
	private String address;
	private String zipcode;
	private String tel;
	private String mobile;
	private String sign_building;
	private String country;
	private String province;
	private String city;
	private String CityName;
	private String DinstrictName;
	private String district;
	private String best_time;
	private String cityid;
	private String placeid;
	private String placename;
	private String proviceName;

	public String getProviceName() {
		return proviceName;
	}

	public void setProviceName(String proviceName) {
		this.proviceName = proviceName;
	}

	private int DEVICE_ID;

	public int getDEVICE_ID() {
		return DEVICE_ID;
	}

	public void setDEVICE_ID(int dEVICE_ID) {
		DEVICE_ID = dEVICE_ID;
	}

	public String getPlacename() {
		return placename;
	}

	public void setPlacename(String placename) {
		this.placename = placename;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getPlaceid() {
		return placeid;
	}

	public void setPlaceid(String placeid) {
		this.placeid = placeid;
	}

	public String getCityName() {
		return CityName;
	}

	public void setCityName(String cityName) {
		CityName = cityName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getSign_building() {
		return sign_building;
	}

	public void setSign_building(String sign_building) {
		this.sign_building = sign_building;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDinstrictName() {
		return DinstrictName;
	}

	public void setDinstrictName(String dinstrictName) {
		DinstrictName = dinstrictName;
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

	public String getBest_time() {
		return best_time;
	}

	public void setBest_time(String best_time) {
		this.best_time = best_time;
	}

	@Override
	public String toString() {
		return "AddreInfo [id=" + id + ", consignee=" + consignee + ", email="
				+ email + ", address=" + address + ", zipcode=" + zipcode
				+ ", tel=" + tel + ", mobile=" + mobile + ", sign_building="
				+ sign_building + ", country=" + country + ", province="
				+ province + ", city=" + city + ", CityName=" + CityName
				+ ", DinstrictName=" + DinstrictName + ", district=" + district
				+ ", best_time=" + best_time + ", cityid=" + cityid
				+ ", placeid=" + placeid + ", placename=" + placename + "]";
	}

}
