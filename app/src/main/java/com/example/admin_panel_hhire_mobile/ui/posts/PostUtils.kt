package com.example.admin_panel_hhire_mobile.ui.posts

import android.content.Context
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.admin_panel_hhire_mobile.R
import com.example.admin_panel_hhire_mobile.data.model.Post
import com.example.admin_panel_hhire_mobile.data.model.PostStatus


fun updateMarked(post: Post, markButton: ImageView) {
    markButton.setImageResource(if (post.isMarked) R.drawable.bookmark_filled else R.drawable.bookmark)
}
fun setStatusBackground(status: PostStatus, statusTextView: TextView, context: Context) {
    val green = context.getColor(R.color.green)
    val red = context.getColor(R.color.red)
    val bgDrawable = android.graphics.drawable.GradientDrawable().apply {
        cornerRadius = 360f
        setColor(when(status) {
            PostStatus.ACTIVE -> green
            PostStatus.BLOCKED -> red
        })
    }
    statusTextView.background = bgDrawable
    statusTextView.text = status.name
}

fun updateStatus(post: Post, statusTextView: TextView, blockButton: Button, context: Context) {
    statusTextView.text = post.status.name
    blockButton.text = if (post.status == PostStatus.BLOCKED) "Unblock" else "Block"
    setStatusBackground(post.status, statusTextView, context)
}