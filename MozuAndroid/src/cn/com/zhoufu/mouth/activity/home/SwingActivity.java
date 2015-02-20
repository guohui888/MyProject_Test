package cn.com.zhoufu.mouth.activity.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseActivity;
import cn.com.zhoufu.mouth.activity.cart.ProductDetailActivity;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.model.SearchInfo;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;

/**
 * 摇一摇
 * 
 * @author RCP
 * 
 */
public class SwingActivity extends BaseActivity implements OnClickListener {
	@ViewInject(R.id.ly_swing)
	private LinearLayout ly_swing;

	@ViewInject(R.id.tvSensor)
	private TextView tvSensor;
	private SensorManager sensorManager;

	private Vibrator vibrator;

	private Dialog dialog;
	private static final int SENSOR_SHAKE = 10;
	// 声音提示
	private static SoundPool soundPool;

	// 声音集合
	private static Map<Integer, Integer> soundMap;
	private SearchInfo info;
	// 系统音量
	private static float volume = 0f;

	int tag = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swing);
		ViewUtils.inject(this);
		setTitle("摇一摇");
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		info = new SearchInfo();
		initSounds();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (sensorManager != null) {// 注册监听器
			// 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
			sensorManager.registerListener(sensorEventListener,
					sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_NORMAL);
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (sensorManager != null) {// 取消监听器
			sensorManager.unregisterListener(sensorEventListener);
		}
	}

	// 重力感应监听
	private SensorEventListener sensorEventListener = new SensorEventListener() {

		public void onSensorChanged(SensorEvent event) {
			if (tag == 2) {
				// 传感器信息改变时执行该方法
				float[] values = event.values;
				float x = values[0]; // x轴方向的重力加速度，向右为正
				float y = values[1]; // y轴方向的重力加速度，向前为正
				float z = values[2]; // z轴方向的重力加速度，向上为正
				int medumValue = 18;
				if (Math.abs(x) > medumValue || Math.abs(y) > medumValue
						|| Math.abs(z) > medumValue) {
					tag = 1;
					playSound();
					vibrator.vibrate(500);
					Message msg = new Message();
					msg.what = SENSOR_SHAKE;
					Log.e("", "3333333333333");
					handler.sendMessage(msg);
				}
			}

		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};
	/**
	 * 动作执行
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SENSOR_SHAKE:
				tvSensor.setText("正在搜索商品。。。。");
				sensor();
				break;
			}
		}
	};

	public void sensor() {
		WebServiceUtils.callWebService(Constant.S_SharkItOff, null,
				new WebServiceCallBack() {

					public void callBack(Object result) {
						Log.i("info", result.toString());
						if (result != null) {
							dialog = null;
							Bean bean = JSON.parseObject(result.toString(),
									Bean.class);
							if (bean.getState() == 1) {
								List<SearchInfo> list = JSON.parseArray(
										bean.getData(), SearchInfo.class);
								if (list.size() > 0) {
									info = list.get(0);
									dialog(info);
								} else {
									tvSensor.setText("暂无合适商品,重新试试..");
									tag = 2;
								}
							}
						}
					}
				});
	}

	public void dialog(SearchInfo info) {
		if (dialog == null) {
			Log.e("", "111111111111111111-=================");
			dialog = new Dialog(mContext, R.style.dialog);
			dialog.setContentView(R.layout.dialog_sensor);
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(true);
			TextView goodsName = (TextView) dialog.findViewById(R.id.goodsName);
			TextView tvGoodsPrice = (TextView) dialog
					.findViewById(R.id.tvGoodsPrice);
			ImageView imageUrl = (ImageView) dialog.findViewById(R.id.imageUrl);
			Button GoodsBtn = (Button) dialog.findViewById(R.id.GoodsBtn);
			ImageView close_dialog = (ImageView) dialog
					.findViewById(R.id.close_dialog);
			goodsName.setText(info.getGoods_name());
			tvGoodsPrice.setText("￥" + info.getShop_price());
			application.bitmapUtils.display(imageUrl,
					Constant.IMAGE_URL + info.getGoods_thumb());

			GoodsBtn.setOnClickListener(this);
			close_dialog.setOnClickListener(this);

			WindowManager m = ((Activity) mContext).getWindowManager();
			Display d = m.getDefaultDisplay();
			WindowManager.LayoutParams params = dialog.getWindow()
					.getAttributes();
			params.width = (int) (d.getWidth() * 0.99);
			params.height = (int) (d.getHeight() * 0.7);
		} else {
			dialog.dismiss();
			tag = 2;
		}
		dialog.show();
		tag = 1;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.close_dialog:
			tag = 2;
			dialog.dismiss();
			tvSensor.setText("摇一摇，摇出你想要的");
			break;
		case R.id.GoodsBtn:
			tag = 2;
			dialog.dismiss();
			Intent intent = new Intent(mContext, ProductDetailActivity.class);
			intent.putExtra("info", info);
			intent.putExtra("price", "￥" + info.getShop_price());
			startActivity(intent);
			tvSensor.setText("摇一摇，摇出你想要的");
			break;
		}
	}

	private void initSounds() {
		soundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 100);
		soundMap = new HashMap<Integer, Integer>();
		AudioManager audioManager = (AudioManager) this
				.getSystemService(AUDIO_SERVICE);
		// 获取音量
		float streamVolumeCurrent = audioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volume = streamVolumeCurrent / streamVolumeMax;
		soundMap.put(1, soundPool.load(this, R.raw.shake, 100));
	}

	public static void playSound() {
		try {
			if (soundPool != null) {
				soundPool.play(soundMap.get(1), volume, volume, 100, 0, 1f);
			}
		} catch (Exception e) {
		}
	}
}