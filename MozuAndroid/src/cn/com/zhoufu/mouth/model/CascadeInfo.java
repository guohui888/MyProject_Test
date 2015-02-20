package cn.com.zhoufu.mouth.model;

import java.io.Serializable;

public class CascadeInfo implements Serializable {

	private int region_id;
	private int parent_id;
	private String region_name;
	private String region_type;
	private String agency_id;
	private String is_quan;
	private String is_xiaofei;
	private int sorder;

	public int getRegion_id() {
		return region_id;
	}

	public void setRegion_id(int region_id) {
		this.region_id = region_id;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public String getRegion_type() {
		return region_type;
	}

	public void setRegion_type(String region_type) {
		this.region_type = region_type;
	}

	public String getAgency_id() {
		return agency_id;
	}

	public void setAgency_id(String agency_id) {
		this.agency_id = agency_id;
	}

	public String getIs_quan() {
		return is_quan;
	}

	public void setIs_quan(String is_quan) {
		this.is_quan = is_quan;
	}

	public String getIs_xiaofei() {
		return is_xiaofei;
	}

	public void setIs_xiaofei(String is_xiaofei) {
		this.is_xiaofei = is_xiaofei;
	}

	public int getSorder() {
		return sorder;
	}

	public void setSorder(int sorder) {
		this.sorder = sorder;
	}

}
