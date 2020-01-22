package lilee.hd.anotterredditapptwo.database;

import androidx.annotation.MainThread;

public interface LoadQueryCallback {

    @MainThread
    void onQueryLoaded(String query);

    @MainThread
    void noQueryToLaod();
}
