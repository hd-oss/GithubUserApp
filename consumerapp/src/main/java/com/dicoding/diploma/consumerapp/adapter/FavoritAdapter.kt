package com.dicoding.diploma.consumerapp.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.diploma.consumerapp.R
import com.dicoding.diploma.consumerapp.model.UserItem
import kotlinx.android.synthetic.main.activity_user_item.view.*

class FavoritAdapter (private val activity: Activity) : RecyclerView.Adapter<FavoritAdapter.FavoriteViewHolder>() {

    var listfavorit = ArrayList<UserItem>()
    set(listFavorite) {
        if (listFavorite.size > 0) {
            this.listfavorit.clear()
        }
        this.listfavorit.addAll(listFavorite)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_user_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listfavorit[position])
    }

    override fun getItemCount(): Int = this.listfavorit.size

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("ResourceType")
        fun bind(user: UserItem) {
            with(itemView){
                username.text = user.username
                id_user.text = user.iduser
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(500, 500))
                    .into(itemphoto)
            }
        }
    }
}