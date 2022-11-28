package ir.dariaos.calculator.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.dariaos.calculator.domain.History

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "expression") val expression: String,
    @ColumnInfo(name = "result") val result: String,
    @ColumnInfo(name = "date") val date: Long
)

fun HistoryEntity.toDomain(): History {
    return History(
        expression = expression,
        result = result,
        date = date
    )
}