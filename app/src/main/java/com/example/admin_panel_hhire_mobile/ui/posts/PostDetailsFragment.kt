package com.example.admin_panel_hhire_mobile.ui.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin_panel_hhire_mobile.R
import com.example.admin_panel_hhire_mobile.data.repository.PostRepository
import com.example.admin_panel_hhire_mobile.databinding.FragmentPostDetailsBinding

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
        binding.tvTitle.text = post.title
        binding.tvContent.text = post.content
        binding.tvStatus.text = post.status.name
        binding.tvCreated.text = "Created: ${post.createdDate}"
        binding.tvEdited.text = "Edited: ${post.editDate}"

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}