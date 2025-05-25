package com.emrahkirmizi.monirota.presentation.addexpense

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.emrahkirmizi.monirota.R
import com.emrahkirmizi.monirota.databinding.FragmentAddExpenseBinding
import com.emrahkirmizi.monirota.domain.model.Category
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddExpenseFragment : Fragment(R.layout.fragment_add_expense) {

    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddExpenseViewModel by viewModels()
    private lateinit var categoryAdapter: CategorySelectAdapter
    private var selectedCategory: Category? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddExpenseBinding.bind(view)

        setupCategoryRecyclerView()
        observeCategories()
    }

    private fun setupCategoryRecyclerView() {
        categoryAdapter = CategorySelectAdapter(emptyList()) { selected ->
            selectedCategory = selected
        }

        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = categoryAdapter
        }
    }

    private fun observeCategories() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.categories.collect { list ->
                categoryAdapter = CategorySelectAdapter(list) { selected ->
                    selectedCategory = selected
                }
                binding.rvCategories.adapter = categoryAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
