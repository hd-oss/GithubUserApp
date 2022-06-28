package com.dicoding.diploma.consumerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.diploma.consumerapp.adapter.FavoritAdapter
import com.dicoding.diploma.consumerapp.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.dicoding.diploma.consumerapp.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var favoritAdapter: FavoritAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Favorite User"

        recyclerView_fav.setHasFixedSize(true)

        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val users = deferredNotes.await()
            if (users.size > 0) {
                favoritAdapter.listfavorit = users
            } else {
                favoritAdapter.listfavorit = ArrayList()
                Toast.makeText(this@MainActivity, R.string.empty_text, Toast.LENGTH_SHORT).show()
            }
        }
        showRecyclerView()
    }

    private fun showRecyclerView() {
        recyclerView_fav.layoutManager = LinearLayoutManager(this)
        favoritAdapter = FavoritAdapter(this)
        recyclerView_fav.adapter = favoritAdapter
    }
}
