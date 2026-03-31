package com.example.admin_panel_hhire_mobile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.admin_panel_hhire_mobile.data.viewmodel.DataViewModel
import com.example.admin_panel_hhire_mobile.ui.posts.PostsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.admin_panel_hhire_mobile.ui.users.UsersFragment
import com.example.admin_panel_hhire_mobile.ui.settings.SettingsFragment
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val viewModel: DataViewModel by viewModels()
        val postsFragment = PostsFragment()
        val usersFragment = UsersFragment()
        val settingsFragment = SettingsFragment()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PostsFragment())
                .commit()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            val selectedFragment = when(item.itemId) {
                R.id.nav_posts -> postsFragment
                R.id.nav_users -> usersFragment
                R.id.nav_settings -> settingsFragment
                else -> postsFragment
            }
            showFragment(selectedFragment)

            true
        }
    }
    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}