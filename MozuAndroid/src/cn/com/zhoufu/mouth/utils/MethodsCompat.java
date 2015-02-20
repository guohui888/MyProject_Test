/**   
 * @Title: MethodsCompat.java 
 * @Package cn.com.zhoufu.mouth.utils 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-20 上午9:28:45
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.utils;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.Paint;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;

@SuppressLint("NewApi") 
public class MethodsCompat {

	public static void overridePendingTransition(Activity activity, int enter_anim, int exit_anim) {
		activity.overridePendingTransition(enter_anim, exit_anim);
	}

	public static Bitmap getThumbnail(ContentResolver cr, long origId, int kind, Options options) {
		return MediaStore.Images.Thumbnails.getThumbnail(cr, origId, kind, options);
	}

	public static File getExternalCacheDir(Context context) {
		return context.getExternalCacheDir();
	}

	public static void recreate(Activity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			activity.recreate();
		}
	}

	public static void setLayerType(View view, int layerType, Paint paint) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			view.setLayerType(layerType, paint);
		}
	}

	public static void setUiOptions(Window window, int uiOptions) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			window.setUiOptions(uiOptions);
		}
	}

}
