package nl.stefharing.turv.Database;

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

    public ArrayList<String> getArrayFromGroupNames() {

        ArrayList<String> groupNamesArrayList = new ArrayList<>();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        myRef.child("groups").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> dataSnapshot) {

                if (dataSnapshot.getResult().getChildrenCount() > 0) {
                    Map<String, Object> groupMap = (HashMap<String, Object>) dataSnapshot.getResult().getValue();

                    for (String i : groupMap.keySet()) {
                        groupNamesArrayList.add(i);
                    }
                }
            }
        });

        return groupNamesArrayList;
    }

    public ArrayList<String> getArrayFromPersonNames(String groupName) {
        ArrayList<String> personNamesArrayList = new ArrayList<>();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        myRef.child("groups").child(groupName).child("people").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> dataSnapshot) {

                if (dataSnapshot.getResult().getChildrenCount() > 0) {
                    Map<String, Object> peopleMap = (HashMap<String, Object>) dataSnapshot.getResult().getValue();

                    for (String i : peopleMap.keySet()) {
                        personNamesArrayList.add(i);
                    }
                }
            }
        });

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

