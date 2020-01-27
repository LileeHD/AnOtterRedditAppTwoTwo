package lilee.hd.anotterredditapptwo.viewmodel;

import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import lilee.hd.anotterredditapptwo.database.SubredditDB;
import lilee.hd.anotterredditapptwo.home.FeedCallback;
import lilee.hd.anotterredditapptwo.model.CustomQuery;
import lilee.hd.anotterredditapptwo.model.Feed;
import lilee.hd.anotterredditapptwo.model.Post;
import lilee.hd.anotterredditapptwo.model.Subreddit;
import lilee.hd.anotterredditapptwo.search.SubredditCallback;
import lilee.hd.anotterredditapptwo.search.SubredditNetworking;

public class SubredditViewModel extends ViewModel {
    private static final String TAG = "SubredditViewModel";
    private SubredditRepository mRepository;
    private SubredditNetworking mNetworking;
    private LiveData<List<Subreddit>> mSubList;
    private LiveData<List<String>> mImplode;
    private SubredditDB database;
    private Executor executor;
    private SubredditNetworking networking;

    private final MutableLiveData<Post> currentPost = new MutableLiveData<>();
    private final MutableLiveData<Subreddit> currentSubreddit = new MutableLiveData<>();
    private MutableLiveData<Feed> mutableLiveData;
    private MutableLiveData<Feed> userFeed;

    private ArrayList<String> mNames = new ArrayList<>();
    private String string ="";


    public SubredditViewModel(SubredditRepository repository) {
        this.mRepository = repository;
        mNetworking = repository.getNetworking();
        mSubList = repository.getSubList();
        mImplode = repository.getNamesFromDb();
    }

    //    ---------------------------Subreddit in database ---------------
    public void saveSubreddit(EditText userInput) {
        String subredditName = userInput.getText().toString().trim();
        mNetworking.fetchSubreddit(subredditName, new SubredditCallback(mRepository, userInput));
        Log.d(TAG, "DAO: insertSubreddit: " + subredditName);
    }

    public LiveData<List<Subreddit>> getSubreddits() {
        return mSubList;
    }

    public void removeSubreddit(Subreddit subreddit){
        mRepository.removeSubreddit(subreddit);
        Log.d(TAG, "removeSubreddit: " + subreddit.getName());
    }

//    ------------------------- open one subreddit
    public void sendSubredditforNewFeed(Subreddit subreddit) {
        this.currentSubreddit.setValue(subreddit);
        String name = subreddit.getName();
        Log.d(TAG, "sendSubredditforNewFeed: " + name);
    }

    //    ------------------------- user feed

    public LiveData<List<String>> getnames() {
        return mImplode;
    }

}
