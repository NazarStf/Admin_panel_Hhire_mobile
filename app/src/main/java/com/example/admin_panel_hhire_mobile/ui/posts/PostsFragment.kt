package com.example.admin_panel_hhire_mobile.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.admin_panel_hhire_mobile.databinding.FragmentPostsBinding
import com.example.admin_panel_hhire_mobile.data.model.Post
import com.example.admin_panel_hhire_mobile.data.repository.PostRepository
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin_panel_hhire_mobile.R
import com.example.admin_panel_hhire_mobile.data.model.PostStatus
import androidx.core.widget.addTextChangedListener

class PostsFragment : Fragment() {
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PostAdapter
    private var allPosts = listOf<Post>()
    private var filteredPosts = listOf<Post>()
    private var currentStatus = "ALL"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val posts: List<Post> = PostRepository.getAllPosts()
        allPosts = PostRepository.getAllPosts()
        filteredPosts = allPosts
        binding.etSearch.addTextChangedListener {
            val query = it.toString()
            filterPosts(query, currentStatus)
        }

        val adapter = PostAdapter(filteredPosts) { post ->
            val fragment = PostDetailsFragment()

            val bundle = Bundle()
            bundle.putInt("postId", post.id)

            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        binding.recyclerViewPosts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPosts.adapter = adapter

    }
    private fun filterPosts(query: String, status: String) {

        filteredPosts = allPosts.filter { post ->

            val author = PostRepository.getUserById(post.authorId)

            val matchesQuery =
                post.content.contains(query, true) ||
                        (author?.name?.contains(query, true) == true)

            val matchesStatus = when (status) {
                "ALL" -> true
                "ACTIVE" -> post.status == PostStatus.ACTIVE
                "BLOCKED" -> post.status == PostStatus.BLOCKED
                "MARKED" -> post.isMarked
                else -> true
            }

            matchesQuery && matchesStatus
        }

        adapter.updateList(filteredPosts)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
