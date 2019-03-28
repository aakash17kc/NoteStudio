package com.androhuman.notestudio

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class NoteAdapter() : RecyclerView.Adapter<NoteAdapter.NoteHolder>(){

    var list:ArrayList<NoteClass>?=null
    var context:Context?=null
    var addNote:AddNotes?=null
    var selectedIds = ArrayList<Int>()
    var isInAction:Boolean = false
    var addNotes = AddNotes()

    var deleteList:ArrayList<NoteClass>?=null




   constructor(list: ArrayList<NoteClass>,context: Context) : this() {
       this.list = list
       this.context = context
       addNote = context as AddNotes

   }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): NoteHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.content, viewGroup, false)

       view.setOnLongClickListener {v->

           if(!isInAction){
               selectedIds.add(i)
               view.setBackgroundResource(R.drawable.border)
               AddNotes().actionActive()
              // var g = getItem(i)
               deleteList!!.add(getItem(i))
           }


           return@setOnLongClickListener true
       }

        view.setOnClickListener {
            if(isInAction){
                if(selectedIds.contains(i)){
                    selectedIds.remove(i)
                    deleteList!!.remove(getItem(i))
                    view.setBackgroundResource(R.drawable.border2)
                }
                else{
                    selectedIds.add(i)
                    deleteList!!.add(getItem(i))
                    view.setBackgroundResource(R.drawable.border)
                }
                if(selectedIds.size>0){
                    AddNotes().actionBarContextActive()
                }
                else{
                    AddNotes().actionBarContextPassive()
                }

            }
            else{
                val p = getItem(i)
                AddNotes().editUpdateNote(p)
            }

        }
        return NoteHolder(view, addNote!!)
    }

    override fun onBindViewHolder(noteHolder: NoteHolder, i: Int) {
         val noteClass:NoteClass = list!!.get(i)
        noteHolder.title.text = noteClass.mNoteTitle
        noteHolder.description.text = noteClass.mNoteDes



       /* if (selectedIds.contains(i)) {
            //if item is selected then,set foreground color of FrameLayout.
            noteHolder.cardView!!.setBackgroundResource(R.drawable.border)
            notifyDataSetChanged()
        } else {
            //else remove selected item color.
            noteHolder.cardView!!.setBackgroundResource(R.drawable.border2)
            notifyDataSetChanged()
        }*/

       /* if (!addNote!!.isInAction) {
            noteHolder.checkBox.visibility = View.GONE

        } else {
            noteHolder.checkBox.visibility = View.VISIBLE
            noteHolder.checkBox.isChecked = false
        }*/



    }
    override fun getItemCount(): Int {
        return list!!.size
    }

    fun getItem(position: Int):NoteClass{
        return list!!.get(position)
    }

    inner class NoteHolder (itemView: View,addNotes: AddNotes) :
        RecyclerView.ViewHolder(itemView) {

        internal var title: TextView
        internal var description: TextView
        var cardView:CardView?=null
         var note:AddNotes?=null


        init {

            title = itemView.findViewById(R.id.titleHeading)
            this.note = addNotes
            description = itemView.findViewById(R.id.description)
            cardView = itemView.findViewById(R.id.cardview)
        }

        }

    internal fun updateAdapter(listitems: ArrayList<NoteClass>) {

        val databaseHandler = DatabaseHandler(context!!)

        for (noteClass in listitems) {
            val slectionArgs = arrayOf(noteClass.mNoteID.toString())
            list!!.remove(noteClass)
            databaseHandler.deleteData("ID=?", slectionArgs)
        }
        notifyDataSetChanged()
        addNote!!.mLoadQuery("%")
        addNote!!.showSnackbar()
    }

    fun setSelectedIds(selectedIds: List<Int>) {
        this.selectedIds = selectedIds as ArrayList<Int>
        notifyDataSetChanged()
    }





}

