package com.patricioveratapia.myweather.weather.extensions

import android.app.Activity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.patricioveratapia.myweather.R
import com.patricioveratapia.myweather.weather.util.ViewUtils

fun ImageView.loadImageUrl(url: String?) {
    Glide.with(this).load(url).error(R.drawable.ic_placeholder).into(this)
}

fun Fragment.toast(message: String?, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, message, length).show()
}

fun Activity.toast(message: String?, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}

fun FrameLayout.setRipple() {

    val rippleColor = ContextCompat.getColor(context, R.color.colorRipple)

    this.background = ViewUtils.getRippleDrawable(rippleColor)
}



