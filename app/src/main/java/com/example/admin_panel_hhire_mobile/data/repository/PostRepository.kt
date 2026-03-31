package com.example.admin_panel_hhire_mobile.data.repository

import com.example.admin_panel_hhire_mobile.data.model.Post
import com.example.admin_panel_hhire_mobile.data.model.PostStatus
import com.example.admin_panel_hhire_mobile.data.model.User
import com.example.admin_panel_hhire_mobile.data.model.UserStatus
import kotlin.collections.List

object PostRepository {

    // ---------- MOCK USERS ----------
    private val users = arrayListOf(
        User(1, "Іван", "ivan@gmail.com", UserStatus.ACTIVE, "avatar1.png", "Люблю подорожі ✈️"),
        User(2, "Петро", "petro@gmail.com", UserStatus.ACTIVE, "avatar2.png", "Коджу і п’ю каву ☕"),
        User(3, "Олег", "oleg@gmail.com", UserStatus.BLOCKED, "avatar3.png", "Фотограф 📸"),
        User(4, "Марія", "maria@gmail.com", UserStatus.FROZEN, "avatar4.png", "UI/UX дизайнер 🎨"),
        User(5, "Анна", "anna@gmail.com", UserStatus.ACTIVE, "avatar5.png", "Фітнес і стиль 💪"),
        User(6, "Юрій", "yurii@gmail.com", UserStatus.ACTIVE, "avatar6.png", "Авто і швидкість 🚗")
    )

    // ---------- MOCK POSTS ----------
    private val posts = arrayListOf(

        Post(
            1, 1,
            "Ранок ☀️",
            "Сьогодні прокинувся дуже рано і пішов гуляти містом. Атмосфера топ!",
            System.currentTimeMillis(),
            PostStatus.ACTIVE,
            System.currentTimeMillis(),
            photoLinks = listOf("test"),
            tags = listOf("ранок", "життя", "місто"),
            isMarked = true
        ),

        Post(
            2, 2,
            "Код і кава",
            "Без кави сьогодні взагалі нічого не працює 😅",
            System.currentTimeMillis(),
            PostStatus.ACTIVE,
            System.currentTimeMillis(),
            tags = listOf("код", "кава", "жиза")
        ),

        Post(
            3, 3,
            "Новий кадр",
            "Зловив класний момент на заході сонця 🌇",
            System.currentTimeMillis(),
            PostStatus.BLOCKED,
            System.currentTimeMillis(),
            tags = listOf("фото", "sunset")
        ),

        Post(
            4, 4,
            "UI натхнення",
            "Знайшла крутий дизайн для мобільного додатку 😍",
            System.currentTimeMillis(),
            PostStatus.ACTIVE,
            System.currentTimeMillis(),
            tags = listOf("design", "ui", "inspiration")
        ),

        Post(
            5, 5,
            "Тренування",
            "Сьогодні був жорсткий тренінг, але результат того вартий 💪",
            System.currentTimeMillis(),
            PostStatus.ACTIVE,
            System.currentTimeMillis(),
            photoLinks = listOf("@drawable/test"),
            tags = listOf("спорт", "fitness", "мотивація", "жесть")
        ),

        Post(
            6, 6,
            "Нічна поїздка",
            "Їхати нічним містом — окремий вайб 🌃",
            System.currentTimeMillis(),
            PostStatus.ACTIVE,
            System.currentTimeMillis(),
            tags = listOf("ніч", "місто", "drive")
        )
    )
    fun clearAllData(){
        users.clear()
        posts.clear()
    }

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