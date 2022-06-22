package com.bintangfajarianto.submission2.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.bintangfajarianto.submission2.R

class CustomEmail : AppCompatEditText {

    private lateinit var leadingIcon: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        // Add Leading Icon
        leadingIcon = ContextCompat.getDrawable(context, R.drawable.ic_outline_email) as Drawable
        setLeadingIcon(leadingIcon)

        // Style
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        compoundDrawablePadding = 20

        // Hint
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        setHint(R.string.email)
        setAutofillHints(AUTOFILL_HINT_EMAIL_ADDRESS)

        // On Change Listener
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.isNullOrEmpty() && !Patterns.EMAIL_ADDRESS.matcher(p0).matches())
                    error = context.getString(R.string.email_invalid)
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun setLeadingIcon(icon: Drawable) {
        setDrawables(startOfTheText = icon)
    }

    private fun setDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ){
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }
}