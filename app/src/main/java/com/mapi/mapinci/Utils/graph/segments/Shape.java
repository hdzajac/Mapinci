package com.mapi.mapinci.Utils.graph.segments;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Shape {
    private List<Segment> segments;
    private Double length;

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }


    public JSONObject toJson() {
        JSONArray segmentsJson = new JSONArray(segments);
        JSONObject json = new JSONObject();

        try {
            json.put("segments", segmentsJson);
            json.put("length", length);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
