package lilee.hd.anotterredditapptwo.widget;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.request.target.AppWidgetTarget;

import lilee.hd.anotterredditapptwo.R;
import lilee.hd.anotterredditapptwo.home.SearchActivity;
import lilee.hd.anotterredditapptwo.home.SearchFragment;
import lilee.hd.anotterredditapptwo.model.Subreddit;
import lilee.hd.anotterredditapptwo.util.GlideApp;

import static android.content.ContentValues.TAG;

/**
 * Implementation of App Widget functionality.
 */
public class TrendyWidgetProvider extends AppWidgetProvider {
    public static final String ACTION_WIDGET = "ACTION_BROADCASTWIDGETSAMPLE";
    private static int mCounter = 0;
    private PendingIntent service;
    private Subreddit mSubreddit;
    private String imgUrl;
    private String name;
    SharedPreferences prefs;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.trendy_widget);
        AppWidgetTarget target = new AppWidgetTarget(context,80, 80, R.id.trendy_img, views, appWidgetId);
        views.setTextViewText(R.id.trendy_title, Integer.toString(mCounter));
//        try{
//            GlideApp.with(context.getApplicationContext())
//                    .asBitmap()
//                    .load("url")
//                    .placeholder(R.drawable.leaf)
//                    .into(target);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        // Construct an Intent which is pointing this class.
//        Intent intent = new Intent(context, SearchFragment.class);
//        intent.setAction(ACTION_WIDGET);
//        // And this time we are sending a broadcast with getBroadcast
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//
//        views.setOnClickPendingIntent(R.id.widget_container, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        context.startService(new Intent(context, UpdateIntentService.class));

        // Create an Intent to launch ExampleActivity
        Intent intent = new Intent(context, SearchActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Get the layout for the App Widget and attach an on-click listener
        // to the button
//        Intent dataIntent = new Intent(context, UpdateIntentService.class);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.trendy_widget);
//        views.setTextViewText(R.id.trendy_title, "last subreddit added: "+name);
        views.setOnClickPendingIntent(R.id.widget_container, pendingIntent);

        // Tell the AppWidgetManager to perform an update on the current app widget
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

//    private Subreddit getPrefs(Subreddit subreddit) {
//        mSubreddit = subreddit;
//
//        if (context != null) {
//            SharedPreferences prefs = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE);
//            String imgUrl = prefs.getString("icon", mSubreddit.getIconUrl());
//            String name = prefs.getString("title", mSubreddit.getName());
//
//        }
//        Log.d(TAG, "getPrefs: "+ subreddit.getName());
//        return mSubreddit;
//    }
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        super.onReceive(context, intent);
//        if (ACTION_WIDGET.equals(intent.getAction())) {
////            mCounter++;
//        }
//    }
}

