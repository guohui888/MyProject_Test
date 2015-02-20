/**   
 * @Title: DBUtils.java 
 * @Package cn.com.zhoufu.mouth.db 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-13 下午3:45:22
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cn.com.zhoufu.mouth.model.HistoricalInfo;

public class DBUtils {

	private SQLiteDatabase sdb;

	public DBUtils(SQLiteDatabase sdb) {
		this.sdb = sdb;
	}

	// 添加历史记录
	public boolean addHistorical(HistoricalInfo historicalInfo) {
		Cursor cursor = null;
		try {
			final String sql = "select 1 from " + MouthSQLiteOpenHelper.TABLE_HISTORICAL + " where name=?";
			cursor = sdb.rawQuery(sql, new String[] { historicalInfo.getName() });
			if (cursor.getCount() > 0) {
				return true;
			}
			ContentValues values = new ContentValues();
			values.put("name", historicalInfo.getName());
			if (sdb.insert(MouthSQLiteOpenHelper.TABLE_HISTORICAL, null, values) > 0)
				return true;
		} catch (Exception e) {
			Log.i("info", "添加历史记录失败");
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return false;
	}

	// 查询历史记录
	public List<HistoricalInfo> queryHistorical() {
		Cursor cursor = null;
		try {
			final StringBuilder sql = new StringBuilder("select name from " + MouthSQLiteOpenHelper.TABLE_HISTORICAL);
			cursor = sdb.rawQuery(sql.toString(), null);
			List<HistoricalInfo> mList = new ArrayList<HistoricalInfo>();
			while (cursor.moveToNext()) {
				HistoricalInfo info = new HistoricalInfo();
				info.setName(cursor.getString(0));
				mList.add(info);
			}
			return mList;
		} catch (Exception e) {
			Log.i("info", "查询历史记录失败");
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}

	// 清空历史记录
	public void deleteHistorical() {
		try {
			sdb.delete(MouthSQLiteOpenHelper.TABLE_HISTORICAL, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
