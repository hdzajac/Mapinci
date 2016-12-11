package com.mapi.mapinci.Utils.graph.segments;


import java.io.Serializable;

public class Shape implements Serializable {
    private Segment[] segments;
    private Double length;

    public Segment[] getSegments() {
        return segments;
    }

    public void setSegments(Segment[] segments) {
        this.segments = segments;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }
}
