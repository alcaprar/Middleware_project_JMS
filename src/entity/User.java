package entity;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.IndexOptions;
import org.bson.*;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

/**
 * Created by vsywn9 on 4/10/2017.
 */
public class User {

    private ObjectId _id = null;
    private String username;
    private ArrayList<String> followers;
    private ArrayList<String> fans;
    private ArrayList<Post> timeline ;

    private MongoCollection collection;
    private Document document;

    public User(){
        // Since 2.10.0, uses MongoClient
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        MongoDatabase db = mongoClient.getDatabase("middleware");
        this.collection = db.getCollection("user");
        this.collection.createIndex(new BasicDBObject("username", 1), new IndexOptions().unique(true));
        this.document = new Document();

        this.timeline = new ArrayList<Post>();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.document.put("username", username);
        this.username = username;
    }

    public ArrayList<String> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<String> followers) {
        this.document.put("followers", followers);
        this.followers = followers;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public ArrayList<String> getFans() {
        return fans;
    }

    public void setFans(ArrayList<String> fans) {
        this.fans = fans;
    }

    public MongoCollection getCollection() {
        return collection;
    }

    public void setCollection(MongoCollection collection) {
        this.collection = collection;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public ArrayList<Post> getTimeline() {
        return timeline;
    }

    public void setTimeline(ArrayList<Post> timeline) {
        ArrayList<Document> timelineDoc = new ArrayList<Document>();
        for(Post p : timeline){
            timelineDoc.add(p.toDocument());
        }
        this.document.put("timeline", timelineDoc);
        this.timeline = timeline;
    }

    public void addPostToTimeline(Post post){
        if(this._id==null){
            System.out.println("User not loaded yet.");
        }else{
            System.out.println("Adding post to user. " + this._id);
            Document findQuery = new Document("_id", this._id);
            Document updateQuery = new Document("$push", new Document("timeline", post.toDocument()));
            this.collection.updateOne(findQuery, updateQuery);
        }
    }

    public void save(){
        if(this._id!=null){
            //update
        }else{
            this.getCollection().insertOne(document);
        }
    }

    public void load(String username){
        Document where = new Document();
        where.put("username", username);
        MongoCursor<Document> cursor = this.getCollection().find(where).iterator();
        while(cursor.hasNext()){
            Document doc = cursor.next();
            this.set_id(doc.getObjectId("_id"));
            this.setUsername(doc.getString("username"));
            this.setFollowers((ArrayList<String>) doc.get("followers"));
            ArrayList<Document> timelineDoc = (ArrayList<Document>) doc.get("timeline");
            if(timelineDoc!=null){
                ArrayList<Post> timeline = new ArrayList<Post>();
                for(Document t : timelineDoc){
                    Post p = new Post(t);
                    timeline.add(p);
                }
                this.setTimeline(timeline);
            }

            //find fans
            MongoCursor<Document> c = this.getCollection().find().iterator();
            ArrayList<String> fans =  new ArrayList<String>();
            while (c.hasNext()){
                Document d = c.next();
                ArrayList<String> followers = (ArrayList<String>) d.get("followers");
                if(followers!=null){
                    for(String f : followers){
                        if(username.equals(f)){
                            fans.add(d.getString("username"));
                        }
                    }
                }
            }
            this.setFans(fans);
        }
    }
}
