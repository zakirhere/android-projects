package com.zsayed.mtanextbus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MtaHome extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    public final String API_KEY = "953b93c3-6d34-4ea2-8487-934d6da2374c";
    public static String displayOutput = "";
    public static String busNumber = "";
    public static int busStopId = 0;

    Context ctx;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    ArrayAdapter<String> dataAdapter = null;

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

        displayListView();
        ctx = this;
    }

    public void customBusData(boolean visible) {
        EditText fil_busNumber = (EditText) findViewById(R.id.fil_busNumber);
        EditText fil_busStopId = (EditText) findViewById(R.id.fil_stopId);
        Button submit_btn = (Button) findViewById(R.id.bSubmit);

        if(visible) {
            fil_busNumber.setVisibility(View.VISIBLE);
            fil_busStopId.setVisibility(View.VISIBLE);
            submit_btn.setVisibility(View.VISIBLE);
        }
        else if(!visible) {
            fil_busNumber.setVisibility(View.INVISIBLE);
            fil_busStopId.setVisibility(View.INVISIBLE);
            submit_btn.setVisibility(View.INVISIBLE);
        }
    }

    private void displayListView() {

        //Array list of countries
        List<String> countryList = new ArrayList<String>();
        countryList.add("Aruba");
        countryList.add("Anguilla");
        countryList.add("Netherlands Antilles");
        countryList.add("Antigua and Barbuda");
        countryList.add("Bahamas");
        countryList.add("Belize");
        countryList.add("Bermuda");
        countryList.add("Barbados");
        countryList.add("Canada");
        countryList.add("Costa Rica");
        countryList.add("Cuba");
        countryList.add("Cayman Islands");
        countryList.add("Dominica");
        countryList.add("Dominican Republic");
        countryList.add("Guadeloupe");
        countryList.add("Grenada");
        countryList.add("Greenland");
        countryList.add("Guatemala");
        countryList.add("Honduras");
        countryList.add("Haiti");
        countryList.add("Jamaica");

        //create an ArrayAdaptar from the String Array
        dataAdapter = new ArrayAdapter<String>(this,
                R.layout.bus_lists, countryList);
        ListView listView = (ListView) findViewById(R.id.bus_number);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        //enables filtering for the contents of the given ListView
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        EditText fil_busNumber = (EditText) findViewById(R.id.fil_busNumber);
        fil_busNumber.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                busNumber = s.toString().toUpperCase();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dataAdapter.getFilter().filter(s.toString());
            }
        });

        EditText fil_stopId = (EditText) findViewById(R.id.fil_stopId);
        fil_stopId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                busStopId = Integer.parseInt(s.toString());
            }
        });

        Button submit_btn = (Button) findViewById(R.id.bSubmit);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SlideActions sa = new SlideActions();
                TextView resultText = (TextView) findViewById(R.id.resulttxt);
                resultText.setText(getString(R.string.work_in_progress));
//                http://www.mysamplecode.com/2012/07/android-listview-edittext-filter.html
                displayOutput = sa.goingOther();
                makeAPICall(busStopId, "MTA%20NYCT_" + busNumber);
            }
        });
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
        SlideActions sa = new SlideActions();
        customBusData(false);
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section2);
                displayOutput = sa.goingOffice();
                this.makeAPICall(501363, "MTABC_Q25");
                this.makeAPICall(501363, "MTABC_Q34");
                this.makeAPICall(501363, "MTA%20NYCT_Q17");
                this.makeAPICall(501363, "MTA%20NYCT_Q27");
                break;
            case 2:
                mTitle = getString(R.string.title_section1);
                displayOutput = sa.goingOffice();
                this.makeAPICall(501362, "MTABC_Q25");
                this.makeAPICall(501362, "MTABC_Q34");
                this.makeAPICall(501362, "MTA%20NYCT_Q17");
                this.makeAPICall(501362, "MTA%20NYCT_Q27");
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                displayOutput = sa.goingOther();
                this.makeAPICall(501372, "MTA%20NYCT_Q27");  //going towards car parking
                this.makeAPICall(501570, "MTA%20NYCT_Q27");  // coming from car parking
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                customBusData(true);

                TextView resultText = (TextView) findViewById(R.id.resulttxt);
                resultText.setText(getString(R.string.Manual_selection_busstop));
//                http://www.mysamplecode.com/2012/07/android-listview-edittext-filter.html
//                displayOutput = sa.goingOther();
//                this.makeAPICall(busStopId, "MTA%20NYCT_" + busNumber);
//                this.makeAPICall(501570, "MTA%20NYCT_Q27");
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
        OptionsMenu om = new OptionsMenu();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            TextView resultText = (TextView) findViewById(R.id.resulttxt);
            resultText.setText(getString(R.string.work_in_progress));
//            Intent intent = new Intent(MtaHome.this, Activity_Settings.class);
//            startActivity(intent);
//            finish();
            return true;
        }
        else if (id == R.id.action_help) {
            om.helpMenuItem(this);
            return true;
        }
        else if (id == R.id.action_about) {
            om.aboutMenuItem(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            ((MtaHome) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public void makeAPICall(long busStopId, String busNumber) {
        String baseUrl = "http://bustime.mta.info/api/siri/stop-monitoring.json?";
        String query = "key=" + API_KEY +"&OperatorRef=MTA&MonitoringRef=" + busStopId + "&LineRef=" + busNumber;

        String fullUrlStr = baseUrl + query;
        new CallAPI().execute(fullUrlStr, busNumber);
    }


    public class CallAPI extends AsyncTask<String, String, String> {
        HelperMethods h = new HelperMethods();
        JsonResponse jr = new JsonResponse();
        ApiCaller ac = new ApiCaller();

        @Override
        protected String doInBackground(String... params) {
            displayOutput += ac.extractStopsAway(params);
            return displayOutput;
        }

        protected void onPostExecute(String result) {
            TextView resultText = (TextView) findViewById(R.id.resulttxt);
            resultText.setText(result);
        }
    }

}
