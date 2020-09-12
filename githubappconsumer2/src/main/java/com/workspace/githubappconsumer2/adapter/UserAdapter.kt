package com.workspace.githubappconsumer2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.workspace.githubappconsumer2.R

import com.workspace.githubappconsumer2.model.UserModel

import kotlinx.android.synthetic.main.item_row_user.view.*

class UserAdapter(
    private val listUser: MutableList<UserModel>? = mutableListOf(),
    val onClickListener: ((UserModel) -> Unit)? = null
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private lateinit var itemView: View


    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageUser: ImageView = itemView.imageList
        val nameUser: TextView = itemView.userName


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)

        return UserViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listUser?.size ?: 0
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userItem = listUser?.get(position)
        holder.nameUser.text = userItem?.userName
        Glide.with(itemView.context).load(userItem?.imageUrl).circleCrop().into(holder.imageUser)
        holder.itemView.setOnClickListener {
            if (userItem != null) {
                onClickListener?.invoke(userItem)
            }

        }


    }

    fun addAll(result: List<UserModel>?) {
        if (result != null) {
            listUser?.clear()
            listUser?.addAll(result)
            notifyDataSetChanged()

        }
    }






}