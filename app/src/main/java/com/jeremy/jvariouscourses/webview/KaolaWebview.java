//package com.jeremy.jvariouscourses.webview;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.annotation.RequiresApi;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.webkit.DownloadListener;
//import android.webkit.WebResourceRequest;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//
///**
// * author: Jeremy
// * date: 2018/7/17
// * desc: 为了解决白屏问题，考拉目前的解决思路和上面的回退栈问题思路有些类似，通过监听touch事件分发以及onPageFinished事件来判断是否产生白屏，代码如下：
// */
//public class KaolaWebview extends BaseWebView implements DownloadListener, Lifeful, OnActivityResultListener {
//
//    private boolean mIsBlankPageRedirect;  //是否因重定向导致的空白页面。
//
//    public KaolaWebview(Context context) {
//        super(context);
//        init();
//    }
//
//    public KaolaWebview(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    public KaolaWebview(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init();
//    }
//
//    protected void back() {
//        if (mBackStep < 1) {
//            mJsApi.trigger2("kaolaGoback");
//        } else {
//            realBack();
//        }
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_UP) {
//            mIsBlankPageRedirect = true;
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//
//    private WebViewClient mWebViewClient = new WebViewClient() {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            url = WebViewUtils.removeBlank(url);
//            //允许启动第三方应用客户端
//            if (WebViewUtils.canHandleUrl(url)) {
//                boolean handleByCaller = false;
//                // 如果不是用户触发的操作，就没有必要交给上层处理了，直接走url拦截规则。
//                if (null != mIWebViewClient && isTouchByUser()) {
//                    handleByCaller = mIWebViewClient.shouldOverrideUrlLoading(view, url);
//                }
//                if (!handleByCaller) {
//                    handleByCaller = handleOverrideUrl(url);
//                }
//                return handleByCaller || super.shouldOverrideUrlLoading(view, url);
//            } else {
//                try {
//                    notifyBeforeLoadUrl(url);
//                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
//                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                    mContext.startActivity(intent);
//                    if (!mIsBlankPageRedirect) {
//                        // 如果遇到白屏问题，手动后退
//                        back();
//                    }
//                } catch (Exception e) {
//                    ExceptionUtils.printExceptionTrace(e);
//                }
//                return true;
//            }
//        }
//
//        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            return shouldOverrideUrlLoading(view, request.getUrl().toString());
//        }
//
//        private boolean handleOverrideUrl(final String url) {
//            RouterResult result =  WebActivityRouter.startFromWeb(
//                    new IntentBuilder(mContext, url).setRouterActivityResult(new RouterActivityResult() {
//                        @Override
//                        public void onActivityFound() {
//                            if (!mIsBlankPageRedirect) {
//                                // 路由已经拦截到跳转到native页面，但此时可能发生了
//                                // 301/302跳转，那么执行后退动作，防止白屏。
//                                back();
//                            }
//                        }
//
//                        @Override
//                        public void onActivityNotFound() {
//                            if (mIWebViewClient != null) {
//                                mIWebViewClient.onActivityNotFound();
//                            }
//                        }
//                    }));
//            return result.isSuccess();
//        }
//    };
//
//    @Override
//    public void onPageFinished(WebView view, String url) {
//        mIsBlankPageRedirect = true;
//        if (null != mIWebViewClient) {
//            mIWebViewClient.onPageReallyFinish(view, url);
//        }
//        super.onPageFinished(view, url);
//    }
//}