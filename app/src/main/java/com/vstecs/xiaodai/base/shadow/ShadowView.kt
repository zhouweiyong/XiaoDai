package com.vstecs.xiaodai.base.shadow

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.vstecs.xiaodai.base.ILog
import com.vstecs.xiaodai.base.Json
import com.vstecs.xiaodai.base.dp

class ShadowView(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {
    private var shadowRadius = dp(4).toFloat()
    private var shadowSize = dp(20).toFloat()
    private var maxShadowSize = dp(20).toFloat()
    private var shadowColor = 0
    private var width = 0F
    private var height = 0F

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
        child.post {
            lateinit var drawable: Drawable
            if (child is ImageView) {
                drawable = child.drawable
                child.setImageDrawable(null)
            } else {
                drawable = child.background
                child.background = null
            }
            drawable.setBounds(child.left, child.top, child.right, child.bottom)
            when (drawable) {
                is ColorDrawable -> shadowColor = drawable.color
                is GradientDrawable -> {
                    shadowRadius = drawable.cornerRadius
                    val maxShadowRadius = Math.min(child.width, child.height) / 2
                    if (shadowRadius > maxShadowRadius) shadowRadius = maxShadowRadius.toFloat()
                    shadowColor = drawable.color.defaultColor
                }
                is BitmapDrawable -> {
                    val bitmap = drawable.bitmap
                    shadowColor = bitmap.getPixel(bitmap.width / 2, bitmap.height - 4)
                }
                else ->
                    ILog.error(Json.stringify(drawable.state))
            }
            background = ShadowDrawableWrapper(context, drawable, shadowRadius, shadowSize, maxShadowSize, shadowColor)
        }
    }
}