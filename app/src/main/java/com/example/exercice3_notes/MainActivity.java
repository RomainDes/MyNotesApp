package com.example.exercice3_notes;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ListView notesListView;
    TextView emptyTv;

    static List<String> nameNotes;
    static List<String> bodyNotes;
    static ArrayAdapter adapter;

    private Button buttonNewNote;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //récupérer toutes les notes lors du lancement de l'application
        buttonNewNote = (Button) findViewById(R.id.newNote);
        buttonNewNote.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateNewNoteActivity.class);
                startActivity(intent);
            }
        });

        sharedPreferences = this.getSharedPreferences("mynotes", Context.MODE_PRIVATE);

        notesListView = (ListView) findViewById(R.id.listview);
        emptyTv = (TextView) findViewById(R.id.emptyTv);

        nameNotes = new ArrayList<>();
        bodyNotes = new ArrayList<>();

        HashSet<String> nameSet = (HashSet<String>) sharedPreferences.getStringSet("name", null);
        HashSet<String> bodySet = (HashSet<String>) sharedPreferences.getStringSet("body", null);

        Log.d ("TESTER", String.valueOf(nameNotes.size()));
        if(nameSet.isEmpty() || nameSet == null){
            emptyTv.setVisibility(View.VISIBLE);
        }
        else{
            emptyTv.setVisibility(View.GONE);
            nameNotes = new ArrayList<>(nameSet);
            bodyNotes = new ArrayList<>(bodySet);
        }


        adapter = new ArrayAdapter(getApplicationContext(), R.layout.custom_notes_row, R.id.notesTV, nameNotes);
        notesListView.setAdapter(adapter);

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), CreateNewNoteActivity.class);
                intent.putExtra("noteId", i);
                startActivity(intent);
            }
        });

        notesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itemToDelete = i;
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Are you sure ?")
                        .setMessage("Do you want to delete this note ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                nameNotes.remove(itemToDelete);
                                bodyNotes.remove(itemToDelete);
                                adapter.notifyDataSetChanged();



                                HashSet<String> noteSet = new HashSet<String>(nameNotes);
                                HashSet<String> bodySet = new HashSet<String>(bodyNotes);


                                sharedPreferences.edit().putStringSet("name", noteSet).apply();
                                sharedPreferences.edit().putStringSet("body", bodySet).apply();

                                if(nameNotes.isEmpty() || nameNotes == null){
                                    emptyTv.setVisibility(View.VISIBLE);
                                }
                            }
                        }).setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }



}