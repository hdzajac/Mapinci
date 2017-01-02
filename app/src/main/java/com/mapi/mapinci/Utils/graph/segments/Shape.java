package com.mapi.mapinci.Utils.graph.segments;


import com.mapi.mapinci.Utils.graph.Node;
import com.mapi.mapinci.Utils.graph.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shape {
    private List<Segment> segments;
    private Double length;
    private Double radius;
    private Node startPoint;

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

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Node getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Node startPoint) {
        this.startPoint = startPoint;
    }



    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        try {
            JSONArray segmentsJson = createJsonArrayFromList(segments);
            //System.out.println(segmentsJson.toString());
            json.put("segments", segmentsJson);
            json.put("length", length);
            json.put("radius", radius);
            json.put("startPoint", startPoint.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //System.out.println(json.toString());
        return json;
    }

    private JSONArray createJsonArrayFromList(List<Segment> list) throws JSONException{
        JSONArray jsonArray = new JSONArray();
        for(Segment segment : list) {
//            System.out.println("LENGTH: "+segment.getLength());
//            System.out.println("PERCENTAGE LENGTH: "+segment.getPercentLength());
//            System.out.println("SLOPE: "+segment.getSlope());

            JSONObject json = new JSONObject();

            json.put("id", segment.getId());
            json.put("n1", segment.getNode1().toJson());
            json.put("n2", segment.getNode2().toJson());
            json.put("length", Double.parseDouble(segment.getLength().toString()));
            json.put("percentLength", Double.parseDouble(segment.getPercentLength().toString()));
            json.put("slope", Double.parseDouble(segment.getSlope().toString()));
            json.put("vectors", createJsonArrayFromMap(segment.getVectors()));

            jsonArray.put(json);

        }
        return jsonArray;
    }

    private JSONArray createJsonArrayFromMap(HashMap<Long, Vector> map) throws JSONException{
        JSONArray vectorArray = new JSONArray();

        for (Map.Entry entry : map.entrySet()) {
            JSONObject json = new JSONObject();
            json.put("id", entry.getKey());
            json.put("vector", ((Vector)entry.getValue()).toJson());

            vectorArray.put(json);
        }

        return vectorArray;
    }
}
