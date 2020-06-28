package com.patricioveratapia.myweather.weather.util

import android.annotation.TargetApi
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.*
import android.graphics.drawable.shapes.OvalShape
import android.os.Build

object ViewUtils {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun getRippleDrawable(pressedColor: Int): Drawable {

        return RippleDrawable(getPressedColorSelector(pressedColor), null, null)
    }

    fun getPressedColorSelector(pressedColor: Int): ColorStateList {

        return ColorStateList(
                arrayOf(intArrayOf()),
                intArrayOf(pressedColor)
        )
    }

    fun getSelectorDrawable(color: Int): StateListDrawable {

        val res = StateListDrawable()

        val drawable = ShapeDrawable(OvalShape())

        drawable.paint.color = color

        res.addState(intArrayOf(android.R.attr.state_pressed), drawable)

        res.addState(intArrayOf(), ColorDrawable(Color.TRANSPARENT))

        return res
    }
}