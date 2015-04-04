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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.ZFApplication;
import cn.com.zhoufu.mouth.activity.BaseActivity;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.view.CircleImageView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MyMemberActivity extends BaseActivity {

	@ViewInject(R.id.tvMemberScore)
	private TextView memberScore;

	@ViewInject(R.id.img_grade_member)
	private TextView gradeMember;
	
	@ViewInject(R.id.img_avatar_member)
	private CircleImageView  avatarMember;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(this, R.layout.activity_member);
		setTitle("会员中心");
		initDate();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (application.getUser().getUser_photo().indexOf("http") != -1) {
			application.bitmapUtils.display(avatarMember, application
					.getUser().getUser_photo());
		} else
			application.bitmapUtils.display(avatarMember, Constant.HOST_URLS
					+ application.getUser().getUser_photo());
	}

	private void initDate() {
		getUserGrade();
	}

	private void getUserGrade() {
		ZFApplication mInstance = ZFApplication.getInstance();
		HashMap<String, Object> addData = mInstance.addData();
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("UserID",""+ addData.get("UserID") );
		 
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, "http://www.mzzkd.com/app/members.php",
				params, new RequestCallBack<String>() {

					private String rank_id;
					private String rank_points;

					@Override
					public void onStart() {
						showProgress("正在努力加载中");
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;
						System.out.println(result);
						dismissProgress();
						JSONObject jsonObjSplit = null;
						try {
							jsonObjSplit = new JSONObject(result);
						} catch (JSONException e) {
							e.printStackTrace();
							return;
						}
						String state = (String) jsonObjSplit.optString("state");
						String msg = (String) jsonObjSplit.optString("msg");
						JSONArray optJSONArray = jsonObjSplit.optJSONArray("data");
						if (optJSONArray != null) {
							for (int i = 0; i < optJSONArray.length(); i++) {
								 JSONObject optJSONObject = optJSONArray.optJSONObject(i);
								 rank_id = optJSONObject.optString("rank_id");
								 rank_points = optJSONObject.optString("rank_points");
							}
						}
						if ("1".equals(rank_id)) {//普通用户
							gradeMember.setBackgroundResource(R.drawable.ordinaryusers);
						} else if ("3".equals(rank_id)) {//白银用户
							gradeMember.setBackgroundResource(R.drawable.silverusers);
						} else if ("4".equals(rank_id)) {//黄金用户
							gradeMember.setBackgroundResource(R.drawable.goldusers);
						} else if ("5".equals(rank_id)) {//钻石用户
							gradeMember.setBackgroundResource(R.drawable.diamondusers);
						}
						
						if (TextUtils.isEmpty(rank_points)) {
							memberScore.setText("我的积分："+1000);
						} else {
							memberScore.setText("我的积分："+rank_points);
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						//
					}
				});

	}

}
