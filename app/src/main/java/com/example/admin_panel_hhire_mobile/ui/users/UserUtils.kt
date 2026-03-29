package com.example.admin_panel_hhire_mobile.ui.users

import android.widget.ImageView
import com.example.admin_panel_hhire_mobile.R
import com.example.admin_panel_hhire_mobile.data.model.User

fun updateMarked(user: User, markButton: ImageView) {
    markButton.setImageResource(if (user.isMarked) R.drawable.bookmark else R.drawable.logo)
}