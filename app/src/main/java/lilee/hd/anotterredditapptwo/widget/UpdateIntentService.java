package lilee.hd.anotterredditapptwo.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;

import java.util.Random;

import lilee.hd.anotterredditapptwo.R;
import lilee.hd.anotterredditapptwo.model.Subreddit;

import static android.content.ContentValues.TAG;

public class UpdateIntentService extends Service {

    static final String ACTION_UPDATE_POST_WIDGET = "";
    private static final String SERVICE_NAME = UpdateIntentService.class.getSimpleName();
    private Subreddit mSubreddit;

//    public UpdateIntentService() {
//        super(SERVICE_NAME);
//    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // generates random number
        Random random = new Random();
        int randomInt = random.nextInt(100);
//
//        Subreddit subreddit = new Subreddit();
//        mSubreddit = getPrefs(subreddit);


        // Reaches the remoteViews on widget and displays the number
        RemoteViews views = getPrefs(this);
        ComponentName theWidget = new ComponentName(this, TrendyWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(theWidget, views);
        Log.d(TAG, "onStartCommand: " + mSubreddit.getName());
        return super.onStartCommand(intent, flags, startId);

    }

    private RemoteViews getPrefs(Context context) {
        mSubreddit = new Subreddit();
//        context = getApplicationContext();
            SharedPreferences prefs = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE);
            String imgUrl = prefs.getString("icon", mSubreddit.getIconUrl());
            String name = prefs.getString("title", mSubreddit.getName());
            String lastUpdate = "Last subreddit added: " + "\n" + name;
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.trendy_widget);
            views.setTextViewText(R.id.trendy_title, lastUpdate);

        Log.d(TAG, "dataReceived: " + name);
        return views;
    }

}
