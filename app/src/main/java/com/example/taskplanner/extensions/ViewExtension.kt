package com.example.taskplanner.extensions

import android.view.View
import android.view.animation.AnimationUtils

fun View.animation(anim: Int) {
    val animation = AnimationUtils.loadAnimation(context, anim)
    startAnimation(animation)
}

fun View.startActionAfterAnimation(anim: Int, action: () -> Unit) {
    val animation = AnimationUtils.loadAnimation(context, anim)
    this.startAnimation(animation)
    if(!animation.hasEnded()){
        action()
    }
}

