package data;

/**
 * Created by vsywn9 on 4/4/2017.
 */
public class Post {

    private String username;
    private String text;
    private String time;

    public Post(String username, String text, String time) {
        this.username = username;
        this.text = text;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
