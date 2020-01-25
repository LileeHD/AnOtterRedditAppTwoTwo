package lilee.hd.anotterredditapptwo.model;

import androidx.room.Entity;

@Entity(tableName = "myquery")
public class CustomQuery {
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
