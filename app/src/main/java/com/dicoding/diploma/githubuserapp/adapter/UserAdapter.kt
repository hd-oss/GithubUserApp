package com.dicoding.diploma.githubuserapp.adapter

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.diploma.githubuserapp.activity.DetailActivity
import com.dicoding.diploma.githubuserapp.R
import com.dicoding.diploma.githubuserapp.model.UserItem
import kotlinx.android.synthetic.main.activity_user_item.view.*

import java.util.ArrayList

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val mData = ArrayList<UserItem>()

    fun setData(items: ArrayList<UserItem>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): UserViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.activity_user_item, viewGroup, false)
        return UserViewHolder(mView)
    }

    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int,
    ) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: UserItem) {
            with(itemView){
                username.text = user.username
                country.text = user.location
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(500, 500))
                    .into(itemphoto)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USER, user)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}