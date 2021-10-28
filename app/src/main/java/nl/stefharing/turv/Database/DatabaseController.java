package nl.stefharing.turv.Database;

import android.util.Log;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseController {

    String itemAmount;

    public void addGroup(String groupName) {

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("groups/" + groupName + "/people/");

        database.setValue(groupName);

    }

    public void addPersonToGroup(String groupName, String userName) {

        Map<String, Object> PersonMap = new HashMap<String, Object>();

        PersonMap.put("bier", 0);
        PersonMap.put("wijn", 0);
        PersonMap.put("fris", 0);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("groups/" + groupName + "/people/" + userName + "/items/");

        myRef.setValue(PersonMap);
    }

    public void removePersonFromGroup(String groupName, String userName) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("groups/" + groupName + "/people/" + userName);
        myRef.removeValue();

    }

    public void removeGroupFromDB(String groupName) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("groups/" + groupName);
        myRef.removeValue();

    }

    public ArrayList<String> getArrayFromGroupNames() {
        // get data from database
        // filter groupnames
        // add groupnames to arraylist

        ArrayList<String> groupNamesArrayList = new ArrayList<>();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        myRef.child("groups").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> dataSnapshot) {

                if (dataSnapshot.getResult().getChildrenCount() > 0) {

                    //Retrieve HashMap from database
                    Map<String, Object> groupMap = (HashMap<String, Object>) dataSnapshot.getResult().getValue();

                    //Convert HashMap to ArrayList with keys
                    for (String i : groupMap.keySet()) {
                        groupNamesArrayList.add(i);
                    }
                }
            }
        });

        return groupNamesArrayList;
    }

    public ArrayList<String> getArrayFromPersonNames(String groupName) {
        // get data from database
        // filter PersonNames
        // add PersonNames to arraylist

        ArrayList<String> personNamesArrayList = new ArrayList<>();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        myRef.child("groups").child(groupName).child("people").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> dataSnapshot) {

                if (dataSnapshot.getResult().getChildrenCount() > 0) {
                    //Retrieve HashMap from database
                    Map<String, Object> peopleMap = (HashMap<String, Object>) dataSnapshot.getResult().getValue();

                    //Convert HashMap to ArrayList with keys

                    for (String i : peopleMap.keySet()) {
                        personNamesArrayList.add(i);
                    }
                }
            }
        });

        Log.d("MESSAGE", "test");

        return personNamesArrayList;
    }

    public void increaseValue(String groupName, String userName, String key) {

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("groups/" + groupName + "/people/" + userName + "/items/" + key);
        myRef.setValue(ServerValue.increment(1));


    }

    public void decreaseValue(String groupName, String userName, String key) {

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("groups/" + groupName + "/people/" + userName + "/items/" + key);
        myRef.setValue(ServerValue.increment(-1));

    }
}

