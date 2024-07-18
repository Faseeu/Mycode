package com.example.pythoncodeeditor.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pythoncodeeditor.R
import com.example.pythoncodeeditor.utils.FileManager
import com.example.pythoncodeeditor.adapters.FileListAdapter
import java.io.File

class FileExplorerFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fileManager: FileManager
    private lateinit var adapter: FileListAdapter
    private lateinit var currentPath: File

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_file_explorer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.file_list)
        fileManager = FileManager(requireContext())
        currentPath = fileManager.getRootDirectory()

        setupFileList()
        updateTitle()
    }

    private fun setupFileList() {
        adapter = FileListAdapter(fileManager.listFiles(currentPath)) { file ->
            when {
                file.isDirectory -> navigateToDirectory(file)
                file.extension == "py" -> openPythonFile(file)
                else -> Toast.makeText(context, "Unsupported file type", Toast.LENGTH_SHORT).show()
            }
        }
        adapter.setOnItemLongClickListener { file ->
            showFileOptions(file)
            true
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun navigateToDirectory(directory: File) {
        currentPath = directory
        updateFileList()
        updateTitle()
    }

    private fun openPythonFile(file: File) {
        // TODO: Implement opening the file in CodeEditorFragment
        Toast.makeText(context, "Opening ${file.name}", Toast.LENGTH_SHORT).show()
    }

    private fun updateFileList() {
        adapter.updateFiles(fileManager.listFiles(currentPath))
    }

    private fun updateTitle() {
        activity?.title = currentPath.name
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_file_explorer, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_new_file -> {
                showCreateFileDialog()
                true
            }
            R.id.action_new_folder -> {
                showCreateFolderDialog()
                true
            }
            R.id.action_go_up -> {
                navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showCreateFileDialog() {
        val input = EditText(context)
        AlertDialog.Builder(context)
            .setTitle("Create new file")
            .setView(input)
            .setPositiveButton("Create") { _, _ ->
                val fileName = input.text.toString()
                if (fileName.isNotEmpty()) {
                    createNewFile(fileName)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showCreateFolderDialog() {
        val input = EditText(context)
        AlertDialog.Builder(context)
            .setTitle("Create new folder")
            .setView(input)
            .setPositiveButton("Create") { _, _ ->
                val folderName = input.text.toString()
                if (folderName.isNotEmpty()) {
                    createNewFolder(folderName)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun createNewFile(fileName: String) {
        try {
            val newFile = fileManager.createFile(currentPath, fileName)
            updateFileList()
            Toast.makeText(context, "File created: ${newFile.name}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error creating file: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createNewFolder(folderName: String) {
        try {
            val newFolder = fileManager.createFolder(currentPath, folderName)
            updateFileList()
            Toast.makeText(context, "Folder created: ${newFolder.name}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error creating folder: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateUp() {
        val parentFile = currentPath.parentFile
        if (parentFile != null && parentFile.canRead()) {
            navigateToDirectory(parentFile)
        } else {
            Toast.makeText(context, "Cannot go up from here", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showFileOptions(file: File) {
        val options = arrayOf("Rename", "Delete", "Copy", "Move")
        AlertDialog.Builder(context)
            .setTitle(file.name)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showRenameDialog(file)
                    1 -> showDeleteConfirmation(file)
                    2 -> copyFile(file)
                    3 -> moveFile(file)
                }
            }
            .show()
    }

    private fun showRenameDialog(file: File) {
        val input = EditText(context).apply { setText(file.name) }
        AlertDialog.Builder(context)
            .setTitle("Rename ${file.name}")
            .setView(input)
            .setPositiveButton("Rename") { _, _ ->
                val newName = input.text.toString()
                if (newName.isNotEmpty() && newName != file.name) {
                    renameFile(file, newName)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun renameFile(file: File, newName: String) {
        try {
            fileManager.renameFile(file, newName)
            updateFileList()
            Toast.makeText(context, "File renamed to $newName", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error renaming file: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteConfirmation(file: File) {
        AlertDialog.Builder(context)
            .setTitle("Delete ${file.name}")
            .setMessage("Are you sure you want to delete this ${if (file.isDirectory) "folder" else "file"}?")
            .setPositiveButton("Delete") { _, _ -> deleteFile(file) }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteFile(file: File) {
        try {
            fileManager.deleteFile(file)
            updateFileList()
            Toast.makeText(context, "${file.name} deleted", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error deleting ${file.name}: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun copyFile(file: File) {
        // TODO: Implement copy functionality
        Toast.makeText(context, "Copy functionality not implemented yet", Toast.LENGTH_SHORT).show()
    }

    private fun moveFile(file: File) {
        // TODO: Implement move functionality
        Toast.makeText(context, "Move functionality not implemented yet", Toast.LENGTH_SHORT).show()
    }
}
