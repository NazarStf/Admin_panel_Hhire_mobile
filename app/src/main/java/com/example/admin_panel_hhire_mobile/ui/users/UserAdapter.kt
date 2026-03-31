package com.example.admin_panel_hhire_mobile.ui.users

import com.example.admin_panel_hhire_mobile.data.model.User
import androidx.recyclerview.widget.RecyclerView
import com.example.admin_panel_hhire_mobile.databinding.ItemUserBinding
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.admin_panel_hhire_mobile.data.model.Post
import com.example.admin_panel_hhire_mobile.data.repository.PostRepository
import com.example.admin_panel_hhire_mobile.ui.users.updateMarked
class UserAdapter(
    private var users: List<User>,
    private val onClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]

        holder.binding.tvName.text = user.name
        holder.binding.tvStatus.text = user.status.name
        updateMarked(user, holder.binding.btnMark)
        updateStatus(user, holder.binding.tvStatus,null ,null,holder.itemView.context)
        holder.itemView.setOnClickListener { onClick(user) }

        holder.binding.btnMark.setOnClickListener {
            val currentUser = PostRepository.getUserById(user.id) ?: return@setOnClickListener
            currentUser.isMarked = !(currentUser.isMarked)
            notifyItemChanged(position)
        }
    }
    fun updateList(newList: List<User>) {
        users = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = users.size
}