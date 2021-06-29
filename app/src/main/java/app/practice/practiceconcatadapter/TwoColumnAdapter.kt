package app.practice.practiceconcatadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.practice.practiceconcatadapter.databinding.ItemTwoColumnBinding
import app.practice.practiceconcatadapter.model.UserModel

class TwoColumnAdapter(
    private val onClicked: (UserModel) -> Unit
) :
    ListAdapter<UserModel, TwoColumnAdapter.ItemViewHolder>(diffItem) {

    companion object {

        const val VIEW_TYPE = 2222

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
            ItemTwoColumnBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ItemTwoColumnBinding) :
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