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
import com.example.admin_panel_hhire_mobile.data.repository.PostRepository

class PostAdapter(
    private var posts: List<Post>,
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
        holder.binding.chipGroupTags.removeAllViews()
        updateMarked(post, holder.binding.btnMark)
        updateStatus(post, holder.binding.tvPostStatus, holder.binding.btnBlock, holder.itemView.context)
        post.tags.forEach { tag ->
            val chip = Chip(holder.itemView.context).apply {
                text = tag
                isClickable = false
                isCheckable = false
                chipBackgroundColor = ColorStateList.valueOf(Color.LTGRAY)
                chipStrokeColor = ColorStateList.valueOf(Color.LTGRAY)
                chipCornerRadius = 50f
                setTextColor(Color.BLACK)
            }
            holder.binding.chipGroupTags.addView(chip)
        }
        holder.itemView.setOnClickListener { onClick(post) }

        holder.binding.btnBlock.setOnClickListener {
            val currentPost = PostRepository.getPostById(post.id) ?: return@setOnClickListener
            if (currentPost.status == PostStatus.BLOCKED) {
                PostRepository.unblockPost(post.id)
            } else {
                PostRepository.blockPost(post.id)
            }
            updateStatus(currentPost, holder.binding.tvPostStatus, holder.binding.btnBlock, holder.itemView.context)
        }

        holder.binding.btnMark.setOnClickListener {
            val currentPost = PostRepository.getPostById(post.id) ?: return@setOnClickListener
            currentPost.isMarked = !(currentPost.isMarked)
            updateMarked(currentPost, holder.binding.btnMark)
        }
    }
    fun updateList(newList: List<Post>) {
        posts = newList
        notifyItemRangeChanged(0, posts.size)
    }

    override fun getItemCount(): Int = posts.size
}