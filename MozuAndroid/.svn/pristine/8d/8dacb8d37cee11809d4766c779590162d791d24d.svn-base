/**   
 * @Title: WelcomeActivity.java 
 * @Package cn.com.zhoufu.mouth 
 * @Description: TODO(欢迎页面activity) 
 * @author 王小杰
 * @date 2014-2-10 上午1:50:48 
 * @version V1.0   
 */
package cn.com.zhoufu.mouth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import cn.com.zhoufu.mouth.activity.BaseActivity;
import cn.com.zhoufu.mouth.activity.MainActivity;
import cn.com.zhoufu.mouth.utils.ImageUtils;
import cn.com.zhoufu.mozu.R;

public class WelcomeActivity extends BaseActivity {

	public static final String splashImageUrl = "splashImageUrl";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View view = View.inflate(this, R.layout.activity_welcome, null);
		LinearLayout wellcome = (LinearLayout) view
				.findViewById(R.id.app_start_view);
//		check(wellcome);
		setContentView(view);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(WelcomeActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		}, 2000);
	}

	/**
	 * 检查是否需要换图片
	 * 
	 * @param view
	 */
	private void check(LinearLayout view) {

		String path = sharedPreferences.getString(splashImageUrl, null);
		Log.e("tag", "path  " + path);
		if (path == null) {
			return;
		}
		Bitmap bitmap = ImageUtils.getimage(path);
		if (bitmap == null)
			return;
		view.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
	}

	/**
	 * 分析显示的时间
	 * 
	 * @param time
	 * @return
	 */
	private long[] getTime(String time) {
		Log.e("", time);
		long res[] = new long[2];
		try {
			time = time.substring(0, time.indexOf("."));
			String t[] = time.split("-");
			res[0] = Long.parseLong(t[0]);
			if (t.length >= 2) {
				res[1] = Long.parseLong(t[1]);
			} else {
				res[1] = Long.parseLong(t[0]);
			}
		} catch (Exception e) {
		}
		return res;
	}

}
