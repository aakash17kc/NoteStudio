package com.androhuman.notestudio

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DatabaseHandler{
    val DbName = "NoteStudio"
    val DbTable = "Notes"
    val colID = "ID"
    val colTitle = "Title"
    val colDescription = "Description"
    val DbVersion = 1

    // CREATE TABLE OF NOT EXTIS NoteStudio ( ID INTEGER PRIMARY KEY, title TEXT, Description TEXT);

    val SqlCreateTable = "CREATE TABLE IF NOT EXISTS "+ DbTable +" ("+ colID +" INTEGER PRIMARY KEY,"+
            colTitle + " TEXT, "+ colDescription +" TEXT);"
    var SqlDB:SQLiteDatabase?=null

    constructor(context: Context){

        val Db = NotesDatabase((context))
        SqlDB = Db.writableDatabase

    }

    inner class NotesDatabase:SQLiteOpenHelper{

        var context:Context? = null


        constructor(context: Context):super(context,DbName, null,DbVersion){
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
                db!!.execSQL(SqlCreateTable)
            Toast.makeText(this.context,"Database Created",Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
db!!.execSQL("Drop Table IF EXISTS "+DbTable)
        }

    }


    fun insertData(value: ContentValues):Long{
        val ID = SqlDB!!.insert(DbTable,"",value)
        return  ID
    }

    fun queryData(projection:Array<String>,selection:String,selectionArgs:Array<String>, sortOrder:String):Cursor{
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = DbTable
        val cursor = queryBuilder.query(SqlDB,projection,selection,selectionArgs, null,null,sortOrder)
        return cursor
    }

    fun deleteData(selection: String, selectionArgs: Array<String>):Int{
        val count =SqlDB!!.delete(DbTable,selection,selectionArgs)
        return count
    }

    fun updateNote(value: ContentValues,selection: String,selectionArgs: Array<String>):Int{
        val count = SqlDB!!.update(DbTable,value,selection,selectionArgs)
        return count
    }

}