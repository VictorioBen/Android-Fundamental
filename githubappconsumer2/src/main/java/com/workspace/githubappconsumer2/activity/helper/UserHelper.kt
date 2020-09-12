package com.workspace.githubappconsumer2.activity.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.workspace.githubappconsumer2.activity.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.workspace.githubappconsumer2.activity.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.workspace.githubappconsumer2.activity.db.DatabaseContract.UserColumns.Companion._ID
import com.workspace.githubappconsumer2.activity.db.DatabaseHelper

class UserHelper(context: Context) {
    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: UserHelper? = null

        fun getInstance(context: Context): UserHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserHelper(context)
            }
    }
    init {
        databaseHelper = DatabaseHelper(context)
    }

    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC", null
        )
    }



    fun queryByUsername(username: String?): Cursor {
        return database.query(DATABASE_TABLE, null, "$USERNAME = ?", arrayOf(username), null, null, null, null)
    }


    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }



    fun deleteByUsername(username: String?) : Int {
        return database.delete(DATABASE_TABLE, "$USERNAME = '$username'", null)
    }


}