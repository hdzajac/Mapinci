package com.mapi.mapinci.Utils.graph.segments;


import java.io.Serializable;
import java.util.List;

public class Shape implements Serializable {
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
}
