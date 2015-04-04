/**   
 * @Title: SettingActivity.java 
 * @Package cn.com.zhoufu.mouth.activity.setting 
 * @Description: TODO(设置activity) 
 * @author 王小杰
 * @date 2014-2-10 下午4:33:26 
 * @version V1.0   
 */
package cn.com.zhoufu.mouth.activity.setting;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseActivity;
import cn.com.zhoufu.mouth.adapter.CascadeAdapter;
import cn.com.zhoufu.mouth.model.CascadeInfo;


public class MyWuLiuActivity extends BaseActivity implements OnClickListener  {

	 

	private TextView companyName;
	private Dialog dialog;
	private CascadeAdapter mAdapter;
//	@ViewInject(R.id.wuliu_company_title)
//	private TextView companyTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(this, R.layout.activity_wuliu);
		setTitle("我的物流");
	}

	public void initView(){
		companyName = (TextView)this.findViewById(R.id.wuliu_company_title);
		companyName.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.wuliu_company_title:
			getCompanyName();
//			MenuContorl.getMenuContorl(this).constructListViewDialog();
			break;
		}
	}

	/**
	 * 获取物流公司名字
	 */
	private void getCompanyName() {
		 List<String> data = new ArrayList<String>();
	        data.add("顺丰快递");
	        data.add("申通快递");
	        data.add("圆通快递");
	        data.add("韵达快递");
	        data.add("中通快递");
	        data.add("天天快递");
	        data.add("EMS");

		dialog = new Dialog(mContext, R.style.dialog);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(R.layout.dialog_city);
		final ListView mListView = (ListView) dialog
				.findViewById(R.id.cityListView);
		mAdapter = new CascadeAdapter(mContext);
//		mAdapter.setList(data);
		mListView.setAdapter(mAdapter);
		mListView
				.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(
							AdapterView<?> parent,
							View view, int position, long id) {
						dialog.dismiss();
					}
				});
		dialog.show();
	}

}
