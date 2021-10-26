package nl.stefharing.turv.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import nl.stefharing.turv.Adapters.PersonRecyclerViewAdapter;
import nl.stefharing.turv.R;

public class GroupActivity extends AppCompatActivity implements PersonRecyclerViewAdapter.ItemClickListener2 {

    PersonRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        TextView groepNaam = findViewById(R.id.extraText);

        Intent i = new Intent();
        String text = i.getStringExtra("button");

        String naam = getIntent().getStringExtra("groepNaam");

        groepNaam.setText(naam);

        // data to populate the RecyclerView with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Naam 1");
        animalNames.add("Naam 2");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.person_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PersonRecyclerViewAdapter(this, animalNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d("message", "hello");
    }
}