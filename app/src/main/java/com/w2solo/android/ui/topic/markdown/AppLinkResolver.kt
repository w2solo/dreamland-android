package com.w2solo.android.ui.topic.markdown

import android.view.View
import com.w2solo.android.ui.topic.detail.TopicDetailFragment
import com.w2solo.android.utils.AppLog
import io.noties.markwon.LinkResolver
import io.noties.markwon.LinkResolverDef

class AppLinkResolver : LinkResolver {

    private val defaultResolver = LinkResolverDef()

    override fun resolve(view: View, link: String) {
        AppLog.d("MarkWon", "AppLinkResolver==resolve $link $LINK_PREFIX_TOPIC")
        if (link.startsWith(LINK_PREFIX_TOPIC)) {//topic
            val start = LINK_PREFIX_TOPIC.length
            try {
                val idStr = link.substring(start, link.length)
                val topicId = idStr.toLong()
                TopicDetailFragment.showTopic(view.context, topicId)
            } catch (thr: Throwable) {
                thr.printStackTrace()
                defaultResolver.resolve(view, link)
            }
        } else {
            defaultResolver.resolve(view, link)
        }
    }

    companion object {
        private const val W2SOLO_HOST = "https://www.w2solo.com/"
        const val LINK_PREFIX_TOPIC = "${W2SOLO_HOST}topics/"
    }
}