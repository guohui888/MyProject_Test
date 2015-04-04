/**   
 * @Title: SearchFragment.java 
 * @Package cn.com.zhoufu.mouth.activity.search 
 * @Description: TODO(搜索fragment) 
 * @author 王小杰
 * @date 2014-2-11 下午1:56:12 
 * @version V1.0   
 */
package cn.com.zhoufu.mouth.activity.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseFragment;
import cn.com.zhoufu.mouth.adapter.HistoricalAdapter;
import cn.com.zhoufu.mouth.adapter.KeyWordAdapter;
import cn.com.zhoufu.mouth.adapter.ProductNameAdapter;
import cn.com.zhoufu.mouth.adapter.SearchAdapter;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.model.HistoricalInfo;
import cn.com.zhoufu.mouth.model.KeywordInfo;
import cn.com.zhoufu.mouth.model.SearchInfo;
import cn.com.zhoufu.mouth.model.SearchProductInfo;
import cn.com.zhoufu.mouth.utils.ListViewUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;
import cn.com.zhoufu.mouth.view.pullview.AbOnListViewListener;
import cn.com.zhoufu.mouth.view.pullview.AbPullListView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

public class SearchFragment extends BaseFragment implements TextWatcher,
		AbOnListViewListener, OnClickListener {

	@ViewInject(R.id.searchFragmentsearchListView)
	private AbPullListView mListView;
	
	private SearchAdapter mAdapterSearch;

	private List<SearchInfo> listSearch;
	@ViewInject(R.id.tv_myText)
	private TextView tvMyText;
	
	private ListPopupWindow lpw;
	
	@ViewInject(R.id.clearHistorical)
	private Button clearBtn;

	@ViewInject(R.id.scrollView)
	private ScrollView scrollView;

	@ViewInject(R.id.searchBtn)
	private Button searchBtn;

	@ViewInject(R.id.searchContent)
	private EditText searchContent;

	@ViewInject(R.id.ivSwing)
	private ImageView ivSwing;

	@ViewInject(R.id.ly_swing)
	private LinearLayout ly_swing;

	@ViewInject(R.id.tv_product)
	private TextView tvProduct;
	
	@ViewInject(R.id.tv_product_size)
	private TextView tvProductSize;
	
	@ViewInject(R.id.tv_product_fanwei)
	private TextView tvProductFanwei;
	
	@ViewInject(R.id.tvSensor)
	private TextView tvSensor;

	private HistoricalAdapter mAdapter;

	private KeyWordAdapter mAdapter2;

	private List<KeywordInfo> list;

	@ViewInject(R.id.keywordListView)
	private AbPullListView AbPullListView;

	int pageIndex = 1, pageSize = 20;

	String keyword;

	// 声音提示
	private static SoundPool soundPool;

	// 声音集合
	private static Map<Integer, Integer> soundMap;

	// 系统音量
	private static float volume = 0f;

	int tag = 2;
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				break;
			case 1:
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressWarnings("static-access")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_search, container, false);
		ViewUtils.inject(this, view);
		searchContent.addTextChangedListener(this);
		// sensorManager = (SensorManager) getActivity().getSystemService(
		// getActivity().SENSOR_SERVICE);
		// vibrator = (Vibrator) getActivity().getSystemService(
		// getActivity().VIBRATOR_SERVICE);
		// initSounds();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initDate();
	}

	/**
	 * 
	 */
	public void initView() {
		mListView.setEmptyView(tvMyText );
		mAdapterSearch = new SearchAdapter(handler, mContext);
		listSearch = new ArrayList<SearchInfo>();
		mAdapterSearch.setList(listSearch);
		mListView.setAdapter(mAdapter);

	}

	@OnItemClick(R.id.listview)
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(mContext, SearchResultActivity.class);
		intent.putExtra("keyword", dbUtils.queryHistorical().get(arg2)
				.getName());
		Log.i("info", dbUtils.queryHistorical().get(arg2).getName());
		startActivity(intent);
	}

	@OnItemClick(R.id.keywordListView)
	public void onItemClicks(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		HistoricalInfo info = new HistoricalInfo();
		info.setName(list.get(arg2).getKeyword());
		dbUtils.addHistorical(info);
		Intent intent = new Intent(mContext, SearchResultActivity.class);
		intent.putExtra("keyword", list.get((int) arg3).getKeyword());
		Log.i("info", list.get(arg2).getKeyword());
		startActivity(intent);
	}

	@SuppressLint("NewApi")
	public void initDate() {
		for (int i = 0; i < dbUtils.queryHistorical().size(); i++) {
			Log.i("info", dbUtils.queryHistorical().get(i).getName());
		}
		if (dbUtils.queryHistorical().size() == 0) {
			scrollView.setVisibility(View.GONE);
		} else {
			mAdapter = new HistoricalAdapter(mContext);
			mAdapter.setList(dbUtils.queryHistorical());
			// mListView.setAdapter(mAdapter);
//			ListViewUtils.setListViewHeightBasedOnChildren(mListView);
		}
		mAdapter2 = new KeyWordAdapter(mContext);
		list = new ArrayList<KeywordInfo>();
		mAdapter2.setList(list);
		AbPullListView.setAdapter(mAdapter2);

		AbPullListView.setPullLoadEnable(true);
		AbPullListView.setPullRefreshEnable(true);
		AbPullListView.setAbOnListViewListener(this);
		
		lpw = new ListPopupWindow(mContext);
	}

	@Override
	public void onResume() {
		super.onResume();
		// if (sensorManager != null) {// 注册监听器
		// // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
		// sensorManager.registerListener(sensorEventListener,
		// sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
		// SensorManager.SENSOR_DELAY_NORMAL);
		// }
	}

	@Override
	public void onStop() {
		super.onStop();
		// if (sensorManager != null) {// 取消监听器
		// sensorManager.unregisterListener(sensorEventListener);
		// }
	}

	@OnClick(R.id.searchBtn)
	public void searchClick(View v) {
		String content = searchContent.getText().toString().trim();
		if ("".equals(content)) {
			application.showToast("请输入搜索内容");
			return;
		}
		HistoricalInfo info = new HistoricalInfo();
		info.setName(content);
		dbUtils.addHistorical(info);
		Intent intent = new Intent(mContext, SearchResultActivity.class);
		intent.putExtra("keyword", content);
		searchContent.setText("");
		startActivity(intent);
	}
	
	@OnClick(R.id.tv_product)
	public void productClick(View v) {
		getProductDate(v);
//		showWindow(v,1);
	}

	/**
	 * 获取产品名称列表数据
	 * @param v
	 */
	private void getProductDate(final View v) {
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET,
		    "http://www.mzzkd.com/app/search_one.php",
		    new RequestCallBack<String>(){
		        @Override
		        public void onLoading(long total, long current, boolean isUploading) {
		        	showProgress("正在努力加载中");
//		            testTextView.setText(current + "/" + total);
		        }

		        @Override
		        public void onSuccess(ResponseInfo<String> responseInfo) {
//		            textView.setText(responseInfo.result);
		        	dismissProgress();
		        	String jsonData = responseInfo.result;
		        	System.out.println(jsonData);
		        	JSONObject jsonObjSplit = null;
					try {
						jsonObjSplit =new JSONObject(jsonData);
					} catch (JSONException e) {
						e.printStackTrace();
						return;
					}
					List<SearchProductInfo> producetNames = new ArrayList<SearchProductInfo>();
		        	JSONArray parseArray = jsonObjSplit.optJSONArray("search_one");  
					if (null != parseArray) {
						for (int i = 0; i < parseArray.length(); i++) {
							JSONObject jsonObject = parseArray
									.optJSONObject(i);
							if (null != jsonObject) {
								producetNames.add(new SearchProductInfo(jsonObject));
							}
						}
					}
					
					showWindow2(v, producetNames);
					
		        }

		        @Override
		        public void onStart() {
		        }

		        @Override
		        public void onFailure(HttpException error, String msg) {
		        }
		});
	}

	
	@OnClick(R.id.tv_product_size)
	public void productFanweiClick(View v) {
		  // 加载数据  
	      List<String>  size = new ArrayList<String>();  
	      size.add("128*128");  
	      size.add("256*128");  
	      size.add("160*160");  
	      size.add("320*160");  
	      size.add("192*192");  
	      size.add("384*192");  
		showWindow(v,size,2);
	}
	@OnClick(R.id.tv_product_fanwei)
	public void productSizeClick(View v) {
		  // 加载数据  
	      List<String>  Ranges = new ArrayList<String>();  
	      Ranges.add("0-100片");  
	      Ranges.add("100-200片");  
	      Ranges.add("200-300片");  
	      Ranges.add("300-400片");  
	      Ranges.add("400-500片");  
	      Ranges.add("500-600片");  
	      Ranges.add("600-700片");  
	      Ranges.add("700-800片");  
		showWindow(v,Ranges,3);
	}
	/**
	 * @param v
	 * @param i
	 */
	private void showWindow2(View view, List<SearchProductInfo> list) {
		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(mContext).inflate(
				R.layout.pop_window, null);
		ListView lv_group = (ListView) contentView.findViewById(R.id.lvGroup);

		final ProductNameAdapter groupAdapter = new ProductNameAdapter(mContext,list) ;
		lv_group.setAdapter(groupAdapter);

		final PopupWindow popupWindow = new PopupWindow(contentView, 230, LayoutParams.WRAP_CONTENT,
				true);

		popupWindow.setTouchable(true);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.menu_bg));
		popupWindow.showAsDropDown(view);
		lv_group.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				SearchProductInfo  item = (SearchProductInfo) groupAdapter.getItem(position);
				tvProduct.setText(item.getOne_name());
				popupWindow.dismiss();
				
				getProductDetailData(item);
			}

		});
	}
	
	/**
	 * @param v
	 * @param i
	 */
	private void showWindow(View view, List<String> list,final int type) {
		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(mContext).inflate(
				R.layout.pop_window, null);
		ListView lv_group = (ListView) contentView.findViewById(R.id.lvGroup);

		final GroupAdapter groupAdapter = new GroupAdapter(mContext, list);
		lv_group.setAdapter(groupAdapter);

		final PopupWindow popupWindow = new PopupWindow(contentView, 200, LayoutParams.WRAP_CONTENT,
				true);

		popupWindow.setTouchable(true);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.menu_bg));
		popupWindow.showAsDropDown(view);
		lv_group.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String  item = (String) groupAdapter.getItem(position);
				if (2 == type) {
					tvProductSize.setText(item);
				} else {
					tvProductFanwei.setText(item);
				}
				popupWindow.dismiss();
				getProductDetailData(null);
			}

		});
	}
	
	/**
	 * @param item
	 * 获取产品列表
	 */
	public void getProductDetailData(SearchProductInfo  item) {
		
		String size = tvProductFanwei.getText().toString().trim();//范围
		String scope = tvProductSize.getText().toString().trim() ;//尺寸
		String goods_one = "" ;
		if (null != item) {//有产品尺寸
			goods_one = item.getOne_id();
		} else if ("产品".equals(size)) {
			goods_one ="";
		}
		if ("范围".equals(size)) {
			size ="";
		}
		if ("尺寸".equals(scope)) {
			scope ="";
		}
		
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("goods_one", goods_one);
		params.addQueryStringParameter("size", size);
		params.addQueryStringParameter("scope", scope);
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, "http://www.mzzkd.com/app/search_goods.php", params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
						// testTextView.setText("conn...");
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						showProgress("正在努力加载中");
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;
						System.out.println(result);
						dismissProgress();
//						JSONObject jsonObjSplit = null;
//						try {
//							jsonObjSplit =new JSONObject(result);
//						} catch (JSONException e) {
//							e.printStackTrace();
//							return;
//						}
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							List<SearchInfo> list = JSON.parseArray(
									bean.getData(), SearchInfo.class);
							for (int i = 0; i < list.size(); i++) {
								Log.e("tag", list.get(i).getPresenttime()
										+ "  "
										+ list.get(i).getPromote_end_date()
										+ "  "
										+ list.get(i).getPromote_start_date());
							}
							mAdapterSearch.addAll(list);
						}
//						List<SearchInfo> listSearch = new ArrayList<SearchInfo>();
//			        	JSONArray parseArray = jsonObjSplit.optJSONArray("goods_con");  
//						if (null != parseArray) {
//							for (int i = 0; i < parseArray.length(); i++) {
//								JSONObject jsonObject = parseArray
//										.optJSONObject(i);
//								if (null != jsonObject) {
//									listSearch.add(new SearchInfo(jsonObject));
//								}
//							}
//						}
//						testTextView.setText("reply: " + responseInfo.result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
//						 
					}
				});
	}
	@OnClick(R.id.clearHistorical)
	public void clearClick(View v) {
		dbUtils.deleteHistorical();
		mAdapter.removeAll();
		initDate();
	}

	@OnClick(R.id.ivSwing)
	public void swingClick(View v) {
		if (scrollView.getVisibility() == View.VISIBLE) {
			scrollView.setVisibility(View.GONE);
			ly_swing.setVisibility(View.VISIBLE);
		} else {
			scrollView.setVisibility(View.VISIBLE);
			ly_swing.setVisibility(View.GONE);
		}
	}

	public void afterTextChanged(Editable s) {

	}

	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		keyword = s.toString();
		// if (s.toString() == null || "".equals(s.toString())) {
		// Log.i("info", "空");
		// AbPullListView.setVisibility(View.GONE);
		// mListView.setVisibility(View.VISIBLE);
		// clearBtn.setVisibility(View.VISIBLE);
		// } else {
		// Log.i("info", "NO空");
		// AbPullListView.setVisibility(View.VISIBLE);
		// mListView.setVisibility(View.GONE);
		// clearBtn.setVisibility(View.GONE);
		// }
		// mAdapter2.removeAll();
		// keyword(keyword, pageIndex, pageSize);
	}

	public void keyword(String content, int pageIndex, int pageSize) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", content);
		map.put("page", pageIndex);
		map.put("count", pageSize);
		WebServiceUtils.callWebService(Constant.S_Keywords, map,
				new WebServiceCallBack() {

					public void callBack(Object result) {
						Log.i("info", result.toString());
						if (result != null) {
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							List<KeywordInfo> list = JSON.parseArray(
									bean.getData(), KeywordInfo.class);
							mAdapter2.addAll(list);
						}
					}
				});
	}

	public void onRefresh() {
		pageIndex = 1;
		mAdapter2.removeAll();
		keyword(keyword, pageIndex, pageSize);
		AbPullListView.stopRefresh();
	}

	public void onLoadMore() {
		pageIndex++;
		keyword(keyword, pageIndex, pageSize);
		AbPullListView.stopLoadMore();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	// 重力感应监听
	// private SensorEventListener sensorEventListener = new
	// SensorEventListener() {
	//
	// public void onSensorChanged(SensorEvent event) {
	// if (ly_swing.getVisibility() == View.VISIBLE && tag == 2) {
	// // 传感器信息改变时执行该方法
	// float[] values = event.values;
	// float x = values[0]; // x轴方向的重力加速度，向右为正
	// float y = values[1]; // y轴方向的重力加速度，向前为正
	// float z = values[2]; // z轴方向的重力加速度，向上为正
	// int medumValue = 18;
	// if (Math.abs(x) > medumValue || Math.abs(y) > medumValue
	// || Math.abs(z) > medumValue) {
	// tag = 1;
	// playSound();
	// vibrator.vibrate(500);
	// Message msg = new Message();
	// msg.what = SENSOR_SHAKE;
	// Log.e("", "3333333333333");
	// handler.sendMessage(msg);
	// }
	// }
	//
	// }
	//
	// public void onAccuracyChanged(Sensor sensor, int accuracy) {
	//
	// }
	// };
	//
	// /**
	// * 动作执行
	// */
	// private Handler handler = new Handler() {
	// @Override
	// public void handleMessage(Message msg) {
	// super.handleMessage(msg);
	// switch (msg.what) {
	// case SENSOR_SHAKE:
	// tvSensor.setText("正在搜索商品。。。。");
	// sensor();
	// break;
	// }
	// }
	// };
	//
	// public void sensor() {
	// WebServiceUtils.callWebService(Constant.S_SharkItOff, null,
	// new WebServiceCallBack() {
	//
	// public void callBack(Object result) {
	// Log.i("info", result.toString());
	// if (result != null) {
	// Bean bean = JSON.parseObject(result.toString(),
	// Bean.class);
	// if (bean.getState() == 1) {
	// List<SearchInfo> list = JSON.parseArray(
	// bean.getData(), SearchInfo.class);
	// if (list.size() > 0) {
	// info = list.get(0);
	// dialog(info);
	// } else {
	// tvSensor.setText("暂无合适商品,重新试试..");
	// tag = 2;
	// }
	// }
	// }
	// }
	// });
	// }
	//
	// public void dialog(SearchInfo info) {
	// if (dialog == null) {
	// Log.e("", "-=================");
	// dialog = new Dialog(mContext, R.style.dialog);
	// dialog.setContentView(R.layout.dialog_sensor);
	// dialog.setCancelable(false);
	// dialog.setCanceledOnTouchOutside(false);
	// TextView goodsName = (TextView) dialog.findViewById(R.id.goodsName);
	// TextView tvGoodsPrice = (TextView) dialog
	// .findViewById(R.id.tvGoodsPrice);
	// ImageView imageUrl = (ImageView) dialog.findViewById(R.id.imageUrl);
	// Button GoodsBtn = (Button) dialog.findViewById(R.id.GoodsBtn);
	// ImageView close_dialog = (ImageView) dialog
	// .findViewById(R.id.close_dialog);
	// goodsName.setText(info.getGoods_name());
	// tvGoodsPrice.setText("￥" + info.getShop_price());
	// application.bitmapUtils.display(imageUrl,
	// Constant.IMAGE_URL + info.getGoods_thumb());
	//
	// GoodsBtn.setOnClickListener(this);
	// close_dialog.setOnClickListener(this);
	//
	// WindowManager m = ((Activity) mContext).getWindowManager();
	// Display d = m.getDefaultDisplay();
	// WindowManager.LayoutParams params = dialog.getWindow()
	// .getAttributes();
	// params.width = (int) (d.getWidth() * 0.99);
	// params.height = (int) (d.getHeight() * 0.7);
	// } else {
	// dialog.dismiss();
	// tag = 2;
	// }
	// dialog.show();
	// tag = 1;
	// }
	//
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.close_dialog:
	// tag = 2;
	// dialog.dismiss();
	// tvSensor.setText("摇一摇，摇出你想要的");
	// break;
	// case R.id.GoodsBtn:
	// tag = 2;
	// dialog.dismiss();
	// Intent intent = new Intent(mContext, ProductDetailActivity.class);
	// intent.putExtra("info", info);
	// intent.putExtra("price", "￥" + info.getShop_price());
	// startActivity(intent);
	// tvSensor.setText("摇一摇，摇出你想要的");
	// break;
	// }
	// }
	//
	// private void initSounds() {
	// soundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 100);
	// soundMap = new HashMap<Integer, Integer>();
	// AudioManager audioManager = (AudioManager) getActivity()
	// .getSystemService(getActivity().AUDIO_SERVICE);
	// // 获取音量
	// float streamVolumeCurrent = audioManager
	// .getStreamVolume(AudioManager.STREAM_MUSIC);
	// float streamVolumeMax = audioManager
	// .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	// volume = streamVolumeCurrent / streamVolumeMax;
	// soundMap.put(1, soundPool.load(getActivity(), R.raw.shake, 100));
	// }
	//
	// public static void playSound() {
	// try {
	// if (soundPool != null) {
	// soundPool.play(soundMap.get(1), volume, volume, 100, 0, 1f);
	// }
	// } catch (Exception e) {
	// }
	// }
}

class GroupAdapter extends BaseAdapter {  
	  
    private Context context;  
  
    private List<String> list;  
  
    public GroupAdapter(Context context, List<String> list) {  
  
        this.context = context;  
        this.list = list;  
    }  
  
    @Override  
    public int getCount() {  
        return list.size();  
    }  
  
    @Override  
    public Object getItem(int position) {  
  
        return list.get(position);  
    }  
  
    @Override  
    public long getItemId(int position) {  
        return position;  
    }  
  
    @Override  
    public View getView(int position, View convertView, ViewGroup viewGroup) {  
  
          
        ViewHolder holder;  
        if (convertView==null) {  
            convertView=LayoutInflater.from(context).inflate(R.layout.group_item_view, null);  
            holder=new ViewHolder();  
              
            convertView.setTag(holder);  
              
            holder.groupItem=(TextView) convertView.findViewById(R.id.groupItem);  
              
        }  
        else{  
            holder=(ViewHolder) convertView.getTag();  
        }  
        holder.groupItem.setTextColor(Color.BLACK);  
        holder.groupItem.setText(list.get(position));  
          
        return convertView;  
    }  
  
    static class ViewHolder {  
        TextView groupItem;  
    }  
  
}  