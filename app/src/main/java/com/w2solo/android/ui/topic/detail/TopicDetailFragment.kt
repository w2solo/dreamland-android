package com.w2solo.android.ui.topic.detail

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.w2solo.android.R
import com.w2solo.android.app.account.AccountManager
import com.w2solo.android.data.entity.Comment
import com.w2solo.android.data.entity.Topic
import com.w2solo.android.ui.base.fragment.BaseFragment
import com.w2solo.android.ui.commentbar.CommentBar
import com.w2solo.android.ui.commonfrag.CommonFragActivity
import com.w2solo.android.ui.topic.markdown.MDParser
import com.w2solo.markwon.recycler.ext.MarkwonAdapter

class TopicDetailFragment : BaseFragment(), TopicDetailContract.View {

    companion object {

        fun showTopic(context: Context, topicId: Long) {
            val args = Bundle()
            args.putLong("extra_data_id", topicId)
            CommonFragActivity.start(context, R.string.title_topic_detail, args)
        }

        fun showTopic(context: Context, topic: Topic) {
            val args = Bundle()
            args.putParcelable("extra_data", topic)
            CommonFragActivity.start(context, R.string.title_topic_detail, args)
        }
    }

    private var initTopicId: Long = -1L
    private var topic: Topic? = null
    private val dataList = arrayListOf<Comment>()

    private lateinit var rv: RecyclerView
    private lateinit var refreshLayout: SwipeRefreshLayout
    private var adapter: MarkwonAdapter? = null
    private lateinit var presenter: TopicDetailPresenterImpl
    private lateinit var commentBar: CommentBar

    override fun getLayout() = R.layout.topic_detail_fragment

    override fun initViews() {
        initTopicId = arguments!!.getLong("extra_data_id", -1L)
        topic = arguments!!.getParcelable("extra_data")
        if (topic == null && initTopicId < 0L) {
            Toast.makeText(context, R.string.error_invalid_params, Toast.LENGTH_SHORT).show()
            activity?.finish()
            return
        }
        rv = fview(R.id.recyclerView)!!
        rv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        presenter = TopicDetailPresenterImpl(this)
        refreshLayout = fview(R.id.swipe_refresh_layout)!!
        refreshLayout.setOnRefreshListener {
            if (topic != null) {
                presenter.loadTopic(topic!!.id)
            }
        }
        commentBar = fview(R.id.comment_bar)!!
        commentBar.setCallback(object : CommentBar.Callback {
            override fun onSuccess(newComment: Comment, isEdit: Boolean) {
                if (isEdit) {
                    for (item in dataList) {
                        if (item.id == newComment.id) {
                            item.body = newComment.body
                            item.createdTime = newComment.createdTime
                            adapter?.notifyDataSetChanged()
                            break
                        }
                    }
                } else {
                    val oldSize = adapter?.itemCount ?: 0
                    dataList.add(newComment)
                    val newSize = adapter?.itemCount ?: 0
                    adapter?.notifyItemRangeChanged(oldSize, newSize - oldSize)
                }
            }
        })

        val topicId = if (topic != null) topic!!.id else initTopicId
        refreshLayout.isRefreshing = true
        presenter.loadTopic(topicId)
    }

    override fun onGetTopic(newTopic: Topic?, fromAPI: Boolean) {
        if (newTopic == null) {
            //no local data ,and load from api failed
            if (fromAPI && topic == null) {
                Toast.makeText(context, R.string.error_data_load_failed, Toast.LENGTH_SHORT).show()
                activity?.finish()
                return
            }
        }
        if (adapter == null) {
            topic = newTopic
            adapter =
                MarkwonAdapter.builder(R.layout.listitem_topic_md_node, R.id.md_text_view)
                    //register a delegate to display comment item in markdown adapter
                    .adapterDelegate(
                        TopicMarkdownAdapterDelegate(dataList,
                            object : TopicMarkdownAdapterDelegate.OnItemClickListener {
                                override fun onItemClick(comment: Comment) {
                                    onClickComment(comment)
                                }
                            })
                    )
                    .build()
            rv.adapter = adapter
        } else {
            //only replace content
            topic!!.bodyHtml = newTopic!!.bodyHtml
            topic!!.body = newTopic.body
        }
        commentBar.setDataId(topic!!.id)
        val markwon = MDParser.parseMarkDown(context!!)
        adapter!!.setMarkdown(markwon, topic!!.bodyHtml!!)
        //refresh topic details
        adapter!!.notifyDataSetChanged()
        presenter.loadReplies(topic!!.id, true)
    }

    override fun onGetReplies(newList: List<Comment>?, isRefresh: Boolean) {
        if (newList == null) {
            if (isRefresh) {
                refreshLayout.isRefreshing = false
            }
            return
        }
        if (isRefresh) {
            refreshLayout.isRefreshing = false
            dataList.clear()
        }
        dataList.addAll(newList)
        adapter!!.notifyDataSetChanged()
    }

    private fun onClickComment(comment: Comment) {
        if (comment.isDeleted) {
            return
        }
        if (!AccountManager.getInstance().isLogin || comment.user == null) {
            return
        }
        if (!comment.canAction()) {
            return
        }
        val user = AccountManager.getInstance().loginUser
        if (user!!.id == comment.user!!.id) {
            val items = arrayOf<CharSequence>(
                getString(R.string.comment_option_edit),
                getString(R.string.comment_option_delete)
            )
            MaterialAlertDialogBuilder(context!!)
                .setItems(items) { _, which ->
                    if (which == 0) {
                        commentBar.setEditComment(comment)
                    } else if (which == 1) {
                        deleteComment(comment)
                    }
                }.create().show()
        }
    }

    private fun deleteComment(comment: Comment) {
        MaterialAlertDialogBuilder(context!!)
            .setTitle(R.string.comment_option_delete)
            .setMessage(R.string.comment_option_delete_msg)
            .setPositiveButton(R.string.confirm) { _, _ ->
                presenter.deleteReply(comment.id)
            }
            .create().show()
    }

    override fun onDeleteReply(replyId: Long) {
        if (replyId <= 0L) {
            Toast.makeText(context!!, R.string.error_request_failed, Toast.LENGTH_SHORT).show()
            return
        }
        for (item in dataList) {
            if (item.id == replyId) {
                item.isDeleted = true
                adapter?.notifyDataSetChanged()
                break
            }
        }
    }
}