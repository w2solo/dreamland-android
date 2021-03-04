package com.w2solo.android.ui.topic.list

import android.text.TextUtils
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.w2solo.android.R
import com.w2solo.android.data.entity.Topic
import com.w2solo.android.ui.base.IScrollToTop
import com.w2solo.android.ui.base.fragment.BaseFragment
import com.w2solo.android.utils.ViewHelper

//TODO 考虑改进为 多种 filter 机制，避免写多个子类去实现 topic 的加载，应该由此页面完成即可
/**
 * filters
 * by-node
 * by-user
 * by-type
 */
abstract class AbsTopicListFrag : BaseFragment(), TopicListContract.View, IScrollToTop {

    private val dataList = arrayListOf<Topic>()

    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var rv: RecyclerView
    private val adapter = TopicListAdapter(dataList)
    private val presenter = TopicListPresenterImpl(this)

    private val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    abstract fun getCurrentNodeId(): Long

    open fun getUserLogin(): String? {
        return null
    }

    open fun canLoadMore() = true

    override fun initViews() {
        fview<Toolbar>(R.id.home_topic_list)?.setOnClickListener {
            scrollToTop()
        }
        rv = fview(R.id.recyclerView)!!
        rv.layoutManager = layoutManager
        rv.adapter = adapter
        rv.addItemDecoration(ViewHelper.buildRecyclerViewDivider(context!!))

        refreshLayout = fview(R.id.swipe_refresh_layout)!!
        refreshLayout.setOnRefreshListener {
            loadTopicList(true)
        }
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!canLoadMore()) {
                    return
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && layoutManager.findLastVisibleItemPosition() == dataList.size - 1
                ) {
                    loadTopicList(false)
                }
            }
        })
        addLifecycleObserver(presenter)
        refreshLayout.isRefreshing = true
        loadTopicList(true)
    }

    private fun loadTopicList(isRefresh: Boolean) {
        val userLogin = getUserLogin()
        if (!TextUtils.isEmpty(userLogin)) {
            presenter.loadListByUser(isRefresh, userLogin!!)
        } else {
            presenter.loadListByNode(isRefresh, getCurrentNodeId())
        }
    }

    override fun onGetList(newList: List<Topic>?, isRefresh: Boolean) {
        if (isRefresh) {
            refreshLayout.isRefreshing = false
        }
        if (newList == null) {
            return
        }
        if (isRefresh) {
            dataList.clear()
            dataList.addAll(newList)
            adapter.notifyDataSetChanged()
        } else {
            val pos = dataList.size
            dataList.addAll(newList)
            adapter.notifyItemRangeInserted(pos, newList.size)
        }
    }

    override fun scrollToTop() {
        val first = layoutManager.findFirstVisibleItemPosition()
        if (first > 40) {
            rv.scrollToPosition(0)
        } else {
            rv.smoothScrollToPosition(0)
        }
    }
}