package uz.ismoilroziboyev.footballlivescores.utils

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import uz.ismoilroziboyev.footballlivescores.R


fun ImageView.setImageWithUrl(url: String) {
    Glide.with(this).load(url).into(this)
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}
