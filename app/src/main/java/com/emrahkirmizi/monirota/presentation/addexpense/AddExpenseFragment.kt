package com.emrahkirmizi.monirota.presentation.addexpense

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
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

    // ViewBinding referansı
    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!

    // ViewModel çağrısı
    private val viewModel: AddExpenseViewModel by viewModels()

    // Kategori liste adaptörü
    private lateinit var categoryAdapter: CategorySelectAdapter

    // Seçilen kategori (şimdilik saklanıyor)
    private var selectedCategory: Category? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddExpenseBinding.bind(view)

        setupCategoryRecyclerView()   // Kategori listesini başlat
        observeCategories()           // ViewModel'den gelen kategori verisini dinle
        setupListeners()              // 🟢 Şimdi aktif! Hazır tuş gizleme burada
    }

    // Kategori RecyclerView setup
    private fun setupCategoryRecyclerView() {
        categoryAdapter = CategorySelectAdapter(emptyList()) { selected ->
            selectedCategory = selected
        }

        binding.rvCategories.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.rvCategories.adapter = categoryAdapter
    }

    // ViewModel'deki kategori listesini dinler ve günceller
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

    // 🟢 HAZIR TUŞLARI GİZLEME ÖZELLİĞİ BURADA AKTİF
    private fun setupListeners() {
        // Kullanıcı manuel tutar girişi alanına tıklayınca, hazır tuşlar gizlensin
        binding.etManualAmount.setOnFocusChangeListener { _, hasFocus ->
            binding.gridKeypad.isVisible = !hasFocus
        }
    }

    /*
     * 🟡 İLERİDE EKLENECEKLER:
     *
     * 1. private fun saveExpense()
     *    - Seçilen kategori ve tutarı kontrol et
     *    - ViewModel aracılığıyla veritabanına kaydet
     *
     * 2. private fun clearInputs()
     *    - Kayıt sonrası alanları temizle
     *    - Kategori seçimini sıfırla
     *
     * 3. ViewModel > fun addExpense(amount: Double, categoryId: Int)
     *    - Room DB kullanılarak harcama verisi eklenir
     *
     * 4. Navigation → Kaydettikten sonra başka ekrana yönlendirme (örn: ana liste ekranı)
     */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
