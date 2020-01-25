package lilee.hd.anotterredditapptwo.viewmodel;

import android.util.EventLog;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import lilee.hd.anotterredditapptwo.model.Feed;
import lilee.hd.anotterredditapptwo.model.Post;

public class PostViewModel extends ViewModel {

    private final MutableLiveData<Post> currentPost = new MutableLiveData<>();
    private MutableLiveData<Feed> mutableLiveData;
    private StateLiveData<Feed>stateLiveData = new StateLiveData<Feed>();
    private Observer<Throwable> errorObserver;
    private Observer<EventLog>loadingObserver;

    public void initHome() {
        if (mutableLiveData != null) {
            return;
        }
        RedditRepository repository = RedditRepository.getInstance();
        mutableLiveData = repository.getFeed();
    }

    public void initUserFeed(String query) {
        if (mutableLiveData != null) {
            return;
        }
        RedditRepository repository = RedditRepository.getInstance();
        mutableLiveData = repository.getUserFeed(query);
    }

    public MutableLiveData<Feed> getDefaultFeed() {
        return mutableLiveData;
    }

    public void sendData(Post post) {
        this.currentPost.setValue(post);
    }

    public LiveData<Post> getCurrentPost() {
        return currentPost;
    }


}
