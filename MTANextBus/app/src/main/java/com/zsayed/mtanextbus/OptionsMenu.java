package com.zsayed.mtanextbus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by ZSayed on 8/15/2015.
 */
public class OptionsMenu {

    public void settingMenuItem(Context c) {
        // PERFORM SETTINGS ACTION
    }

    public void helpMenuItem(Context c) {
        new AlertDialog.Builder(c)
                .setTitle("Help")
                .setMessage("This will be updated after the app complete 1.0 version")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing for now
                    }
                }).show();
    }

    public void aboutMenuItem(Context c) {
        new AlertDialog.Builder(c)
                .setTitle("About")
                .setMessage("Sole developer Zakir Sayed and can be contacted at zakirhere@gmail.com")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing for now
                    }
                }).show();
    }
}
