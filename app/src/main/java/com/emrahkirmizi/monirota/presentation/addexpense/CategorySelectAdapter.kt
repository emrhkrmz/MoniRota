package com.emrahkirmizi.monirota.presentation.addexpense

/**
 * Bu adapter, AddExpenseFragment’ta kategori listesini yatay gösterir.
 * Ve tıklanınca seçilen kategoriyi belirler, UI’yi günceller..
 */
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emrahkirmizi.monirota.R
import com.emrahkirmizi.monirota.databinding.ItemCategorySelectBinding
import com.emrahkirmizi.monirota.domain.model.Category

class CategorySelectAdapter(
    private val categories: List<Category>,
    private val onCategorySelected: (Category) -> Unit
) : RecyclerView.Adapter<CategorySelectAdapter.CategoryViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    inner class CategoryViewHolder(val binding: ItemCategorySelectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category, isSelected: Boolean) = with(binding) {
            tvCategoryName.text = category.name

            val iconResId = root.context.resources.getIdentifier(
                category.iconName, "drawable", root.context.packageName
            )
            ivIcon.setImageResource(
                if (iconResId != 0) iconResId else R.drawable.ic_launcher_background
            )

            // Seçili arka plan rengi
            cardView.setCardBackgroundColor(
                if (isSelected) Color.parseColor("#FFBB86FC") else Color.parseColor("#F0F0F0")
            )

            root.setOnClickListener {
                val previous = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previous)
                notifyItemChanged(selectedPosition)
                onCategorySelected(category)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategorySelectBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], position == selectedPosition)
    }

    override fun getItemCount(): Int = categories.size
}
