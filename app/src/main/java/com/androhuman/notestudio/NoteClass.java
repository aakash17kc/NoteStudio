package com.androhuman.notestudio;

public class NoteClass implements ListItem {


    int mNoteID;
    String mNoteTitle;

    public String getmNoteTitle() {
        return mNoteTitle;
    }

    public String getmNoteDes() {
        return mNoteDes;
    }
    public int getmNoteID() {
        return mNoteID;
    }

    String mNoteDes;

    public NoteClass(int mNoteID, String mNoteTitle, String mNoteDes) {
        this.mNoteID = mNoteID;
        this.mNoteTitle = mNoteTitle;
        this.mNoteDes = mNoteDes;
    }



    @Override
    public int getListItemType() {
        return 0;
    }
}
