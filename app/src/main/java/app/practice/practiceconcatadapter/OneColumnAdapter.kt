package app.practice.practiceconcatadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.practice.practiceconcatadapter.databinding.ItemOneColumnBinding
import app.practice.practiceconcatadapter.model.UserModel

class OneColumnAdapter(private val onClicked: (UserModel) -> Unit) :
    ListAdapter<UserModel, OneColumnAdapter.ItemViewHolder>(diffItem) {

    companion object {

        const val VIEW_TYPE = 1111

        private val diffItem = object : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ItemOneColumnBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, onClicked)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(
        private val binding: ItemOneColumnBinding,
        onClicked: (UserModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClicked.invoke(getItem(bindingAdapterPosition))
            }
        }

        fun bind(item: UserModel?) {
            binding.textView.text = item?.name.orEmpty()
        }

    }

}