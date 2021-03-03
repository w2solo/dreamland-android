package com.w2solo.android.ui.topic.detail

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.w2solo.android.R
import com.w2solo.android.data.entity.Comment
import com.w2solo.android.data.entity.Topic
import com.w2solo.android.ui.base.fragment.BaseFragment
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
                    .adapterDelegate(TopicMarkdowAdapterDelegate(dataList))
                    .build()
            rv.adapter = adapter
        } else {
            //only replace content
            topic!!.bodyHtml = newTopic!!.bodyHtml
            topic!!.body = newTopic.body
        }
        val markwon = MDParser.parseMarkDown(context!!)
        adapter!!.setMarkdown(markwon, topic!!.bodyHtml!!)
        //refresh topic details
        adapter!!.notifyDataSetChanged()
        presenter.loadReplies(true)
    }

    override fun onGetReplies(newList: List<Comment>?, isRefresh: Boolean) {
        if (isRefresh) {
            refreshLayout.isRefreshing = false
        }
        if (newList == null) {
            return
        }
        if (isRefresh) {
            dataList.clear()
            dataList.addAll(newList)
            adapter!!.notifyDataSetChanged()
        } else {
            val old = dataList.size
            dataList.addAll(newList)
            adapter!!.notifyItemRangeInserted(old, newList.size)
        }
    }
}