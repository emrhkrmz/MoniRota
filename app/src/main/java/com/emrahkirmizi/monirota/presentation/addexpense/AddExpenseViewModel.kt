package com.emrahkirmizi.monirota.presentation.addexpense
/*
Fragment bu ViewModel’i dinleyerek kategori listesini gösterir.
 */
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emrahkirmizi.monirota.domain.model.Category
import com.emrahkirmizi.monirota.domain.usecase.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().collect { list ->
                _categories.value = list
            }
        }
    }
}
