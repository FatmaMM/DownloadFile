package com.example.dowanloadfile.loading_button

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.dowanloadfile.R
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0

    private var loadingSize = 0f
    private var selectedRB = 0

    private val valueAnimator = ValueAnimator()

    private var buttonState: ButtonState by Delegates.observable(ButtonState.Completed) { p, old, new ->
        when (new) {
            is ButtonState.Clicked -> {
                buttonState = ButtonState.Loading
            }
            is ButtonState.Loading -> {
                valueAnimator.setFloatValues(0f, widthSize.toFloat())
                valueAnimator.duration = 1000
                valueAnimator.addUpdateListener {
                    loadingSize = it.animatedValue as Float
                    if (loadingSize == widthSize.toFloat()) {
                        buttonState = ButtonState.Completed
                    }
                    invalidate()
                }
                valueAnimator.start()
            }
            is ButtonState.Completed -> {
                loadingSize = 0f
            }
        }
    }


    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.LoadingState, 0, 0).apply {
            try {
                var stateValue = getInteger(R.styleable.LoadingState_state, 0)
                buttonState = when (stateValue) {
                    0 -> ButtonState.Clicked
                    1 -> ButtonState.Loading
                    2 -> ButtonState.Completed
                    else -> ButtonState.Completed
                }
            } finally {
                recycle()
            }
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.color = ContextCompat.getColor(context, R.color.teal_700)
        canvas?.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), paint)
        paint.color = ContextCompat.getColor(context,(R.color.teal_200))
        canvas?.drawRect(0f, 0f, loadingSize, heightSize.toFloat(), paint)
        paint.color = Color.WHITE
        paint.textSize = 60f

        when(buttonState) {
            is ButtonState.Completed -> {
                canvas?.drawText("Download", (widthSize/2f) - 130, heightSize/2f + 20, paint)
            }
            is ButtonState.Loading -> {
                canvas?.drawText("We are loading", (widthSize/2f) - 200, heightSize/2f + 20, paint)
            }
           is ButtonState.Clicked->{
           }
        }

        val radius = 90f
        val woffset = 200
        val hoffset = 35

        paint.color = Color.YELLOW
        canvas?.drawArc(
            widthSize/2f + woffset,
            heightSize/2f - hoffset,
            widthSize/2f + woffset + radius,
            heightSize/2f - hoffset + radius,
            0f,
            360f - (widthSize - loadingSize) / widthSize * 360f, true, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(MeasureSpec.getSize(w), heightMeasureSpec, 0)
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    override fun performClick(): Boolean {
        super.performClick()
        if (selectedRB == 0) {
            Toast.makeText(context, "Choose which file to download", Toast.LENGTH_SHORT).show()
            return true
        }else{
            buttonState = ButtonState.Clicked
        }
        return true
    }

    fun setSelectedRB(button: Int) {
        selectedRB = button
    }
}