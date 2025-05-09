package com.emrahkirmizi.monirota.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emrahkirmizi.monirota.data.local.dao.CategoryDao
import com.emrahkirmizi.monirota.data.local.dao.ExpenseDao
import com.emrahkirmizi.monirota.domain.model.Category

@Database(
    entities = [Category::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}

