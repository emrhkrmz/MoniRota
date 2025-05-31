package com.emrahkirmizi.monirota.data.local.dao
//DAO : Data Access Object : Yerel Veri Tabanı.
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.emrahkirmizi.monirota.domain.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    //Kategorilerin yönetildiği DAO.

    //Kategori ekleme.
    @Insert //Room'a bu fonksiyon veri ekleyecek.
    suspend fun insertCategory(category: Category)

    //Tüm kategorileri listeleme : REACTIVE(tepkisel).
    @Query("SELECT * FROM categories ORDER BY id DESC")
    fun getAllCategories(): Flow<List<Category>> //Sürekli dinlenen reactive yapısı.

    //Seçilen ID'ye sahip kategoriyi siler.
    @Query("DELETE FROM categories WHERE id = :categoryId")
    suspend fun deleteCategoryById(categoryId: Int)

    //Tüm kategorileri tek seferlik getirir. Flow değil(Zaman içinde değişmez).
    @Query("SELECT * FROM categories")
    suspend fun getAllOnce(): List<Category>

    //Çoklu kategori ekleme.
    @Insert
    suspend fun insertCategories(categories: List<Category>)
}
