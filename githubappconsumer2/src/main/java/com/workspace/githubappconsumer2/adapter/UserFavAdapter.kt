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
import kotlinx.android.synthetic.main.item_row_favorite.view.*

class UserFavAdapter(private val listUser: MutableList<UserModel>? = mutableListOf(),
                     private val onClickListener: ((UserModel) -> Unit)? = null, private val onLongClick: ((UserModel, Int) -> Unit)? = null
) : RecyclerView.Adapter<UserFavAdapter.UserViewHolder>() {
    private lateinit var itemView: View



     class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageFav: ImageView = itemView.imageFav
        val userFav: TextView = itemView.userFav


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_row_favorite, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val userItem = listUser?.get(position)
        Glide.with(itemView.context).load(userItem?.imageUrl).circleCrop().into(holder.imageFav)
        holder.userFav.text = userItem?.userName

        holder.itemView.setOnClickListener {
            if (userItem != null) {
                onClickListener?.invoke(userItem)
            }
        }
        holder.itemView.setOnLongClickListener {
            if (userItem != null){
                onLongClick?.invoke(userItem, position)
            }
            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return listUser?.size ?: 0
    }

    fun addAll(result: List<UserModel>?) {
        if (result != null) {
            listUser?.clear()
            listUser?.addAll(result)
            notifyDataSetChanged()

        }
    }

    fun removeAt(position: Int){
        this.listUser?.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeRemoved(position, this.listUser?.size ?:0)
        notifyDataSetChanged()
    }





}