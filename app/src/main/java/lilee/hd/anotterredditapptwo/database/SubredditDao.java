package lilee.hd.anotterredditapptwo.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import lilee.hd.anotterredditapptwo.model.Subreddit;

@Dao
public interface SubredditDao {

    @Query("SELECT * FROM subreddit")
    LiveData<List<Subreddit>> getAll();

    @Query("SELECT * FROM subreddit WHERE name = :name")
    LiveData<Subreddit> getSubreddit(String name);

    @Query("SELECT name FROM subreddit")
    LiveData<List<String>> getAllNames();

    @Query("SELECT * FROM subreddit WHERE name = :name")
    Subreddit getSubredditForWidget(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void save(Subreddit subreddit);

    @Delete
    void delete(Subreddit subreddit);

}
