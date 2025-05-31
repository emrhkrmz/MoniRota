package com.emrahkirmizi.monirota.domain.usecase

import com.emrahkirmizi.monirota.data.repository.CategoryRepository
import com.emrahkirmizi.monirota.domain.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Inject constructor : Hilt bu sınıfın nasıl oluşturulacağını otomatik olarak bilir.
 * View model bu sınıfı çağırdığında, Hilt CategoryRepository'yi enjekte eder.
 * private val repository : Veriyi sağlayan katman : CategoryRepository üzerinden çalışır.
 * UseCase, hangi kaynak sorusunu sormaz. Sadece "veriyi getir" komutunu verir.
 * Repository : DAO'dan veriyi çeker.
 * UseCase : Repository'e gider.
 * ViewModel : getCategoryUseCase() çağırır.
 */

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(): Flow<List<Category>> {
        return repository.getAllCategories()
    }
}
