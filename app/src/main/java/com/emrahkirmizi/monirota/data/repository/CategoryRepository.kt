package com.emrahkirmizi.monirota.data.repository

import com.emrahkirmizi.monirota.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    /*
    Kategori ile ilgili veri işlemlerini soyutlayan arayüz.
    DAO ile ViewModel arasındaki katman.
    DAO = Data Access Object = Veri Erişim Nesnesi
     */

    //Yeni kategori veri tabanına eklenecek
    //@param category
    suspend fun insertCategory(category: Category)

    fun getAllCategories(): Flow<List<Category>>

    //Verilen ID'ye sahip kategoriyi veritabanından silecek.
    //@param id

    suspend fun deleteCategoryById(id: Int)

    /*
    suspend → Coroutine içinde çalışması gerektiğini belirtir (arka planda, UI’ı kilitlemeden)
    Flow<List<Category>> → Otomatik güncellenen liste akışı sağlar
    Her fonksiyon ViewModel tarafından çağrılıp, UI'da kullanılmak üzere planlanmıştır
    */

}