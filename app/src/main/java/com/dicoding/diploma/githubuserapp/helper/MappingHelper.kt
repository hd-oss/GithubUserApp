package com.dicoding.diploma.githubuserapp.helper

import android.database.Cursor
import com.dicoding.diploma.githubuserapp.model.UserItem
import com.dicoding.diploma.githubuserapp.db.DatabaseContract
import java.util.*


object MappingHelper {
    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<UserItem> {
        val userList = ArrayList<UserItem>()
        userCursor?.apply {
            while (moveToNext()) {
                val user = UserItem()
                user.id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns._ID))
                user.avatar = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR))
                user.iduser = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.ID_USER))
                user.username = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.USERNAME))
                user.name = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME))
                user.following = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWING))
                user.followers = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWERS))
                user.company = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.COMPANY))
                user.location = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOCATION))
                user.blog = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.BLOG))
                userList.add(user)
            }
        }
        return userList
    }
}
