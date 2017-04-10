package entity;

import org.bson.Document;

import java.io.Serializable;

/**
 * Created by vsywn9 on 4/10/2017.
 */
public class Post implements Serializable{

    private String username;
    private String text;
    private String time;
    private String imageName;

    public Post(){

    }

    public Post(Document doc){
        this.username = doc.getString("username");
        this.text = doc.getString("text");
        this.time = doc.getString("time");
        this.imageName = doc.getString("imageName");
    }

    public Post(String text, String time){
        this.text = text;
        this.time = time;
        this.imageName = "";
    }

    public Post(String username, String text, String time, String imageName) {
        this.username = username;
        this.text = text;
        this.time = time;
        this.imageName = imageName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Document toDocument(){
        Document doc = new Document();
        doc.put("username", this.username);
        doc.put("text", this.text);
        doc.put("time", this.time);
        doc.put("imageName", this.imageName);
        return doc;
    }
}
