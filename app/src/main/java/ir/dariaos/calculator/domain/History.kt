package ir.dariaos.calculator.domain

import ir.dariaos.calculator.cache.model.HistoryEntity

data class History(
    val date: Long,
    val expression: String,
    val result: String,
)

fun History.toEntity(): HistoryEntity {
    return HistoryEntity(
        date = date,
        expression = expression,
        result = result
    )
}