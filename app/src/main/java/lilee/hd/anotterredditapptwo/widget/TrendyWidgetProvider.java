package lilee.hd.anotterredditapptwo.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.RemoteViews;

import com.bumptech.glide.request.target.AppWidgetTarget;

import lilee.hd.anotterredditapptwo.R;
import lilee.hd.anotterredditapptwo.util.GlideApp;

/**
 * Implementation of App Widget functionality.
 */
public class TrendyWidgetProvider extends AppWidgetProvider {
    public static final String ACTION_WIDGET = "ACTION_BROADCASTWIDGETSAMPLE";
    private static int mCounter = 0;
    private PendingIntent service;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.trendy_widget);
        AppWidgetTarget target = new AppWidgetTarget(context,80, 80, R.id.trendy_img, views, appWidgetId);
        views.setTextViewText(R.id.trendy_title, Integer.toString(mCounter));
        try{
            GlideApp.with(context.getApplicationContext())
                    .asBitmap()
                    .load("url")
                    .placeholder(R.drawable.leaf)
                    .into(target);

        }catch (Exception e){
            e.printStackTrace();
        }
        // Construct an Intent which is pointing this class.
        Intent intent = new Intent(context, TrendyWidgetProvider.class);
        intent.setAction(ACTION_WIDGET);
        // And this time we are sending a broadcast with getBroadcast
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.trendy_title, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        final AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final Intent i = new Intent(context, UpdateIntentService.class);

        if (service == null) {
            service = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        }
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 60000, service);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (ACTION_WIDGET.equals(intent.getAction())) {
            mCounter++;
        }
    }
}

