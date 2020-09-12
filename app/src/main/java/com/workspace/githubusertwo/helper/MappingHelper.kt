package com.workspace.githubusertwo.helper

import android.database.Cursor
import com.workspace.githubusertwo.db.DatabaseContract.UserColumns.Companion.AVATAR
import com.workspace.githubusertwo.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.workspace.githubusertwo.db.DatabaseContract.UserColumns.Companion._ID
import com.workspace.githubusertwo.model.UserModel

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