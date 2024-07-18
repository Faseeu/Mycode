package com.example.pythoncodeeditor.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView
import android.widget.TextView

class TerminalView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {

    private val textView: TextView

    init {
        textView = TextView(context).apply {
            setTextColor(context.getColor(android.R.color.white))
            setBackgroundColor(context.getColor(android.R.color.black))
            textSize = 14f
            typeface = Typeface.MONOSPACE
        }
        addView(textView)
    }

    fun initializeTerminal() {
        textView.text = ""
    }

    fun setOutput(text: String) {
        textView.text = text
        fullScroll(ScrollView.FOCUS_DOWN)
    }

    fun appendOutput(text: String) {
        textView.append(text)
        fullScroll(ScrollView.FOCUS_DOWN)
    }
}
