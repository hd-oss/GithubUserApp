package com.dicoding.diploma.githubuserapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.diploma.githubuserapp.R
import com.dicoding.diploma.githubuserapp.adapter.UserAdapter
import com.dicoding.diploma.githubuserapp.model.UserItem
import com.dicoding.diploma.githubuserapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment() {

    private lateinit var adapter: UserAdapter
    private lateinit var userview: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)


    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        val user = requireActivity().intent.getParcelableExtra<UserItem>(EXTRA_USER)

        userview= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        val login = user?.username.toString()

        context?.let { userview.setFollowers(login, it) }
        showLoading(true)

        userview.getData().observe(requireActivity(), Observer { useritem ->
            if (useritem != null) {
                adapter.setData(useritem)
                showLoading(false)
            } else {
                showLoading(false)
                Toast.makeText(activity, R.string.error , Toast.LENGTH_SHORT).show()
                return@Observer
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}