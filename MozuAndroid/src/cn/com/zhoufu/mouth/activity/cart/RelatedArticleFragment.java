package cn.com.zhoufu.mouth.activity.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import cn.com.zhoufu.mozu.R;
import cn.com.zhoufu.mouth.activity.BaseFragment;
import cn.com.zhoufu.mouth.constants.Constant;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class RelatedArticleFragment extends BaseFragment {

	@ViewInject(R.id.product_details_webView)
	private WebView mWebview;

	String goodsId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_related_article, container, false);
		ViewUtils.inject(this, view);
		Bundle bundle = getArguments();
		goodsId = bundle.getString("id");
		initView();
		return view;
	}

	public void initView() {
		mWebview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		mWebview.loadDataWithBaseURL(null, "加载中。。", "text/html", "utf-8", null);
		mWebview.setHorizontalScrollBarEnabled(false);
		mWebview.setHorizontalScrollbarOverlay(false);
		mWebview.getSettings().setBuiltInZoomControls(false);
		mWebview.getSettings().setSupportZoom(false);

		mWebview.setWebViewClient(new WebViewClient() {

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) { // Handle
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}

		});
		mWebview.loadUrl(Constant.PRODUCT_DETAIL_THREE + goodsId);
	}
}
