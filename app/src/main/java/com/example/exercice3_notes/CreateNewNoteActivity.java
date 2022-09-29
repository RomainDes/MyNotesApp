package com.example.exercice3_notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class CreateNewNoteActivity extends AppCompatActivity {


    SharedPreferences sharedPreferencesMyNotes;
    SharedPreferences sharedPreferencesActualNote;

    public EditText nameText;
    public EditText bodyText;
    public String popUp;

    Button saveButton;
    Button cancelButton;

    int noteId;

    public String myNotes = "mynotes2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_note);
        //récupérer toutes les notes lors du lancement de l'application

        saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                sharedPreferencesMyNotes = getApplicationContext().getSharedPreferences(myNotes, 0);
                sharedPreferencesActualNote = getApplicationContext().getSharedPreferences(nameText.getText().toString(), 0);

                sharedPreferencesActualNote.edit().putString("name", nameText.getText().toString()).apply();
                sharedPreferencesActualNote.edit().putString("body", bodyText.getText().toString()).apply();

                sharedPreferencesMyNotes.edit().putString(nameText.getText().toString(), nameText.getText().toString()).apply();

                /*MainActivity.nameNotes.set(noteId, nameText.getText().toString());
                MainActivity.bodyNotes.set(noteId, bodyText.getText().toString());
                MainActivity.adapter.notifyDataSetChanged();

                Set<String> nameSet = new HashSet<String>(MainActivity.nameNotes);
                Set<String> bodySet = new HashSet<String>(MainActivity.bodyNotes);

                sharedPreferences.edit().putStringSet("name", nameSet).apply();
                sharedPreferences.edit().putStringSet("body", bodySet).apply();*/
                MainActivity.deletingNotes = false;
                Toast.makeText(CreateNewNoteActivity.this, popUp, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CreateNewNoteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        cancelButton = findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                MainActivity.deletingNotes = false;
                Intent intent = new Intent(CreateNewNoteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        nameText = (EditText) findViewById(R.id.name);
        bodyText = (EditText) findViewById(R.id.body);



        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        if (noteId != -1) {
            nameText.setText(MainActivity.nameNotes.get(noteId));
            bodyText.setText(MainActivity.bodyNotes.get(noteId));
            popUp = "Note has been modified";
        } else {
            popUp = "Note has been added";
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        // first parameter is the file for icon and second one is menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // We are using switch case because multiple icons can be kept
        switch (item.getItemId()) {
            case R.id.shareButton:

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);

                // type of the content to be shared
                sharingIntent.setType("text/plain");

                // Body of the content
                String shareBody = bodyText.getText().toString();

                // subject of the content. you can share anything
                String shareSubject = nameText.getText().toString();

                // passing body of the content
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                // passing subject of the content
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
