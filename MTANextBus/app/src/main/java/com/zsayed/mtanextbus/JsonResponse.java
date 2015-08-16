package com.zsayed.mtanextbus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * Created by ZSayed on 8/15/2015.
 */
public class JsonResponse {

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

    public String smartJsonParser(JSONObject json, String jsonPath) throws JSONException {


        String[] jsonElements = jsonPath.split("[.]");
        for(String elm: jsonElements) {
            if(elm.endsWith("()")) {
                return json.getString(elm.replaceAll("[()]", ""));
            }
            else if(elm.endsWith("]")) {
                try {
                    myResult splitted = getArrayValues(elm);
                    JSONArray j_arry = json.getJSONArray(splitted.getName());
                    json = j_arry.getJSONObject(splitted.getIndex()); //<< get value here
                }
                catch(Exception e) {
                    return "N/A";
                }
            }
            else {
                json = json.getJSONObject(elm);
            }
        }

        return "not found";
    }


    public myResult getArrayValues(String elm) {
        String[] splitted = elm.split(Pattern.quote("["));
        System.out.println(splitted[0]);
        int Index = Integer.parseInt(splitted[1].replaceAll(Pattern.quote("]"), ""));
        System.out.println(Index);

        return new myResult(splitted[0], Index);
    }
}
