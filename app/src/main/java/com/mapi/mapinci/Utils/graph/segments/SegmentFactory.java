package com.mapi.mapinci.Utils.graph.segments;


import com.mapi.mapinci.Utils.graph.Node;
import com.mapi.mapinci.Utils.graph.Vector;

public final class SegmentFactory {

    private static long id;

    public SegmentFactory(){
        SegmentFactory.id = 1;
    }

    public Segment newFullSegment(Node n1, Node n2){
        Segment segment = new Segment(id,n1,n2);
        id++;
        return segment;
    }

    public SegmentReflection newFullSegment(Long correspondingId, Vector v1, Vector v2){
        SegmentReflection sr = new SegmentReflection(id, correspondingId, v1, v2);
        id++;
        return sr;
    }

    public SegmentReflection newFullSegment(Vector v1, Vector v2){
        SegmentReflection sr = new SegmentReflection(id, v1, v2);
        id++;
        return sr;
    }

    public Segment newHalfSegment(Node n1){
        Segment segment = new Segment(id,n1);
        id++;
        return segment;
    }


}
