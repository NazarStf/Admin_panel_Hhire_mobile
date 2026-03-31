package com.example.admin_panel_hhire_mobile.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.example.admin_panel_hhire_mobile.databinding.FragmentPostsBinding
import com.example.admin_panel_hhire_mobile.data.model.Post
import com.example.admin_panel_hhire_mobile.data.repository.PostRepository
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin_panel_hhire_mobile.R
import com.example.admin_panel_hhire_mobile.data.model.PostStatus
import androidx.core.widget.addTextChangedListener
import com.example.admin_panel_hhire_mobile.data.model.PostFilter

class PostsFragment : Fragment() {
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PostAdapter
    private var allPosts = listOf<Post>()
    private var filteredPosts = listOf<Post>()
    private var currentFilter = PostFilter.ALL


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allPosts = PostRepository.getAllPosts()
        filteredPosts = allPosts
        binding.etSearch.addTextChangedListener {
            val query = it.toString()
            filterPosts(query, currentFilter)
        }

        adapter = PostAdapter(filteredPosts) { post ->
            val fragment = PostDetailsFragment()

            val bundle = Bundle()
            bundle.putInt("postId", post.id)

            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        binding.filterIcon.setOnClickListener { view ->
            val popup = PopupMenu(requireContext(), view)
            popup.inflate(R.menu.filter_menu)

            popup.setOnMenuItemClickListener { item ->
                currentFilter =  when (item.itemId) {
                    R.id.all -> PostFilter.ALL
                    R.id.marked -> PostFilter.MARKED
                    R.id.blocked -> PostFilter.BLOCKED
                    R.id.active -> PostFilter.ACTIVE
                    else -> PostFilter.ALL
                }
                filterPosts(binding.etSearch.text.toString(), currentFilter)
                true
            }

            popup.show()
        }
        binding.recyclerViewPosts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPosts.adapter = adapter

    }
    private fun filterPosts(query: String, status: PostFilter) {

        filteredPosts = allPosts.filter { post ->

            val author = PostRepository.getUserById(post.authorId)

            val matchesQuery =
                post.content.contains(query, true) ||
                        (author?.name?.contains(query, true) == true)

            val matchesFilter = when (status) {
                PostFilter.ALL -> true
                PostFilter.ACTIVE -> post.status == PostStatus.ACTIVE
                PostFilter.BLOCKED -> post.status == PostStatus.BLOCKED
                PostFilter.MARKED -> post.isMarked
                else -> true
            }

            matchesQuery && matchesFilter
        }

        adapter.updateList(filteredPosts)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
