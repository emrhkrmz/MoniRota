package com.emrahkirmizi.monirota.data.repository

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
    override suspend fun insertCategory(category: Category) {
        dao.insertCategory(category)
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return dao.getAllCategories()
    }

    override suspend fun deleteCategoryById(id: Int) {
        dao.deleteCategoryById(id)
    }


}