/**   
* @Title: CommentInfo.java 
* @Package cn.com.zhoufu.mouth.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 王小杰   
* @date 2014-2-20 上午11:06:25
* @version V1.0   
*/ 

package cn.com.zhoufu.mouth.model;

import java.io.Serializable;

public class CommentInfo implements Serializable {
	
	private String admincomment;
	
	private String user_name;
	
	private String email;
	
	private String add_time;
	
	private String content;

	public String getAdmincomment() {
		return admincomment;
	}

	public void setAdmincomment(String admincomment) {
		this.admincomment = admincomment;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
