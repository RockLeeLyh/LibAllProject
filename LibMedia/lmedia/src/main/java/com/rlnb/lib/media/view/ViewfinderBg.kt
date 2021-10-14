package com.rlnb.lib.media.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.rlnb.lib.media.R

class ViewfinderBg : View {
    /** 中間位置向上移動距離  */
    private var attrMarginTopSize = 0

    /** 中間位置向下移動距離  */
    private var attrMarginBottomSize = 0

    /** 取景框邊框邊大小  */
    private var attrStrokeSize = 0

    /** 取景框周圍背景  */
    private var attrBackgroundColor = 0

    /** 取景框邊框顏色  */
    private var attrStrokeColor = 0

    /** 取景框圓角大小  */
    private var attrRoundSize = 0

    /** 以X方向為基準，計算出等比例的高度，取值範圍 0 - 1  */
    private var attrRatioX = 0f

    /** 以Y方向為基準，計算出等比例的高度，取值範圍 0 - 1  */
    private var attrRatioY = 0f

    /** 占X或Y方向全屏的百分之多少，取值範圍 0 - 1，決定取景框大小  */
    private var attrRatioSize = 1.0f


    /** 獲取設置取景框底部坐標值回調接口  */
    var mOnStrokeBottomPointListener: ((size:Int)->Unit)? = null


    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttr(attrs)
        //使用setXfermode，必須關掉硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    /**
     * 初始化 attr 参数
     * @param attrs
     */
    private fun initAttr(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.lmediaViewfinderBg)
            attrRatioX = typedArray.getFloat(R.styleable.lmediaViewfinderBg_lmediaVgRatioX, attrRatioX)
            attrRatioY = typedArray.getFloat(R.styleable.lmediaViewfinderBg_lmediaVgRatioY, attrRatioY)
            attrRatioSize = typedArray.getFloat(R.styleable.lmediaViewfinderBg_lmediaVgRatioSize, attrRatioSize)
            attrMarginTopSize = typedArray.getDimensionPixelSize(R.styleable.lmediaViewfinderBg_lmediaVgMarginTopSize, attrMarginTopSize)
            attrMarginBottomSize = typedArray.getDimensionPixelSize(R.styleable.lmediaViewfinderBg_lmediaVgMarginButtonSize, attrMarginBottomSize)
            attrStrokeSize = typedArray.getDimensionPixelSize(R.styleable.lmediaViewfinderBg_lmediaVgStrokeSize, attrStrokeSize)
            attrRoundSize = typedArray.getDimensionPixelSize(R.styleable.lmediaViewfinderBg_lmediaVgRoundSize, attrRoundSize)
            attrBackgroundColor = ContextCompat.getColor(context, android.R.color.transparent)
            attrBackgroundColor = typedArray.getColor(R.styleable.lmediaViewfinderBg_lmediaVgBackgroundColor, attrBackgroundColor)
            attrStrokeColor = ContextCompat.getColor(context, android.R.color.transparent)
            attrStrokeColor = typedArray.getColor(R.styleable.lmediaViewfinderBg_lmediaVgStrokeColor, attrStrokeColor)
            typedArray.recycle()
        }
    }



    /**
     * 動態設置占X或Y方向全屏的百分之多少，取值範圍 0 - 1
     * @param size
     */
    fun setRatioSize(size: Float) {
        attrRatioSize = size
        postInvalidate()
    }

    /**
     * 動態設置取景框的比例，以X方向為基準，計算出等比例的高度
     * @param x
     * @param y
     */
    fun setRatioX(x: Int, y: Int) {
        attrRatioX = y * 1.0f / x
        postInvalidate()
    }


    /**
     * 返回中間位置向上移動距離
     * @return
     */
    fun getAttrMarginTopSize(): Int {
        return attrMarginTopSize
    }


    /**
     * 計算取景框的坐標 RectF
     * @return 返回取景框 RectF
     */
    fun getRectOval(): RectF {
        val mesuredWidth = measuredWidth
        val mesuredHeight = measuredHeight
        var width = mesuredWidth
        var height = mesuredHeight
        // 動態計算比例寬高
        if (attrRatioX != 0f) {
            width = (width * attrRatioSize).toInt()
            height = (width * attrRatioX).toInt()
        } else if (attrRatioY != 0f) {
            height = (height * attrRatioSize).toInt()
            width = (height * attrRatioY).toInt()
        }
        val left = (mesuredWidth - width) / 2
        // 頂部坐標需要減去或加上離中心點距離
        val top = mesuredHeight / 2 - height / 2 - attrMarginTopSize + attrMarginBottomSize
        val right = left + width
        val botton = top + height
        return RectF(left.toFloat(), top.toFloat(), right.toFloat(), botton.toFloat())
    }

    /**
     * 取景框的坐標 RectF,并檢查調取景框底部回調方法
     * @return 返回取景框 RectF
     */
    private fun getRect(): RectF {
        val oval = getRectOval()
        mOnStrokeBottomPointListener?.invoke(oval.bottom.toInt())
        return oval
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val sc = canvas.saveLayer(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), null, Canvas.ALL_SAVE_FLAG)

        // 画出取景框周圍（半透明）背景
        val p = Paint()
        p.style = Paint.Style.FILL
        p.isAntiAlias = true
        p.color = attrBackgroundColor
        canvas.drawRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), p)
        p.color = attrStrokeColor

        //使用xFermode做运算，画出取景框高亮部分
        val oval = getRect()
        val round = (attrStrokeSize / 2).toFloat()
        oval.top += round
        oval.left += round
        oval.right -= round
        oval.bottom -= round
        p.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        canvas.drawRoundRect(oval, attrRoundSize.toFloat(), attrRoundSize.toFloat(), p)

        // 画出高亮边框
        p.xfermode = null
        p.style = Paint.Style.STROKE
        p.strokeWidth = attrStrokeSize.toFloat()
        canvas.drawRoundRect(oval, attrRoundSize.toFloat(), attrRoundSize.toFloat(), p)
        canvas.restoreToCount(sc)
    }


}