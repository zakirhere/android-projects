package com.zsayed.mtanextbus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class mta_home extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, MyFrag.MyFragInterface {
    public final String API_KEY = "953b93c3-6d34-4ea2-8487-934d6da2374c";
    public static String displayOutput = "";


    public void needsHide() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //find the fragment by View or Tag
        MyFrag myFrag = (MyFrag)fragmentManager.findFragmentById(R.id.container);
        fragmentTransaction.hide(myFrag);
        fragmentTransaction.commit();
        //do more if you must
    }

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Creating the app UI
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mta_home);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();


    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                displayOutput = "BUS DETAILS FOR OFFICE\n";
                this.makeAPICall(501362, "MTA%20NYCT_Q17");
                this.makeAPICall(501362, "MTA%20NYCT_Q27");
                this.makeAPICall(501362, "MTABC_Q25");
                this.makeAPICall(501362, "MTABC_Q34");
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                displayOutput = "BUS DETAILS FOR GYM\n";
                this.makeAPICall(501363, "MTA%20NYCT_Q17");
                this.makeAPICall(501363, "MTA%20NYCT_Q27");
                this.makeAPICall(501363, "MTABC_Q25");
                this.makeAPICall(501363, "MTABC_Q34");
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.mta_home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            TextView resultText = (TextView) findViewById(R.id.resulttxt);
            resultText.setText("");
//            startActivity(new Intent(this, Settings.PrefsFragment.class));
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new PrefsFragment()).commit();
            return true;
        }
        else if (id == R.id.action_help) {
            helpMenuItem();
            return true;
        }
        else if (id == R.id.action_about) {
            aboutMenuItem();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void settingsMenuItem() {
        startActivity(new Intent(this, Settings.class));
    }

    private void helpMenuItem() {
        new AlertDialog.Builder(this)
                .setTitle("Help")
                .setMessage("This will be updated after the app complete 1.0 version")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing for now
                    }
                }).show();
    }

    private void aboutMenuItem() {
        new AlertDialog.Builder(this)
                .setTitle("About")
                .setMessage("Sole developer Zakir Sayed and can be contacted at zakirhere@gmail.com")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing for now
                    }
                }).show();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_mta_home, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((mta_home) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public void makeAPICall(long busStopId, String busNumber) {
        String baseUrl = "http://bustime.mta.info/api/siri/stop-monitoring.json?";
        String query = "key=" + API_KEY +"&OperatorRef=MTA&MonitoringRef=" + busStopId + "&LineRef=" + busNumber;

        String fullUrlStr = baseUrl + query;
        new CallAPI().execute(fullUrlStr, busNumber);
    }


    private class CallAPI extends AsyncTask<String, String, String> {
        helper h = new helper();

        @Override
        protected String doInBackground(String... params) {
            String urlString=params[0]; // URL to call
            InputStream in = h.makeHTTPcall(urlString);
            String BusLineNumber = "Siri.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[0].MonitoredVehicleJourney.PublishedLineName()";
            String RecordedTime = "Siri.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[0].RecordedAtTime()";
            String serverTime = "Siri.ServiceDelivery.ResponseTimestamp()";

            try {
                JSONObject jsonObj = new JSONObject(h.getStringFromInputStream(in));
                serverTime = h.smartJsonParser(jsonObj, serverTime);
                RecordedTime = h.smartJsonParser(jsonObj, RecordedTime);

                displayOutput += "\nData old by: " + h.displayTimeDiff(serverTime, RecordedTime);
                displayOutput += "\nBus detail: " + h.smartJsonParser(jsonObj, BusLineNumber);
                displayOutput += "\n# of stops away: " + h.smartJsonParser(jsonObj, "Siri.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[0].MonitoredVehicleJourney.MonitoredCall.Extensions.Distances.StopsFromCall()");
                displayOutput += "\n";

                return displayOutput;
            } catch (Exception e) {
                displayOutput += "\nBus detail: " + params[1];
                return displayOutput += "\nError: " + e.getMessage() + "\n";
            }
        }

        protected void onPostExecute(String result) {
            TextView resultText = (TextView) findViewById(R.id.resulttxt);
            resultText.setText(result);
        }
    }


}


//public class PrefsFragment1 extends PreferenceFragment {
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // Load the preferences from an XML resource
//        ((MyFrag.MyFragInterface)this.getActivity()).needsHide();
//        addPreferencesFromResource(R.xml.preference);
//
//    }
//}
