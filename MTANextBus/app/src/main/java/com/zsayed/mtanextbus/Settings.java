package com.zsayed.mtanextbus;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by ZSayed on 7/25/2015.
 */
public class Settings extends FragmentActivity {

    public static class PrefsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preference);
        }
    }
}
