package com.dicoding.diploma.githubuserapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Emeth on 10/31/2016.
 */
@Parcelize
class UserItem(
    var id: Int? = null,
    var avatar: String? = null,
    var iduser: String? = null,
    var username: String? = null,
    var name: String? = null,
    var following: Int = 0,
    var followers: Int = 0,
    var company: String? = null,
    var location: String? = null,
    var blog: String? =null
) : Parcelable