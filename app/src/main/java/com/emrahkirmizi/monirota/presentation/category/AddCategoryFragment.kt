package com.emrahkirmizi.monirota.presentation.category

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.emrahkirmizi.monirota.R
import com.emrahkirmizi.monirota.databinding.FragmentAddCategoryBinding
import com.emrahkirmizi.monirota.domain.model.Category
import com.emrahkirmizi.monirota.core.util.ColorUtils
import com.emrahkirmizi.monirota.core.util.IconUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn

@AndroidEntryPoint
class AddCategoryFragment : Fragment(R.layout.fragment_add_category) {

    private val viewModel: CategoryViewModel by viewModels()
    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddCategoryBinding.bind(view)

        setupSpinners()
        setupSaveButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSpinners() {
        val colorAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            ColorUtils.categoryColorMap.keys.toList()
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerColor.adapter = colorAdapter

        val iconAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            IconUtils.categoryIconList
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerIcon.adapter = iconAdapter
    }

    private fun setupSaveButton() {
        binding.buttonSave.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val colorName = binding.spinnerColor.selectedItem?.toString() ?: ""
            val iconName = binding.spinnerIcon.selectedItem?.toString() ?: ""
            val location = binding.editTextLocation.text.toString()

            if (name.isBlank()) {
                Toast.makeText(requireContext(), "Kategori adı boş olamaz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val colorHex = ColorUtils.categoryColorMap[colorName] ?: "#9E9E9E"

            val category = Category(
                name = name,
                colorHex = colorHex,
                iconName = iconName,
                location = location.ifBlank { null }
            )

            viewModel.addCategory(category)

            Toast.makeText(requireContext(), "Kategori eklendi ✅", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }
}
