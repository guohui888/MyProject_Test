/**   
* @Title: AboutActivity.java 
* @Package cn.com.zhoufu.mouth.activity.setting 
* @Description: TODO(关于) 
* @author 王小杰   
* @date 2014-2-11 下午4:14:36
* @version V1.0   
*/ 

package cn.com.zhoufu.mouth.activity.setting;

import android.os.Bundle;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseActivity;

public class AboutActivity  extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(this, R.layout.activity_about);
		setTitle("关于我们");
	}
}
