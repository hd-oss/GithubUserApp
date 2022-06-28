package com.dicoding.diploma.githubuserapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dicoding.diploma.githubuserapp.db.DatabaseContract.UserColumns
import com.dicoding.diploma.githubuserapp.db.DatabaseContract.UserColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "dbGithubUser"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_USER = "CREATE TABLE $TABLE_NAME" +
                " (${UserColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${UserColumns.USERNAME} TEXT NOT NULL UNIQUE,"+
                " ${UserColumns.NAME} TEXT NOT NULL," +
                " ${UserColumns.ID_USER} TEXT NOT NULL," +
                " ${UserColumns.AVATAR} TEXT NOT NULL," +
                " ${UserColumns.COMPANY} TEXT NOT NULL," +
                " ${UserColumns.FOLLOWERS} TEXT NOT NULL," +
                " ${UserColumns.FOLLOWING} TEXT NOT NULL," +
                " ${UserColumns.LOCATION} TEXT NOT NULL," +
                " ${UserColumns.BLOG} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}
