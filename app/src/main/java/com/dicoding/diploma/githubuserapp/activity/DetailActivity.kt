package com.dicoding.diploma.githubuserapp.activity

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.diploma.githubuserapp.R
import com.dicoding.diploma.githubuserapp.model.UserItem
import com.dicoding.diploma.githubuserapp.db.DatabaseContract
import com.dicoding.diploma.githubuserapp.adapter.SectionsPagerAdapter
import com.dicoding.diploma.githubuserapp.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.dicoding.diploma.githubuserapp.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_detail_user.*

class DetailActivity : AppCompatActivity(){

    private var statusFavorite = false
    private lateinit var uriWithId: Uri
    private var user: UserItem? = null

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        user = intent.getParcelableExtra<UserItem>(EXTRA_USER) as UserItem

        setDetail()
        setFavorite()

        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + user?.username)
        val cursor = contentResolver?.query(uriWithId, null, null, null, null)
        val user = MappingHelper.mapCursorToArrayList(cursor)
        for (data in user){
            if (this.user?.username == data.username) {
                statusFavorite = true
                setStatusFavorite()
                cursor?.close()
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

    }

    private fun setFavorite(){
        setStatusFavorite()
        tb_fav.setOnClickListener {
            if (statusFavorite) {
                contentResolver.delete(uriWithId, null, null)
                Toast.makeText(this, R.string.unfav, Toast.LENGTH_SHORT).show()
                statusFavorite = false
                setStatusFavorite()
            } else {
                val values = ContentValues()
                values.put(DatabaseContract.UserColumns._ID, user?.id)
                values.put(DatabaseContract.UserColumns.USERNAME, user?.username)
                values.put(DatabaseContract.UserColumns.NAME,user?.name)
                values.put(DatabaseContract.UserColumns.AVATAR,user?.avatar)
                values.put(DatabaseContract.UserColumns.FOLLOWERS,user?.followers)
                values.put(DatabaseContract.UserColumns.FOLLOWING,user?.following)
                values.put(DatabaseContract.UserColumns.LOCATION,user?.location)
                values.put(DatabaseContract.UserColumns.BLOG,user?.blog)
                values.put(DatabaseContract.UserColumns.COMPANY,user?.company)
                values.put(DatabaseContract.UserColumns.ID_USER,user?.iduser)
                contentResolver.insert(CONTENT_URI, values)
                Toast.makeText(this, R.string.addfav, Toast.LENGTH_SHORT).show()
                statusFavorite = true
                setStatusFavorite()
            }
        }
    }

    private fun setDetail(){
        name.text = user?.name
        followers.text = user?.followers.toString()
        following.text= user?.following.toString()
        compeny.text = user?.company
        location.text = user?.location
        blog.text = user?.blog
        Glide.with(this)
            .load(user?.avatar)
            .apply(RequestOptions().override(1000, 1000))
            .into(profile_image)


        val username = user?.username.toString()
        supportActionBar?.title = username
    }

    private fun setStatusFavorite() {
        if (statusFavorite) {
            tb_fav.setImageResource(R.drawable.favourite_button_yes)
        } else {
            tb_fav.setImageResource(R.drawable.favourite_button_no)
        }
    }
}
