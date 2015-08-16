package com.zsayed.mtanextbus;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by ZSayed on 8/15/2015.
 */
public class ApiCaller extends HelperMethods{
    JsonResponse jr = new JsonResponse();

    public String getStopsAway(int i) {
        return "Siri.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[" + i + "].MonitoredVehicleJourney.MonitoredCall.Extensions.Distances.StopsFromCall()";
    }

    public String extractStopsAway(String... params) {

        String result;
        String urlString=params[0]; // URL to call
        InputStream in = this.makeHTTPcall(urlString);
        String BusLineNumber = "Siri.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[0].MonitoredVehicleJourney.PublishedLineName()";
        String RecordedTime = "Siri.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[0].RecordedAtTime()";
        String serverTime = "Siri.ServiceDelivery.ResponseTimestamp()";

        try {
            JSONObject jsonObj = new JSONObject(this.getStringFromInputStream(in));
            serverTime = jr.smartJsonParser(jsonObj, serverTime);
            RecordedTime = jr.smartJsonParser(jsonObj, RecordedTime);

            result = "\nData old by: " + this.displayTimeDiff(serverTime, RecordedTime);
            result += "\nBus detail: " + jsonBusDetail(jr.smartJsonParser(jsonObj, BusLineNumber), params[1]);
            result += "\n# of stops away: " + jr.smartJsonParser(jsonObj, getStopsAway(0));
            result += "\n# of stops away: " + jr.smartJsonParser(jsonObj, getStopsAway(1));
            result += "\n# of stops away: " + jr.smartJsonParser(jsonObj, getStopsAway(2));

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
