//package cn.com.zhoufu.mouth.utils.api;
//import java.io.ByteArrayOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.weibo.sdk.android.Oauth2AccessToken;
//import com.weibo.sdk.android.WeiboAuthListener;
//import com.weibo.sdk.android.WeiboException;
//import com.weibo.sdk.android.net.RequestListener;
//import com.weibo.sdk.android.util.AccessTokenKeeper;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.widget.Toast;
//
//
///**
// * 新浪微博登录工具类
// * @author yw-tony
// *
// */
//public class SinaLoginUtil {
//    private static final String appkey = "你的appkey";
//    // http://www.sina.comhttp://www.fortrun.com
//    private static final String redirectUrl = "http://www.fortrun.com";
//    private static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
//            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
//            + "follow_app_official_microblog," + "invitation_write";
//    /** 登陆认证对应的listener */
//    private AuthListener mLoginListener = new AuthListener();
//    /** 登出操作对应的listener */
//    private LogOutRequestListener mLogoutListener = new LogOutRequestListener();
//    private Context context;
//    private AuthInfo authInfo = null;
//    public String token = ",";private SinaLoginUtil(){}
//    private static SinaLoginUtil instance = new SinaLoginUtil();
//    public static SinaLoginUtil getInstance(){
//        return instance;
//    }
//    public void initSina(Context context,LoginButton loginButton){
//        this.context = context;
//        // 创建授权认证信息
//        authInfo = new AuthInfo(context, "你的appkey",redirectUrl, SCOPE);
//        loginButton.setWeiboAuthInfo(authInfo, mLoginListener);
//    }
//    /**
//     * 登入按钮的监听器，接收授权结果。
//     */
//    private class AuthListener implements WeiboAuthListener {
//        @Override
//        public void onComplete(Bundle values) {
//            final Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(values);
//            if (accessToken != null && accessToken.isSessionValid()) {
//                /*String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
//                        new java.util.Date(accessToken.getExpiresTime()));*/
////                String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
////                mTokenView.setText(String.format(format, accessToken.getToken(), date));
//                AccessTokenKeeper.writeAccessToken(context, accessToken);
//            }
//        }
//       
//    /**
//     * 登出按钮的监听器，接收登出处理结果。（API 请求结果的监听器）
//     */
//    private class LogOutRequestListener implements RequestListener {
//        @Override
//        public void onComplete(String response) {
//            if (!TextUtils.isEmpty(response)) {
//                try {
//                    JSONObject obj = new JSONObject(response);
//                    String value = obj.getString("result");
//
//                    if ("true".equalsIgnoreCase(value)) {
//                        AccessTokenKeeper.clear(context);
////                        mTokenView.setText(R.string.weibosdk_demo_logout_success);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        
//        @Override
//        public void onComplete4binary(ByteArrayOutputStream responseOS) {
//            // Do nothing
//        }        
//        
//        @Override
//        public void onIOException(IOException e) {
////            mTokenView.setText(R.string.weibosdk_demo_logout_failed);
//        }
//
//        @Override
//        public void onError(WeiboException e) {
////            mTokenView.setText(R.string.weibosdk_demo_logout_failed);
//        }
//    }
//}