package nl.stefharing.turv.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;
import nl.stefharing.turv.Adapters.GroupRecyclerViewAdapter;
import nl.stefharing.turv.Database.DatabaseController;
import nl.stefharing.turv.R;


public class MainActivity extends AppCompatActivity implements GroupRecyclerViewAdapter.ItemClickListener {

    GroupRecyclerViewAdapter adapter;
    String groupname;
    DatabaseController DB = new DatabaseController();
    ArrayList<String> groupNameList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load groups before layout
        groupNameList = DB.getArrayFromGroupNames();
        adapter = new GroupRecyclerViewAdapter(this, groupNameList);

        // Load layout
        setContentView(R.layout.activity_main);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.groups_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Instantiate adapter
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

    public void addGroupButton(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        // Set up the input
        final EditText input = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Voeg toe", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                groupname = input.getText().toString();

                DB.addGroup(groupname);
                groupNameList.add(groupname);

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