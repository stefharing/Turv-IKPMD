package nl.stefharing.turv;

import java.util.ArrayList;

public class Group {
    private String groupName;
    private ArrayList<Person> memberlist;

    public Group(String groupName, ArrayList memberlist) {
        this.groupName = groupName;
        this.memberlist = memberlist;
    }

    public String getGroupName() {
        return groupName;
    }

    public ArrayList getMembers() {
        return memberlist;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void addPersonToMemberList(Person person){
        memberlist.add(person);
    }

    public void removePersonFromMemberList(Person person){
        memberlist.remove(person);
    }
}




