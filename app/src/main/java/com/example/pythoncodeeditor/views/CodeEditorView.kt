package com.example.pythoncodeeditor.views

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.langs.python.PythonLanguage

class CodeEditorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CodeEditor(context, attrs, defStyleAttr) {

    private var onTextChangedListener: ((String) -> Unit)? = null

    init {
        colorScheme = EditorColorScheme().apply {
            // Customize color scheme here
        }
        typefaceText = Typeface.MONOSPACE
        nonPrintablePaintingFlags = CodeEditor.FLAG_DRAW_WHITESPACE_LEADING or CodeEditor.FLAG_DRAW_LINE_SEPARATOR
        setEditorLanguage(PythonLanguage())
        setTextSize(14f)
        
        addTextChangedListener { text ->
            onTextChangedListener?.invoke(text.toString())
        }
    }

    fun setOnTextChangedListener(listener: (String) -> Unit) {
        onTextChangedListener = listener
    }

    fun setText(text: String) {
        this.setText(text)
    }

    fun getText(): String {
        return this.text.toString()
    }
}
