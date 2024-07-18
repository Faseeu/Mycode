package com.example.pythoncodeeditor.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.pythoncodeeditor.R
import com.example.pythoncodeeditor.fragments.CodeEditorFragment
import com.example.pythoncodeeditor.fragments.TerminalFragment
import com.example.pythoncodeeditor.fragments.FileExplorerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class CodeEditorActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_editor)

        setupToolbar()
        setupBottomNavigation()

        if (savedInstanceState == null) {
            loadFragment(CodeEditorFragment())
        }
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun setupBottomNavigation() {
        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_editor -> loadFragment(CodeEditorFragment())
                R.id.nav_terminal -> loadFragment(TerminalFragment())
                R.id.nav_files -> loadFragment(FileExplorerFragment())
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
        return true
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
