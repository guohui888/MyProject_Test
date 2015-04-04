package cn.com.zhoufu.mouth.model;

import java.io.Serializable;

import org.json.JSONObject;


public class SearchProductInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  String one_id;
	private  String one_name;
	
	public SearchProductInfo(JSONObject jsonObject) {
		this.one_id = jsonObject.optString("one_id");
		this.one_name = jsonObject.optString("one_name");// TODO Auto-generated constructor stub
	}
	public String getOne_id() {
		return one_id;
	}
	public void setOne_id(String one_id) {
		this.one_id = one_id;
	}
	public String getOne_name() {
		return one_name;
	}
	public void setOne_name(String one_name) {
		this.one_name = one_name;
	}
	

}
