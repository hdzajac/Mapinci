package com.mapi.mapinci.Utils.graph;

import java.util.HashMap;
import java.util.Random;

public final class NodeFactory {

    private final HashMap<Long, String> ids;

    public NodeFactory(){
        this.ids = new HashMap<>();
    }

    public Node newNode(Double longitude, Double latitude){
        Random r = new Random();
        Long id = r.nextLong();
        while (ids.containsKey(id)){
            id = r.nextLong();
        }
        ids.put(id,"");
        return new Node(id,longitude, latitude);
    }

}
