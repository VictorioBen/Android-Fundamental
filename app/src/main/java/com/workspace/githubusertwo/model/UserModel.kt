package com.workspace.githubusertwo.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    @SerializedName("login")
     val userName: String? = "",
    @SerializedName("avatar_url")
    val imageUrl: String? = "",
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("html_url")
    val htmlUrl: String? = "",
    @SerializedName("followers_url")
    val followersUrl: String? = "",
    @SerializedName("following_url")
    val followingUrl: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("location")
    val location: String? = "",
    @SerializedName("followers")
    val totalFollower: Int? = 0,
    @SerializedName("following")
    val totalFollowing: Int? = 0,
    @SerializedName("company")
    val company: String? = "",
    @SerializedName("public_repos")
    val repository: String? = "",
    @SerializedName("updated_at")
    val updated_at: String? = "",
    val followingCount: String? = "",
    val avatar: Int = -1
) : Parcelable

data class SearchResponse(
    val items: List<UserModel> = mutableListOf()
)
