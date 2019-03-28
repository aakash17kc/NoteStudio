package com.androhuman.notestudio;

import android.net.Uri;

public class ImageNote implements ListItem {
    String data;
    Uri image;

    public String getData() {
        return data;
    }
    public  Uri getImage(){
        return image;
    }

    public ImageNote(String data, Uri image) {
        this.data = data;
        this.image = image;
    }

    @Override
    public int getListItemType() {
        return ListItem.TYPE_B;
    }
}