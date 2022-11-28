package ir.dariaos.calculator.ui.about

import android.view.LayoutInflater
import ir.dariaos.calculator.databinding.ActivityAboutBinding
import ir.dariaos.calculator.ui.base.BaseActivity

class AboutActivity : BaseActivity<ActivityAboutBinding>() {

    override fun getViewBinding(inflater: LayoutInflater) = ActivityAboutBinding.inflate(inflater)
}