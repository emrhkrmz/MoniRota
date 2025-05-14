package com.emrahkirmizi.monirota.core.init

import com.emrahkirmizi.monirota.domain.model.Category

object InitialCategoryProvider {
    fun getDefaultCategories(): List<Category> {
        return listOf(
            Category(name = "Gıda", colorHex = "#F44336", iconName = "ic_food"),
            Category(name = "Eşya", colorHex = "#F9C27B0", iconName = "ic_goods"),
            Category(name = "Faturalar", colorHex = "#FF9800", iconName = "ic_invoices"),
        )
    }
}