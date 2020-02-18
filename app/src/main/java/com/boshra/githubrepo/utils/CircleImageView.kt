package com.boshra.githubrepo.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Bitmap.Config
import android.graphics.PorterDuff.Mode
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.widget.ImageView

class CircleImageView : ImageView {
    internal var mContext: Context

    constructor(context: Context) : super(context) {
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        mContext = context
    }

    constructor(
        context: Context, attrs: AttributeSet,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        mContext = context
    }

    override fun setImageBitmap(bm: Bitmap?) {
        if (bm == null) return
        setImageDrawable(
            BitmapDrawable(
                mContext.resources,
                getCircularBitmap(bm)
            )
        )
    }

    /**
     * Creates a circular bitmap and uses whichever dimension is smaller to determine the width
     * <br></br>Also constrains the circle to the leftmost part of the image
     *
     * @param bitmap
     * @return bitmap
     */
    fun getCircularBitmap(bitmap: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(
            bitmap.width,
            bitmap.height, Config.ARGB_8888
        )
        val canvas = Canvas(output)
        var width = bitmap.width
        if (bitmap.width > bitmap.height)
            width = bitmap.height
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, width, width)
        val rectF = RectF(rect)
        val roundPx = (width / 2).toFloat()

        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)

        paint.xfermode = PorterDuffXfermode(Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)

        return output
    }

}