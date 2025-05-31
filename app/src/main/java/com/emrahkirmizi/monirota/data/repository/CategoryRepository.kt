package com.emrahkirmizi.monirota.data.repository

/*
UI (Fragment)
  ↓
ViewModel
  ↓
UseCase
  ↓
Repository
  ↓
DAO / API
 */

/**
 * Kategori ile ilgili veri işlemlerini soyutlayan arayüz.
 * DAO ile ViewModel arasındaki katman.
 * DAO = Data Access Object = Veri Erişim Nesnesi
 */

import com.emrahkirmizi.monirota.domain.model.Category
import kotlinx.coroutines.flow.Flow
//interface → Ne yapılacağını söyler, nasıl yapılacağını söylemez.
interface CategoryRepository {
    //CategoryRepository'de kategori işlemlerinin neler olduğu tarif edilir.

    //Yeni kategori veri tabanına eklenecek.
    suspend fun insertCategory(category: Category)

    //Tüm kategorileri flow olarak döndürür.
    fun getAllCategories(): Flow<List<Category>>

    //Verilen ID'ye sahip kategoriyi veritabanından silecek.
    suspend fun deleteCategoryById(id: Int)

    //Sadece app ilk açıldığında çalışır bir sefer çalışır.
    //Kategoriler boş ise uygulama çalıştığında, ön tanımlı kategori verileri eklenir.
    suspend fun insertDefaultCategoriesIfEmpty()

    /*
    suspend → Coroutine içinde çalışması gerektiğini belirtir (arka planda, UI’ı kilitlemeden)
    Flow<List<Category>> → Otomatik güncellenen liste akışı sağlar
    Her fonksiyon ViewModel tarafından çağrılıp, UI'da kullanılmak üzere planlanmıştır
    */

}