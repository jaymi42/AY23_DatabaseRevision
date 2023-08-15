package com.myapplicationdev.databaserevision;

import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RetrieveActivityTextView extends AppCompatActivity {

    Button btnGetNotes;
    TextView tvResults;
    ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_tv);

        btnGetNotes = findViewById(R.id.btnGetTasks);
        tvResults = findViewById(R.id.tvResults);

        btnGetNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(RetrieveActivityTextView.this);

                ArrayList<String> noteStrings = dbh.getNotesInStrings();

                dbh.close();

                String text = "";
                for (int i = 0; i < noteStrings.size(); i++) {
                    String note = noteStrings.get(i);
                    text += (i + 1) + ". " + note + "\n";
                }
                tvResults.setText(text);
            }
        });


    }
}