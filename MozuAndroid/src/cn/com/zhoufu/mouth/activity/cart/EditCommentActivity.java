/**   
 * @Title: EditCommentActivity.java 
 * @Package cn.com.zhoufu.mouth.activity.cart 
 * @Description: TODO(发表评论) 
 * @author 王小杰   
 * @date 2014-2-20 上午10:49:49
 * @version V1.0   
 */
package cn.com.zhoufu.mouth.activity.cart;

import java.util.HashMap;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseActivity;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.utils.CheckTextBox;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class EditCommentActivity extends BaseActivity implements OnRatingBarChangeListener {

	private String productId;

	@ViewInject(R.id.right_button2)
	private Button right_button2;

	@ViewInject(R.id.ratingBar)
	private RatingBar ratingBar;

	@ViewInject(R.id.edUseName)
	private TextView edUseName;

	@ViewInject(R.id.edEmail)
	private TextView edEmail;

	@ViewInject(R.id.et_content)
	private TextView et_content;

	int ratingBars = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(this, R.layout.activity_edit_comment);
		setTitle("发表评论");
		initView();
	}

	public void initView() {
		productId = getIntent().getStringExtra("productId");
		right_button2.setVisibility(View.VISIBLE);
		right_button2.setText("确定");
		if (application.getUser().getUser_id() == 0) {
			edUseName.setText("匿名用户");
		} else {
			edUseName.setText(application.getUser().getUser_name()+"");
		}
		ratingBar.setOnRatingBarChangeListener(this);
	}

	@OnClick(R.id.right_button2)
	public void ClickComment(View v) {
		editComment();
	}

	public void editComment() {
		String content = et_content.getText().toString().trim();
		String email = edEmail.getText().toString().trim();
		if (content == null || "".equals(content)) {
			application.showToast("请输入评论内容");
			return;
		}
		if (email == null || "".equals(email)) {
			application.showToast("请输入E-mail");
			return;
		}
		if (CheckTextBox.isEmail(email) == false) {
			application.showToast("请输入正确的E-mail");
			return;
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Comment_Type", 1);
		map.put("ID_Value", productId);
		map.put("Email", email);
		map.put("User_Name", edUseName.getText().toString());
		map.put("Content", content);
		map.put("comment_rank", ratingBars);
		showProgress("正在发表评论");
		Log.i("info", productId);
		WebServiceUtils.callWebService(Constant.S_Comment, map, new WebServiceCallBack() {

			public void callBack(Object result) {
				dismissProgress();
				Log.i("info", result.toString());
				if (result != null) {
					Bean bean = JSON.parseObject(result.toString(), Bean.class);
					if (bean.getState() == 1) {
						application.showToast("发表评论成功");
					} else {
						application.showToast("发表评论失败");
					}
					finish();
				}
			}
		});
	}

	public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
		Log.i("info", rating + "");
		ratingBars = (int) rating;
	}
}