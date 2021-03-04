package com.w2solo.markwon.recycler.ext

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonReducer
import org.commonmark.node.Node
import java.util.*

class MarkwonAdapterImpl(
    private val entries: SparseArray<Entry<Node?, RecyclerView.ViewHolder?>?>? = null,
    private val defaultEntry: Entry<Node?, RecyclerView.ViewHolder?>,
    private val reducer: MarkwonReducer? = null,
    private var adapterDelegate: RecyclerAdapterDelegate? = null
) : MarkwonAdapter() {

    private lateinit var markwon: Markwon
    private var nodes: List<Node?>? = null
    var layoutInflater: LayoutInflater? = null

    init {
        setHasStableIds(true)
    }

    override fun setMarkdown(markwon: Markwon, markdown: String) {
        setParsedMarkdown(markwon, markwon.parse(markdown))
    }

    override fun setParsedMarkdown(markwon: Markwon, document: Node) {
        setParsedMarkdown(markwon, reducer!!.reduce(document))
    }

    override fun setParsedMarkdown(markwon: Markwon, nodes: List<Node?>) {
        // clear all entries before applying
        defaultEntry.clear()
        var i = 0
        val size = entries!!.size()
        while (i < size) {
            entries.valueAt(i)?.clear()
            i++
        }
        this.markwon = markwon
        this.nodes = nodes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val delegateVH = adapterDelegate?.onCreateViewHolder(parent, viewType)
        if (delegateVH != null) {
            return delegateVH
        }
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val entry: Entry<Node?, RecyclerView.ViewHolder?> = getEntry(viewType)
        return entry.createHolder(layoutInflater!!, parent)!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val result = adapterDelegate?.onBindViewHolder(holder, position, getNodeCount()) ?: false
        if (result) {
            return
        }
        val node = nodes!![getMarkdownItemPos(position)]
        val viewType: Int = getNodeViewType(node!!.javaClass)
        val entry: Entry<Node?, RecyclerView.ViewHolder?> = getEntry(viewType)
        entry.bindHolder(markwon, holder, node)
    }

    override fun getItemCount() =
        getNodeCount() + (adapterDelegate?.getItemCount() ?: 0) + getHeaderCount()

    private fun getNodeCount() = nodes?.size ?: 0

    private fun getHeaderCount() = adapterDelegate?.getHeaderCount() ?: 0

    /**
     * check is current position is between markwon nodes' range
     */
    private fun isMarkDownNode(position: Int): Boolean {
        val headerCount = getHeaderCount()
        return position >= headerCount && position < (getNodeCount() + headerCount)
    }

    private fun getMarkdownItemPos(posInAdapter: Int) = posInAdapter - getHeaderCount()

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        val isDelegateItem = adapterDelegate?.isDelegateItem(holder) ?: false
        if (isDelegateItem) {
            return
        }
        super.onViewRecycled(holder)
        val entry: Entry<Node?, RecyclerView.ViewHolder?> = getEntry(holder.itemViewType)
        entry.onViewRecycled(holder)
    }

    fun getItems(): List<Node?> {
        return if (nodes != null) Collections.unmodifiableList(nodes) else emptyList()
    }

    override fun getItemViewType(position: Int): Int {
        if (!isMarkDownNode(position)) {
            return adapterDelegate!!.getItemViewType(position, getNodeCount())
        }
        return getNodeViewType(nodes!![getMarkdownItemPos(position)]!!.javaClass)
    }

    override fun getItemId(position: Int): Long {
        if (!isMarkDownNode(position)) {
            return 0
        }
        val node = nodes!![getMarkdownItemPos(position)]
        val type: Int = getNodeViewType(node!!.javaClass)
        val entry: Entry<Node?, RecyclerView.ViewHolder?> = getEntry(type)
        return entry.id(node)
    }

    override fun getNodeViewType(node: Class<out Node?>): Int {
        // if has registered -> then return it, else 0
        val hash = node.hashCode()
        return if (entries!!.indexOfKey(hash) > -1) {
            hash
        } else 0
    }

    fun getEntry(viewType: Int): Entry<Node?, RecyclerView.ViewHolder?> {
        return if (viewType == 0) defaultEntry else entries!![viewType]!!
    }

    class BuilderImpl(private val defaultEntry: Entry<Node?, RecyclerView.ViewHolder?>?) : Builder {

        private val entries: SparseArray<Entry<Node?, RecyclerView.ViewHolder?>?>? = SparseArray(3)

        private var reducer: MarkwonReducer? = null
        private var delegate: RecyclerAdapterDelegate? = null

        override fun <N : Node?> include(
            node: Class<N>,
            entry: Entry<in N, out RecyclerView.ViewHolder?>
        ): Builder {
            entries!!.append(node.hashCode(), entry as Entry<Node?, RecyclerView.ViewHolder?>)
            return this
        }

        override fun reducer(reducer: MarkwonReducer): Builder {
            this.reducer = reducer
            return this
        }

        override fun adapterDelegate(delegate: RecyclerAdapterDelegate): Builder {
            this.delegate = delegate
            return this
        }

        override fun build(): MarkwonAdapter {
            if (reducer == null) {
                reducer = MarkwonReducer.directChildren()
            }
            return MarkwonAdapterImpl(entries, defaultEntry!!, reducer!!, delegate)
        }
    }
}
