package com.example.admin_panel_hhire_mobile.ui.posts

import com.example.admin_panel_hhire_mobile.data.model.Post
import com.example.admin_panel_hhire_mobile.data.model.PostStatus
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.admin_panel_hhire_mobile.databinding.ItemPostBinding
import com.example.admin_panel_hhire_mobile.R
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.flexbox.FlexboxLayout

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

        holder.binding.tvPostDescription.text = post.content
        holder.binding.tvPostStatus.text = post.status.name
        holder.binding.tvPostTitle.text = post.title

        holder.binding.tvPostStatus.setTextColor(
            if (post.status == PostStatus.ACTIVE)
                Color.parseColor("#388E3C")
            else
                Color.parseColor("#D32F2F")
        )

        holder.binding.flexboxTags.removeAllViews()
        post.tags.forEach { tag ->
            val tagView = TextView(holder.itemView.context).apply {
                text = tag
                setTextColor(Color.BLACK)
                textSize = 12f
                setPadding(24, 8, 24, 8) // відступи всередині овалу
                background = ContextCompat.getDrawable(context, R.drawable.tag_background)
            }
            val layoutParams = FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(8, 4, 8, 4) // відстань між тегами
            }
            tagView.layoutParams = layoutParams
            holder.binding.flexboxTags.addView(tagView)
        }

        holder.itemView.setOnClickListener { onClick(post) }
    }

    override fun getItemCount(): Int = posts.size
}