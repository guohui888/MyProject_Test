/**   
 * @Title: HeadInfo.java 
 * @Package cn.com.zhoufu.mouth.model 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-21 上午11:39:02
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.model;

import java.io.Serializable;

public class HeadInfo implements Serializable {

	private int state;

	private String FilePath;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getFilePath() {
		return FilePath;
	}

	public void setFilePath(String filePath) {
		FilePath = filePath;
	}
	
	
}
