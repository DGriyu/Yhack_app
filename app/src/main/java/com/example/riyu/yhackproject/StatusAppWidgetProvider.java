package com.example.riyu.yhackproject;

import android.content.ComponentName;
import android.app.Activity;
import android.content.Context;
import android.appwidget.AppWidgetManager;
import android.widget.Toast;
import android.appwidget.AppWidgetProvider;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothAdapter;



import java.util.prefs.Preferences;

/**
 * Created by Riyu on 10/31/14.
 */
public class StatusAppWidgetProvider extends AppWidgetProvider {
    private BluetoothAdapter mBluetoothAdapter;
    private final static int REQUEST_ENABLE_BT = 1;
    Boolean onForBroadcasting;
    AppWidgetManager ref;
    int id;
    public static String WIDGET_BUTTON = "MY_PACKAGE_NAME.WIDGET_BUTTON";

    private static final String onMyClick = "onMyClickTag";

    @Override
    public void onReceive(Context context, Intent intent) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget);
        final BluetoothManager bluetoothManager =
                (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        super.onReceive(context, intent);
        if (WIDGET_BUTTON.equals(intent.getAction())) {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(context);
            boolean value = prefs.getBoolean("onForBroadcasting", false);
            Editor editor = prefs.edit();

            if (value) {
                onForBroadcasting = false;
            } else {
                onForBroadcasting = true;
            }


            if (onForBroadcasting) {
                Log.v("Hey", "change green");
                if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    enableBtIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(enableBtIntent);
                }
                else {
                    views.setImageViewResource(R.id.statusImage, R.drawable.cloud_green);
                    //image.setImageResource(R.drawable.cloud_red);
                    editor.putBoolean("onForBroadcasting", true);
                    editor.commit();
                }
            }
            else {
                Log.v("Hey", "change red");
                views.setImageViewResource(R.id.statusImage, R.drawable.cloud_red);
                editor.putBoolean("onForBroadcasting", false);
                editor.commit();
            }
            ComponentName cn = new ComponentName(context, StatusAppWidgetProvider.class);
            AppWidgetManager.getInstance(context).updateAppWidget(cn,views);

        }
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        ref = appWidgetManager;
        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            id = appWidgetId;
            Log.v("hey","Lets repeat.");
            // Get the layout for the App Widget and attach an on-click listener to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget);
            //views.setOnClickPendingIntent(R.id.button, pendingIntent);
            //Intent intent = new Intent(context, StatusAppWidgetProvider.class);
            //intent.setAction(clicked);
            //PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            //views.setOnClickPendingIntent(R.id.statusImage, pendingIntent);
            Intent intent = new Intent(WIDGET_BUTTON);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.statusImage, pendingIntent );


            // Tell the AppWidgetManager to perform an update on the current app widget
            //appWidgetManager.updateAppWidget(appWidgetId, views);

            //ImageView image = (ImageView)views.findViewById(R.id.statusImage);
            //image.setImageResource(R.drawable.cloud_green);
            //onForBroadcasting = !onForBroadcasting;
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(context);
            boolean value = prefs.getBoolean("onForBroadcasting", false);

            if (value) {
                Editor editor = prefs.edit();
                editor.putBoolean("onForBroadcasting", false);
                editor.commit();
            }
           /* final BluetoothManager bluetoothManager =
                    (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            mBluetoothAdapter = bluetoothManager.getAdapter();*/
            // Ensures Bluetooth is available on the device and it is enabled. If not,
// displays a dialog requesting user permission to enable Bluetooth.
          /*  if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                enableBtIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(enableBtIntent);
            }*/
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
