package com.example.pythoncodeeditor.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.pythoncodeeditor.R
import com.example.pythoncodeeditor.views.CodeEditorView
import com.example.pythoncodeeditor.utils.CodeQualityTools

class CodeEditorFragment : Fragment() {

    private lateinit var codeEditorView: CodeEditorView
    private lateinit var codeQualityTools: CodeQualityTools

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_code_editor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        codeEditorView = view.findViewById(R.id.code_editor_view)
        codeQualityTools = CodeQualityTools(requireContext())

        initializeCodeEditor()
    }

    private fun initializeCodeEditor() {
        codeEditorView.setLanguage("python")
        codeEditorView.setText("# Write your Python code here")
        codeEditorView.setOnTextChangedListener { text ->
            // Implement real-time linting or other features here
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_code_editor, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_run -> {
                runCode()
                true
            }
            R.id.action_format -> {
                formatCode()
                true
            }
            R.id.action_lint -> {
                lintCode()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun runCode() {
        val code = codeEditorView.getText()
        // Implement code execution logic here
    }

    private fun formatCode() {
        val code = codeEditorView.getText()
        val formattedCode = codeQualityTools.formatCode(code)
        codeEditorView.setText(formattedCode)
    }

    private fun lintCode() {
        val code = codeEditorView.getText()
        val lintResults = codeQualityTools.lintCode(code)
        // Display lint results to the user
    }
}
