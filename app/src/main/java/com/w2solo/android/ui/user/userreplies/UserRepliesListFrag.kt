package com.w2solo.android.ui.user.userreplies

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.w2solo.android.R
import com.w2solo.android.data.entity.Comment
import com.w2solo.android.ui.base.fragment.BaseFragment
import com.w2solo.android.ui.topic.detail.TopicDetailFragment
import com.w2solo.android.utils.ViewHelper

class UserRepliesListFrag : BaseFragment(), UserRepliesContract.View {

    companion object {
        const val EXTRA_LOGIN = "extra_login"

        fun newInstance(userLogin: String): UserRepliesListFrag {
            val args = Bundle()
            args.putString(EXTRA_LOGIN, userLogin)
            val frag = UserRepliesListFrag()
            frag.arguments = args
            return frag
        }
    }

    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var rv: RecyclerView
    private val dataList = arrayListOf<Comment>()
    private val adapter = CommentListAdapter(dataList, object : CommentListAdapter.Callback {
        override fun onCommentClicked(comment: Comment) {
            TopicDetailFragment.showTopic(context!!, comment.topicId)
        }
    })
    private val presenter = UserRepliesPresenterImpl(this)
    private var userLogin: String? = null

    override fun getLayout() = R.layout.user_replies_list_fragment

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(EXTRA_LOGIN, userLogin)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        userLogin = savedInstanceState?.getString(EXTRA_LOGIN)
    }

    override fun initViews() {
        userLogin = arguments?.getString(EXTRA_LOGIN, null)
        if (TextUtils.isEmpty(userLogin)) {
            Toast.makeText(context!!, R.string.error_invalid_params, Toast.LENGTH_SHORT).show()
            activity?.finish()
            return
        }
        rv = fview(R.id.recyclerView)!!
        rv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rv.adapter = adapter
        rv.addItemDecoration(ViewHelper.buildRecyclerViewDivider(context!!))

        refreshLayout = fview(R.id.swipe_refresh_layout)!!
        refreshLayout.setOnRefreshListener {
            loadList()
        }
        addLifecycleObserver(presenter)
        refreshLayout.isRefreshing = true
        loadList()
    }

    override fun onGetList(newList: List<Comment>?, isRefresh: Boolean) {
        refreshLayout.isRefreshing = false
        if (newList == null) {
            Toast.makeText(context!!, R.string.error_request_failed, Toast.LENGTH_SHORT).show()
            return
        }
        dataList.clear()
        dataList.addAll(newList)
        adapter.notifyDataSetChanged()
    }

    private fun loadList() {
        if (!TextUtils.isEmpty(userLogin)) {
            presenter.loadList(true, userLogin!!)
        }
    }
}