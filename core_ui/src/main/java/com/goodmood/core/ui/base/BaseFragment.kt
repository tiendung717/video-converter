package com.goodmood.core.ui.base

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {

    protected val disposable = CompositeDisposable()

    override fun onDestroy() {
        disposable.dispose()
        disposable.clear()
        super.onDestroy()
    }
}