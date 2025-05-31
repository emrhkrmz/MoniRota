package com.emrahkirmizi.monirota.di

/**
 * Room veritabanını (AppDatabase) singleton olarak sağlar.
 * CategoryDao'yu AppDatabase üzerinden üretir.
 * Hilt ile tüm projeye enjekte edilebilir hale getirir.
 * Enjejte : Bir sınıfa başka bir nesneyi otomatik olarak vermek.
 * Enjejte : Her sınıfı elle tek tek oluşturmazsın, @Inject yazarsın, Hilt senin yerine verir.
 * Enjekte edilebilir hale getirmek = O nesneyi uygulamanın her yerinde otomatik olarak kullanabilir hale getirmektir.
 */

import android.content.Context
import androidx.room.Room
import com.emrahkirmizi.monirota.data.local.dao.CategoryDao
import com.emrahkirmizi.monirota.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module //Hilt Modülü
@InstallIn(SingletonComponent::class) //Singleton
object DatabaseModule {
    @Provides //Hilt'e bu nesneyi ben sağlıyorum diyor.
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "monirota_db"
        ).build()
    }
}
