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

    private GroupRecyclerViewAdapter adapter;
    private String groupname;
    private DatabaseController DB = new DatabaseController();
    private ArrayList<String> groupNameList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groupNameList = DB.getArrayFromGroupNames();
        adapter = new GroupRecyclerViewAdapter(this, groupNameList);

        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.groups_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent i = new Intent(this, GroupActivity.class);

        String naam = adapter.getItem(position);

        i.putExtra("groepNaam", naam);
        startActivity(i);

    }

    public void addGroupButton(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Geef je groep een naam");

        EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

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