/**   
 * @Title: SettingActivity.java 
 * @Package cn.com.zhoufu.mouth.activity.setting 
 * @Description: TODO(设置activity) 
 * @author 王小杰
 * @date 2014-2-10 下午4:33:26 
 * @version V1.0   
 */
package cn.com.zhoufu.mouth.activity.setting;

import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseActivity;
import cn.com.zhoufu.mouth.activity.mine.LoginActivity;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.utils.DataCleanManager;
import cn.com.zhoufu.mouth.utils.XMLParser;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MyMessageActivity extends BaseActivity {

	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(this, R.layout.activity_message);
		setTitle("我的消息");
	}

	 

}
