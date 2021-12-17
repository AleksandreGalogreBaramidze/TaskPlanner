package com.example.taskplanner.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

fun <T> Fragment.observeData(liveData: LiveData<T>, observer: (it: T) -> Unit) {
    liveData.observe(this.viewLifecycleOwner, { observer(it) })
}