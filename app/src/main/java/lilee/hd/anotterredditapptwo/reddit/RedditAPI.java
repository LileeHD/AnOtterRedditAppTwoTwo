package lilee.hd.anotterredditapptwo.reddit;

import lilee.hd.anotterredditapptwo.model.Feed;
import lilee.hd.anotterredditapptwo.model.SubredditNode;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface RedditAPI {
    /**
     * Home data
     *
     * @return
     */
    @Headers("Content-Type: application/json")

    // home display not logged in
//    @GET("r/otters/.json?&sort=new&raw_json=1&type=sr")
//    @GET(".json?t=new&raw_json=1&type=link")
    @GET(".json?t=new&raw_json=1&type=link")
    Call<Feed> getHomeFeed();

    // editText
    @GET("r/{subreddit}/about.json?&sort=new&raw_json=1&type=sr")
    Call<SubredditNode> getSubreddit(@Path("subreddit") String subreddit);

    @GET("r/{subreddit_name}/new.json")
    Call<Feed> getMyFeed(@Path("subreddit_name") String subredditList);

    @GET("r/{subreddit_name}/new.json&raw_json=1&limit=1")
    Call<Feed> getWidgetPost(@Path("subreddit_name") String subredditList);
}
