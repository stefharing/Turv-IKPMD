package nl.stefharing.turv.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import nl.stefharing.turv.Adapters.GroupRecyclerViewAdapter;
import nl.stefharing.turv.Database.DatabaseController;
import nl.stefharing.turv.R;

public class MainActivity extends AppCompatActivity implements GroupRecyclerViewAdapter.ItemClickListener {

    GroupRecyclerViewAdapter adapter;
    ArrayList<String> animalNames = new ArrayList<>();

    DatabaseController DB = new DatabaseController();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // data to populate the RecyclerView with
        animalNames.add("Groep 1");
        animalNames.add("Groep 2");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.groups_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Instantiate adapter
        adapter = new GroupRecyclerViewAdapter(this, animalNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onItemClick(View view, int position) {
        Intent i = new Intent(this, GroupActivity.class);

        //Get groupname from clicked group
        String naam = adapter.getItem(position);

        //Give clicked groupname to new activity
        i.putExtra("groepNaam", naam);
        startActivity(i);
    }

    public void addGroupButton(View v){
        v.setEnabled(true);


    }



}