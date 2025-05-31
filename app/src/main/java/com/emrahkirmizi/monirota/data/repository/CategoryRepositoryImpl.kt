package com.emrahkirmizi.monirota.data.repository

import com.emrahkirmizi.monirota.core.init.InitialCategoryProvider
import com.emrahkirmizi.monirota.data.local.dao.CategoryDao
import com.emrahkirmizi.monirota.domain.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/*
CategoryRepositoryImpl interface'ini implement eden sınıf.
DAO üzerinden veritabanı işlemlerini gerçekleştirir.
 */

class CategoryRepositoryImpl @Inject constructor(
    private val dao: CategoryDao
) : CategoryRepository {

    //Yeni kategori ekler.
    override suspend fun insertCategory(category: Category) {
        dao.insertCategory(category)
    }

    //Tüm kategorileri canlı olarak getirir.
    override fun getAllCategories(): Flow<List<Category>> {
        return dao.getAllCategories()
    }

    //ID'ye göre kategori siler.
    override suspend fun deleteCategoryById(id: Int) {
        dao.deleteCategoryById(id)
    }

    //Eğer veritabanı boşsa, ön tanımlı kategorileri ekler.
    override suspend fun insertDefaultCategoriesIfEmpty() {
        val existing = dao.getAllOnce()
        if (existing.isEmpty()) {
            dao.insertCategories(InitialCategoryProvider.getDefaultCategories())
        }
    }
}
