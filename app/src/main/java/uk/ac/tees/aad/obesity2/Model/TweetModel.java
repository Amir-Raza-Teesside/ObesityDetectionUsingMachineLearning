package uk.ac.tees.aad.obesity2.Model;

public class TweetModel {

    private String authorname;
    private String email;
    private String tweet;
    private String likeCounter;

    public TweetModel(String authorname, String email, String tweet, String likeCounter) {
        this.authorname = authorname;
        this.email = email;
        this.tweet = tweet;
        this.likeCounter = likeCounter;
    }

    public TweetModel() {
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getLikeCounter() {
        return likeCounter;
    }

    public void setLikeCounter(String likeCounter) {
        this.likeCounter = likeCounter;
    }
}
