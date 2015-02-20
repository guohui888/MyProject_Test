/**   
* @Title: KeywordInfo.java 
* @Package cn.com.zhoufu.mouth.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 王小杰   
* @date 2014-2-14 上午10:00:40
* @version V1.0   
*/ 

package cn.com.zhoufu.mouth.model;

import java.io.Serializable;

public class KeywordInfo implements Serializable {

	private String date;
	
	private String searchengine;
	
	private String keyword;
	
	private int count;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSearchengine() {
		return searchengine;
	}

	public void setSearchengine(String searchengine) {
		this.searchengine = searchengine;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
