package com.example.usman.noteapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllNoteFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {


    private ListView lvStudents;
    private SQLiteDatabase db;
    private SharedPreferences shared;
    private Context c;
    private ListView list;

    private ArrayList<Note> noteList;
    private NoteListAdapter noteAdapter;

    private TextView note;
    private View v;
    private String ss;
    private Cursor cursor;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         v = inflater.inflate(R.layout.all_notes_fragment, container, false);



        list = (ListView)v.findViewById((R.id.lvStudents));
        c = container.getContext();

        noteList = new ArrayList<Note>();
           SharedPreferences sh = getActivity().getPreferences(Context.MODE_PRIVATE);
        db = c.openOrCreateDatabase("note",c.MODE_PRIVATE,null);
        shared = c.getSharedPreferences("loginhandling",c.MODE_PRIVATE); // need a where condition here

         ss = shared.getString("email","").toString();

        if(sh.getBoolean("firstTime", false)) {
            calldb();
        }
        noteAdapter = new NoteListAdapter(c,noteList);
        list.setAdapter(noteAdapter);
        noteAdapter.notifyDataSetChanged();

        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);
        return v;
    }

    private void calldb() {
        cursor = db.rawQuery("SELECT note , date from Allnotes where email ='" + ss + "' ", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            Note obj = new Note();
            obj.id = cursor.getInt(cursor.getColumnIndex("note"));
            obj.n = cursor.getString(cursor.getColumnIndex("note"));
            obj.d = cursor.getString(cursor.getColumnIndex("date"));
            noteList.add(obj);
            cursor.moveToNext();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(c,NoteNavigation.class);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder simple = new AlertDialog.Builder(c);
        simple.setTitle("Alert");
        simple.setMessage("Do You Really Want To Delete No?");
        simple.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Note n = noteList.get(position);
                db.execSQL("DELETE FROM notes WHERE id = "+n.id+"");
                noteList.remove(position);
                noteAdapter.notifyDataSetChanged();
                Toast.makeText(c, position+"", Toast.LENGTH_SHORT).show();
            }
        });
        simple.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(c, "CANCEL", Toast.LENGTH_SHORT).show();
            }
        });
        simple.setCancelable(false);
        //simple.create();
        simple.show();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
