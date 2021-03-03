package com.w2solo.markwon.recycler.ext

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonReducer
import org.commonmark.node.Node

abstract class MarkwonAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {

        fun builderTextViewIsRoot(@LayoutRes defaultEntryLayoutResId: Int) =
            builder(SimpleEntry.createTextViewIsRoot(defaultEntryLayoutResId))

        /**
         * Factory method to obtain [Builder] instance.
         *
         * @see Builder
         */
        fun builder(
            @LayoutRes defaultEntryLayoutResId: Int,
            @IdRes defaultEntryTextViewResId: Int
        ) =
            builder(
                SimpleEntry.create(
                    defaultEntryLayoutResId,
                    defaultEntryTextViewResId
                )
            )

        fun builder(defaultEntry: Entry<out Node?, out RecyclerView.ViewHolder?>): Builder {
            return MarkwonAdapterImpl.BuilderImpl(defaultEntry as Entry<Node?, RecyclerView.ViewHolder?>)
        }

        fun createTextViewIsRoot(@LayoutRes defaultEntryLayoutResId: Int): MarkwonAdapter {
            return builderTextViewIsRoot(defaultEntryLayoutResId).build()
        }

        /**
         * Factory method to create a [MarkwonAdapter] for evaluation purposes. Resulting
         * adapter will use default layout for all blocks. Default layout has no styling and should
         * be specified explicitly.
         *
         * @see .create
         * @see .builder
         * @see SimpleEntry
         */
        open fun create(
            @LayoutRes defaultEntryLayoutResId: Int,
            @IdRes defaultEntryTextViewResId: Int
        ): MarkwonAdapter {
            return builder(defaultEntryLayoutResId, defaultEntryTextViewResId).build()
        }

        /**
         * Factory method to create a [MarkwonAdapter] that uses supplied entry to render all
         * nodes.
         *
         * @param defaultEntry [Entry] to be used for node rendering
         * @see .builder
         */
        open fun create(defaultEntry: Entry<out Node?, out RecyclerView.ViewHolder?>): MarkwonAdapter {
            return builder(defaultEntry).build()
        }
    }

    /**
     * Builder to create an instance of [MarkwonAdapter]
     *
     * @see .include
     * @see .reducer
     * @see .build
     */
    interface Builder {
        /**
         * Include a custom [Entry] rendering for a Node. Please note that `node` argument
         * must be *exact* type, as internally there is no validation for inheritance. if multiple
         * nodes should be rendered with the same [Entry] they must specify so explicitly.
         * By calling this method for each.
         *
         * @param node  type of the node to register
         * @param entry [Entry] to be used for `node` rendering
         * @return self
         */
        fun <N : Node?> include(
            node: Class<N>,
            entry: Entry<in N, out RecyclerView.ViewHolder?>
        ): Builder

        /**
         * Specify how root Node will be *reduced* to a list of nodes. There is a default
         * [MarkwonReducer] that will be used if not provided explicitly (there is no need to
         * register your own unless you require it).
         *
         * @param reducer [MarkwonReducer]
         * @return self
         * @see MarkwonReducer
         */
        fun reducer(reducer: MarkwonReducer): Builder

        /**
         * Specify a RecyclerView.Adapter delegate to show other items
         */
        fun adapterDelegate(delegate: RecyclerAdapterDelegate): Builder

        /**
         * @return [MarkwonAdapter]
         */
        fun build(): MarkwonAdapter
    }

    /**
     * @see SimpleEntry
     */
    abstract class Entry<N : Node?, H : RecyclerView.ViewHolder?> {

        abstract fun createHolder(inflater: LayoutInflater, parent: ViewGroup): H

        abstract fun bindHolder(markwon: Markwon, holder: H, node: N)

        /**
         * Will be called when new content is available (clear internal cache if any)
         */
        open fun clear() {}

        fun id(node: N): Long = node.hashCode().toLong()

        fun onViewRecycled(holder: H) {}
    }

    abstract fun setMarkdown(markwon: Markwon, markdown: String)

    abstract fun setParsedMarkdown(markwon: Markwon, document: Node)

    abstract fun setParsedMarkdown(markwon: Markwon, nodes: List<Node?>)

    abstract fun getNodeViewType(node: Class<out Node?>): Int
}