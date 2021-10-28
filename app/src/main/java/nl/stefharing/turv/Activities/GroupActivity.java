package nl.stefharing.turv.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import nl.stefharing.turv.Adapters.PersonRecyclerViewAdapter;
import nl.stefharing.turv.Database.DatabaseController;
import nl.stefharing.turv.R;

public class GroupActivity extends AppCompatActivity implements PersonRecyclerViewAdapter.ItemClickListener2 {

    PersonRecyclerViewAdapter adapter;
    DatabaseController DB = new DatabaseController();
    MainActivity main = new MainActivity();


    private String userName = "";
    private String groupName = "";
    ArrayList<String> personNameList;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        TextView groepNaamView = findViewById(R.id.extraText);
        groupName = getIntent().getStringExtra("groepNaam");
        groepNaamView.setText(groupName);

        // Give personName list to adapter
        personNameList = DB.getArrayFromPersonNames(groupName);
        adapter = new PersonRecyclerViewAdapter(this, personNameList);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.person_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initiate adapter
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent i = new Intent(this, UserActivity.class);

        //Get username from clicked group
        String naam = adapter.getItem(position);

        //Give clicked username to new activity
        i.putExtra("userName", naam);
        i.putExtra("groupName", groupName);
        startActivity(i);
    }

    public void addPersonButton(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        // Set up the inputs
        final EditText nameInput = new EditText(this);
        nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(nameInput);

        // Set up the buttons
        builder.setPositiveButton("Voeg toe", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userName = nameInput.getText().toString();

                DB.addPersonToGroup(groupName, userName);
                personNameList.add(userName);

                adapter.notifyDataSetChanged();
            }

        });
        builder.setNegativeButton("Stop", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();


    }
}