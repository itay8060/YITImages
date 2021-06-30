package com.example.yitimages.utils

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity


object UIUtils {

    fun getScreenHeight(requireActivity: FragmentActivity): Int {
//        val insets = metrics.windowInsets.getInsets(WindowInsets.Type.systemBars())
//        return metrics.bounds.height() - insets.bottom - insets.top

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = requireActivity.windowManager.currentWindowMetrics
            val insets = metrics.windowInsets.getInsets(WindowInsets.Type.systemBars())
            metrics.bounds.height() - insets.bottom - insets.top
        } else {
//            val view = requireActivity.window.decorView
//            val insets = WindowInsetsCompat.toWindowInsetsCompat(view.rootWindowInsets, view).getInsets(
//                WindowInsetsCompat.Type.systemBars())
//            requireActivity.resources.displayMetrics.heightPixels - insets.bottom - insets.top

            val windowManager =
                requireActivity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display =  windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val height: Int = size.y
            height
        }
    }

    fun getHeightDp(requireActivity: FragmentActivity): Float {
        val displayMetrics: DisplayMetrics = requireActivity.getResources().getDisplayMetrics()
        val px =
            Math.round(
                displayMetrics.heightPixels / displayMetrics.density * getPixelScaleFactor(requireActivity)
            )
        return px.toFloat()

    }

    private fun getPixelScaleFactor(context: Context): Float {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT
    }
}