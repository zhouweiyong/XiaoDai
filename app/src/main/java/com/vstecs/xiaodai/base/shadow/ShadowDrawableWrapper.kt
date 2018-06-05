package com.vstecs.xiaodai.base.shadow

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable

internal class ShadowDrawableWrapper(context: Context, content: Drawable, radius: Float, shadowSize: Float, maxShadowSize: Float, shadowColor: Int = 0) : DrawableWrapper(content) {

    val mCornerShadowPaint: Paint
    val mEdgeShadowPaint: Paint

    val mContentBounds: RectF

    var mCornerRadius: Float = 0.toFloat()

    var mCornerShadowPath: Path? = null

    // updated value with inset
    var mMaxShadowSize: Float = 0.toFloat()
    // actual value set by developer
    var mRawMaxShadowSize: Float = 0.toFloat()

    // multiplied value to account for shadow offset
    var mShadowSize: Float = 0.toFloat()
    // actual value set by developer
    var mRawShadowSize: Float = 0.toFloat()

    private var mDirty = true

    private val mShadowStartColor: Int
    private val mShadowMiddleColor: Int
    private val mShadowEndColor: Int

    private var mAddPaddingForCorners = true

    private var mRotation: Float = 0.toFloat()

    /**
     * If shadow size is set to a value above max shadow, we print a warning
     */
    private var mPrintedShadowClipWarning = false

    var cornerRadius: Float
        get() = mCornerRadius
        set(radius) {
            var radius = radius
            radius = Math.round(radius).toFloat()
            if (mCornerRadius == radius) {
                return
            }
            mCornerRadius = radius
            mDirty = true
            invalidateSelf()
        }

    var shadowSize: Float
        get() = mRawShadowSize
        set(size) = setShadowSize(size, mRawMaxShadowSize)

    var maxShadowSize: Float
        get() = mRawMaxShadowSize
        set(size) = setShadowSize(mRawShadowSize, size)

    val minWidth: Float
        get() {
            val content = 2 * Math.max(mRawMaxShadowSize, mCornerRadius + mRawMaxShadowSize / 2)
            return content + mRawMaxShadowSize * 2
        }

    val minHeight: Float
        get() {
            val content = 2 * Math.max(mRawMaxShadowSize, mCornerRadius + mRawMaxShadowSize * SHADOW_MULTIPLIER / 2)
            return content + mRawMaxShadowSize * SHADOW_MULTIPLIER * 2
        }

    init {
        val red = Color.red(shadowColor)
        val green = Color.green(shadowColor)
        val blue = Color.blue(shadowColor)
        mShadowStartColor = Color.argb(0x64, red, green, blue)
        mShadowMiddleColor = Color.argb(0x24, red, green, blue)
        mShadowEndColor = Color.parseColor("#00000000")

        mCornerShadowPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        mCornerShadowPaint.style = Paint.Style.FILL
        mCornerRadius = Math.round(radius).toFloat()
        mContentBounds = RectF()
        mEdgeShadowPaint = Paint(mCornerShadowPaint)
        mEdgeShadowPaint.isAntiAlias = false
        setShadowSize(shadowSize, maxShadowSize)
    }

    fun setAddPaddingForCorners(addPaddingForCorners: Boolean) {
        mAddPaddingForCorners = addPaddingForCorners
        invalidateSelf()
    }

    override fun setAlpha(alpha: Int) {
        super.setAlpha(alpha)
        mCornerShadowPaint.alpha = alpha
        mEdgeShadowPaint.alpha = alpha
    }

    override fun onBoundsChange(bounds: Rect) {
        mDirty = true
    }

    fun setShadowSize(shadowSize: Float, maxShadowSize: Float) {
        var shadowSize = shadowSize
        var maxShadowSize = maxShadowSize
        if (shadowSize < 0 || maxShadowSize < 0) {
            throw IllegalArgumentException("invalid shadow size")
        }
        shadowSize = toEven(shadowSize).toFloat()
        maxShadowSize = toEven(maxShadowSize).toFloat()
        if (shadowSize > maxShadowSize) {
            shadowSize = maxShadowSize
            if (!mPrintedShadowClipWarning) {
                mPrintedShadowClipWarning = true
            }
        }
        if (mRawShadowSize == shadowSize && mRawMaxShadowSize == maxShadowSize) {
            return
        }
        mRawShadowSize = shadowSize
        mRawMaxShadowSize = maxShadowSize
        mShadowSize = Math.round(shadowSize * SHADOW_MULTIPLIER).toFloat()
        mMaxShadowSize = maxShadowSize
        mDirty = true
        invalidateSelf()
    }

    override fun getPadding(padding: Rect): Boolean {
        val vOffset = Math.ceil(calculateVerticalPadding(mRawMaxShadowSize, mCornerRadius, mAddPaddingForCorners).toDouble()).toInt()
        val hOffset = Math.ceil(calculateHorizontalPadding(mRawMaxShadowSize, mCornerRadius, mAddPaddingForCorners).toDouble()).toInt()
        padding.set(hOffset, vOffset, hOffset, vOffset)
        return true
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun draw(canvas: Canvas) {
        if (mDirty) {
            buildComponents(bounds)
            mDirty = false
        }
        drawShadow(canvas)

        super.draw(canvas)
    }

    fun setRotation(rotation: Float) {
        if (mRotation != rotation) {
            mRotation = rotation
            invalidateSelf()
        }
    }

    private fun drawShadow(canvas: Canvas) {
        val rotateSaved = canvas.save()
        canvas.rotate(mRotation, mContentBounds.centerX(), mContentBounds.centerY())

        val edgeShadowTop = -mCornerRadius - mShadowSize
        val shadowOffset = mCornerRadius
        val drawHorizontalEdges = mContentBounds.width() - 2 * shadowOffset > 0
        val drawVerticalEdges = mContentBounds.height() - 2 * shadowOffset > 0

        val shadowOffsetTop = mRawShadowSize - mRawShadowSize * SHADOW_TOP_SCALE
        val shadowOffsetHorizontal = mRawShadowSize - mRawShadowSize * SHADOW_HORIZ_SCALE
        val shadowOffsetBottom = mRawShadowSize - mRawShadowSize * SHADOW_BOTTOM_SCALE

        val shadowScaleHorizontal = shadowOffset / (shadowOffset + shadowOffsetHorizontal)
        val shadowScaleTop = shadowOffset / (shadowOffset + shadowOffsetTop)
        val shadowScaleBottom = shadowOffset / (shadowOffset + shadowOffsetBottom)

        // LT
        var saved = canvas.save()
        canvas.translate(mContentBounds.left + shadowOffset, mContentBounds.top + shadowOffset)
        canvas.scale(shadowScaleHorizontal, shadowScaleTop)
        canvas.drawPath(mCornerShadowPath!!, mCornerShadowPaint)
        if (drawHorizontalEdges) {
            // TE
            canvas.scale(1f / shadowScaleHorizontal, 1f)
            canvas.drawRect(0f, edgeShadowTop, mContentBounds.width() - 2 * shadowOffset, -mCornerRadius, mEdgeShadowPaint)
        }
        canvas.restoreToCount(saved)
        // RB
        saved = canvas.save()
        canvas.translate(mContentBounds.right - shadowOffset, mContentBounds.bottom - shadowOffset)
        canvas.scale(shadowScaleHorizontal, shadowScaleBottom)
        canvas.rotate(180f)
        canvas.drawPath(mCornerShadowPath!!, mCornerShadowPaint)
        if (drawHorizontalEdges) {
            // BE
            canvas.scale(1f / shadowScaleHorizontal, 1f)
            canvas.drawRect(0f, edgeShadowTop, mContentBounds.width() - 2 * shadowOffset, -mCornerRadius + mShadowSize, mEdgeShadowPaint)
        }
        canvas.restoreToCount(saved)
        // LB
        saved = canvas.save()
        canvas.translate(mContentBounds.left + shadowOffset, mContentBounds.bottom - shadowOffset)
        canvas.scale(shadowScaleHorizontal, shadowScaleBottom)
        canvas.rotate(270f)
        canvas.drawPath(mCornerShadowPath!!, mCornerShadowPaint)
        if (drawVerticalEdges) {
            // LE
            canvas.scale(1f / shadowScaleBottom, 1f)
            canvas.drawRect(0f, edgeShadowTop, mContentBounds.height() - 2 * shadowOffset, -mCornerRadius, mEdgeShadowPaint)
        }
        canvas.restoreToCount(saved)
        // RT
        saved = canvas.save()
        canvas.translate(mContentBounds.right - shadowOffset, mContentBounds.top + shadowOffset)
        canvas.scale(shadowScaleHorizontal, shadowScaleTop)
        canvas.rotate(90f)
        canvas.drawPath(mCornerShadowPath!!, mCornerShadowPaint)
        if (drawVerticalEdges) {
            // RE
            canvas.scale(1f / shadowScaleTop, 1f)
            canvas.drawRect(0f, edgeShadowTop, mContentBounds.height() - 2 * shadowOffset, -mCornerRadius, mEdgeShadowPaint)
        }
        canvas.restoreToCount(saved)

        canvas.restoreToCount(rotateSaved)
    }

    private fun buildShadowCorners() {
        val innerBounds = RectF(-mCornerRadius, -mCornerRadius, mCornerRadius, mCornerRadius)
        val outerBounds = RectF(innerBounds)
        outerBounds.inset(-mShadowSize, -mShadowSize)

        if (mCornerShadowPath == null) {
            mCornerShadowPath = Path()
        } else {
            mCornerShadowPath!!.reset()
        }
        mCornerShadowPath!!.fillType = Path.FillType.EVEN_ODD
        mCornerShadowPath!!.moveTo(-mCornerRadius, 0f)
        mCornerShadowPath!!.rLineTo(-mShadowSize, 0f)
        // outer arc
        mCornerShadowPath!!.arcTo(outerBounds, 180f, 90f, false)
        // inner arc
        mCornerShadowPath!!.arcTo(innerBounds, 270f, -90f, false)
        mCornerShadowPath!!.close()

        val shadowRadius = -outerBounds.top
        if (shadowRadius > 0f) {
            val startRatio = mCornerRadius / shadowRadius
            val midRatio = startRatio + (1f - startRatio) / 2f
            mCornerShadowPaint.shader = RadialGradient(0f, 0f, shadowRadius,
                intArrayOf(0, mShadowStartColor, mShadowMiddleColor, mShadowEndColor),
                floatArrayOf(0f, startRatio, midRatio, 1f),
                Shader.TileMode.CLAMP)
        }

        // we offset the content shadowSize/2 pixels up to make it more realistic.
        // this is why edge shadow shader has some extra space
        // When drawing bottom edge shadow, we use that extra space.
        mEdgeShadowPaint.shader = LinearGradient(0f, innerBounds.top, 0f, outerBounds.top,
            intArrayOf(mShadowStartColor, mShadowMiddleColor, mShadowEndColor),
            floatArrayOf(0f, .5f, 1f), Shader.TileMode.CLAMP)
        mEdgeShadowPaint.isAntiAlias = false
    }

    private fun buildComponents(bounds: Rect) {
        val verticalOffset = mRawMaxShadowSize * SHADOW_MULTIPLIER
        mContentBounds.set(bounds.left + mRawMaxShadowSize, bounds.top + verticalOffset, bounds.right - mRawMaxShadowSize, bounds.bottom - verticalOffset)
        wrappedDrawable!!.setBounds(mContentBounds.left.toInt(), mContentBounds.top.toInt(), mContentBounds.right.toInt(), mContentBounds.bottom.toInt())
        buildShadowCorners()
    }

    companion object {
        // used to calculate content padding
        val COS_45 = Math.cos(Math.toRadians(45.0))

        val SHADOW_MULTIPLIER = 1.5f

        val SHADOW_TOP_SCALE = 0.25f
        val SHADOW_HORIZ_SCALE = 0.5f
        val SHADOW_BOTTOM_SCALE = 1f

        /**
         * Casts the value to an even integer.
         */
        private fun toEven(value: Float): Int {
            val i = Math.round(value)
            return if (i % 2 == 1) i - 1 else i
        }

        fun calculateVerticalPadding(maxShadowSize: Float, cornerRadius: Float, addPaddingForCorners: Boolean): Float {
            return if (addPaddingForCorners) {
                (maxShadowSize * SHADOW_MULTIPLIER + (1 - COS_45) * cornerRadius).toFloat()
            } else {
                maxShadowSize * SHADOW_MULTIPLIER
            }
        }

        fun calculateHorizontalPadding(maxShadowSize: Float, cornerRadius: Float, addPaddingForCorners: Boolean): Float {
            return if (addPaddingForCorners) {
                (maxShadowSize + (1 - COS_45) * cornerRadius).toFloat()
            } else {
                maxShadowSize
            }
        }
    }
}
