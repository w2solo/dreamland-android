package com.w2solo.android.ui.topic.list

import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.w2solo.android.R
import com.w2solo.android.data.entity.Topic
import com.w2solo.android.ui.base.IScrollToTop
import com.w2solo.android.ui.base.fragment.BaseFragment

abstract class AbsTopicListFrag : BaseFragment(), TopicListContract.View, IScrollToTop {

    private val dataList = arrayListOf<Topic>()

    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var rv: RecyclerView
    private val adapter = TopicListAdapter(dataList)
    private val presenter = TopicListPresenterImpl(this)

    private val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

     abstract fun getCurrentNodeId(): Long

    override fun initViews() {
        fview<Toolbar>(R.id.home_topic_list)?.setOnClickListener {
            scrollToTop()
        }
        rv = fview(R.id.recyclerView)!!
        rv.layoutManager = layoutManager
        rv.adapter = adapter

        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.common_divider)!!)
        rv.addItemDecoration(divider)

        refreshLayout = fview(R.id.swipe_refresh_layout)!!
        refreshLayout.setOnRefreshListener {
            presenter.loadList(true, getCurrentNodeId())
        }
        addLifecycleObserver(presenter)
        refreshLayout.isRefreshing = true
        presenter.loadList(true, getCurrentNodeId())

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && layoutManager.findLastVisibleItemPosition() == dataList.size - 1
                ) {
                    presenter.loadList(false, getCurrentNodeId())
                }
            }
        })
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