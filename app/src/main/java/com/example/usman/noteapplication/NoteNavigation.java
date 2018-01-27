package com.example.usman.noteapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Usman on 1/25/2018.
 */
public class NoteNavigation extends AppCompatActivity implements View.OnClickListener{

    private EditText updatenote;
    private Button update;
    private SharedPreferences shared;
    private SQLiteDatabase db;
    private String ss;
    private Cursor cursor;
    int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_note);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        updatenote = (EditText)findViewById(R.id.updatenote);
        update= (Button)findViewById(R.id.update);
        update.setOnClickListener(this);
        db = openOrCreateDatabase("note",MODE_PRIVATE,null);
        shared = getSharedPreferences("loginhandling",MODE_PRIVATE); // need a where condition here
        ss = shared.getString("email","").toString();
        cursor = db.rawQuery("SELECT note , date, id from Allnotes where email ='"+ss+"' ", null);
        cursor.moveToFirst();
        updatenote.setText(cursor.getString(cursor.getColumnIndex("note")));
        id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
    }

    @Override
    public void onClick(View v) {

        String p = updatenote.getText().toString();

        db.execSQL("UPDATE Allnotes SET note = '"+p+"' where id ='"+id+"'  ");
        Intent i = new Intent(this, NoteActivity.class);
        startActivity(i);

    }
}
