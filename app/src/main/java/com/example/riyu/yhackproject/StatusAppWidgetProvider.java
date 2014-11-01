package com.example.riyu.yhackproject;

import android.content.Context;
import android.appwidget.AppWidgetManager;
import android.widget.Toast;
import android.appwidget.AppWidgetProvider;
import android.widget.ImageView;
import android.widget.RemoteViews;

/**
 * Created by Riyu on 10/31/14.
 */
public class StatusAppWidgetProvider extends AppWidgetProvider {

    Boolean onForBroadcasting = true;

    private static final String onMyClick = "onMyClickTag";

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Get the layout for the App Widget and attach an on-click listener to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget);
            //views.setOnClickPendingIntent(R.id.button, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            //appWidgetManager.updateAppWidget(appWidgetId, views);
            Toast.makeText(context, "You pressed the widget", Toast.LENGTH_SHORT);

            //flip the type of image displayed
            //ImageView image = (ImageView)views.findViewById(R.id.statusImage);
            if (onForBroadcasting)
                views.setImageViewResource(R.id.statusImage, R.drawable.cloud_red);
                //image.setImageResource(R.drawable.cloud_red);
            else
                views.setImageViewResource(R.id.statusImage, R.drawable.cloud_green);
            //image.setImageResource(R.drawable.cloud_green);
            onForBroadcasting = !onForBroadcasting;

            //views.setOnClickPendingIntent(R.id.button,
            //        getPendingSelfIntent(context, MyOnClick));

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    /*protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(intent);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public void onReceive(Context context, Intent intent) {
        if(onMyClick.equals(intent.getAction())) {
            //my onClick action is here
        }
    }*/

}
