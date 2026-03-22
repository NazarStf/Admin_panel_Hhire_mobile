package com.example.admin_panel_hhire_mobile.ui.posts

import com.example.admin_panel_hhire_mobile.data.model.Post
import com.example.admin_panel_hhire_mobile.data.model.PostStatus
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.admin_panel_hhire_mobile.databinding.ItemPostBinding
import com.google.android.material.chip.Chip
import android.content.res.ColorStateList

class PostAdapter(
    private val posts: List<Post>,
    private val onClick: (Post) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]

        holder.binding.tvPostTitle.text = post.title
        holder.binding.tvPostStatus.text = post.status.name
        holder.binding.tvPostDescription.text = post.content
        holder.binding.tvPostStatus.setTextColor(
            if (post.status == PostStatus.ACTIVE) Color.parseColor("#388E3C")
            else Color.parseColor("#D32F2F")
        )
        holder.binding.chipGroupTags.removeAllViews()

        post.tags.forEach { tag ->
            val chip = Chip(holder.itemView.context).apply {
                text = tag
                isClickable = false
                isCheckable = false
                chipBackgroundColor = ColorStateList.valueOf(Color.LTGRAY)
                setTextColor(Color.BLACK)
            }
            holder.binding.chipGroupTags.addView(chip)
        }
        holder.itemView.setOnClickListener { onClick(post) }
    }

    override fun getItemCount(): Int = posts.size
}