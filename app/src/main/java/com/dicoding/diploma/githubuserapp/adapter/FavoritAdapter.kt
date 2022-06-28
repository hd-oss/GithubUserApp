package com.dicoding.diploma.githubuserapp.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.diploma.githubuserapp.CustomOnItemClickListener
import com.dicoding.diploma.githubuserapp.activity.DetailActivity
import com.dicoding.diploma.githubuserapp.R
import com.dicoding.diploma.githubuserapp.model.UserItem
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
                country.text = user.iduser
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(500, 500))
                    .into(itemphoto)

                useritem.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnitemClickCallback {
                    override fun onItemClicked(view: View, position: Int) {
                        val intent = Intent(activity, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_USER, user)
                        activity.startActivity(intent)
                    }
                }))
            }
        }
    }
}