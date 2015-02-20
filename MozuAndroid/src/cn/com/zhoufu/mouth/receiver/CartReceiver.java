/**   
* @Title: CartReceiver.java 
* @Package cn.com.zhoufu.mouth.receiver 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 王小杰   
* @date 2014-2-25 下午3:52:12
* @version V1.0   
*/ 

package cn.com.zhoufu.mouth.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import cn.com.zhoufu.mouth.constants.Constant;

public class CartReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final String action = intent.getAction();
		if (action.equals(Constant.CART_CONTENT)) {
			
		}
	}

}
