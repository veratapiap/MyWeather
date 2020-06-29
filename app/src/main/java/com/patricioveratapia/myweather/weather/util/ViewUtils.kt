package com.patricioveratapia.myweather.weather.util

import android.annotation.TargetApi
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.os.Build

object ViewUtils {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun getRippleDrawable(pressedColor: Int): Drawable {

        return RippleDrawable(getPressedColorSelector(pressedColor), null, null)
    }

    private fun getPressedColorSelector(pressedColor: Int): ColorStateList {

        return ColorStateList(
            arrayOf(intArrayOf()),
            intArrayOf(pressedColor)
        )
    }
}