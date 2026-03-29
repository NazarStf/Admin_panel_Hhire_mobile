package com.example.admin_panel_hhire_mobile.ui.posts

import android.widget.ImageView
import com.example.admin_panel_hhire_mobile.R
import com.example.admin_panel_hhire_mobile.data.model.Post

fun updateMarked(post: Post, markButton: ImageView) {
    markButton.setImageResource(if (post.isMarked) R.drawable.bookmark else R.drawable.logo)
}