package com.emrahkirmizi.monirota.di

/**
 * Interface(CategoryRepository) ile  implementasyon(CategoryRepositoryImpl) eşleştirilir.
 * @Binds ile Hilt'e bu eşleşme bildirilir.
 * ViewModel ve UseCase katmanlarında sadece interface kullanılır, bağımlılık otomatik çözülür.
 */

import com.emrahkirmizi.monirota.data.repository.CategoryRepository
import com.emrahkirmizi.monirota.data.repository.CategoryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    //RepositoryModule = Tüm Impl sınıflarını interface’leriyle bağladığın merkez.

    @Binds //Hilt’e interface’i hangi gerçek sınıfla eşleştireceğini söylemek.
    @Singleton
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository

}

