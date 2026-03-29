package com.example.admin_panel_hhire_mobile.ui.users

import android.content.Context
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.admin_panel_hhire_mobile.R
import com.example.admin_panel_hhire_mobile.data.model.User
import com.example.admin_panel_hhire_mobile.data.model.UserStatus

fun updateMarked(user: User, markButton: ImageView) {
    markButton.setImageResource(if (user.isMarked) R.drawable.bookmark else R.drawable.logo)
}
fun updateStatus(user: User, statusTextView: TextView, blockButton: Button?, context: Context) {
    statusTextView.text = user.status.name
    blockButton?.text = if (user.status == UserStatus.BLOCKED) "Unblock" else "Block"
    setStatusBackground(user.status, statusTextView, context)
}
fun setStatusBackground(status: UserStatus, statusTextView: TextView, context: Context) {
    val green = context.getColor(R.color.green)
    val red = context.getColor(R.color.red)
    val blue = context.getColor(R.color.blue)
    val bgDrawable = android.graphics.drawable.GradientDrawable().apply {
        cornerRadius = 360f
        setColor(when(status) {
            UserStatus.ACTIVE -> green
            UserStatus.BLOCKED -> red
            UserStatus.FROZEN -> blue
        })
    }
    statusTextView.background = bgDrawable
    statusTextView.text = status.name
}
