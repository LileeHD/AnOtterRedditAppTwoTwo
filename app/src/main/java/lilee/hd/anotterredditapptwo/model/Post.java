package lilee.hd.anotterredditapptwo.model;

import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//    @Entity(tableName = "post_table")
public class Post {
    //    Media
    private Media media;
    private String id;

    @SerializedName("subreddit_name_prefixed")
    @Expose
    private String subredditR;
    @SerializedName("author")
    @Expose
    private String author;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("selftext")
    @Expose
    private String body;

    @SerializedName("url")
    @Expose
    private String imageUrl;

    private String videoUrl;
    @SerializedName("is_video")
    private boolean isVideo = false;

    @SerializedName("permalink")
    private String postUrl;

    private Boolean expanded;

//    ----------------------------------------Getter Setter----------------------------------------

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubredditR() {
        return subredditR;
    }

    public void setSubredditR(String subredditR) {
        this.subredditR = subredditR;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        if (videoUrl == null) {
            RedditVideo redditVideo = media != null ? media.getRedditVideo() : null;
            videoUrl = redditVideo != null ? redditVideo.getUrl() : null;
        }
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        this.isVideo = video;
    }
}
