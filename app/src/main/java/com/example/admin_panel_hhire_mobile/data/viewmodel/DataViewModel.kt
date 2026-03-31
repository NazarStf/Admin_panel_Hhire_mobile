package com.example.admin_panel_hhire_mobile.data.viewmodel

import androidx.lifecycle.ViewModel
import com.example.admin_panel_hhire_mobile.data.model.Post
import com.example.admin_panel_hhire_mobile.data.model.User
import com.example.admin_panel_hhire_mobile.data.repository.PostRepository

class DataViewModel : ViewModel() {
    val originalPosts: List<Post> = PostRepository.getAllPosts().map { it.copy() }
    val originalUsers: List<User> = PostRepository.getAllUsers().map { it.copy() }

    fun resetData() {
        PostRepository.restorePosts(originalPosts.map { it.copy() })
        PostRepository.restoreUsers(originalUsers.map { it.copy() })
    }
}