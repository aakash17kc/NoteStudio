package com.androhuman.notestudio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView


class AddNotes : AppCompatActivity(), ActionMode.Callback {



    var list = ArrayList<NoteClass>()
    var recyclerView: RecyclerView?=null


    var noteAdapter:NoteAdapter?=null

    var nav:ImageView?=null
    var drawer: DrawerLayout?=null
    var isInAction:Boolean = false
    var tbar: Toolbar?=null
    var count:Int?=null
    var selected: ArrayList<Int>?=null
    lateinit var noteClass:NoteClass
    private val TAG = "MainActivity"
    private var actionMode: ActionMode? = null
    var gridv = true
    var deleteItems:ArrayList<NoteClass>?=null
    lateinit var noteView:ImageView
    var layout:LinearLayout?=null


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        nav = findViewById(R.id.nav_drawer)
        drawer = findViewById(R.id.drawerlayout)
        tbar = findViewById(R.id.header)
        count = 0
        setSupportActionBar(tbar)
        noteView  = findViewById(R.id.noteView)
        deleteItems = ArrayList<NoteClass>()
        recyclerView = findViewById(R.id.recyclerView)
        noteAdapter = NoteAdapter(list,this)
        layout = findViewById(R.id.root)


        var linearlayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        var gridlayoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView!!.layoutManager = gridlayoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.adapter = noteAdapter


        noteView.setOnClickListener{
            if(gridv==false) {
                gridv = true
                recyclerView!!.layoutManager = gridlayoutManager
                recyclerView!!.adapter = noteAdapter
                recyclerView!!.itemAnimator = DefaultItemAnimator()
                noteView.setImageResource(R.drawable.outline_view_agenda_black_24)

            }
            else {
                gridv = false
                recyclerView!!.layoutManager = linearlayoutManager
                recyclerView!!.adapter = noteAdapter
                recyclerView!!.itemAnimator = DefaultItemAnimator()
                noteView.setImageResource(R.drawable.outline_dashboard_black_24)

            }
        }

        val toggle = ActionBarDrawerToggle(this,drawer,R.string.opendrawer,R.string.closedrawer)
        drawer!!.setDrawerListener(toggle)
        toggle.syncState()

        nav!!.setOnClickListener(){
            drawer!!.openDrawer(Gravity.START)
        }



        list.add(NoteClass(1,"Hello","My Name is Aakash My Name is Asim My Name is Asim"))
        list.add(NoteClass(2,"Hello","My Name is Asim My Name is Asim My Name is Asim My Name is Asim My Name is Asim"))
        list.add(NoteClass(3,"Hello","My Name is Ankit My Name is Asim"))
         //load from database
        mLoadQuery("%")

        val noteType:TextView = findViewById(R.id.typenote)


        noteType.setOnClickListener {startActivity(Intent(this,NewNoteActivity::class.java)) }



       /* recyclerView!!.addOnItemTouchListener(
            RecyclerItemClickListener(this, recyclerView!!,  object: RecyclerItemClickListener.OnItemClickListener {
                override fun onItemLongClick(view: View?, position: Int) {

                    if(!isInAction){
                        selected = ArrayList<Int>()
                        isInAction = true
                        if(actionMode==null){
                           /* if (selected!!.contains(position)) {
                                //if item is selected then,set foreground color of FrameLayout.
                               view.setBackgroundResource(R.drawable.border)
                            } else {
                                //else remove selected item color.
                                view!!.setBackgroundResource(R.drawable.border2)
                            }*/
                            view!!.setBackgroundResource(R.drawable.border)

                            var p = recyclerView!!.getChildLayoutPosition(view!!)
                            noteClass = list[p]
                            noteAdapter!!.notifyDataSetChanged()
                            deleteItems!!.add(noteClass)
                            actionMode = startActionMode(this@AddNotes)
                        }
                    }

                    multiSelect(position,view!!)
                }



                override fun onItemClick(view: View, position: Int) {
                    if(isInAction) {
                        view.setBackgroundResource(R.drawable.border)
                        multiSelect(position,view)


                    }
                    else{
                        view.setBackgroundResource(R.drawable.border2)
                        var p = recyclerView!!.getChildAdapterPosition(view)
                        noteClass = list[p]
                        editUpdateNote(noteClass)
                    }
                    }
                }))*/


    }

    fun actionActive(){
        if(actionMode==null)
              actionMode = startActionMode(this@AddNotes)

    }
    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        var id = item!!.itemId
        when(item.getItemId()){
            R.id.delete->{
                isInAction = false
                val adapter = noteAdapter
                adapter!!.updateAdapter(adapter.deleteList!!)
                clearAction()
                mLoadQuery("%")
                onDestroyActionMode(mode)
            }


        }

        return true
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {

        var inflate:MenuInflater = mode!!.menuInflater
      //  mode!!.customView = LayoutInflater.from(this).inflate(R.layout.actionmodecontext,null,false)
       inflate.inflate(R.menu.multiselect_menu,menu)
        //tbar!!.menu.clear()
       // tbar!!.inflateMenu(R.menu.multiselect_menu)

       // supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
       // tbar!!.menu.clear()
        actionMode!!.finish() //hide action mode.
        layout!!.elevation = 2f
        layout!!.translationZ = 2f


        actionMode = null
        isInAction = false
        selected = ArrayList<Int>()
        noteAdapter!!.setSelectedIds(ArrayList<Int>())
    }


    private fun multiSelect(position: Int,v: View) {
        val data:NoteClass = noteAdapter!!.getItem(position)
       // var view:CardView = noteAdapter!!.getItem(position)
        if (data != null) {
            if (actionMode != null) {
                if (selected!!.contains(data.getmNoteID())) {
                    selected!!.remove(Integer.valueOf(data.getmNoteID()))


                }
                else {
                    selected!!.add(data.getmNoteID())

                }

                if (selected!!.size > 0) {
                    actionBarContextActive()

                }//show selected item count on action mode.
                else {
                    actionBarContextPassive()
                }
                noteAdapter!!.setSelectedIds(selected!!)

            }
        }
    }

    fun actionBarContextActive(){
        layout!!.elevation = 0f
        layout!!.translationZ = 0f
        actionMode!!.setTitle(selected!!.size.toString())
    }

    fun actionBarContextPassive(){
        layout!!.elevation = 2f
        layout!!.translationZ = 2f
        // actionMode!!.setTitle("") //remove item count from action mode.
        actionMode!!.finish() //hide action mode.
    }

   @SuppressLint("WrongConstant")
   override fun onBackPressed() {
        if(drawer!!.isDrawerOpen(Gravity.START))
            drawer!!.closeDrawer(Gravity.START)
        else
            super.onBackPressed()
       if(isInAction){
           clearAction()
           noteAdapter!!.notifyDataSetChanged()
           mLoadQuery("%")

       }
       else
           super.onBackPressed()
    }

    fun mLoadQuery(title:String){
        val databaseHandler = DatabaseHandler(this)
        val selectionArgs = arrayOf(title)
        val projection = arrayOf("ID","Title","Description")
        val cursor = databaseHandler.queryData(projection,"Title like ?",selectionArgs,"Title")

        list.clear()

        if(cursor.moveToFirst()){
            do{
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val desciption = cursor.getString(cursor.getColumnIndex("Description"))
                list.add(NoteClass(ID,title,desciption))
            }
                while (cursor.moveToNext())
        }

        recyclerView!!.adapter = noteAdapter


    }


   /* override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId==R.id.delete){
            isInAction = false
            val adapter = noteAdapter
            adapter!!.updateAdapter(de)
            clearAction()
            mLoadQuery("%")
        }
        else if (item!!.itemId==R.id.clear){
            clearAction()
            noteAdapter!!.notifyDataSetChanged()
            mLoadQuery("%")

        }

        return true
    }*/

    fun clearAction(){
        isInAction = false
        tbar!!.menu.clear()
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        count = 0
        selected!!.clear()
        deleteItems!!.clear()
    }


    fun showSnackbar(){
        Snackbar.make(findViewById(R.id.coordinator),"Note Deleted",Snackbar.LENGTH_SHORT)
            .setAction("UNDO",null).show()


    }


    fun editUpdateNote(note: NoteClass){
        var intent = Intent(this,NewNoteActivity::class.java)
        intent.putExtra("ID",note.mNoteID)
        intent.putExtra("Title",note.mNoteTitle)
        intent.putExtra("Description",note.mNoteDes)

        startActivity(intent)

    }


}



