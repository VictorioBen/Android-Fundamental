package com.workspace.githubappconsumer2.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.workspace.githubappconsumer2.db.DatabaseContract.UserColumns.Companion.AVATAR
import com.workspace.githubappconsumer2.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.workspace.githubappconsumer2.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.workspace.githubappconsumer2.db.DatabaseContract.UserColumns.Companion._ID


internal class  DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbgituserapp"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_USER =
            "CREATE TABLE $TABLE_NAME" + " (${_ID} INTEGER PRIMARY KEY AUTOINCREMENT," + "$USERNAME TEXT NOT NULL," + "$AVATAR TEXT NOT NULL)"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}
