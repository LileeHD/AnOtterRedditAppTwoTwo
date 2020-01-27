package lilee.hd.anotterredditapptwo.viewmodel;

import android.app.ActivityManager;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import lilee.hd.anotterredditapptwo.reddit.RedditService;
import lilee.hd.anotterredditapptwo.model.Feed;
import lilee.hd.anotterredditapptwo.reddit.RedditAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RedditRepository {

    private static final String TAG = "RedditRepository";
    private static RedditRepository sInstance;
    private RedditAPI redditAPI;


    public RedditRepository() {
        redditAPI = RedditService.createService(RedditAPI.class);
    }

    public synchronized static RedditRepository getInstance() {
        if (sInstance == null) {
            sInstance = new RedditRepository();
        }
        return sInstance;
    }

    public MutableLiveData<Feed> getFeed() {
        MutableLiveData<Feed> feedMutableLiveData = new MutableLiveData<>();
        redditAPI.getHomeFeed().enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                if (response.isSuccessful()) {
                    feedMutableLiveData.postValue(response.body());
                }

            }
            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                feedMutableLiveData.postValue(null);
                t.printStackTrace();
            }
        });
        return feedMutableLiveData;
    }

    public MutableLiveData<Feed> getUserFeed(String queryString) {
        MutableLiveData<Feed> feedMutableLiveData = new MutableLiveData<>();
        redditAPI.getMyFeed(queryString).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                if (response.isSuccessful()) {
                    feedMutableLiveData.postValue(response.body());
                    Log.d("DAO OTTER", "onResponse: " + response.code());

                    Log.d(TAG, "onResponse: " + feedMutableLiveData.getValue());
                }
            }
            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                feedMutableLiveData.postValue(null);
                Log.d(TAG, "onFailure: "+ t);
                t.printStackTrace();
            }
        });
        return feedMutableLiveData;
    }



    public MutableLiveData<Feed> getWidgetFeed() {
        MutableLiveData<Feed> feedMutableLiveData = new MutableLiveData<>();
        redditAPI.getWidgetPost().enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                if (response.isSuccessful()) {
                    feedMutableLiveData.postValue(response.body());
                }

            }
            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                feedMutableLiveData.postValue(null);
                t.printStackTrace();
            }
        });
        return feedMutableLiveData;
    }

}
