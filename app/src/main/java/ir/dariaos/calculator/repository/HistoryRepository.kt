package ir.dariaos.calculator.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.dariaos.calculator.cache.Response
import ir.dariaos.calculator.cache.dao.HistoryDao
import ir.dariaos.calculator.cache.model.HistoryEntity
import ir.dariaos.calculator.cache.safeCacheCall
import ir.dariaos.calculator.domain.History
import ir.dariaos.calculator.domain.toEntity
import ir.dariaos.calculator.util.printLogD
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val cache: HistoryDao
) {

    suspend fun saveHistory(history: History) {
        when (val response = safeCacheCall { cache.insertHistory(history.toEntity()) }) {
            is Response.Failure -> printLogD(
                this.javaClass.simpleName,
                "saveHistory() : ${response.message}"
            )
            is Response.Success -> printLogD(
                this.javaClass.simpleName,
                "saveHistory() : expression saved to history"
            )
        }
    }

    fun getAllHistory(): LiveData<List<HistoryEntity>> {
        return try {
            cache.getAllHistory()
        } catch (e: Exception) {
            printLogD(
                this.javaClass.simpleName,
                "getAllHistory() : ${e.message}"
            )
            MutableLiveData()
        }
    }

    suspend fun clearHistory() {
        try {
            cache.clearHistory()
        } catch (e: Exception) {
            printLogD(
                this.javaClass.simpleName,
                "clearHistory() : ${e.message}"
            )
        }
    }

    suspend fun deleteHistory(expression: String) {
        when (val response =
            safeCacheCall { cache.deleteHistoryByExpression(expression) }) {
            is Response.Failure -> printLogD(
                this.javaClass.simpleName,
                "deleteHistory() : ${response.message}"
            )
            is Response.Success -> printLogD(
                this.javaClass.simpleName,
                "deleteHistory() : deleted a history"
            )
        }
    }

    suspend fun deleteHistoryBefore(date: Long) {
        when (val response =
            safeCacheCall { cache.deleteHistoryBefore(date) }) {
            is Response.Failure -> printLogD(
                this.javaClass.simpleName,
                "deleteHistoryBefore() : ${response.message}"
            )
            is Response.Success -> printLogD(
                this.javaClass.simpleName,
                "deleteHistoryBefore() : deleted all history before $date"
            )
        }
    }

}