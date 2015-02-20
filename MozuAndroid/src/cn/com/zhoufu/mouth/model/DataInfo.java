/**   
 * @Title: DataInfo.java 
 * @Package cn.com.zhoufu.mouth.model 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-17 下午3:58:56
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.model;

/**
 * @author Administrator
 * 
 */
public class DataInfo {

	private int IsAttention;

	private String starlevel;

	private String picturelist;

	public int getIsAttention() {
		return IsAttention;
	}

	public void setIsAttention(int isAttention) {
		IsAttention = isAttention;
	}

	public String getStarlevel() {
		return starlevel;
	}

	public void setStarlevel(String starlevel) {
		this.starlevel = starlevel;
	}

	public String getPicturelist() {
		return picturelist;
	}

	public void setPicturelist(String picturelist) {
		this.picturelist = picturelist;
	}

}
