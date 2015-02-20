/**   
 * @Title: MouthSQLiteOpenHelper.java 
 * @Package cn.com.zhoufu.mouth.db 
 * @Description: TODO(数据库) 
 * @author 王小杰   
 * @date 2014-2-13 下午3:39:18
 * @version V1.0   
 */
package cn.com.zhoufu.mouth.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import cn.com.zhoufu.mouth.constants.Constant;

public class MouthSQLiteOpenHelper extends SQLiteOpenHelper {

	// 历史记录表
	public static final String TABLE_HISTORICAL = "historical";

	final String CREATE_HISTORICAL = "CREATE TABLE IF NOT EXISTS " + TABLE_HISTORICAL + " (id INTEGER PRIMARY KEY AUTOINCREMENT,name)";

	public MouthSQLiteOpenHelper(Context context) {
		super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_HISTORICAL);
	}

	public void onDrop(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORICAL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > oldVersion) {
			onDrop(db);
			onCreate(db);
		}
	}

}