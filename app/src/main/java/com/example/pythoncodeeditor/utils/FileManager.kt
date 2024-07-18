package com.example.pythoncodeeditor.utils

import android.content.Context
import java.io.File
import java.io.IOException

class FileManager(private val context: Context) {

    private val rootDirectory: File = context.filesDir

    fun getRootDirectory(): File = rootDirectory

    fun listFiles(directory: File = rootDirectory): List<File> {
        return directory.listFiles()?.sortedWith(compareBy({ !it.isDirectory }, { it.name })) ?: emptyList()
    }

    fun createFile(parentDir: File, fileName: String): File {
        val file = File(parentDir, fileName)
        if (!file.createNewFile()) {
            throw IOException("Failed to create file: $fileName")
        }
        return file
    }

    fun createFolder(parentDir: File, folderName: String): File {
        val folder = File(parentDir, folderName)
        if (!folder.mkdir()) {
            throw IOException("Failed to create folder: $folderName")
        }
        return folder
    }

    fun renameFile(file: File, newName: String): File {
        val newFile = File(file.parentFile, newName)
        if (!file.renameTo(newFile)) {
            throw IOException("Failed to rename file: ${file.name} to $newName")
        }
        return newFile
    }

    fun deleteFile(file: File) {
        if (!file.delete()) {
            throw IOException("Failed to delete: ${file.name}")
        }
    }

    fun readFile(file: File): String {
        return file.readText()
    }

    fun writeFile(file: File, content: String) {
        file.writeText(content)
    }

    fun copyFile(source: File, destination: File) {
        source.copyTo(destination, overwrite = true)
    }

    fun moveFile(source: File, destination: File) {
        source.copyTo(destination, overwrite = true)
        deleteFile(source)
    }

    fun getFileExtension(file: File): String {
        return file.extension
    }

    fun isPython(file: File): Boolean {
        return file.extension.lowercase() == "py"
    }
}
