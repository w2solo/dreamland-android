package com.w2solo.android.ui.topic.markdown

import android.content.Context
import android.view.View
import com.w2solo.android.ui.topic.detail.TopicDetailFragment
import com.w2solo.android.utils.AppLog
import io.noties.markwon.LinkResolver
import io.noties.markwon.LinkResolverDef

/**
 * 考虑到针对不同App的适配性，此处应该改为可配置
 * topic
 * user
 * node
 */
class AppLinkResolver : LinkResolver {

    private val defaultResolver = LinkResolverDef()

    override fun resolve(view: View, link: String) {
        AppLog.d("MarkWon", "AppLinkResolver==resolve $link")
        if (filterTopic(view.context, link, LINK_PREFIX_TOPIC)
            || filterTopic(view.context, link, LINK_PREFIX_TOPIC2)
            || filterTopic(view.context, link, LINK_PREFIX_TOPIC3)
        ) {
            return
        }
        defaultResolver.resolve(view, link)
    }

    private fun filterTopic(context: Context, link: String, prefix: String): Boolean {
        if (link.startsWith(prefix)) {
            try {
                val start = prefix.length
                val idStr = link.substring(start, link.length)
                val topicId = idStr.toLong()
                TopicDetailFragment.showTopic(context, topicId)
                return true
            } catch (thr: Throwable) {
                thr.printStackTrace()
            }
        }
        return false
    }

    companion object {
        private const val W2SOLO_HOST = "https://www.w2solo.com/"
        private const val W2SOLO_HOST2 = "https://w2solo.com/"
        private const val W2SOLO_HOST3 = "https://indiehackers.net/"

        const val LINK_PREFIX_TOPIC = "${W2SOLO_HOST}topics/"
        const val LINK_PREFIX_TOPIC2 = "${W2SOLO_HOST2}topics/"
        const val LINK_PREFIX_TOPIC3 = "${W2SOLO_HOST3}topics/"
    }
}