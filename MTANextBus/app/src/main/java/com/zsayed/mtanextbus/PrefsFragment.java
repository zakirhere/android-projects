package com.zsayed.mtanextbus;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.TextView;

/**
 * Created by ZSayed on 8/9/2015.
 */
public class PrefsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
//        needsHide();
        addPreferencesFromResource(R.xml.preference);

    }
}
