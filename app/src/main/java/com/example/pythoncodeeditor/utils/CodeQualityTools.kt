package com.example.pythoncodeeditor.utils

import android.content.Context

class CodeQualityTools(private val context: Context) {

    private val pythonEnvironment = PythonEnvironment(context)

    fun formatCode(code: String): String {
        val formattingCommand = """
            import black
            print(black.format_str($code, mode=black.FileMode()))
        """.trimIndent()

        var formattedCode = code
        pythonEnvironment.executeCommand(formattingCommand) { output ->
            formattedCode = output
        }
        return formattedCode
    }

    fun lintCode(code: String): List<String> {
        val lintingCommand = """
            import pylint.lint
            from io import StringIO
            import sys

            original_stdout = sys.stdout
            sys.stdout = StringIO()

            pylint.lint.Run(['--from-stdin'], do_exit=False)
            output = sys.stdout.getvalue()
            sys.stdout = original_stdout
            print(output)
        """.trimIndent()

        val lintResults = mutableListOf<String>()
        pythonEnvironment.executeCommand(lintingCommand) { output ->
            lintResults.addAll(output.split("\n"))
        }
        return lintResults
    }
}
