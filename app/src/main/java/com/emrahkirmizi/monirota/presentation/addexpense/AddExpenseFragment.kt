/**
 * presentation : Kullanıcıdan veri alır. ViewModel'e iletir.
 * presentation: Uygulamanın kullanıcıya görünen kısımları burada yönetilir..
 * ViewModel'den gelen verileri ekrana yansıtır.
 * Memory Like : Bir nesne işi bittikten sonra hata bellekte tutuluyorsa.
 * AddExpenseFragment.kt
 * Bu Fragment, kullanıcıdan kategori ve tutar bilgisi alır; bu veriyi ViewModel aracılığıyla
 * işleyip (ileride) Room veritabanına kaydeder. Aynı zamanda klavye durumu izlenerek tuş takımını
 * (GridLayout) gizleyip/gösterir. Tüm kodun içerisinde neler olduğuna dair açıklayıcı yorumlar bulunmakta.
 */

package com.emrahkirmizi.monirota.presentation.addexpense

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
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
import androidx.core.widget.doOnTextChanged

@AndroidEntryPoint
class AddExpenseFragment : Fragment(R.layout.fragment_add_expense) {

    // ViewBinding referansını tutan property. _binding null olabilir, bu yüzden nullable.
    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!

    // Hilt ile enjekte edilen ViewModel. İş mantığı (kategori listesi, ileride kaydetme vs.) burada.
    private val viewModel: AddExpenseViewModel by viewModels()

    // Kategori seçimi için adapter. RecyclerView horizontal liste olduğu için burada saklanır.
    private lateinit var categoryAdapter: CategorySelectAdapter

    // Şu anda seçili kategori. İlk başta null; kullanıcı tıklayınca güncellenecek.
    private var selectedCategory: Category? = null

    // Klavye açıldığında/kapatıldığında tetiklenen listener referansı.
    // Fragment yok edilirken clean-up yapmak için saklıyoruz.
    private var keyboardLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // ViewBinding’i başlatıyoruz.
        _binding = FragmentAddExpenseBinding.bind(view)

        // 1) Kategori RecyclerView’ını hazırlıyoruz.
        setupCategoryRecyclerView()

        // 2) ViewModel’den kategori listesini dinleyip adapter’ı güncelle.
        observeCategories()

        // 3) EditText ve diğer butonlar için dinleyicileri ayarla.
        setupListeners()

        // 4) Klavye görünüp gizlendiğinde tuş takımını gizleyen/gösteren yapıyı başlat.
        listenKeyboardVisibility()
    }

    /**
     * Kategori RecyclerView’ını hazırlar.
     * - LayoutManager: yatay (horizontal) LinearLayoutManager
     * - Başlangıçta boş liste veriyoruz.
     */
    private fun setupCategoryRecyclerView() {
        // Boş liste ile adapter oluşturuluyor; callback ile seçili kategori güncellenir.
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

    /**
     * ViewModel'den gelen kategori listesini Flow olarak dinler.
     * Her güncellemede yeni adapter set edilir (daha performanslı bir yaklaşım için DiffUtil önerilebilir).
     */
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

    /**
     * EditText içinde yazı değişimini dinler. Kullanıcı manuel değer girerse tvAmount TextView'ü günceller.
     * İleride hazır tuşlara (200,100,50...) tıklama dinleyicileri burada eklenebilir.
     */
    private fun setupListeners() {
        binding.etManualAmount.doOnTextChanged { text, _, _, _ ->
            val input = text.toString().trim()
            binding.tvAmount.text = if (input.isEmpty()) "0 TL" else "$input TL"
        }
        // Şu anda hazır tuş dinleyicileri eklenmedi, ileride butonlara setOnClickListener eklenebilir.
    }

    /**
     * Klavye görünüp kaybolduğunda, GridLayout içindeki hazır tuş takımını gizleyen/gösteren fonksiyon.
     * ViewTreeObserver.OnGlobalLayoutListener ekleyerek ekranın kapladığı alanı ölçeriz.
     * Klavye yüksekliğini hesaplayıp, %15’ten büyükse “klavye açık” kabul edip tuş takımını gizleriz.
     * Fragment yok edilirken listener’ı kaldırmak için değişkene atıyoruz.
     */
    private fun listenKeyboardVisibility() {
        // Eğer daha önce eklenen bir listener varsa, öncelikle onu kaldırıyoruz.
        keyboardLayoutListener?.let { existingListener ->
            binding.root.viewTreeObserver.removeOnGlobalLayoutListener(existingListener)
        }

        // Yeni bir OnGlobalLayoutListener nesnesi oluşturuyoruz.
        keyboardLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            // Eğer view binding null ise (onDestroyView sonrası), işlemi iptal et.
            if (_binding == null) return@OnGlobalLayoutListener

            // Ekranda görünür alanı ölçmek için bir Rect objesi kullanıyoruz.
            val rect = Rect()
            binding.root.getWindowVisibleDisplayFrame(rect)
            // Tüm ekranın yüksekliği
            val screenHeight = binding.root.rootView.height
            // Klavye nedeniyle kaplanan alan = ekran yüksekliği - görünür alt sınır
            val keypadHeight = screenHeight - rect.bottom

            // Eğer klavye yüksekliği ekranın %15’inden büyükse klavye açık say.
            val isKeyboardVisible = keypadHeight > screenHeight * 0.15

            // Klavye açıksa tuş takımını gizle, kapalıysa göster.
            binding.gridKeypad.isVisible = !isKeyboardVisible
        }

        // Listener'ı ViewTreeObserver’a ekliyoruz.
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(keyboardLayoutListener!!)
    }

    /**
     * Fragment view’ı yok edilirken yapılacak temizleme işlemleri:
     * Klavye dinleyicisini kaldırmak
     * Binding referansını null yapmak (memory leak önler).
     */
    override fun onDestroyView() {
        // 1) Eğer bir klavye listener’ı eklenmişse, kaldırıyoruz.
        keyboardLayoutListener?.let { listener ->
            binding.root.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
        keyboardLayoutListener = null

        // 2) Süper çağrısı ve binding’in iptali
        super.onDestroyView()
        _binding = null
    }
}