package com.emrahkirmizi.monirota.presentation.addexpense

import android.graphics.Rect
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
import android.view.MotionEvent
import androidx.core.widget.doOnTextChanged

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
        setupListeners()              //  Şimdi aktif! Hazır tuş gizleme burada
        listenKeyboardVisibility()         // klavye takibi aktif ediliyor
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

    // HAZIR TUŞLARI GİZLEME ÖZELLİĞİ BURADA AKTİF

    private fun setupListeners() {
        binding.etManualAmount.doOnTextChanged { text, _, _, _ ->
            val input = text.toString().trim()
            binding.tvAmount.text = if (input.isEmpty()) "0 TL" else "$input TL"
        }

        // Şimdilik hazır tuşlar burada değil,
        // sadece buton tıklamaları vs. ileride buraya eklenecek
    }


    private fun listenKeyboardVisibility() {
        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
            if (_binding == null) return@addOnGlobalLayoutListener

            val rect = Rect()
            binding.root.getWindowVisibleDisplayFrame(rect)
            val screenHeight = binding.root.rootView.height
            val keypadHeight = screenHeight - rect.bottom

            val isKeyboardVisible = keypadHeight > screenHeight * 0.15

            // 👇 Klavye AÇIKSA gizle
            if (isKeyboardVisible) {
                binding.gridKeypad.isVisible = false
            } else {
                // 👇 Klavye kapalıysa göster
                binding.gridKeypad.isVisible = true
            }
        }
    }




    override fun onDestroyView() {
        binding.etManualAmount.setOnFocusChangeListener(null)
        super.onDestroyView()
        _binding = null
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