package com.example.admin_panel_hhire_mobile.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin_panel_hhire_mobile.R
import com.example.admin_panel_hhire_mobile.data.model.Post
import com.example.admin_panel_hhire_mobile.data.model.PostStatus
import com.example.admin_panel_hhire_mobile.data.model.User
import com.example.admin_panel_hhire_mobile.data.model.UserStatus
import com.example.admin_panel_hhire_mobile.data.repository.PostRepository
import com.example.admin_panel_hhire_mobile.databinding.FragmentUsersBinding
import com.example.admin_panel_hhire_mobile.ui.posts.PostAdapter

class UsersFragment : Fragment() {
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: UserAdapter
    private var allUsers = listOf<User>()
    private var filteredUsers = listOf<User>()
    private var currentStatus = "ALL"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allUsers = PostRepository.getAllUsers()
        filteredUsers = allUsers
        binding.etSearch.addTextChangedListener {
            val query = it.toString()
            filterUsers(query, currentStatus)
        }

        adapter = UserAdapter(filteredUsers) { user ->
            val fragment = UserDetailsFragment()
            fragment.arguments = Bundle().apply {
                putInt("userId", user.id)
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewUsers.adapter = adapter
    }

    private fun filterUsers(query: String, status: String) {

        filteredUsers = allUsers.filter { user ->

            val matchesQuery =
                user.email.contains(query, true) ||
                        (user.name.contains(query, true) == true)
            val matchesStatus = when (status) {
                "ALL" -> true
                "ACTIVE" -> user.status == UserStatus.ACTIVE
                "BLOCKED" -> user.status == UserStatus.BLOCKED
                "FROZEN" -> user.status == UserStatus.BLOCKED
                "MARKED" -> user.isMarked
                else -> true
            }

            matchesQuery && matchesStatus
        }

        adapter.updateList(filteredUsers)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}