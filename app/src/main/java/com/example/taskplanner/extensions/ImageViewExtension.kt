package com.example.taskplanner.extensions

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.taskplanner.R

fun AppCompatImageView.getImageFromUrl(url: String){
    Glide.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(this)
}