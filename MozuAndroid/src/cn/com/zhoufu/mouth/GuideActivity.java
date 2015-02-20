/**   
* @Title: GuideActivity.java 
* @Package cn.com.zhoufu.mouth 
* @Description: TODO(引导页activity) 
* @author 王小杰  
* @date 2014-2-10 上午11:51:10 
* @version V1.0   
*/ 
package cn.com.zhoufu.mouth;

import android.os.Bundle;
import cn.com.zhoufu.mouth.activity.BaseActivity;
import cn.com.zhoufu.mozu.R;

public class GuideActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(this,R.layout.activity_guide);
	}
}
