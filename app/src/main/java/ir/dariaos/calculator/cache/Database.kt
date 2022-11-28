package ir.dariaos.calculator.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.dariaos.calculator.cache.dao.HistoryDao
import ir.dariaos.calculator.cache.model.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

}