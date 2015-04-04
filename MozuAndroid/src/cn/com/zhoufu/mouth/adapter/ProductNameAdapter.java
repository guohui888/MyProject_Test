/**   
 * @Title: ProductAdapter.java 
 * @Package cn.com.zhoufu.mouth.adapter 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-17 下午3:38:11
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.ZFApplication;
import cn.com.zhoufu.mouth.model.SearchProductInfo;

public class ProductNameAdapter extends BaseAdapter {

	List<SearchProductInfo> list;

	Context context;

	ZFApplication application;

	public static String url;

	public ProductNameAdapter(Context context, List<SearchProductInfo> list) {
		this.list = list;
		this.context = context;
		this.application = (ZFApplication) context.getApplicationContext();
		 
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
        holder.groupItem.setText(list.get(position).getOne_name());  
          
        return convertView;  
    }  
  
    static class ViewHolder {  
        TextView groupItem;  
    } 

}
