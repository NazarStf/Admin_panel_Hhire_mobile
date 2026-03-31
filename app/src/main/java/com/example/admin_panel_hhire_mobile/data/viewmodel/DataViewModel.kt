package com.example.admin_panel_hhire_mobile.data.viewmodel

import androidx.lifecycle.ViewModel
import com.example.admin_panel_hhire_mobile.data.model.Post
import com.example.admin_panel_hhire_mobile.data.model.User
import com.example.admin_panel_hhire_mobile.data.repository.PostRepository

class DataViewModel : ViewModel() {
    private val _originalUsers = mutableListOf<User>()
    private val _originalPosts = mutableListOf<Post>()

    init {
        // Створюємо “початкову” копію відразу при створенні ViewModel
        _originalUsers.addAll(PostRepository.getAllUsers().map { it.copy() })
        _originalPosts.addAll(PostRepository.getAllPosts().map { it.copy() })
    }

    fun resetData() {
        PostRepository.restorePosts(_originalPosts.map { it.copy() })
        PostRepository.restoreUsers(_originalUsers.map { it.copy() })
    }
}