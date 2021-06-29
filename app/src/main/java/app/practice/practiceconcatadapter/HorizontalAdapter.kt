package app.practice.practiceconcatadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.practice.practiceconcatadapter.databinding.ItemHorizontalColumnBinding
import app.practice.practiceconcatadapter.databinding.ItemOneColumnBinding
import app.practice.practiceconcatadapter.model.UserModel

class HorizontalAdapter(
    private val onClicked: (UserModel) -> Unit
) : ListAdapter<UserModel, RecyclerView.ViewHolder>(diffItem()) {

    companion object {
        fun diffItem(): DiffUtil.ItemCallback<UserModel> {
            return object : DiffUtil.ItemCallback<UserModel>() {
                override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                    return oldItem == newItem
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHorizontalColumnBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemHorizontalColumnBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClicked.invoke(getItem(bindingAdapterPosition))
            }
        }

        fun bind(item: UserModel?) {
            binding.textView.text = item?.name
        }

    }

}