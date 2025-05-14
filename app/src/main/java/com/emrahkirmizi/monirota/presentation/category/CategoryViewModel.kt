package com.emrahkirmizi.monirota.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emrahkirmizi.monirota.data.repository.CategoryRepository
import com.emrahkirmizi.monirota.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

//SEKME 3 – Kategori Yönetimi
//ViewModel

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    // 🔹 1. App ilk açıldığında, eğer veritabanı boşsa ön tanımlı kategoriler eklensin
    init {
        viewModelScope.launch {
            repository.insertDefaultCategoriesIfEmpty()
        }
    }

    // 🔹 2. Tüm kategorileri canlı olarak alır
    val categories = repository.getAllCategories()
        .map { it.sortedByDescending { cat -> cat.id } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    // 🔹 3. Yeni kategori ekler
    fun addCategory(category: Category) {
        viewModelScope.launch {
            repository.insertCategory(category)
        }
    }

    // 🔹 4. ID'ye göre kategori siler
    fun deleteCategoryById(id: Int) {
        viewModelScope.launch {
            repository.deleteCategoryById(id)
        }
    }
}
