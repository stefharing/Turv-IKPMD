package nl.stefharing.turv.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import nl.stefharing.turv.Database.DatabaseController;
import nl.stefharing.turv.R;

public class UserActivity extends AppCompatActivity {

    DatabaseController DB = new DatabaseController();
    String userName;
    String groupName;




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

        try{
            Thread.sleep(1500);
        } catch (Exception e){
            e.printStackTrace();
        }

        setContentView(R.layout.activity_user);
        TextView userNameView = findViewById(R.id.userName);

        userName = getIntent().getStringExtra("userName");
        groupName = getIntent().getStringExtra("groupName");

        userNameView.setText(userName);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("groups").child(groupName).child("people").child(userName).child("items");

        TextView bier = findViewById(R.id.bier_amount);
        TextView fris = findViewById(R.id.fris_amount);
        TextView wijn = findViewById(R.id.wijn_amount);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    switch (snapshot.getKey()){
                        case "bier":
                            bier.setText(snapshot.getValue().toString());
                        case "fris":
                            fris.setText(snapshot.getValue().toString());
                        case "wijn":
                            wijn.setText(snapshot.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("message", "error");
            }

        });
    }

    public void increaseItem(View v){

        String rawButtonId = v.getResources().getResourceName(v.getId());

        String[] parts_raw = rawButtonId.split("/");
        String itemName = parts_raw[1];

        String[] parts = itemName.split("_");
        String buttonIdItemName = parts[0];

        DB.increaseValue(groupName, userName, buttonIdItemName);
        updateallItemAmounts();

    }

    public void decreaseItem(View v){

        String rawButtonId = v.getResources().getResourceName(v.getId());

        String[] parts_raw = rawButtonId.split("/");
        String itemName = parts_raw[1];

        String[] parts = itemName.split("_");
        String buttonIdItemName = parts[0];

        Log.d("message", buttonIdItemName);

        DB.decreaseValue(groupName, userName, buttonIdItemName);


    }

    public void updateallItemAmounts(){
    }

}