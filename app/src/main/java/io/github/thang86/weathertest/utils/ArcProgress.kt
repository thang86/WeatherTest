package io.github.thang86.weathertest.utils

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import io.github.thang86.weathertest.R


/**
 *
 *    ArcProgress.kt
 *
 *    Created by ThangTX on 11/08/2021
 *
 */
class ArcProgress : View {

    constructor(context: Context) : super(context, null) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        initCofig(context, attrs)
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initCofig(context, attrs)
        initView()
    }

    private var diameter = 500

    private var centerX = 0f
    private var centerY = 0f

    private var allArcPaint: Paint? = null
    private var progressPaint: Paint? = null
    private var degreePaint: Paint? = null
    private var curSpeedPaint: Paint? = null

    private var bgRect: RectF? = null

    private var progressAnimator: ValueAnimator? = null
    private var mDrawFilter: PaintFlagsDrawFilter? = null
    private var rotateMatrix: Matrix? = null

    private val startAngle = 135f
    private var sweepAngle = 270f
    private var currentAngle = 0f
    private var lastAngle = 0f
    private var maxValues = 60f
    private var curValues = 0f
    private var bgArcWidth = dipToPx(2f).toFloat()
    private var progressWidth = dipToPx(10f).toFloat()
    private val aniSpeed = 1000
    private val longdegree = dipToPx(13f).toFloat()
    private val DEGREE_PROGRESS_DISTANCE = dipToPx(8f)

    private val hintColor = "#676767"
    private val longDegreeColor = "#111111"
    private val bgArcColor = "#BEBEBE"

    // sweepAngle / maxValues
    private var k = 0f


    private fun initCofig(context: Context, attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ColorArcProgressBar)
        sweepAngle = a.getInteger(R.styleable.ColorArcProgressBar_total_engle, 270).toFloat()
        bgArcWidth =
            a.getDimension(R.styleable.ColorArcProgressBar_back_width, dipToPx(2f).toFloat())
        progressWidth =
            a.getDimension(R.styleable.ColorArcProgressBar_front_width, dipToPx(10f).toFloat())
        curValues = a.getFloat(R.styleable.ColorArcProgressBar_current_value, 0f)
        maxValues = a.getFloat(R.styleable.ColorArcProgressBar_max_value, 60f)
        setCurrentValues(curValues)
        setMaxValues(maxValues)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width =
            (2 * longdegree + progressWidth + diameter + 2 * DEGREE_PROGRESS_DISTANCE).toInt()
        val height =
            (2 * longdegree + progressWidth + diameter + 2 * DEGREE_PROGRESS_DISTANCE).toInt()
        setMeasuredDimension(width, height)
    }

    private fun initView() {
        diameter = getScreenWidth() / 4

        bgRect = RectF()
        bgRect!!.top = longdegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE
        bgRect!!.left = longdegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE
        bgRect!!.right = diameter + (longdegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE)
        bgRect!!.bottom = diameter + (longdegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE)

        centerX = (2 * longdegree + progressWidth + diameter + 2 * DEGREE_PROGRESS_DISTANCE) / 2
        centerY = (2 * longdegree + progressWidth + diameter + 2 * DEGREE_PROGRESS_DISTANCE) / 2

        degreePaint = Paint()
        degreePaint!!.color = Color.parseColor(longDegreeColor)

        allArcPaint = Paint()
        allArcPaint!!.isAntiAlias = true
        allArcPaint!!.style = Paint.Style.STROKE
        allArcPaint!!.strokeWidth = bgArcWidth
        allArcPaint!!.color = Color.parseColor(bgArcColor)
        allArcPaint!!.strokeCap = Paint.Cap.ROUND

        progressPaint = Paint()
        progressPaint!!.isAntiAlias = true
        progressPaint!!.style = Paint.Style.STROKE
        progressPaint!!.strokeCap = Paint.Cap.ROUND
        progressPaint!!.strokeWidth = progressWidth
        progressPaint!!.color = Color.WHITE

        curSpeedPaint = Paint()
        curSpeedPaint!!.color = Color.parseColor(hintColor)
        curSpeedPaint!!.textAlign = Paint.Align.CENTER
        mDrawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        rotateMatrix = Matrix()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawFilter = mDrawFilter

        canvas.drawArc(bgRect!!, startAngle, sweepAngle, false, allArcPaint!!)

        rotateMatrix!!.setRotate(130f, centerX, centerY)

        //draw circle
        val drawPaint = Paint()
        drawPaint.color = Color.WHITE
        drawPaint.isAntiAlias = true
        drawPaint.style = Paint.Style.FILL_AND_STROKE
        drawPaint.strokeJoin = Paint.Join.ROUND
        drawPaint.strokeCap = Paint.Cap.ROUND
        canvas.drawCircle(width / 2f, height / 2f, 100f, drawPaint)

        canvas.drawArc(bgRect!!, startAngle, currentAngle, false, progressPaint!!)
        invalidate()
    }

    fun setMaxValues(maxValues: Float) {
        this.maxValues = maxValues
        k = sweepAngle / maxValues
    }

    fun setCurrentValues(currentValues: Float) {
        var currentValues = currentValues
        if (currentValues > maxValues) {
            currentValues = maxValues
        }
        if (currentValues < 0) {
            currentValues = 0f
        }
        curValues = currentValues
        lastAngle = currentAngle
        setAnimation(lastAngle, currentValues * k, aniSpeed)
    }


    private fun setAnimation(last: Float, current: Float, length: Int) {
        progressAnimator = ValueAnimator.ofFloat(last, current)
        progressAnimator?.duration = length.toLong()
        progressAnimator?.setTarget(currentAngle)
        progressAnimator?.addUpdateListener(AnimatorUpdateListener { animation ->
            currentAngle = animation.animatedValue as Float
            curValues = currentAngle / k
        })
        progressAnimator?.start()
    }

    private fun dipToPx(dip: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dip * density + 0.5f * if (dip >= 0) 1 else -1).toInt()
    }

    private fun getScreenWidth(): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }
}