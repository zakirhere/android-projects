package com.zsayed.mtanextbus;

import android.content.Intent;
import android.os.AsyncTask;

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
    public final static String EXTRA_MESSAGE = "com.example.webapitutorial.MESSAGE";
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
//            if( result.equals("Spam Trap")) {
//                resultToDisplay = "Dangerous email, please correct";
//            }
//            else if( Integer.parseInt(result) >= 300) {
//                resultToDisplay = "Invalid email, please re-enter";
//            }
//            else {
//                resultToDisplay = "Thank you for your submission";
//            }

        }
        else {
            resultToDisplay = "Exception Occured";
        }

        return resultToDisplay;

    }

    protected void onPostExecute(String result) {
//        Intent intent = new Intent(getApplicationContext(), mta_home.class);
//        intent.putExtra(EXTRA_MESSAGE, resultToDisplay);
//        startActivity(intent);
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