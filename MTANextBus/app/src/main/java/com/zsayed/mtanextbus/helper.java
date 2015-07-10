package com.zsayed.mtanextbus;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ZSayed on 7/9/2015.
 */
public class helper {

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

