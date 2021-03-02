package com.w2solo.android.ui.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import com.w2solo.android.mvp.BasePresenter
import com.w2solo.android.mvp.IView
import com.w2solo.android.ui.base.broadcast.BaseBroadcastReceiver
import com.w2solo.android.ui.base.broadcast.BroadcastHelper
import com.w2solo.android.ui.base.lifecycle.LifeCycleFragment
import java.util.*

abstract class BaseFragment : LifeCycleFragment() {

    protected var rootView: View? = null
    private var isFirstResume = false

    /**
     * 广播管理
     */
    private var broadcastReceivers: ArrayList<BaseBroadcastReceiver>? = null

    private fun clearBroadcastReceiver() {
        if (broadcastReceivers == null) {
            return
        }
        for (receiver in broadcastReceivers!!) {
            BroadcastHelper.unregisterBroadcast(receiver)
        }
        broadcastReceivers!!.clear()
        broadcastReceivers = null
    }

    override fun onResume() {
        super.onResume()
        if (!isFirstResume) {
            isFirstResume = true
            onFirstResume()
        }
    }

    /**
     * 子Fragment可以拦截Activity的 onBackPressed() 事件，需要在Activity中手动调用来判断
     *
     * @return 子Fragment已经消费了返回事件，返回true，否则返回false
     */
    fun onBackPressed(): Boolean {
        return false
    }

    fun onFirstResume() {}

    /**
     * 注册广播监听事件，不需要在Fragment退出时取消注册 [BaseFragment.onDestroy]
     */
    protected fun registerBroadcastReceiver(
        receiver: BaseBroadcastReceiver?,
        vararg actions: String
    ): Boolean {
        if (receiver == null) {
            return false
        }
        if (broadcastReceivers == null) {
            broadcastReceivers = ArrayList()
        }
        if (!broadcastReceivers!!.contains(receiver)) {
            broadcastReceivers!!.add(receiver)
        }
        BroadcastHelper.registerBroadcast(receiver, actions)
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(getLayout(), null)
        initViews()
        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        clearBroadcastReceiver()
    }

    protected fun <T : View> fview(
        @IdRes id: Int,
        onClickListener: View.OnClickListener? = null
    ): T? {
        if (rootView != null) {
            val view: T = rootView!!.findViewById(id)
            if (onClickListener != null) {
                view.setOnClickListener(onClickListener)
            }
            return view
        }
        return null
    }

    abstract fun getLayout(): Int

    abstract fun initViews()

    protected fun <V : IView> attachPresenter(presenter: BasePresenter<V>) {
        addLifecycleObserver(presenter)
    }
}