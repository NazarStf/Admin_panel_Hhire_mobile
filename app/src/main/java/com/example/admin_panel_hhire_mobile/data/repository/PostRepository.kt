package com.example.admin_panel_hhire_mobile.data.repository

import com.example.admin_panel_hhire_mobile.data.model.Post
import com.example.admin_panel_hhire_mobile.data.model.User
import com.example.admin_panel_hhire_mobile.data.model.PostStatus
import com.example.admin_panel_hhire_mobile.data.model.UserStatus


object PostRepository {

    // ---------- MOCK USERS ----------
    private val users = arrayListOf(
        User(1, "Іван", "ivan@gmail.com", UserStatus.ACTIVE, "avatar1.png", "Майстер"),
        User(2, "Петро", "petro@gmail.com", UserStatus.ACTIVE, "avatar2.png", "Електрик"),
        User(3, "Олег", "oleg@gmail.com", UserStatus.BLOCKED, "avatar3.png", "Сантехнік"),
        User(4, "Марія", "maria@gmail.com", UserStatus.FROZEN, "avatar4.png", "Дизайнер"),
        User(5, "Анна", "anna@gmail.com", UserStatus.ACTIVE, "avatar5.png", "Будівельник"),
        User(6, "Юрій", "yurii@gmail.com", UserStatus.ACTIVE, "avatar6.png", "Механік")
    )

    // ---------- MOCK POSTS ----------
    private val posts = arrayListOf(
        Post(1, 1, "TITLE1","Ремонт пральної машини", System.currentTimeMillis(), PostStatus.ACTIVE, tags = listOf("ремонт", "техніка")),
        Post(3,  2, "TITLE2", "Потрібен електрик", System.currentTimeMillis(), PostStatus.ACTIVE, tags = listOf("електрика")),
        Post(3, 3, "3","Заміна труб", System.currentTimeMillis(), PostStatus.BLOCKED, tags = listOf("сантехніка")),
        Post(4, 4,"4", "Дизайн квартири", System.currentTimeMillis(), PostStatus.ACTIVE, tags = listOf("дизайн")),
        Post(5, 5, "TITLE5","Ремонт даху", System.currentTimeMillis(), PostStatus.ACTIVE, tags = listOf("будівництво")),
        Post(6, 6,"TITLE6", "Ремонт авто", System.currentTimeMillis(), PostStatus.ACTIVE, tags = listOf("авто"))
    )

    // ---------- GET LISTS ----------
    fun getAllUsers(): List<User> = users

    fun getAllPosts(): List<Post> = posts

    // ---------- FIND BY ID ----------
    fun getUserById(id: Int): User? = users.find { it.id == id }

    fun getPostById(id: Int): Post? = posts.find { it.id == id }

    // ---------- MODERATION ----------
    fun blockPost(postId: Int) {
        getPostById(postId)?.status = PostStatus.BLOCKED
    }

    fun unblockPost(postId: Int) {
        getPostById(postId)?.status = PostStatus.ACTIVE
    }

    fun changeUserStatus(userId: Int, status: UserStatus) {
        getUserById(userId)?.status = status
    }

    // ---------- TAGS ----------
    fun getPostsByTag(tag: String): List<Post> {
        return posts.filter { tag in it.tags }
    }

    fun addTag(postId: Int, tag: String) {
        val post = getPostById(postId)
        post?.let {
            val updated = it.tags.toMutableList()
            if (!updated.contains(tag)) {
                updated.add(tag)
                it.tags = updated
            }
        }
    }

    fun removeTag(postId: Int, tag: String) {
        val post = getPostById(postId)
        post?.let {
            val updated = it.tags.toMutableList()
            updated.remove(tag)
            it.tags = updated
        }
    }
}