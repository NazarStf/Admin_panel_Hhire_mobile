package com.example.admin_panel_hhire_mobile.data.model

data class User(
    val id: Int,
    var name: String,
    var email: String,
    var status: UserStatus,
    val avatar: String,
    var description: String
)