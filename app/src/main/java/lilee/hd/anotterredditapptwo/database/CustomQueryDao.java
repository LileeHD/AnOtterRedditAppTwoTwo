package lilee.hd.anotterredditapptwo.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import lilee.hd.anotterredditapptwo.model.CustomQuery;

@Dao
public interface CustomQueryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveQuery(CustomQuery query);

    @Update
    void updateQuery(CustomQuery query);
}
