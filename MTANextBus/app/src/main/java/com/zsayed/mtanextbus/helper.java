package com.zsayed.mtanextbus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by ZSayed on 7/9/2015.
 */
public class helper {

    public long getElapsedTime(String serverResponseTime, String RecordedTime) throws ParseException {
//    	String RecordedTime = "2015-07-24T21:34:55.000-04:00";
        serverResponseTime = serverResponseTime.replaceFirst("T", " ");
        String getServerTimeStamp = serverResponseTime.substring(0, 19);

        RecordedTime = RecordedTime.replaceFirst("T", " ");
        String getRecordedTimeStamp = RecordedTime.substring(0, 19);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDateStamp = format.parse(getServerTimeStamp);
        Date recordedDateStamp = format.parse(getRecordedTimeStamp);

        long timeDiff = currentDateStamp.getTime() - recordedDateStamp.getTime();
        return timeDiff/1000 - 2;
    }

    public String displayTimeDiff(String serverResponseTime, String RecordedTime) {
        int getMins = 0;
        int getSeconds = 0;
        long timeInSeconds = 0;
        try {
            timeInSeconds = getElapsedTime(serverResponseTime, RecordedTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(timeInSeconds > 59) {
            getMins = (int) (timeInSeconds/60);
            getSeconds = (int) (timeInSeconds % 60);
        }
        else {
            return Integer.toString((int) timeInSeconds) + " (seconds)";
        }

        return Integer.toString(getMins) + ":" + Integer.toString(getSeconds) + " (min:sec)";
    }

    public InputStream makeHTTPcall(String urlString) {
        InputStream in = null;
        // HTTP Get
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
            return in;
        } catch (Exception e ) {
            System.out.println(e.getMessage());
            e.getMessage();
            return in;
        }
    }

    // convert InputStream to String
    public String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    public XmlPullParser getXMLparser(InputStream in) {
        // Parse XML
        XmlPullParserFactory pullParserFactory;
        XmlPullParser parser = null;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            parser = pullParserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return parser;
        }
    }

    public class myResult {
        String jsonName;
        int jsonInt;

        public myResult(String name, int val) {
            this.jsonName = name;
            this.jsonInt = val;
        }

        public String getName() {
            return jsonName;
        }

        public int getIndex() {
            return jsonInt;
        }

    }

    public myResult getArrayValues(String elm) {
        String[] splitted = elm.split(Pattern.quote("["));
        System.out.println(splitted[0]);
        int Index = Integer.parseInt(splitted[1].replaceAll(Pattern.quote("]"), ""));
        System.out.println(Index);

        return new myResult(splitted[0], Index);
    }


    public String smartJsonParser(JSONObject json, String jsonPath) throws JSONException {

        String[] jsonElements = jsonPath.split("[.]");
        for(String elm: jsonElements) {
            if(elm.endsWith("()")) {
                return json.getString(elm.replaceAll("[()]", ""));
            }
            else if(elm.endsWith("]")) {
                myResult splitted = getArrayValues(elm);
                JSONArray j_arry = json.getJSONArray(splitted.getName());
                json = j_arry.getJSONObject(splitted.getIndex()); //<< get value here

            }
            else {
                json = json.getJSONObject(elm);

            }
        }

        return "not found";
    }


    public String parseXML( XmlPullParser parser, String nodeName) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        String result = new String();

        while( eventType!= XmlPullParser.END_DOCUMENT) {
            String name = null;
            switch(eventType)
            {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if ( name.equals(nodeName)) {
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

}

