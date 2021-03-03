package com.w2solo.android.ui.webview

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.ProgressBar
import android.widget.Toast
import com.w2solo.android.R
import com.w2solo.android.ui.base.fragment.BaseFragment
import com.w2solo.android.utils.AppLog
import com.w2solo.android.utils.FunctionUtils

/**
 * Created by tangyuchun on 1/8/16.
 */
class WebViewFragment : BaseFragment() {

    var webView: WebView? = null
    var progressBar: ProgressBar? = null

    override fun getLayout() = R.layout.frag_webview

    override fun initViews() {
        webView = fview(R.id.webview)
        progressBar = fview(R.id.webview_progress)
        initWebView()
    }

    private fun initWebView() {
        val settings = webView!!.settings
        if (settings != null) {
            settings.javaScriptEnabled = true
            //兼容 https页面的http地址图片无法下载的问题
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
            //            settings.setUseWideViewPort(true);//加上此句会导致 web页面无法自适应 移动端
//            settings.setSupportMultipleWindows(true);
            settings.loadWithOverviewMode = true
            settings.blockNetworkImage = false
            settings.domStorageEnabled = true //不加载此句，部分网页不展示图片 比如一点资讯
            //设置UA
            val userAgent = settings.userAgentString + getWebUserAgent()
            settings.userAgentString = userAgent
            if (AppLog.isEnabled()) {
                AppLog.d(TAG, "UserAgent=$userAgent")
            }
        }
        WebView.setWebContentsDebuggingEnabled(AppLog.isEnabled())
        webView!!.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(
                view: WebView,
                newProgress: Int
            ) {
                super.onProgressChanged(view, newProgress)
                progressBar!!.progress = newProgress
                progressBar!!.visibility = View.VISIBLE
                if (newProgress >= 95) {
                    progressBar!!.visibility = View.INVISIBLE
                }
                if (onWebListener != null) {
                    onWebListener!!.onProgressChanged(view, newProgress)
                }
            }

            override fun onReceivedTitle(
                view: WebView,
                title: String
            ) {
                super.onReceivedTitle(view, title)
                AppLog.d(TAG, "tang----onReceivedTitle $title")
                if (onWebListener != null) {
                    onWebListener!!.onReceivedTitle(view, title)
                }
            }
        })
        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, urlStr: String): Boolean {
                AppLog.d(TAG, "tang---shouldOverrideUrlLoading $urlStr")
                //如果可以处理，则返回false
                return loadUrl(urlStr)
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
                AppLog.d(TAG, "tang-----onPageStarted $url")
            }

            override fun onPageFinished(view: WebView, url: String) {
                AppLog.d(
                    "WebViewFragment",
                    "tang----onPageFinished 111  " + url + "  " + webView!!.url
                )
                super.onPageFinished(view, url)
                AppLog.d(
                    "WebViewFragment",
                    "tang----onPageFinished 222  " + url + "  " + webView!!.url
                )
                //                addImageClickListener();
            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    AppLog.d(
                        "WebViewFragment",
                        "tang----onReceivedError " + error.errorCode + "  " + error.description
                    )
                }
                if (onWebListener != null) {
                    onWebListener!!.onReceivedError(view, request, error)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //部分翻墙的梯子，直接不显示错误信息
                    //                    if (!needTizi(request.getUrl().toString())) {
                    //                        onPageError(view.getUrl());
                    //                    }
                } else {
                    //                    onPageError(view.getUrl());
                }
            }
        }
        webView!!.setDownloadListener { url: String, _: String?, _: String?, _: String?, _: Long ->
            AppLog.d(TAG, "onDownloadStart 下载文件 $url")
            if (canDownload()) {
                try {
                    val uri = Uri.parse(url)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                } catch (thr: Throwable) {
                    thr.printStackTrace()
                }
            } else {
                Toast.makeText(context, "不能下载文件", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getWebUserAgent() = ";w2soloand=w2solo"

    private fun canDownload() = true

    private fun validateUrl(urlStr: String): Boolean {
        return if (TextUtils.isEmpty(urlStr)) {
            false
        } else urlStr.startsWith("http://")
                || urlStr.startsWith("https://")
                || urlStr.startsWith("ftp://")
    }

    private fun validateSchema(urlString: String): Boolean {
        return urlString.startsWith("sinaweibo://") ||
                urlString.startsWith("market://")
    }

    /**
     * 是否进行拦截处理 如果为true，则使用 Jsoup 拦截内容
     *
     * @param urlStr
     */
    fun loadUrl(urlStr: String): Boolean {
        var urlStr = urlStr
        AppLog.d(TAG, "tang----loadUrl $urlStr")
        if (webView == null || TextUtils.isEmpty(urlStr)) {
            return false
        }
        //固定Scheme，则打开微博
        if (validateSchema(urlStr)) {
            if (!FunctionUtils.openScheme(context, urlStr)) {
            }
            //means stop webview to process this urlStr
            return true
        }
        if (!validateUrl(urlStr)) {
            AppLog.e(TAG, "tang----loadUrl（） 非法Url 不加载")
            return false
        }
        AppLog.d(TAG, "tang------不拦截处理 $urlStr")
        webView!!.loadUrl(urlStr)
        return true
    }

    val url: String get() = if (webView != null) webView!!.url else ""

    fun canGoback(): Boolean {
        return webView != null && webView!!.canGoBack() && !TextUtils.equals(
            webView!!.url,
            "about:blank"
        )
    }

    fun goBack(): Boolean {
        if (webView != null) {
            webView!!.goBack()
            return true
        }
        return false
    }

    fun scrollTo(x: Int, y: Int) {
        webView!!.scrollTo(x, y)
    }

    /**
     * 正确的WebView释放内存
     */
    private fun release() {
        if (webView != null) {
            webView!!.removeAllViews()
            if (webView!!.parent != null) {
                val group = webView!!.parent as ViewGroup
                group.removeView(webView)
            }
            webView!!.destroy()
            webView = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        release()
    }

    private var onWebListener: OnWebListener? = null
    fun setOnWebListener(onWebListener: OnWebListener?) {
        this.onWebListener = onWebListener
    }

    open class OnWebListener {
        fun onProgressChanged(view: WebView?, newProgress: Int) {}
        open fun onReceivedTitle(
            view: WebView?,
            title: String?
        ) {
        }

        fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
        }
    }

    companion object {
        private val TAG = WebViewFragment::class.java.simpleName
    }
}
