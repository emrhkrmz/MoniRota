package com.emrahkirmizi.monirota.data.local.database

/**
 * ROOM : Android Uygulamalarında, SQLite veritabanı kullanımı kolaylaştıran kütüphanedir. Google tarafından geliştirilmiştir.
 * Jetpack'in resmi parçasıdır.
 * Jetpack, Google tarafından geliştirilen, Android uygulamalarını daha kolay, hızlı ve güvenli geliştirilmesini sağlayan kütüphane.
 * Jetpack Android geliştirmede ihtiyacın olan tüm modern araçları ve kütüphaneleri tek çatı altında sunar.
 * @Dao -> @Entity -> ROOM'da bir tabloyu temsil eder -> Category, Expense
 * @Dao -> SQL Room'a ne yapacağını söyler -> CategoryDao, ExpenseDao
 * @Dao -> @Database -> Tüm tablo ve dataları yönetir -> Appdatabase
 * @Dao → Room → @Database → Hilt (@Provides) → @Singleton
 * @Dao → SQL işlemlerini tanımlar (Room'a ne yapılacağını söyler)
 * @Database → DAO'ları sağlar (AppDatabase içinde)
 * @Hilt → Bunları otomatik dağıtır (@Module + @Provides ile)
 * @Provides -> Hilt’e “şu nesneyi nasıl oluşturacağını ben sana söylüyorum” demek.
 * @Singleton → Aynı nesnenin yalnızca 1 kez yaratılmasını sağlar
 */
import androidx.room.Database
import androidx.room.RoomDatabase
import com.emrahkirmizi.monirota.data.local.dao.CategoryDao
import com.emrahkirmizi.monirota.domain.model.Category

@Database(
    entities = [Category::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}

