package com.mapi.mapinci.Utils.graph;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

public class Vector{

    private Double x;
    private Double y;
    private Logger log;

    public Vector(Node n1, Node n2){
        x = n2.getLongitude() - n1.getLongitude();
        y = n2.getLatitude() - n1.getLatitude();
        this.log = Logger.getLogger("Reference rotator");
    }

    public Vector(Double x, Double y){
        this.x = x;
        this.y = y;
    }

    public Vector() {};

    public Double[] getPoints() {
        Double[] points = new Double[2];
        points[0] = x;
        points[1] = y;
        return points;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getAngleBetween(Vector v){
        Double otherX = v.getX();
        Double otherY = v.getY();
        return Math.acos((x*otherX + y*otherY)/(Math.sqrt(x*x + y*y) * Math.sqrt(otherX*otherX + otherY*otherY)));
    }

    public String toString(){
        return String.format("X: %f    Y: %f",x,y);
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        try {
            json.put("x", x);
            json.put("y", y);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

}
