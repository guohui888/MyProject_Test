package cn.com.zhoufu.mouth.model;

public class Bean {

	private String msg;
	private String data;
	private int state;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Bean [msg=" + msg + ", data=" + data + ", state=" + state + "]";
	}
	

}
