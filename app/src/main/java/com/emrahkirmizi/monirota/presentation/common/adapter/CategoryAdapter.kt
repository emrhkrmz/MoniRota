package com.emrahkirmizi.monirota.presentation.common.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emrahkirmizi.monirota.databinding.ItemCategoryBinding
import com.emrahkirmizi.monirota.domain.model.Category

class CategoryAdapter : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(DiffCallback()) {

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.textViewName.text = category.name

            // Renk hex kodu parse edilirken çökmesin diye
            try {
                binding.textViewColor.setBackgroundColor(Color.parseColor(category.colorHex))
            } catch (e: IllegalArgumentException) {
                binding.textViewColor.setBackgroundColor(Color.GRAY)
            }

            binding.textViewIcon.text = "Icon: ${category.iconName}"

            //Lokasyon bilgisi eklenmiş ise göster
            if (category.location != null) {
                binding.textViewLocation.text = "Lokasyon: ${category.location}"
                binding.textViewLocation.visibility = View.VISIBLE
            } else {
                binding.textViewLocation.visibility = View.GONE
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category) = oldItem == newItem
    }
}
