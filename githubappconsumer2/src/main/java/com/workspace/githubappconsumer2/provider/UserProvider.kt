package com.workspace.githubappconsumer2.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.workspace.githubappconsumer2.db.DatabaseContract.Companion.AUTHORITY
import com.workspace.githubappconsumer2.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.workspace.githubappconsumer2.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.workspace.githubappconsumer2.helper.UserHelper

class UserProvider : ContentProvider() {
    private lateinit var helper: UserHelper


    companion object {
        private const val USER = 1
        private const val USER_NAME = 3
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER)
            sUriMatcher.addURI(
                AUTHORITY,
                "$TABLE_NAME/#",
                USER_NAME
            )
        }
    }

        override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (USER_NAME) {
            sUriMatcher.match(uri) -> helper.deleteByUsername(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (USER) {
            sUriMatcher.match(uri) -> helper.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun onCreate(): Boolean {
        helper = UserHelper.getInstance(context as Context)
        helper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            USER -> cursor = helper.queryAll()
            USER_NAME -> cursor = helper.queryById(uri.lastPathSegment.toString())
            else -> cursor = null
        }
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}
