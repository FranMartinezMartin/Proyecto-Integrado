package org.dipalme.proteApp.ui.liveEvents

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val TAG = "SingleLiveEvent"
    private val aBool = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered")
        }
        super.observe(owner, Observer<T>() { T ->
            if (aBool.compareAndSet(true, false)) {
                observer.onChanged(T)
            }
        })
    }

    override fun setValue(t: T?) {
        aBool.set(true)
        super.setValue(t)
    }
}