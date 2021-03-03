package com.w2solo.android.ui.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.w2solo.android.R;
import com.w2solo.android.ui.base.activity.BaseToolbarActivity;
import com.w2solo.android.utils.AppLog;

/**
 * 显示网页信息
 * Project LaughDay4And
 * Package com.laughday.activity
 * Created by LikeCarmack on 12/6/14.
 */
public class WebViewActivity extends BaseToolbarActivity {

    private WebViewFragment webViewFragment;
    private String pageUrl, initTitle;

    public static void start(Context activity, String url, @Nullable String title) {
        if (TextUtils.isEmpty(url)) {
            AppLog.INSTANCE.e("Web", "url不能为空");
            return;
        }
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("param_web_url", url);
        if (!TextUtils.isEmpty(title)) {
            intent.putExtra("param_web_title", title);
        }

        activity.startActivity(intent);
        ((Activity) activity).overridePendingTransition(R.anim.activity_bottom_enter, R.anim.activity_anim_nothing);
    }

    @Override
    public int getLayout() {
        return R.layout.act_webview;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        getMToolbar().setOnClickListener(view -> {
            if (webViewFragment != null) {
                webViewFragment.scrollTo(0, 0);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null || TextUtils.isEmpty((pageUrl = intent.getStringExtra("param_web_url")))) {
            Toast.makeText(this, R.string.error_invalid_params, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        initTitle = intent.getStringExtra("param_web_title");

        webViewFragment = (WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.webview_fragment);
        webViewFragment.setOnWebListener(onWebListener);

        if (!TextUtils.isEmpty(initTitle)) {
            setTitle(initTitle);
        }
        webViewFragment.loadUrl(pageUrl);
    }

    private WebViewFragment.OnWebListener onWebListener = new WebViewFragment.OnWebListener() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
//            if (!TextUtils.isEmpty(initTitle)) {
//                return;
//            }
            if (!TextUtils.isEmpty(title)) {
                setTitle(title);
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (webViewFragment.canGoback()) {
            webViewFragment.goBack();
            inflateMenu(R.menu.menu_webview);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_close) {
            finish();
        }
        return super.onMenuItemClick(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_anim_nothing, R.anim.activity_bottom_exit);
    }
}
