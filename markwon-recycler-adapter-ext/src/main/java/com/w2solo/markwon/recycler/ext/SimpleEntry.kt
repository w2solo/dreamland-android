package com.w2solo.markwon.recycler.ext

import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import io.noties.markwon.Markwon
import io.noties.markwon.utils.NoCopySpannableFactory
import org.commonmark.node.Node
import java.util.*

public class SimpleEntry(
    @LayoutRes private val layoutResId: Int, @IdRes private val textViewIdRes: Int
) : MarkwonAdapter.Entry<Node, SimpleEntry.Holder>() {

    companion object {

        /**
         * Create [SimpleEntry] that has TextView as the root view of
         * specified layout.
         */
        fun createTextViewIsRoot(@LayoutRes layoutResId: Int) =
            SimpleEntry(layoutResId, 0)

        fun create(@LayoutRes layoutResId: Int, @IdRes textViewIdRes: Int) =
            SimpleEntry(layoutResId, textViewIdRes)

    }

    // small cache for already rendered nodes
    val cache: MutableMap<Node, Spanned?> = HashMap()

    override fun createHolder(inflater: LayoutInflater, parent: ViewGroup): Holder {
        return Holder(textViewIdRes, inflater.inflate(layoutResId, parent, false))
    }

    override fun bindHolder(markwon: Markwon, holder: Holder, node: Node) {
        var spanned = cache[node]
        if (spanned == null) {
            spanned = markwon.render(node)
            cache[node] = spanned
        }
        markwon.setParsedMarkdown(holder.textView, spanned)
    }

    override fun clear() {
        cache.clear()
    }

    class Holder(@IdRes textViewIdRes: Int, itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView: TextView

        init {
            val textView: TextView
            if (textViewIdRes == 0) {
                check(itemView is TextView) {
                    "TextView is not root of layout " +
                            "(specify TextView ID explicitly): " + itemView
                }
                textView = itemView
            } else {
                textView = itemView.findViewById(textViewIdRes)
            }
            this.textView = textView
            this.textView.setSpannableFactory(NoCopySpannableFactory.getInstance())
        }
    }
}
