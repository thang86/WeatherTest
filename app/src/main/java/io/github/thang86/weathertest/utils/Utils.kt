package io.github.thang86.weathertest.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas

import android.graphics.Bitmap

import android.graphics.drawable.VectorDrawable

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

import android.graphics.drawable.BitmapDrawable

import android.graphics.drawable.Drawable

import androidx.appcompat.content.res.AppCompatResources




/**
 *
 *    Uitls.kt
 *
 *    Created by ThangTX on 11/08/2021
 *
 */
object Utils {
    fun dp2px(resources: Resources, dp: Int): Float {
        val scale: Float = resources.getDisplayMetrics().density
        return dp * scale + 0.5f
    }

    fun sp2px(resources: Resources, sp: Int): Float {
        val scale: Float = resources.getDisplayMetrics().scaledDensity
        return sp * scale
    }

    fun getBitmap(context: Context?, drawableId: Int): Bitmap? {
        val drawable = context?.let { AppCompatResources.getDrawable(it, drawableId) }
        return drawable?.let { getBitmap(it) }
    }

    fun getBitmap(drawable: Drawable): Bitmap? {
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else if (drawable is VectorDrawableCompat || drawable is VectorDrawable) {
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        } else {
            throw IllegalArgumentException("unsupported drawable type")
        }
    }
}