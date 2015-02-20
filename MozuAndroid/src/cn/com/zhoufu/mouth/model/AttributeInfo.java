/**   
* @Title: AttributeInfo.java 
* @Package cn.com.zhoufu.mouth.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 王小杰   
* @date 2014-2-26 下午3:09:47
* @version V1.0   
*/ 

package cn.com.zhoufu.mouth.model;

import java.io.Serializable;

public class AttributeInfo implements Serializable {

	private int goods_id;
	
	private String attr_name;
	
	private String attr_value;

	public int getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	public String getAttr_name() {
		return attr_name;
	}

	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}

	public String getAttr_value() {
		return attr_value;
	}

	public void setAttr_value(String attr_value) {
		this.attr_value = attr_value;
	}
	
	
}
