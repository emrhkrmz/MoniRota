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
//Hilt, repository’yi buraya enjekte eder : @inject consturctor
class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    //Tüm kategorileri canlı olarak alır
    val categories = repository.getAllCategories()
        .map { it.sortedByDescending { cat -> cat.id } }
        //Flow’u UI’da canlı ve cache’li tutar : stateIn
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    //Yeni kategori ekler
    fun addCategory(category: Category) {

        viewModelScope.launch {

            repository.insertCategory(category)

        }

    }

    //ID' ye göre kategori siler
    fun deleteCategoryById(id: Int) {

        //Coroutine ile arka planda çalışır : viewModelScope.launch
        viewModelScope.launch {

            repository.deleteCategoryById(id)

        }

    }

}