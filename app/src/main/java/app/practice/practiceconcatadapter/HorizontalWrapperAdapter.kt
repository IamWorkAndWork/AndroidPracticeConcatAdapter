package app.practice.practiceconcatadapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.practice.practiceconcatadapter.databinding.ItemHorizontalColumnWrapperBinding

class HorizontalWrapperAdapter(
    private val horizontalAdapter: HorizontalAdapter,
) : RecyclerView.Adapter<HorizontalWrapperAdapter.ViewHolder>() {

    private var lastScrollX = 0

    companion object {
        const val VIEW_TYPE = 3333
        private const val KEY_SCROLL_X = "horizontal.wrapper.adapter.key_scroll_x"
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHorizontalColumnWrapperBinding.inflate(inflater, parent, false)
        return ViewHolder(binding) { x ->
            lastScrollX = x
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(horizontalAdapter, lastScrollX)
    }

    inner class ViewHolder(
        private val binding: ItemHorizontalColumnWrapperBinding,
        private val onScrolled: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.horizontalRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        onScrolled(recyclerView.computeHorizontalScrollOffset())
                    }
                })
            }
        }

        fun bind(horizontalAdapter: HorizontalAdapter, lastScrollX: Int) {
            binding.horizontalRecyclerView.apply {
                adapter = horizontalAdapter
                doOnPreDraw {
                    binding.horizontalRecyclerView.scrollBy(lastScrollX, 0)
                }
            }
        }

    }

    fun onSaveState(outState: Bundle) {
        outState.putInt(KEY_SCROLL_X, lastScrollX)
    }

    fun onRestoreState(savedState: Bundle) {
        lastScrollX = savedState.getInt(KEY_SCROLL_X)
    }

    override fun getItemCount(): Int {
        return 1
    }

}