package com.workspace.githubappconsumer2.helper

import android.database.Cursor
import com.workspace.githubappconsumer2.db.DatabaseContract.UserColumns.Companion.AVATAR
import com.workspace.githubappconsumer2.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.workspace.githubappconsumer2.db.DatabaseContract.UserColumns.Companion._ID
import com.workspace.githubappconsumer2.model.UserModel

object MappingHelper {


    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<UserModel>{
        val list = ArrayList<UserModel>()
        userCursor?.apply {
            while (moveToNext()){
                val id = getInt(getColumnIndexOrThrow(_ID))
                val userName = getString(getColumnIndexOrThrow(USERNAME))
                val imageUrl = getString(getColumnIndexOrThrow(AVATAR))
                val user = UserModel(userName = userName, imageUrl = imageUrl)
                list.add(user)
            }
        }
        return list
    }

}