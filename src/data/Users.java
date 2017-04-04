package data;

import java.util.ArrayList;
import java.util.HashMap;

public class Users {

    private HashMap<String, ArrayList<String>> users = new HashMap<String, ArrayList<String>>();

    public Users(){
        ArrayList<String> following = new ArrayList<String>();
        following.add("ale");
        following.add("alex");
        following.add("jorge");
        users.put("ale", following);

        ArrayList<String> following1 = new ArrayList<String>();
        following1.add("jorge");
        following1.add("ale");
        users.put("alex", following1);

        ArrayList<String> following2 = new ArrayList<String>();
        following2.add("alex");
        following2.add("ale");
        users.put("jorge", following2);

        System.out.println(users);
    }

    public ArrayList<String> getFollowing(String username){
        ArrayList<String> following = null;

        following = (ArrayList<String>) users.get(username);

        return following;
    }

    public ArrayList<String> getFollowers(String username){
        ArrayList<String> followers = new ArrayList<String>();

        // Iterating over keys only
        for (String key : users.keySet()) {
            ArrayList<String> following = users.get(key);
            if (following.contains(username)) {
                followers.add(key);
            }
        }
        return followers;
    }

    public Boolean isValidUsername(String username){
        if(username.equals("ale")){
            return true;
        }else if(username.equals("alex")){
            return true;
        }else if(username.equals("jorge")){
            return true;
        }else {
            return false;
        }
    }
}
