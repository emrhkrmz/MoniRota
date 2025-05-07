package com.emrahkirmizi.monirota.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//data class kullanacaz

/**
 * Harcama kategorilerini temsil eden model.
 * Room veritabanında "categories" tablosuna karşılık gelir.
 */

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,              // Otomatik ID

    val name: String,             // Kategori adı (örnek: "Yemek")

    val colorHex: String,         // Renk kodu (örnek: "#F44336")

    val iconName: String,         // Drawable ismi (örnek: "ic_food")

    val location: String? = null  // Opsiyonel konum bilgisi
)
