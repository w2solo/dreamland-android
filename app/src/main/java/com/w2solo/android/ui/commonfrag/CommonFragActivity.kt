package com.w2solo.android.ui.commonfrag

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.w2solo.android.R
import com.w2solo.android.ui.base.IScrollToTop
import com.w2solo.android.ui.base.activity.BaseToolbarActivity
import com.w2solo.android.ui.base.fragment.BaseFragment

class CommonFragActivity : BaseToolbarActivity() {


    companion object {
        private val KEY_FRAG_KEY = "frag_key"
        private val KEY_FRAG_ARGS = "frag_args"

        fun start(context: Context, @StringRes titleResId: Int) {
            start(context, titleResId, null)
        }

        fun start(
            context: Context,
            @StringRes titleResId: Int,
            args: Bundle?
        ) {
            val intent =
                Intent(context, CommonFragActivity::class.java)
            intent.putExtra(KEY_FRAG_KEY, titleResId)
            if (args != null) {
                intent.putExtra(KEY_FRAG_ARGS, args)
            }
            context.startActivity(intent)
        }
    }

    protected var curPage: Fragment? = null

    override fun getLayout() = R.layout.act_common_frag_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val titleResId = intent.getIntExtra(KEY_FRAG_KEY, -1)
        val args = intent.getBundleExtra(KEY_FRAG_ARGS)
        initInternal(titleResId, args)
        if (mToolbar != null) {
            mToolbar!!.setOnClickListener {
                if (curPage != null) {
                    if (curPage is IScrollToTop) {
                        (curPage as IScrollToTop).scrollToTop()
                    }
                }
            }
        }
    }

    private fun initInternal(@StringRes titleResId: Int, args: Bundle?) {
        if (titleResId == -1) {
            return
        }
        var title: String? = null
        try {
            title = getString(titleResId)
        } catch (thr: Throwable) {
            thr.printStackTrace()
        }
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, R.string.error_invalid_params, Toast.LENGTH_SHORT).show();
            finish()
            return
        }
        val curPage =
            CommonFragPageConfigs.newFragment(titleResId, args)
        if (curPage == null) {
            Toast.makeText(this, R.string.error_invalid_params, Toast.LENGTH_SHORT).show();
            finish()
            return
        }
        setTitle(title)
        showFragment(curPage)
    }

    protected fun showFragment(fragment: Fragment?) {
        curPage = fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, curPage!!)
            .commitAllowingStateLoss()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (curPage != null) {
            curPage!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (curPage != null) {
            curPage!!.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        if (curPage != null) {
            if (curPage is BaseFragment) {
                if ((curPage as BaseFragment).onBackPressed()) {
                    return
                }
            }
        }
        super.onBackPressed()
    }
}