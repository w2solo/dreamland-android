package com.w2solo.android.ui.user.baselist

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.w2solo.android.R
import com.w2solo.android.data.entity.User
import com.w2solo.android.ui.base.IScrollToTop
import com.w2solo.android.ui.base.fragment.BaseFragment
import com.w2solo.android.ui.user.UserListAdapter
import com.w2solo.android.utils.ViewHelper

abstract class AbsUsersListFrag : BaseFragment(), AbsUsersListContract.View, IScrollToTop {

    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var rv: RecyclerView
    private val dataList = arrayListOf<User>()
    private val adapter = UserListAdapter(dataList)
    private lateinit var presenter: AbsUserListPresenter
    private lateinit var layoutManager: LinearLayoutManager

    override fun getLayout() = R.layout.top_user_list_fragment

    abstract fun canLoadMore(): Boolean

    abstract fun getPresenter(view: AbsUsersListContract.View): AbsUserListPresenter

    override fun initViews() {
        rv = fview(R.id.recyclerView)!!
        layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
        rv.layoutManager = layoutManager
        rv.adapter = adapter
        rv.addItemDecoration(ViewHelper.buildRecyclerViewDivider(context!!))

        presenter = getPresenter(this)
        addLifecycleObserver(presenter)

        refreshLayout = fview(R.id.swipe_refresh_layout)!!
        refreshLayout.setOnRefreshListener {
            presenter.loadList(true)
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
                    presenter.loadList(false)
                }
            }
        })

        refreshLayout.isRefreshing = true
        presenter.loadList(true)
    }

    override fun onGetList(newList: List<User>?, isRefresh: Boolean) {
        if (newList == null) {
            refreshLayout.isRefreshing = false
            Toast.makeText(context!!, R.string.error_request_failed, Toast.LENGTH_SHORT).show()
            return
        }
        if (isRefresh) {
            dataList.clear()
            dataList.addAll(newList)

            refreshLayout.isRefreshing = false
            adapter.notifyDataSetChanged()
        } else {
            val oldCount = dataList.size
            dataList.addAll(newList)
            adapter.notifyItemRangeInserted(oldCount, newList.size)
        }
    }

    override fun scrollToTop() {
        rv.smoothScrollToPosition(0)
    }
}