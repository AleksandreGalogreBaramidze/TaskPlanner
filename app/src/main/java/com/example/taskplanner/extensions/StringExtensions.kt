package com.example.taskplanner.extensions

import android.content.Context
import android.widget.Toast

fun String.toast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    return Toast.makeText(context, this, duration).show()
}