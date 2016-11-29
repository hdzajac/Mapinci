package com.mapi.mapinci.Utils.graph.segments;

import com.mapi.mapinci.Utils.graph.Vector;

import java.util.HashMap;

public class SegmentReflection implements SegmentSoul{

    private long id;
    private long correspondingSegmentId;
    private HashMap<Integer, Vector> vectors;
    private Double slope;

    SegmentReflection(long id, Long correspondingSegmentId, Vector v1, Vector v2){
        this.id = id;
        this.correspondingSegmentId = correspondingSegmentId;
        this.vectors = new HashMap<>();
        vectors.put(0,v1);
        vectors.put(1,v2);
        this.slope = v1.getY()/v1.getX();
    }

    SegmentReflection(long id, Vector v1, Vector v2){
        this.id = id;
        this.correspondingSegmentId = id;
        this.vectors = new HashMap<>();
        vectors.put(0,v1);
        vectors.put(1,v2);
        this.slope = v1.getY()/v1.getX();
    }

    public long getCorrespondingSegmentId(){
        return correspondingSegmentId;
    }

    public Vector getVector1(){
        return vectors.get(0);
    }
    public Vector getVector2(){
        return vectors.get(1);
    }

    @Override
    public Double getLength() {
        return Math.sqrt(Math.pow(vectors.get(0).getX(),2.0) + Math.pow(vectors.get(1).getY(),2.0));
    }

    public Long getId() {
        return id;
    }

    public Double getSlope() {
        return slope;
    }

    public String toString() {
        return String.format("[Corresponding id: %d] [Slope: %s]", id, slope.toString());
    }
}
