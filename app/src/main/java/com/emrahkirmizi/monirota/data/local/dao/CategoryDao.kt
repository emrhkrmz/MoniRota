package com.emrahkirmizi.monirota.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.emrahkirmizi.monirota.domain.model.Category
import kotlinx.coroutines.flow.Flow

//interface (DAO)

@Dao
interface CategoryDao {

    //Kategori ekleme
    @Insert
    suspend fun insertCategory(category: Category)

    //Tüm kategorileri listeleme
    @Query("SELECT * FROM categories ORDER BY id DESC")
    fun getAllCategories(): Flow<List<Category>>

    //Belirli bir kategoriyi silme
    @Query("DELETE FROM categories WHERE id = :categoryId")
    suspend fun deleteCategoryById(categoryId: Int)

    //Tüm kategorileri tek seferlik getirir
    @Query("SELECT * FROM categories")
    suspend fun getAllOnce(): List<Category>

    //Çoklu kategori ekleme
    @Insert
    suspend fun insertCategories(categories: List<Category>)
}
