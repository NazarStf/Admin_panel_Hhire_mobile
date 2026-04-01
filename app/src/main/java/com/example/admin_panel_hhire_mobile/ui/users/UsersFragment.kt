package com.example.admin_panel_hhire_mobile.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin_panel_hhire_mobile.R
import com.example.admin_panel_hhire_mobile.data.model.Post
import com.example.admin_panel_hhire_mobile.data.model.PostFilter
import com.example.admin_panel_hhire_mobile.data.model.PostStatus
import com.example.admin_panel_hhire_mobile.data.model.User
import com.example.admin_panel_hhire_mobile.data.model.UserFilter
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
    private var currentFilter = UserFilter.ALL

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
            filterUsers(query, currentFilter)
        }

        adapter = UserAdapter(filteredUsers) { user ->
            val fragment = UserDetailsFragment()
            fragment.arguments = Bundle().apply {
                putInt("userId", user.id)
            }
            fragment.enterTransition = com.google.android.material.transition.MaterialSharedAxis(
                com.google.android.material.transition.MaterialSharedAxis.X, true
            )
            fragment.exitTransition = com.google.android.material.transition.MaterialSharedAxis(
                com.google.android.material.transition.MaterialSharedAxis.X, false
            )
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_right,  // enter
                    R.anim.slide_out_left,  // exit
                    R.anim.slide_in_left,   // popEnter (коли повертаємось)
                    R.anim.slide_out_right  // popExit
                )
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        binding.filterIcon.setOnClickListener { view ->
            val popup = PopupMenu(requireContext(), view)
            popup.inflate(R.menu.filter_menu_users)

            popup.setOnMenuItemClickListener { item ->
                currentFilter =  when (item.itemId) {
                    R.id.all -> UserFilter.ALL
                    R.id.marked -> UserFilter.MARKED
                    R.id.blocked -> UserFilter.BLOCKED
                    R.id.active -> UserFilter.ACTIVE
                    R.id.frozen -> UserFilter.FROZEN
                    else -> UserFilter.ALL
                }
                filterUsers(binding.etSearch.text.toString(), currentFilter)
                true
            }

            popup.show()
        }

        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewUsers.adapter = adapter
    }

    private fun filterUsers(query: String, status: UserFilter) {
        allUsers = PostRepository.getAllUsers()
        filteredUsers = allUsers.filter { user ->

            val matchesQuery =
                user.description.contains(query, true) ||
                        user.email.contains(query, true)

            val matchesFilter = when (status) {
                UserFilter.ALL -> true
                UserFilter.ACTIVE -> user.status == UserStatus.ACTIVE
                UserFilter.BLOCKED -> user.status == UserStatus.BLOCKED
                UserFilter.FROZEN -> user.status == UserStatus.FROZEN
                UserFilter.MARKED -> user.isMarked
                else -> true
            }

            matchesQuery && matchesFilter
        }

        adapter.updateList(filteredUsers)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}