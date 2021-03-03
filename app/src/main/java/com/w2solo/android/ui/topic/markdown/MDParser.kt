package com.w2solo.android.ui.topic.markdown

import android.content.Context
import android.text.Spanned
import com.w2solo.android.R
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonConfiguration
import io.noties.markwon.core.MarkwonTheme
import io.noties.markwon.ext.tasklist.TaskListPlugin
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.glide.GlideImagesPlugin

object MDParser {
    fun parseMarkDown(context: Context, source: String): Spanned {
        val markwon = Markwon.builder(context)
            .usePlugin(HtmlPlugin.create { plugin ->
//                plugin.addHandler(TagHandlerNoOp.create("img"));
            })
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configureTheme(builder: MarkwonTheme.Builder) {
                    super.configureTheme(builder)
                    builder.linkColor(context.resources.getColor(R.color.theme_color_secondary))
                        .listItemColor(context.resources.getColor(R.color.theme_text_color_secondary))
                        .bulletWidth(10)
                }

                override fun configureConfiguration(builder: MarkwonConfiguration.Builder) {
                    super.configureConfiguration(builder)
                }
            })
            .usePlugin(GlideImagesPlugin.create(context))
            .usePlugin(TaskListPlugin.create(context))
            .build()
        return markwon.render(markwon.parse(source))
    }
}