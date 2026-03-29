package com.example.admin_panel_hhire_mobile.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin_panel_hhire_mobile.R
import com.example.admin_panel_hhire_mobile.data.repository.PostRepository
import com.example.admin_panel_hhire_mobile.ui.posts.PostAdapter
import com.example.admin_panel_hhire_mobile.ui.posts.PostDetailsFragment
import androidx.fragment.app.Fragment
import com.example.admin_panel_hhire_mobile.data.model.UserStatus
import com.example.admin_panel_hhire_mobile.databinding.FragmentUserDetailsBinding

class UserDetailsFragment : Fragment() {
    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getInt("userId") ?: return
        val user = PostRepository.getUserById(userId) ?: return

        binding.tvName.text = user.name
        binding.tvEmail.text = user.email
        updateMarked(user, binding.btnMark)
        updateStatus(user, binding.tvStatus,binding.btnBlock ,binding.btnFreeze,requireContext())
        binding.btnMark.setOnClickListener {
            val currentUser = PostRepository.getUserById(user.id) ?: return@setOnClickListener
            currentUser.isMarked = !(currentUser.isMarked)
            updateMarked(currentUser, binding.btnMark)
        }
        binding.btnBlock.setOnClickListener {
            val currentUser = PostRepository.getUserById(user.id) ?: return@setOnClickListener

            if (currentUser.status == UserStatus.BLOCKED) {
                PostRepository.changeUserStatus(user.id,UserStatus.ACTIVE)
            } else {
                PostRepository.changeUserStatus(user.id, UserStatus.BLOCKED)
            }
            updateStatus(user, binding.tvStatus, binding.btnBlock,binding.btnFreeze, requireContext())
        }
        binding.btnFreeze.setOnClickListener {
            val currentUser = PostRepository.getUserById(user.id) ?: return@setOnClickListener

            if (currentUser.status == UserStatus.FROZEN) {
                PostRepository.changeUserStatus(user.id,UserStatus.ACTIVE)
            } else {
                PostRepository.changeUserStatus(user.id, UserStatus.FROZEN)
            }
            updateStatus(user, binding.tvStatus, binding.btnBlock,binding.btnFreeze, requireContext())
        }

        val posts = PostRepository.getAllPosts().filter { it.authorId == userId }

        val adapter = PostAdapter(posts) { post ->
            val fragment = PostDetailsFragment()
            fragment.arguments = Bundle().apply {
                putInt("postId", post.id)
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerViewPosts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPosts.adapter = adapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}