package  com.yts.ytscleanarchitecture.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.yts.ytscleanarchitecture.BR
import com.yts.ytscleanarchitecture.extension.hideKeyboard

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {
    private var mLastClickTime: Long = 0
    lateinit var binding: B

    abstract fun onLayoutId(): Int
    abstract fun observer()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, onLayoutId())

        if (::binding.isInitialized) {
            binding.lifecycleOwner = this
            observer()
        }
    }

    protected fun addBindingVariable(variableId: Int, value: Any) {
        binding.setVariable(variableId, value)
    }
}
