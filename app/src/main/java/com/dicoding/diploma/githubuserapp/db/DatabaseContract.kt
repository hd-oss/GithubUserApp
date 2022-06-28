package com.dicoding.diploma.githubuserapp.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract{

    const val AUTHORITY = "com.dicoding.diploma.githubuserapp"

    const val SCHEME = "content"

    class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "user"
            const val _ID = "_id"
            const val NAME = "name"
            const val USERNAME = "username"
            const val ID_USER = "id_user"
            const val AVATAR = "avatar"
            const val FOLLOWING = "following"
            const val FOLLOWERS = "followers"
            const val LOCATION = "location"
            const val COMPANY = "company"
            const val BLOG = "blog"

            val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }

    }
}
