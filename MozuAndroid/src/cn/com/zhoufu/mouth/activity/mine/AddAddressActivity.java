/**   
 * @Title: AddAddressActivity.java 
 * @Package cn.com.zhoufu.mouth.activity.mine 
 * @Description: TODO(添加收货地址) 
 * @author 王小杰   
 * @date 2014-2-12 下午4:46:54
 * @version V1.0   
 */
package cn.com.zhoufu.mouth.activity.mine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.tsz.afinal.FinalDb;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zhoufu.mouth.activity.BaseActivity;
import cn.com.zhoufu.mouth.adapter.CascadeAdapter;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.AddreInfo;
import cn.com.zhoufu.mouth.model.AddressInfo;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.model.CascadeInfo;
import cn.com.zhoufu.mouth.utils.AbStrUtil;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;
import cn.com.zhoufu.mozu.R;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class AddAddressActivity extends BaseActivity {

	@ViewInject(R.id.consignee)
	private EditText mEdtConsignee;

	@ViewInject(R.id.email)
	private EditText mEdtEmail;

	@ViewInject(R.id.province)
	private TextView mTvProvince;

	@ViewInject(R.id.city)
	private TextView mTvCity;

	@ViewInject(R.id.place)
	private TextView mTvPlace;

	@ViewInject(R.id.address)
	private EditText mEdtAddress;

	@ViewInject(R.id.zipcode)
	private EditText mEdtZipCode;

	@ViewInject(R.id.tel)
	private EditText mEdtTel;

	@ViewInject(R.id.mobile)
	private EditText mEdtMobile;

	@ViewInject(R.id.sign_building)
	private EditText mEdtSignBuilding;

	@ViewInject(R.id.save_shipping_address)
	private Button mBtnSaveShippingAddress;

	@ViewInject(R.id.base_title)
	private TextView base_title;

	@ViewInject(R.id.left_button)
	Button left_button;

	List<CascadeInfo> list = new ArrayList<CascadeInfo>();

	private Dialog dialog;

	private CascadeAdapter mAdapter;

	private int regionId = 3409;

	private int tag;

	private AddressInfo info;

	private AddreInfo info1;

	private String cityid, placeid, privaceid;

	public String cityname = "", placename = "", privoceName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(this, R.layout.activity_addaddress);
		Intent intent = getIntent();
		tag = Integer.parseInt(intent.getStringExtra("tag"));
		if (tag == 2) {
			base_title.setText("编辑收货地址");
		} else {
			base_title.setText("新增收货地址");
		}
		// 修改
		if (tag == 2) {
			if (application.getUser().getUser_id() != 0) {
				info = (AddressInfo) intent.getSerializableExtra("info");
				mEdtConsignee.setText(info.getConsignee());
				mEdtEmail.setText(info.getEmail());
				mEdtAddress.setText(info.getAddress());
				mEdtZipCode.setText(info.getZipcode());
				mEdtTel.setText(info.getTel());
				mEdtMobile.setText(info.getMobile());
				mEdtSignBuilding.setText(info.getSign_building());
				// -----------------------
				privaceid = info.getProvince();
				cityname = info.getCityName();
				placename = info.getDinstrictName();
				cityid = info.getCity();
				placeid = info.getDistrict();
				privoceName = info.getPrivoceName();
				mTvCity.setText(info.getCityName());
				mTvPlace.setText(info.getDinstrictName());
				mTvProvince.setText(privoceName);
			} else {
				info1 = (AddreInfo) intent.getSerializableExtra("info");
				mEdtConsignee.setText(info1.getConsignee());
				mEdtEmail.setText(info1.getEmail());
				mEdtAddress.setText(info1.getAddress());
				mEdtZipCode.setText(info1.getZipcode());
				mEdtTel.setText(info1.getTel());
				mEdtMobile.setText(info1.getMobile());
				mEdtSignBuilding.setText(info1.getSign_building());
				// --------------------
				mTvProvince.setText(info1.getProviceName());
				mTvCity.setText(info1.getCityName());
				mTvPlace.setText(info1.getPlacename());
				cityname = info1.getCityName();
				placename = info1.getPlacename();
				privoceName = info1.getProviceName();
				privaceid = info1.getProvince();
				cityid = info1.getCity();
				placeid = info1.getDistrict();
			}
		}
	}

	@OnClick(R.id.province)
	public void onClickToSelectProvince(View v) {
		getCity();
	}

	@OnClick(R.id.city)
	public void onClickToSelectCity(View v) {
		getDistrice(privaceid);
	}

	@OnClick(R.id.left_button)
	public void OnclickBack(View v) {
		AddressActivity.index = 0;
		finish();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		AddressActivity.index = 0;
	}

	// 获取省
	private void getCity() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Parent_ID", 1);
		map.put("Region_Type", 1);
		WebServiceUtils.callWebService(Constant.S_Cascade, map,
				new WebServiceCallBack() {

					public void callBack(Object result) {
						Bean bean = JSON.parseObject(result.toString(),
								Bean.class);
						int state = bean.getState();
						switch (state) {
						case 1:
							list = JSON.parseArray(bean.getData(),
									CascadeInfo.class);
							dialog = new Dialog(mContext, R.style.dialog);
							dialog.setCancelable(true);
							dialog.setCanceledOnTouchOutside(true);
							dialog.setContentView(R.layout.dialog_city);
							final ListView mListView = (ListView) dialog
									.findViewById(R.id.cityListView);
							mAdapter = new CascadeAdapter(mContext);
							mAdapter.setList(list);
							mListView.setAdapter(mAdapter);
							mListView
									.setOnItemClickListener(new OnItemClickListener() {

										public void onItemClick(
												AdapterView<?> parent,
												View view, int position, long id) {
											CascadeInfo cascadeInfo = list
													.get(position);
											mTvProvince.setText(cascadeInfo
													.getRegion_name());
											privoceName = cascadeInfo
													.getRegion_name();
											mTvCity.setText("");
											mTvPlace.setText("");
											privaceid = cascadeInfo
													.getRegion_id() + "";
											dialog.dismiss();
										}
									});
							dialog.show();
							break;
						default:
							break;
						}
					}
				});
	}

	@OnClick(R.id.place)
	public void onClickToSelectPlace(View v) {
		if (cityname.equals("")) {
			application.showToast("请选择地区");
			return;
		}
		getPlace(cityid);
	}

	// 获取省份城市
	private void getDistrice(String privaceID) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Parent_ID", privaceID);
		map.put("Region_Type", 2);
		showProgress("正在获取");
		WebServiceUtils.callWebService(Constant.S_Cascade, map,
				new WebServiceCallBack() {

					public void callBack(Object result) {
						dismissProgress();
						Bean bean = JSON.parseObject(result.toString(),
								Bean.class);
						int state = bean.getState();
						switch (state) {
						case 1:
							list = JSON.parseArray(bean.getData(),
									CascadeInfo.class);
							dialog = new Dialog(mContext, R.style.dialog);
							dialog.setCancelable(true);
							dialog.setCanceledOnTouchOutside(true);
							dialog.setContentView(R.layout.dialog_city);
							final ListView mListView = (ListView) dialog
									.findViewById(R.id.cityListView);
							mAdapter = new CascadeAdapter(mContext);
							mAdapter.setList(list);
							mListView.setAdapter(mAdapter);
							mListView
									.setOnItemClickListener(new OnItemClickListener() {

										public void onItemClick(
												AdapterView<?> parent,
												View view, int position, long id) {
											CascadeInfo cascadeInfo = list
													.get(position);
											mTvCity.setText(cascadeInfo
													.getRegion_name());
											mTvPlace.setText("");

											cityname = cascadeInfo
													.getRegion_name();
											cityid = cascadeInfo.getRegion_id()
													+ "";
											Log.e("", "tag  ===cityid="
													+ cityid + "   cityname="
													+ cityname);
											dialog.dismiss();
										}
									});
							dialog.show();
							break;
						default:
							break;
						}
					}
				});
	}

	/**
	 * 获取地区ID
	 * 
	 * @param regionId
	 */
	private void getPlace(String regionId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Parent_ID", regionId);
		map.put("Region_Type", 3);
		showProgress("正在获取");
		WebServiceUtils.callWebService(Constant.S_Cascade, map,
				new WebServiceCallBack() {
					public void callBack(Object result) {
						dismissProgress();
						Bean bean = JSON.parseObject(result.toString(),
								Bean.class);
						int state = bean.getState();
						switch (state) {
						case 1:
							list = JSON.parseArray(bean.getData(),
									CascadeInfo.class);
							dialog = new Dialog(mContext, R.style.dialog);
							dialog.setCancelable(true);
							dialog.setCanceledOnTouchOutside(true);
							dialog.setContentView(R.layout.dialog_city);
							final ListView mListView = (ListView) dialog
									.findViewById(R.id.cityListView);

							mAdapter = new CascadeAdapter(mContext);
							mAdapter.setList(list);
							mListView.setAdapter(mAdapter);
							mListView
									.setOnItemClickListener(new OnItemClickListener() {
										public void onItemClick(
												AdapterView<?> parent,
												View view, int position, long id) {
											mTvPlace.setText(list.get(position)
													.getRegion_name());
											placename = list.get(position)
													.getRegion_name();
											placeid = list.get(position)
													.getRegion_id() + "";
											Log.e("", "tag  placeid=" + placeid
													+ "    			placename="
													+ placename);

											dialog.dismiss();
										}
									});
							dialog.show();
							break;
						default:
							break;
						}
					}
				});
	}

	@OnClick(R.id.save_shipping_address)
	public void onClickToSaveShippingAddress(View v) {
		String consignee = mEdtConsignee.getText().toString().trim();
		String email = mEdtEmail.getText().toString().trim();
		String address = mEdtAddress.getText().toString().trim();
		String zipCode = mEdtZipCode.getText().toString().trim();
		String tel = mEdtTel.getText().toString().trim();
		String mobile = mEdtMobile.getText().toString().trim();
		String signBuilding = mEdtSignBuilding.getText().toString().trim();
		if (AbStrUtil.isEmpty(consignee)) {
			showToast("请输入收货人");
			return;
		}
		if (AbStrUtil.isEmpty(address)) {
			showToast("请输入详细地址");
			return;
		}
		if (AbStrUtil.isEmpty(tel)) {
			showToast("请输入手机号码");
			return;
		}
		if (!AbStrUtil.isMobileNo(tel)) {
			showToast("手机号码格式不正确");
			return;
		}

		if (cityname.equals("")) {
			application.showToast("请选择地区");
			return;
		}
		if (placename.equals("")) {
			showToast("请选择学府");
			return;
		}
		if (application.getUser().getUser_id() != 0) {
			StringBuffer json = new StringBuffer();
			json.append("[{\"user_id\":\"" + application.getUser().getUser_id()
					+ "\",");
			json.append("\"consignee\":\"" + consignee + "\",");
			json.append("\"email\":\"" + email + "\",");
			json.append("\"country\":\"" + 0 + "\",");
			json.append("\"province\":\"" + privaceid + "\",");
			json.append("\"city\":\"" + cityid + "\",");
			json.append("\"district\":\"" + placeid + "\",");
			json.append("\"address\":\"" + address + "\",");
			json.append("\"zipcode\":\"" + zipCode + "\",");
			json.append("\"tel\":\"" + tel + "\",");
			json.append("\"mobile\":\"" + mobile + "\",");
			json.append("\"sign_building\":\"" + signBuilding + "\",");
			json.append("\"best_time\":\"" + "" + "\"}]");

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("json", json.toString());
			WebServiceUtils.callWebService(Constant.S_ShippingAddress, map,
					new WebServiceCallBack() {
						public void callBack(Object result) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							int state = bean.getState();
							switch (state) {
							case 1:
								if (tag == 2) {
									showToast("编辑收货地址成功");
								} else {
									showToast("添加收货地址成功");
								}
								finish();
								AddressActivity.index = 0;
								break;
							default:
								break;
							}
						}
					});
		} else {
			FinalDb db = FinalDb.create(this);
			AddreInfo addressInfo = new AddreInfo();
			addressInfo.setConsignee(consignee);
			addressInfo.setDistrict(placeid);
			addressInfo.setEmail(email);
			addressInfo.setAddress(address);
			addressInfo.setZipcode(zipCode);
			addressInfo.setTel(tel);
			addressInfo.setMobile(mobile);
			addressInfo.setSign_building(signBuilding);
			addressInfo.setCity(cityid);
			addressInfo.setProvince(privaceid);
			addressInfo.setProviceName(privoceName);
			addressInfo.setCityName(cityname);
			addressInfo.setPlacename(placename);
			db.save(addressInfo);
			finish();
		}
		if (tag == 2) {// 更新
			if (application.getUser().getUser_id() != 0) {
				HashMap<String, Object> map;
				map = new HashMap<String, Object>();
				map.put("UserID", application.getUser().getUser_id());
				map.put("Address_ID", info.getAddress_id());
				WebServiceUtils.callWebService(
						Constant.S_DeleteShippingAddress, map,
						new WebServiceCallBack() {
							public void callBack(Object result) {
								Bean bean = JSON.parseObject(result.toString(),
										Bean.class);
								int state = bean.getState();
								switch (state) {
								case 1:
									mAdapter.notifyDataSetChanged();
									break;
								case 0:
									break;
								default:
									break;
								}
							}
						});
			} else {
				FinalDb db = FinalDb.create(this);
				db.delete(info1);
				list.remove(info1);
				mAdapter.setList(list);
			}
		}
	}
}
