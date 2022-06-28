package com.dicoding.diploma.githubuserapp.viewmodel


import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.diploma.githubuserapp.BuildConfig
import com.dicoding.diploma.githubuserapp.R
import com.dicoding.diploma.githubuserapp.model.UserItem
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class MainViewModel : ViewModel() {

    private val listUser = MutableLiveData<ArrayList<UserItem>>()
    private val listItems = ArrayList<UserItem>()

    fun setSearchUser(username: String, context: Context) {
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", BuildConfig.GITHUB_TOKEN)
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get("https://api.github.com/search/users?q=$username", object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    //parsing json
                    listItems.clear()
                    val result = String(responseBody)
                    val listArray = JSONObject(result)
                    val list = listArray.getJSONArray("items")

                    if (list.length() > 0) {
                        for (i in 0 until list.length()) {
                            val user = list.getJSONObject(i)
                            val login = user.getString("login")
                            setDetailUser(login,context)
                        }
                    } else {
                        Toast.makeText(context, R.string.notfound, Toast.LENGTH_SHORT).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()

                }

            }
            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()

            }
        })
    }

    fun setDetailUser(username: String, context: Context) {

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", BuildConfig.GITHUB_TOKEN)
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get("https://api.github.com/users/$username", object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val result = String(responseBody)
                try {
                    //parsing json
                    val user = JSONObject(result)
                    val userItem = UserItem()
                    userItem.iduser = user.getString("id")
                    userItem.username = user.getString("login")
                    userItem.avatar = user.getString("avatar_url")
                    userItem.company = user.getString("company")
                    userItem.name = user.getString("name")
                    userItem.followers = user.getInt("followers")
                    userItem.following = user.getInt("following")
                    userItem.location = user.getString("location")
                    userItem.blog = user.getString("blog")

                    listItems.add(userItem)

                    listUser.postValue(listItems)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()

                }

            }
            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setFollowers(username: String, context: Context) {

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", BuildConfig.GITHUB_TOKEN)
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get("https://api.github.com/users/$username/followers", object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    //parsing json
                    listItems.clear()
                    val result = String(responseBody)
                    val listArray = JSONArray(result)

                    for (i in 0 until listArray.length()) {
                        val user = listArray.getJSONObject(i)
                        val login = user.getString("login")
                        setDetailUser(login,context)
                    }
                }  catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()

                }

            }
            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setFollowing(username: String, context: Context) {

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", BuildConfig.GITHUB_TOKEN)
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get("https://api.github.com/users/$username/following", object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    //parsing json
                    listItems.clear()
                    val result = String(responseBody)
                    val listArray = JSONArray(result)

                    for (i in 0 until listArray.length()) {
                        val user = listArray.getJSONObject(i)
                        val login = user.getString("login")
                        setDetailUser(login,context)
                    }
                }  catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()

                }

            }
            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()

            }
        })
    }

    fun getData(): LiveData<ArrayList<UserItem>> {
        return listUser
    }
}
