package  com.yts.ytscleanarchitecture.presentation.base

import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.keyIterator
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.yts.ytscleanarchitecture.extension.hideKeyboard

// binding 을 외부에서 설정함
// extends = out 서브
// super = in  슈퍼
abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {
    private var mLastClickTime: Long = 0

    abstract fun onLayoutId(): Int
    abstract fun setupViewModel(): SparseArray<ViewModel>
    abstract fun observer()

    lateinit var binding: B

    private val tag = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(tag, "onCreate")
        binding = DataBindingUtil.setContentView(this, onLayoutId())

        if (::binding.isInitialized) {
            for (variableId in setupViewModel().keyIterator()) {
                binding.setVariable(variableId, setupViewModel()[variableId])
            }
            binding.lifecycleOwner = this
            observer()
        }

    }

    fun clickTimeCheck(): Boolean {
        if (System.currentTimeMillis() - mLastClickTime < 700) {
            return true
        }
        mLastClickTime = System.currentTimeMillis()
        return false
    }

    override fun onBackPressed() {
        this.hideKeyboard()
        super.onBackPressed()
    }

    override fun onStart() {
        super.onStart()
        Log.e(tag, "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(tag, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.e(tag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e(tag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e(tag, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(tag, "onDestroy")
    }

}
