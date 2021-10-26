package nl.stefharing.turv.Database;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DatabaseController {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public void addGroup(String name){

        Map<String, Object> GroupMap = new HashMap<String, Object>();
        Map<String,Object> PersonMap = new HashMap<String, Object>();

        GroupMap.put("Person", PersonMap);

        DatabaseReference myRef = database.getReference(name);

        myRef.setValue(GroupMap);
    }

    public void addPersonToGroup(String groupName, String userName, int age, int bier, int wijn, int fris) {
        Map<String, Object> PersonMap = new HashMap<String, Object>();

        PersonMap.put("age", age);
        PersonMap.put("bier", bier);
        PersonMap.put("wijn", wijn);
        PersonMap.put("fris", fris);

        DatabaseReference myRef = database.getReference(groupName + "/" + userName);

        myRef.setValue(PersonMap);
    }



}
