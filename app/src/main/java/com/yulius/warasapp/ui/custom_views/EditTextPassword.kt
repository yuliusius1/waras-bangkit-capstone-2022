package com.yulius.warasapp.ui.custom_views

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.yulius.warasapp.R

class EditTextPassword : AppCompatEditText, View.OnTouchListener {
    private lateinit var togglePasswordTrue: Drawable
    private lateinit var keyIcon: Drawable
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        togglePasswordTrue = ContextCompat.getDrawable(context, R.drawable.ic_baseline_remove_red_eye_24) as Drawable
        keyIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_lock_24) as Drawable
        setOnTouchListener(this)
        setButtonDrawables()
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable) {
                if (s.toString().length < 6) validateError()
            }
        })
    }


    private fun validateError() {
        error = context.getString(R.string.validate_password)
    }

    private fun setButtonDrawables(
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ){
        setCompoundDrawablesWithIntrinsicBounds(
            keyIcon,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val eyeButtonStart: Float
            val eyeButtonEnd: Float
            var isEyeButtonClicked = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                eyeButtonEnd = (togglePasswordTrue.intrinsicWidth + paddingStart).toFloat()
                if (event.x < eyeButtonEnd) isEyeButtonClicked = true
            } else {
                eyeButtonStart = (width - paddingEnd - togglePasswordTrue.intrinsicWidth).toFloat()
                if (event.x > eyeButtonStart) isEyeButtonClicked = true
            }
            if(isEyeButtonClicked){
                return when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (transformationMethod.equals(HideReturnsTransformationMethod.getInstance())) {
                            transformationMethod =
                                PasswordTransformationMethod.getInstance()
                            togglePasswordTrue =
                                ContextCompat.getDrawable(context, R.drawable.ic_eye_off) as Drawable
                            setToggleIcon()
                        } else {
                            transformationMethod =
                                HideReturnsTransformationMethod.getInstance()
                            togglePasswordTrue =
                                ContextCompat.getDrawable(context, R.drawable.ic_baseline_remove_red_eye_24) as Drawable
                            setToggleIcon()
                        }
                        true
                    }
                    else -> false
                }
            } else return false
        }
        return false
    }

    private fun setToggleIcon() {
        setButtonDrawables(endOfTheText = togglePasswordTrue)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        setToggleIcon()
    }
}