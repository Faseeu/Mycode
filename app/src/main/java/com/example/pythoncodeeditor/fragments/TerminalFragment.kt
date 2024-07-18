package com.example.pythoncodeeditor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.pythoncodeeditor.R
import com.example.pythoncodeeditor.views.TerminalView
import com.example.pythoncodeeditor.utils.PythonEnvironment

class TerminalFragment : Fragment() {

    private lateinit var terminalView: TerminalView
    private lateinit var commandInput: EditText
    private lateinit var executeButton: Button
    private lateinit var pythonEnvironment: PythonEnvironment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_terminal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        terminalView = view.findViewById(R.id.terminal_view)
        commandInput = view.findViewById(R.id.command_input)
        executeButton = view.findViewById(R.id.execute_button)
        pythonEnvironment = PythonEnvironment(requireContext())

        initializeTerminal()
        setupExecuteButton()
    }

    private fun initializeTerminal() {
        terminalView.initializeTerminal()
        terminalView.setOutput("Python ${pythonEnvironment.getPythonVersion()}\n>>> ")
    }

    private fun setupExecuteButton() {
        executeButton.setOnClickListener {
            val command = commandInput.text.toString()
            executeCommand(command)
            commandInput.text.clear()
        }
    }

    private fun executeCommand(command: String) {
        pythonEnvironment.executeCommand(command) { output ->
            activity?.runOnUiThread {
                terminalView.appendOutput(output)
                terminalView.appendOutput("\n>>> ")
            }
        }
    }
}
