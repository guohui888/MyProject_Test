package cn.com.zhoufu.mouth.model;

public class AddInfos {

	
	private String city;
	private String district;
	
	private String province;
	private String street;
	private String street_number;
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
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreet_number() {
		return street_number;
	}
	public void setStreet_number(String street_number) {
		this.street_number = street_number;
	}
	@Override
	public String toString() {
		return "AddInfos [city=" + city + ", district=" + district
				+ ", province=" + province + ", street=" + street
				+ ", street_number=" + street_number + "]";
	}
	
	
}
