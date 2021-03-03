package com.w2solo.android.ui.topic.markdown

import android.content.Context
import com.w2solo.android.R
import io.noties.markwon.*
import io.noties.markwon.core.MarkwonTheme
import io.noties.markwon.ext.tasklist.TaskListPlugin
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.AsyncDrawable
import io.noties.markwon.image.AsyncDrawableSpan
import io.noties.markwon.image.ImageProps
import io.noties.markwon.image.ImageSize
import io.noties.markwon.image.glide.GlideImagesPlugin
import io.noties.markwon.linkify.LinkifyPlugin
import org.commonmark.node.Image

object MDParser {
    fun parseMarkDown(context: Context): Markwon {
        val markwon = Markwon.builder(context)
            .usePlugin(HtmlPlugin.create())
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configureTheme(builder: MarkwonTheme.Builder) {
                    super.configureTheme(builder)
                    builder.linkColor(context.resources.getColor(R.color.theme_color_secondary))
                        .listItemColor(context.resources.getColor(R.color.theme_text_color_secondary))
                        .bulletWidth(10)
                }

                override fun configureConfiguration(builder: MarkwonConfiguration.Builder) {
                    super.configureConfiguration(builder)
                    builder.linkResolver(AppLinkResolver())
                }

                override fun configureSpansFactory(builder: MarkwonSpansFactory.Builder) {
                    super.configureSpansFactory(builder)
                    builder.setFactory(Image::class.java) { configuration, props ->
                        AsyncDrawableSpan(
                            configuration.theme(),
                            AsyncDrawable(
                                ImageProps.DESTINATION.require(props),
                                configuration.asyncDrawableLoader(),
                                configuration.imageSizeResolver(),
                                imageSize(props)
                            ),
                            AsyncDrawableSpan.ALIGN_CENTER,
                            ImageProps.REPLACEMENT_TEXT_IS_LINK.get(props, false)
                        )
                    }
                }
            })
            .usePlugin(LinkifyPlugin.create())
            .usePlugin(GlideImagesPlugin.create(context))
            .usePlugin(TaskListPlugin.create(context))
            .build()
        return markwon
    }

    private fun imageSize(props: RenderProps): ImageSize {
        val imageSize = ImageProps.IMAGE_SIZE[props]
        return imageSize ?: ImageSize(
            ImageSize.Dimension(100f, "%"),
            null
        )
    }
}