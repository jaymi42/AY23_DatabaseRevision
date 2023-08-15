package com.myapplicationdev.databaserevision;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RetrieveActivityListView extends AppCompatActivity {

    Button btnGetNotes;

    ListView lv;
    ArrayAdapter<Note> aa;
    ArrayList<Note> al;
    Note data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_lv);

        btnGetNotes = findViewById(R.id.btnGetTasks);
        lv = findViewById(R.id.lv);

        al = new ArrayList<>();
        aa = new ArrayAdapter<Note>(RetrieveActivityListView.this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);
        registerForContextMenu(lv);


        btnGetNotes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Create the DBHelper object, passing in the activity's Context
                DBHelper dbh = new DBHelper(RetrieveActivityListView.this);

                al.clear();
                al.addAll(dbh.getNotesInObjects());
                aa.notifyDataSetChanged();

            }
        });



        //Option: Implement dialog to edit a record
        //Option: Implement context to delete a record
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
//
                DBHelper dbh = new DBHelper(RetrieveActivityListView.this);
                al.clear();
                al.addAll(dbh.getNotesInObjects());
                data = al.get(position);

                // Inflate the input.xml layout file
                LayoutInflater inflater =
                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.input, null);

                // Obtain the UI component in the input.xml layout
                // It needs to be defined as "final", so that it can used in the onClick() method later
                final EditText etContent = viewDialog.findViewById(R.id.editTextContent);
                final EditText etPri = viewDialog.findViewById(R.id.editTextPriority);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(RetrieveActivityListView.this);
                myBuilder.setView(viewDialog);  // Set the view of the dialog
                myBuilder.setTitle("Edit Note");
                myBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Extract the text entered by the user
                        String content = etContent.getText().toString();
                        int priority = Integer.parseInt(etPri.getText().toString());
                        data.setContent(content);
                        data.setPriority(String.valueOf(priority));
                        dbh.updateTask(data);
                        btnGetNotes.callOnClick();




                        // Set the text to the TextView
//                        tvDemo3.setText(message);
                    }
                });
                myBuilder.setNegativeButton("CANCEL", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }

        });




    }
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0,1,0,"Delete");


    }

    public boolean onContextItemSelected(MenuItem item) {

            if (item.getItemId() == 1) { //check whether the selected menu item ID is 0

               AlertDialog.Builder myBuilder = new AlertDialog.Builder(RetrieveActivityListView.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the content?");

                myBuilder.setCancelable(false);



                myBuilder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                myBuilder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(RetrieveActivityListView.this);

                        dbh.deleteTask(data.getId());

                        finish();
//
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();


            }

        return true; //menu item successfully handled
}
}