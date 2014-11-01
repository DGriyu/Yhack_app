package com.example.riyu.yhackproject;

import android.content.Context;
import android.appwidget.AppWidgetManager;

import android.content.Intent;
import android.app.PendingIntent;

import android.widget.RemoteViews;


/**
 * Created by Riyu on 10/31/14.
 */
public class AppWidgetProvider {
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, SettingsActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            //RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_provider_layout);
            //views.setOnClickPendingIntent(R.id.button, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            //appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
