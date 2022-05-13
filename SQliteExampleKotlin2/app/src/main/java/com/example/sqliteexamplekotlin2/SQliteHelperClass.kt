package com.example.sqliteexamplekotlin2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQliteHelperClass(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT,MARKS INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun insertvalues(name: String?, surname: String?, marks: String?): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, name)
        contentValues.put(SURNAME, surname)
        contentValues.put(MARKS, marks)
        val result = db.insert(TABLE_NAME, null, contentValues)
        return result > 0
    }

    fun showdata(): Cursor {
        val sql = this.writableDatabase
        return sql.rawQuery("select * from $TABLE_NAME", null)
    }

    fun updatetable(id: String, name: String?, surnmae: String?, marks: String?): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, id)
        contentValues.put(NAME, name)
        contentValues.put(SURNAME, surnmae)
        contentValues.put(MARKS, marks)
        val result = db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
        return result > 0
    }

    fun deletedata(userid: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "ID = ?", arrayOf(userid))
    }

    companion object {
        const val DATABASE_NAME = "student.db"
        const val TABLE_NAME = "student_table"
        const val ID = "ID"
        const val NAME = "NAME"
        const val SURNAME = "SURNAME"
        const val MARKS = "MARKS"
    }
}