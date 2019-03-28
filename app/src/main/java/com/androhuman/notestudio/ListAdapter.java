package com.androhuman.notestudio;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHoder> {
    List<ListItem> listInterfaces;
    Context context;
    AddNotes addNotes ;

    ListAdapter(List<ListItem> listInterfaces, Context context){
        this.listInterfaces = listInterfaces;
        this.context = context;
         addNotes = (AddNotes) context;
    }

    @Override
    public int getItemViewType(int position) {
        return listInterfaces.get(position).getListItemType();
    }

    @NonNull
    @Override
    public ListHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = null;
       switch (i){
           case ListItem.TYPE_A:
               view =  LayoutInflater.from(context).inflate(R.layout.content,viewGroup,false);
               return new ListNoteHolder(view);
           case ListItem.TYPE_B:
               view = LayoutInflater.from(context).inflate(R.layout.imagenote,viewGroup,false);
               return new ListImageHolder(view);
       }
       return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListHoder listHoder, int i) {
        ListItem listInterface = listInterfaces.get(i);
        listHoder.bindType(listInterface);


    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public abstract class ListHoder extends RecyclerView.ViewHolder {
        public ListHoder(@NonNull View itemView) {
            super(itemView);
        }
        public abstract void bindType(ListItem item);
    }

    public  class ListImageHolder extends ListHoder{
        ImageView imageView;
        TextView textView;

        public ListImageHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagenote);
            textView = itemView.findViewById(R.id.imagedes);
        }

        @Override
        public void bindType(ListItem item) {
            imageView.setImageURI(((ImageNote)item).getImage());
            textView.setText(((ImageNote)item).getData());

        }
    }

    public class ListNoteHolder extends ListHoder{

        TextView title,description;

        public ListNoteHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            title = itemView.findViewById(R.id.titleHeading);

        }

        @Override
        public void bindType(ListItem item) {
            title.setText(((NoteClass)item).getmNoteTitle());

            description.setText(((NoteClass)item).getmNoteDes());


        }
    }
}
