package data;

/**
 * Created by vsywn9 on 4/4/2017.
 */
public class Post {

    private String username;
    private String text;
    private String time;
    private String imageName;

    public Post(String username, String text, String time, String imageName) {
        this.username = username;
        this.text = text;
        this.time = time;
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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
