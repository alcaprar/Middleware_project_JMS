package middleware;

import java.util.ArrayList;
import java.util.HashMap;

public class Users {

    private HashMap<String, ArrayList<String>> users = new HashMap<String, ArrayList<String>>();

    Users(){
        ArrayList<String> following = new ArrayList<String>();
        following.add("alex");
        following.add("jorge");
        users.put("ale", following);

        ArrayList<String> following1 = new ArrayList<String>();
        following1.add("jorge");
        users.put("alex", following1);

        ArrayList<String> following2 = new ArrayList<String>();
        following2.add("alex");
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
}
