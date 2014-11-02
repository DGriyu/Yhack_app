package com.example.riyu.yhackproject;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.content.Context;
import android.widget.RemoteViews;
import android.content.Intent;


public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int mAppWidgetId = 0;
        if(extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment(mAppWidgetId))
                    .commit();
        }
        setResult(RESULT_CANCELED);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        Button button;
        EditText text;
        Intent intent;
        int mAppWidgetId;

        public PlaceholderFragment() {
            mAppWidgetId = 0;
        }

        public PlaceholderFragment(int mId) {
            mAppWidgetId = mId;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
            button = (Button)rootView.findViewById(R.id.submit_name_to_cloud);
            text = (EditText)rootView.findViewById(R.id.new_username);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Context context = getActivity().getApplicationContext();
                    //perform some actions here
                    //for this demo, spit the text out to the user via toast
                    //in the future, spit out to Galileo server via HTTP
                    //should implement some way that we don't exit app until HTTP request is returned back
                    //implemented server side
                    String strMACAddr = ((BluetoothManager) getActivity().getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter().getAddress();
                    String strAddrSimple = "";
                    for (char c : strMACAddr.toCharArray()) {
                        if (c >= '0' && c <= '9') {
                            strAddrSimple += c;
                        }
                    }
                    long addr = Long.parseLong(strAddrSimple, 16);
                    WebClient.transmitUserMACPair(getActivity().getApplicationContext(), text.getText().toString(), addr);
                    String userInput = text.getText().toString();
                    Toast.makeText(getActivity().getApplicationContext(), userInput, Toast.LENGTH_SHORT).show();

                    //now automatically return to the other widget, now that HTTP has finished - RIGHT!?!?!
                    AppWidgetManager awm = AppWidgetManager.getInstance(context);
                    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget);
                    awm.updateAppWidget(mAppWidgetId, views);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                    getActivity().setResult(RESULT_OK, returnIntent);
                    getActivity().finish();
                }
            });
            return rootView;
        }


    }
}
