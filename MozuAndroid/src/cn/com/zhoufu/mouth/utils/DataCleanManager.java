/**   
* @Title: DataCleanManager.java 
* @Package cn.com.zhoufu.mouth.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 王小杰   
* @date 2014-2-20 上午9:31:21
* @version V1.0   
*/ 

package cn.com.zhoufu.mouth.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import cn.com.zhoufu.mouth.ZFApplication;

public class DataCleanManager {

	/**
	 * 清除app缓存
	 * 
	 * @param activity
	 */
	public static void clearAppCache(final Activity activity) {
		final ZFApplication application = (ZFApplication) activity.getApplication();
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					application.showToast("缓存清除成功");
				} else {
					application.showToast("缓存清除失败");
				}
			}
		};
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					application.clearAppCache();
					msg.what = 1;
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = -1;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

}
