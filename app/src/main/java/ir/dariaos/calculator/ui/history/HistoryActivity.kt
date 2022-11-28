package ir.dariaos.calculator.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import ir.dariaos.calculator.R
import ir.dariaos.calculator.cache.model.toDomain
import ir.dariaos.calculator.databinding.ActivityHistoryBinding
import ir.dariaos.calculator.domain.HistoryAdapterItem
import ir.dariaos.calculator.ui.base.BaseActivity
import ir.dariaos.calculator.ui.history.adapter.HistoryAdapter
import ir.dariaos.calculator.ui.history.viewmodel.HistoryViewModel
import ir.dariaos.calculator.ui.main.helper.removeNumberSeparator
import ir.dariaos.calculator.util.SHARE_EXPRESSION
import ir.dariaos.calculator.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryActivity : BaseActivity<ActivityHistoryBinding>() {

    private val viewModel by viewModels<HistoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)

        setupView()
        setupObservables()

    }

    private fun setupObservables() {
        viewModel.historyList.observe(this) { historyList ->
            if (historyList != null && historyList.isNotEmpty()) {
                binding.noHistory.visible(false)
                binding.rv.visible(true)
                val list = viewModel.transformHistory(historyList.map { it.toDomain() })
                val adapter = HistoryAdapter(list, object : HistoryAdapter.OnHistoryClickListener {
                    override fun onHistoryClick(history: HistoryAdapterItem) {
                        viewModel.saveExpression(removeNumberSeparator(history.expression))
                        finish()
                    }
                })
                binding.rv.adapter = adapter
            } else {
                binding.rv.visible(false)
                binding.noHistory.visible(true)
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            101 -> {
                val position = item.groupId
                val history = (binding.rv.adapter as HistoryAdapter).getHistory(position)
                viewModel.deleteHistory(history.expression)
                true
            }
            102 -> {
                val position = item.groupId
                val history = (binding.rv.adapter as HistoryAdapter).getHistory(position)
                val sharedEquation = "${history.expression} = ${history.result}"
                logEvent(SHARE_EXPRESSION)
                startActivity(
                    Intent.createChooser(
                        Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, "Calculator Plus Expression")
                            putExtra(Intent.EXTRA_TEXT, sharedEquation)
                        },
                        getString(R.string.choose)
                    )
                )
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.history_trash -> viewModel.clearHistory()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupView() {
        binding.toolbar.setNavigationOnClickListener { handleBackPress() }
    }

    override fun onBackPressed() {
        handleBackPress()
    }

    private fun handleBackPress() {
        finish()
    }

    override fun getViewBinding(inflater: LayoutInflater) = ActivityHistoryBinding.inflate(inflater)

}