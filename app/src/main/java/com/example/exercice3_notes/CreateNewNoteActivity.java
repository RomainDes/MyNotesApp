package com.example.exercice3_notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class CreateNewNoteActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;

    public EditText nameText;
    public EditText bodyText;
    public String popUp;

    Button saveButton;
    Button cancelButton;

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_note);
        //récupérer toutes les notes lors du lancement de l'application

        saveButton = findViewById(R.id.save);
        saveNote();


        cancelButton = findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(CreateNewNoteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        nameText = (EditText) findViewById(R.id.name);
        bodyText = (EditText) findViewById(R.id.body);

        //getting preferences from a specified file
        sharedPreferences = this.getSharedPreferences("mynotes", Context.MODE_PRIVATE);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        if (noteId != -1) {
            nameText.setText(MainActivity.nameNotes.get(noteId));
            bodyText.setText(MainActivity.bodyNotes.get(noteId));
            popUp = "Note has been modified";
        } else {
            MainActivity.nameNotes.add("");
            MainActivity.bodyNotes.add("");
            noteId = MainActivity.nameNotes.size() - 1;
            popUp = "Note has been added";
        }
    }

    public void saveNote(){
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                MainActivity.nameNotes.set(noteId, nameText.getText().toString());
                MainActivity.bodyNotes.set(noteId, bodyText.getText().toString());
                MainActivity.adapter.notifyDataSetChanged();


                HashSet<String> nameSet = new HashSet<String>(MainActivity.nameNotes);
                HashSet<String> bodySet = new HashSet<String>(MainActivity.bodyNotes);

                sharedPreferences.edit().putStringSet("name", nameSet).apply();
                sharedPreferences.edit().putStringSet("body", bodySet).apply();

                Toast.makeText(CreateNewNoteActivity.this, popUp, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CreateNewNoteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
