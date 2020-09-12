package com.workspace.githubappconsumer2.activity.db
import android.net.Uri
import android.provider.BaseColumns

 class DatabaseContract {
    companion object{
        const val AUTHORITY = "com.workspace.githubusertwo"
        const val SCHEME = "content"
    }
    internal class UserColumns : BaseColumns{
        companion object{
            const val TABLE_NAME = "github_user"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val AVATAR = "avatar"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}