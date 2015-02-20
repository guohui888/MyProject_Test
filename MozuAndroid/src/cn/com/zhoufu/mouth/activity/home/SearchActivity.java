/**   
 * @Title: SearchActivity.java 
 * @Package cn.com.zhoufu.mouth.activity.home 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-24 下午5:29:53
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.activity.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseActivity;
import cn.com.zhoufu.mouth.activity.search.SearchResultActivity;
import cn.com.zhoufu.mouth.adapter.HistoricalAdapter;
import cn.com.zhoufu.mouth.adapter.KeyWordAdapter;
import cn.com.zhoufu.mouth.constants.Constant;
import cn.com.zhoufu.mouth.model.Bean;
import cn.com.zhoufu.mouth.model.HistoricalInfo;
import cn.com.zhoufu.mouth.model.KeywordInfo;
import cn.com.zhoufu.mouth.utils.ListViewUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils;
import cn.com.zhoufu.mouth.utils.WebServiceUtils.WebServiceCallBack;
import cn.com.zhoufu.mouth.view.MyListView;
import cn.com.zhoufu.mouth.view.pullview.AbOnListViewListener;
import cn.com.zhoufu.mouth.view.pullview.AbPullListView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

public class SearchActivity extends BaseActivity implements TextWatcher, AbOnListViewListener {

	@ViewInject(R.id.searchContent)
	private EditText searchContent;

	@ViewInject(R.id.searchBtn)
	private Button searchBtn;

	@ViewInject(R.id.scrollView)
	private ScrollView scrollView;

	@ViewInject(R.id.listview)
	private MyListView mListView;

	@ViewInject(R.id.clearHistorical)
	private Button clearHistorical;

	@ViewInject(R.id.keywordListView)
	private AbPullListView keywordListView;

	private HistoricalAdapter mAdapter;

	private KeyWordAdapter mAdapter2;

	private List<KeywordInfo> list;

	int pageIndex = 1, pageSize = 20;

	String keyword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout(this, R.layout.activity_search);
		initView();
	}

	public void initView() {
		for (int i = 0; i < dbUtils.queryHistorical().size(); i++) {
			Log.i("info", dbUtils.queryHistorical().get(i).getName());
		}
		if (dbUtils.queryHistorical().size() == 0) {
			scrollView.setVisibility(View.GONE);
		} else {
			mAdapter = new HistoricalAdapter(mContext);
			mAdapter.setList(dbUtils.queryHistorical());
			mListView.setAdapter(mAdapter);
			ListViewUtils.setListViewHeightBasedOnChildren(mListView);
		}
		mAdapter2 = new KeyWordAdapter(mContext);
		list = new ArrayList<KeywordInfo>();
		mAdapter2.setList(list);
		keywordListView.setAdapter(mAdapter2);

		keywordListView.setPullLoadEnable(true);
		keywordListView.setPullRefreshEnable(true);
		keywordListView.setAbOnListViewListener(this);
		searchContent.addTextChangedListener(this);
	}

	@OnClick(R.id.searchBtn)
	public void ClickSearch(View v) {
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

	@OnClick(R.id.clearHistorical)
	public void clearClick(View v) {
		dbUtils.deleteHistorical();
		mAdapter.removeAll();
		initView();
	}

	@OnItemClick(R.id.listview)
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(mContext, SearchResultActivity.class);
		intent.putExtra("keyword", dbUtils.queryHistorical().get(arg2).getName());
		Log.i("info", dbUtils.queryHistorical().get(arg2).getName());
		startActivity(intent);
	}

	@OnItemClick(R.id.keywordListView)
	public void onItemClicks(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		HistoricalInfo info = new HistoricalInfo();
		info.setName(list.get(arg2).getKeyword());
		dbUtils.addHistorical(info);
		Intent intent = new Intent(mContext, SearchResultActivity.class);
		intent.putExtra("keyword", list.get((int)arg3).getKeyword());
		Log.i("info", list.get(arg2).getKeyword());
		startActivity(intent);
	}

	public void onRefresh() {
		pageIndex = 1;
		mAdapter2.removeAll();
		keyword(keyword, pageIndex, pageSize);
		keywordListView.stopRefresh();
	}

	public void onLoadMore() {
		pageIndex++;
		keyword(keyword, pageIndex, pageSize);
		keywordListView.stopLoadMore();
	}

	public void afterTextChanged(Editable s) {

	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		keyword = s.toString();
		if (s.toString() == null || "".equals(s.toString())) {
			Log.i("info", "空");
			keywordListView.setVisibility(View.GONE);
			mListView.setVisibility(View.VISIBLE);
			clearHistorical.setVisibility(View.VISIBLE);
		} else {
			Log.i("info", "NO空");
			keywordListView.setVisibility(View.VISIBLE);
			mListView.setVisibility(View.GONE);
			clearHistorical.setVisibility(View.GONE);
		}
		mAdapter2.removeAll();
		keyword(keyword, pageIndex, pageSize);
	}

	public void keyword(String content, int pageIndex, int pageSize) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", content);
		map.put("page", pageIndex);
		map.put("count", pageSize);
		WebServiceUtils.callWebService(Constant.S_Keywords, map, new WebServiceCallBack() {

			public void callBack(Object result) {
				Log.i("info", result.toString());
				if (result != null) {
					Bean bean = JSON.parseObject(result.toString(), Bean.class);
					List<KeywordInfo> list = JSON.parseArray(bean.getData(), KeywordInfo.class);
					mAdapter2.addAll(list);
				}
			}
		});
	}
}
