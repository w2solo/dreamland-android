package com.w2solo.android.ui.base.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.w2solo.android.R

abstract class BaseToolbarActivity : BaseActivity(), Toolbar.OnMenuItemClickListener {

    private var appBarLayout: AppBarLayout? = null

    protected var mToolbar: Toolbar? = null

    private var isFirstResume = false

    protected open fun initToolbar() {
        appBarLayout = findViewById(R.id.activity_appbar_layout_id)
        mToolbar = findViewById(R.id.activity_toolbar_id)
        setupToolbar()
    }

    protected open fun setupToolbar() {
        if (mToolbar == null) {
            return
        }
        createMenu()
        mToolbar!!.setOnMenuItemClickListener(this)
        mToolbar!!.setNavigationOnClickListener { v: View? -> onBackPressed() }
    }

    private fun createMenu() {
        val defaultMenu = getMenuResId()
        if (defaultMenu > 0) {
            inflateMenu(defaultMenu)
        } else {
            clearMenus()
        }
    }


    protected open fun getMenuResId(): Int {
        return -1
    }

    protected open fun inflateMenu(@MenuRes menuResId: Int) {
        if (mToolbar != null) {
            mToolbar!!.menu.clear()
            mToolbar!!.inflateMenu(menuResId)
        }
    }

    protected open fun clearMenus() {
        if (mToolbar != null) {
            mToolbar!!.menu.clear()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isFirstResume) {
            isFirstResume = true
            onFirstResume()
        } else {
            onNotFirstResume()
        }
    }

    protected open fun onFirstResume() {}

    protected open fun onNotFirstResume() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeSetContentView()
        setContentView(getLayout())
        initToolbar()
    }

    open fun beforeSetContentView() {}

    abstract fun getLayout(): Int

    override fun setTitle(@StringRes titleId: Int) {
        if (mToolbar != null) {
            mToolbar!!.setTitle(titleId)
        }
    }

    override fun setTitle(title: CharSequence?) {
        if (mToolbar != null) {
            mToolbar!!.title = title
        } else {
            super.setTitle(title)
        }
    }

    /**
     * 点击Toolbar按钮
     */
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return false
    }

    /**
     * 获得菜单的View
     */
    protected open fun getMenuItemView(menuId: Int): View? {
        return if (mToolbar != null && mToolbar!!.menu != null) {
            mToolbar!!.findViewById(menuId)
        } else null
    }

    protected open fun getMenuItem(menuId: Int): MenuItem? {
        return if (mToolbar != null && mToolbar!!.menu != null) {
            mToolbar!!.menu.findItem(menuId)
        } else null
    }

    override fun invalidateOptionsMenu() {
        super.invalidateOptionsMenu()
        createMenu()
    }

    public fun getAppbarLayout() = appBarLayout
}