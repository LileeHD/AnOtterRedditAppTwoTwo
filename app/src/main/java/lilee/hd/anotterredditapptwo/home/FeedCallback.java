package lilee.hd.anotterredditapptwo.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lilee.hd.anotterredditapptwo.model.Feed;
import lilee.hd.anotterredditapptwo.model.FeedData;
import lilee.hd.anotterredditapptwo.model.Post;
import lilee.hd.anotterredditapptwo.viewmodel.SubredditRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedCallback implements Callback<Feed> {
    private static final String TAG = "SubredditCallback";
    private SubredditRepository mRepository;
    private List<String> mNameList;

    private MutableLiveData<Feed> feedMutableLiveData = new MutableLiveData<>();
    private String mQuery;

    public FeedCallback() {
    }
    @Override
    public void onResponse(@NotNull Call<Feed> call, @NotNull Response<Feed> response) {
        Feed feed = response.body();
        FeedData post = feed != null ? feed.getData():null;
        if (response.isSuccessful()) {
            feedMutableLiveData.setValue(feed);
            Log.d("DAO OTTER", "onResponse: " + response.code());
            Log.d(TAG, "onResponse: " + feedMutableLiveData.getValue());
            Log.d(TAG, "onResponse: ");
        }
    }

    @Override
    public void onFailure(Call<Feed> call, Throwable t) {
        feedMutableLiveData.setValue(null);
        Log.d(TAG, "onFailure: "+ t);
    }
}
