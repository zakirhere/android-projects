package com.zsayed.mtanextbus;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

/**
 * Created by ZSayed on 7/14/2015.
 */
public class parser {

    public void parseJSON(String responseText) {
        // Convert String to json object
        JSONObject json = new JSONObject(responseText);

// get LL json object
        JSONObject json_LL = json.getJSONObject("LL");

// get value from LL Json Object
        String str_value=json_LL.getString("value"); //<< get value here
    }
}

