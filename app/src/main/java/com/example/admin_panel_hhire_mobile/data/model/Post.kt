package com.example.admin_panel_hhire_mobile.data.model

data class Post(
    val id: Int,
    val authorId: Int,
    var content: String,
    val createdDate: Long,
    var status: PostStatus,
    var editDate: Long? = null,
    var photoLinks: List<String> = emptyList(),
    var tags: List<String> = emptyList()
)