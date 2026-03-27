package com.example.admin_panel_hhire_mobile.ui.posts

import android.content.res.ColorStateList
import android.graphics.Color
import com.example.admin_panel_hhire_mobile.data.model.PostStatus
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin_panel_hhire_mobile.R
import com.example.admin_panel_hhire_mobile.data.repository.PostRepository
import com.example.admin_panel_hhire_mobile.databinding.FragmentPostDetailsBinding
import com.google.android.material.chip.Chip

class PostDetailsFragment : Fragment() {
    private var _binding: FragmentPostDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val postId = arguments?.getInt("postId") ?: return
        val post = PostRepository.getPostById(postId) ?: return
        val user = PostRepository.getUserById(post.authorId)
        val imageName = post?.photoLinks?.firstOrNull()
        binding.tvTitle.text = post.title
        binding.tvContent.text = post.content
        binding.tvCreated.text = "Created: ${post.createdDate}"
        binding.tvEdited.text = "Edited: ${post.editDate}"
        binding.tvAuthorName.text = user?.name ?: "Unknown"
        binding.tvAuthorId.text = "Author ID: ${post.authorId}"
        binding.chipGroupTags.removeAllViews()
        setStatusBackground(post.status)

        post.tags.forEach { tag ->
            val chip = Chip(requireContext()).apply {
                text = tag
                isClickable = false
                isCheckable = false
            }
            binding.chipGroupTags.addView(chip)
        }
        if (imageName == null) {
            binding.imgPost.visibility = View.GONE
        }else {
            val resId = requireContext().resources.getIdentifier(
                imageName,
                "drawable",
                requireContext().packageName
            )
            binding.imgPost.setImageResource(resId)
            if (resId != 0) {
                binding.imgPost.setImageResource(resId)
                binding.imgPost.visibility = View.VISIBLE
            }else {
                binding.imgPost.setImageResource(R.drawable.ic_image_placeholder)
                binding.imgPost.visibility = View.VISIBLE
            }
        }

    }
    private fun setStatusBackground(status: PostStatus) {
        val bgDrawable = android.graphics.drawable.GradientDrawable().apply {
            cornerRadius = 12f
            setStroke(2, when(status) {
                PostStatus.ACTIVE -> Color.GREEN
                PostStatus.BLOCKED -> Color.RED
                PostStatus.MARKED -> Color.YELLOW
                PostStatus.FROZEN -> Color.LTGRAY
            })
            setColor(when(status) {
                PostStatus.ACTIVE -> Color.WHITE
                PostStatus.BLOCKED -> Color.RED
                PostStatus.MARKED -> Color.YELLOW
                PostStatus.FROZEN -> Color.LTGRAY
            })
        }
        binding.tvStatus.background = bgDrawable
        binding.tvStatus.text = status.name
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}