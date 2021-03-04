package com.w2solo.android.ui.user.topusers

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.w2solo.android.R
import com.w2solo.android.data.entity.User
import com.w2solo.android.ui.base.IScrollToTop
import com.w2solo.android.ui.base.fragment.BaseFragment
import com.w2solo.android.utils.ViewHelper

class TopUsersListFrag : BaseFragment(), TopUsersListContract.View, IScrollToTop {

    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var rv: RecyclerView
    private val dataList = arrayListOf<User>()
    private val adapter = UserListAdapter(dataList)
    private val presenter = TopUserListPresenterImpl(this)

    override fun getLayout() = R.layout.top_user_list_fragment

    override fun initViews() {
        rv = fview(R.id.recyclerView)!!
        rv.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
        rv.adapter = adapter
        rv.addItemDecoration(ViewHelper.buildRecyclerViewDivider(context!!))

        refreshLayout = fview(R.id.swipe_refresh_layout)!!
        refreshLayout.setOnRefreshListener {
            presenter.loadList(true)
        }
        addLifecycleObserver(presenter)
        refreshLayout.isRefreshing = true
        presenter.loadList(true)
    }

    override fun onGetList(newList: List<User>?, isRefresh: Boolean) {
        if (newList == null) {
            refreshLayout.isRefreshing = false
            return
        }
        if (isRefresh) {
            dataList.clear()
            dataList.addAll(newList)

            refreshLayout.isRefreshing = false
            adapter.notifyDataSetChanged()
        } else {
            //loadMore, not use for now
            val oldCount = dataList.size
            dataList.addAll(newList)
            adapter.notifyItemRangeInserted(oldCount, newList.size)
        }
    }

    override fun scrollToTop() {
        rv.smoothScrollToPosition(0)
    }
}