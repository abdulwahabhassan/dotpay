package com.devhassan.dotpay

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.res.ResourcesCompat
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.text.DecimalFormat

class Utils {

    fun formatCurrency(value: Any): String {
        val valueToBeFormatted: Number =
            if (value is String) value.toDouble() else value as Number
        val df = DecimalFormat("##,###,##0.00")
        return df.format(valueToBeFormatted)
    }

    fun showMotionToast(
        context: Activity,
        message: String?,
        style: MotionToastStyle,
        position: Int = Gravity.CENTER_HORIZONTAL
    ) {
        try {
            if (message != null) {
                MotionToast.createColorToast(
                    context,
                    "DotPay",
                    message,
                    style,
                    position,
                    MotionToast.LONG_DURATION + MotionToast.SHORT_DURATION,
                    ResourcesCompat.getFont(context, R.font.roboto)
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}