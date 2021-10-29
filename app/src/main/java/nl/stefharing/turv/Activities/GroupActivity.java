package nl.stefharing.turv.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import nl.stefharing.turv.Adapters.PersonRecyclerViewAdapter;
import nl.stefharing.turv.Database.DatabaseController;
import nl.stefharing.turv.R;

public class GroupActivity extends AppCompatActivity implements PersonRecyclerViewAdapter.ItemClickListener2 {

    private PersonRecyclerViewAdapter adapter;
    private DatabaseController DB = new DatabaseController();
    private String userName = "";
    private String groupName = "";
    private ArrayList<String> personNameList;

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

        TextView groepNaamView = findViewById(R.id.groupNameTextField);
        groupName = getIntent().getStringExtra("groepNaam");
        groepNaamView.setText(groupName);

        personNameList = DB.getArrayFromPersonNames(groupName);
        adapter = new PersonRecyclerViewAdapter(this, personNameList);
        RecyclerView recyclerView = findViewById(R.id.person_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        try {
            Thread.sleep(1000);
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
        builder.setTitle("Naam");

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