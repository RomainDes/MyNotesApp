package com.example.exercice3_notes;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.Context;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    TextView emptyTv;

    static boolean deletingNotes = false;

    static ArrayList<String> nameNotes;
    static ArrayList<String> bodyNotes;

    private Button buttonNewNote;
    private Button buttonDeleteNote;
    private Button buttonShareNote;
    private MaterialCardView colorRow;

    public static final int custom_notes_row = 1300003;
    public static final int custom_notes_row_delete = 1300000;

    SharedPreferences sharedPreferencesMyNotes;
    SharedPreferences sharedPreferencesActualNote;


    public String myNotes = "mynotes2";

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

        buttonDeleteNote = (Button) findViewById(R.id.delete);
        buttonDeleteNote.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if(!deletingNotes){
                    deletingNotes = true;

                }
                else{

                    deletingNotes = false;
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        sharedPreferencesMyNotes = this.getSharedPreferences(myNotes, 0);

        //notesListView = (ListView) findViewById(R.id.listview);
        emptyTv = (TextView) findViewById(R.id.emptyTv);

        nameNotes = new ArrayList<>();
        bodyNotes = new ArrayList<>();

        Map<String, ?> MapNotes = sharedPreferencesMyNotes.getAll();
        for (Map.Entry<String, ?> entry : MapNotes.entrySet()) {
            sharedPreferencesActualNote = this.getSharedPreferences(entry.getValue().toString(), 0);
            nameNotes.add(0,sharedPreferencesActualNote.getString("name", null));
            bodyNotes.add(0,sharedPreferencesActualNote.getString("body", null));
        }




        if(nameNotes.isEmpty() || nameNotes == null){
            emptyTv.setVisibility(View.VISIBLE);
        }
        else{
            emptyTv.setVisibility(View.GONE);
        }

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, nameNotes, bodyNotes);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int position) {
        if(deletingNotes){
            int itemToDelete = position;

            sharedPreferencesActualNote = this.getSharedPreferences(nameNotes.get(itemToDelete), 0);
            sharedPreferencesMyNotes = this.getSharedPreferences(myNotes, 0);

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Are you sure ?")
                    .setMessage("Do you want to delete this note ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String n = nameNotes.remove(itemToDelete);
                            bodyNotes.remove(itemToDelete);
                            adapter.notifyDataSetChanged();


                            sharedPreferencesMyNotes.edit().remove(n).apply();


                            sharedPreferencesActualNote.edit().clear().apply();

                            Toast.makeText(MainActivity.this, "Note has been deleted", Toast.LENGTH_LONG).show();
                            if(nameNotes.isEmpty() || nameNotes == null){
                                emptyTv.setVisibility(View.VISIBLE);
                            }
                        }
                    }).setNegativeButton("No", null)
                    .show();
        }else{
            Intent intent = new Intent(getApplicationContext(), CreateNewNoteActivity.class);
            intent.putExtra("noteId", position);
            startActivity(intent);
        }


    }

}