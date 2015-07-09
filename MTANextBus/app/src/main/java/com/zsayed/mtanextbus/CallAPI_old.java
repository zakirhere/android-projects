package com.zsayed.mtanextbus;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ZSayed on 7/5/2015.
 */


public class CallAPI_old extends AsyncTask<String, String, String> {
    public static String resultToDisplay = "";

    @Override
    protected String doInBackground(String... params) {
        String urlString=params[0]; // URL to call
        InputStream in = null;
        String result = null;

        // HTTP Get
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (Exception e ) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }

        // Parse XML
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            result = parseXML(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Simple logic to determine if the email is dangerous, invalid, or valid
        if (result != null ) {
            resultToDisplay = result;
        }
        else {
            resultToDisplay = "Exception Occured";
        }

        return resultToDisplay;

    }


    protected void onPostExecute(String result) {
//        mta_home myHome = new mta_home();
//        myHome.printResult(resultToDisplay);

//        TextView resultText = (TextView) findViewById(R.id.resulttxt);
//        resultText.setText("API call is made: " + resultToDisplay);
    }

    private String parseXML( XmlPullParser parser ) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        String result = new String();

        while( eventType!= XmlPullParser.END_DOCUMENT) {
            String name = null;
            switch(eventType)
            {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if( name.equals("Error")) {
                        System.out.println("Web API Error!");
                    }
                    else if ( name.equals("Name")) {
                        result = parser.nextText();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;
            } // end switch

            eventType = parser.next();
        } // end while
        return result;
    }



} // end CallAPI_old