package com.androhuman.notestudio

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_new_note.*

class NewNoteActivity : AppCompatActivity() {
    val DbTable = "Notes"

    var mTitle:String?=null
    var mDescription:String?=null
    var notedata:EditText?=null
    var noteString:String?=null
    var  noteArrayList:Array<String>?=null
    var id =0
    var tbar: Toolbar?=null


    //var noteArrayList = List<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_note)
        tbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        notedata = findViewById(R.id.noteData)


        back.setOnClickListener(View.OnClickListener { mNewNote() })
        addNewNote.setOnClickListener(View.OnClickListener { mNewNote() })

        try {
            var bundle:Bundle = intent.extras
            id= bundle.getInt("ID",0)
            var title:String ?= bundle.getString("Title")
            var des:String? = bundle.getString("Description")
            if(id!=0){
                var data:String = title+"\n"+des
                notedata!!.setText(data)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }


    }



    fun mNewNote(){




        noteString = noteData!!.text.toString()
        noteArrayList= noteString!!.split("\n").toTypedArray()

        if(noteArrayList!!.size==1){
            mTitle = noteArrayList!![0]
            mDescription = " "

        }
        else if(title!!.length==0){
            finish()
            AddNotes().showSnackbar()
        }
        else {
            mTitle = noteArrayList!![0]
            val len = mTitle!!.length
            mDescription = noteString!!.substring(len + 1, noteString!!.length)
        }

       // mDescription = noteString

        val mDBManager = DatabaseHandler(this)
        val values = ContentValues()
        values.put("Title",mTitle)
        values.put("Description",mDescription)

        if(id==0){
            val ID =  mDBManager.insertData(values)

        if(ID>0){
            Toast.makeText(this,"Note Added",Toast.LENGTH_SHORT).show()
            val addNotes  = AddNotes()
            //addNotes.mLoadQuery("%")
            finish()
            startActivity(Intent(this@NewNoteActivity,AddNotes::class.java))
        }
        else{
            Toast.makeText(this,"Note not Added",Toast.LENGTH_SHORT).show()

        }



            }
        else{
            var selectionArgs = arrayOf(id.toString())
            val ID = mDBManager.updateNote(values,"ID=?",selectionArgs)
            if(ID>0){
                Toast.makeText(this,"Note Added",Toast.LENGTH_SHORT).show()
                val addNotes  = AddNotes()
                //addNotes.mLoadQuery("%")

                finish()
                startActivity(Intent(this@NewNoteActivity,AddNotes::class.java))
            }
            else{
                Toast.makeText(this,"Note not Added",Toast.LENGTH_SHORT).show()

            }

        }

        }

}
