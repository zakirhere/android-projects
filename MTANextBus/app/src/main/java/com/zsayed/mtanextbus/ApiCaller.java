package com.zsayed.mtanextbus;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ZSayed on 8/15/2015.
 */
public class ApiCaller extends HelperMethods{
    JsonResponse jr = new JsonResponse();

    public String getStopsAway(int i) {
        return "Siri.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[" + i + "].MonitoredVehicleJourney.MonitoredCall.Extensions.Distances.StopsFromCall()";
    }

    public String getStopsAway(JSONObject jsonObj) {
        ArrayList arr = new ArrayList();
        String result = "";
        try {
            for(int j=0; j<5; j++) {
            String query = "Siri.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[" + j + "].MonitoredVehicleJourney.MonitoredCall.Extensions.Distances.StopsFromCall()";
                int temp = Integer.parseInt(jr.smartJsonParser(jsonObj, query));
                if(temp != 0 && !arr.contains(temp)) {
                    arr.add(temp);
                    result += "\n# of stops away: " + temp;
                }
            }
        }
        catch(Exception e) {

        }
        finally {
            //String result += "\n# of stops away: " + jr.smartJsonParser(jsonObj, getStopsAway(0));
            return result;
        }
    }


    public String extractStopsAway(String... params) {

        String result;
        String urlString=params[0]; // URL to call
        InputStream in = this.makeHTTPcall(urlString);
        String BusLineNumber = "Siri.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[0].MonitoredVehicleJourney.PublishedLineName()";
        String StopPointName = "Siri.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[0].MonitoredVehicleJourney.MonitoredCall.StopPointName()";
        String RecordedTime = "Siri.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[0].RecordedAtTime()";
        String serverTime = "Siri.ServiceDelivery.ResponseTimestamp()";

        try {
            JSONObject jsonObj = new JSONObject(this.getStringFromInputStream(in));
            serverTime = jr.smartJsonParser(jsonObj, serverTime);
            RecordedTime = jr.smartJsonParser(jsonObj, RecordedTime);

            result = "\nData old by: " + this.displayTimeDiff(serverTime, RecordedTime);
            result += "\nBus detail: " + jsonBusDetail(jr.smartJsonParser(jsonObj, BusLineNumber), params[1]);
            result += "\nStop Name: " +  jsonBusDetail(jr.smartJsonParser(jsonObj, StopPointName), params[1]);

            result += getStopsAway(jsonObj);
            result += "\n";

            return result;
        } catch (Exception e) {
            result = "\nBus detail error: " + params[1];
            return result += "\nError: " + e.getMessage() + "\n";
        }
    }

    public String jsonBusDetail(String jsonText, String busNumber) {
        if(jsonText.equals("N/A")) {
            return busNumber;
        }
        return jsonText;
    }
}
