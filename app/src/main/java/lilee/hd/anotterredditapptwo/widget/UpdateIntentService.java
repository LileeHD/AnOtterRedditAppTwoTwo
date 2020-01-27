package lilee.hd.anotterredditapptwo.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;

import java.util.Random;

import lilee.hd.anotterredditapptwo.R;

public class UpdateIntentService extends IntentService {

    private static final String SERVICE_NAME = UpdateIntentService.class.getSimpleName();
    static final String ACTION_UPDATE_POST_WIDGET = "";

    public UpdateIntentService() {
        super(SERVICE_NAME);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        if (ACTION_UPDATE_POST_WIDGET.equals(action)) {
            handleActionUpdatePostWidget();
        }
    }
    private void handleActionUpdatePostWidget() {
//        Repository repository = InjectorUtils.getRepository(this);
//        Post post = repository.getNextUnseenPost();
//        if (post != null) {
//            updatePostsSubreddit(repository, post);
//            updateWidgets(post);
//        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // generates random number
        Random random = new Random();
        int randomInt = random.nextInt(100);
        String lastUpdate = "R: "+randomInt;
        // Reaches the remoteViews on widget and displays the number
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.trendy_widget);
        remoteViews.setTextViewText(R.id.trendy_title, lastUpdate);
        ComponentName theWidget = new ComponentName(this, TrendyWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(theWidget, remoteViews);

        return super.onStartCommand(intent, flags, startId);
    }
}
