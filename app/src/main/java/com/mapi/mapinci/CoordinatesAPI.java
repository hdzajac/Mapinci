package com.mapi.mapinci;


import com.mapi.mapinci.Utils.graph.Nodes;
import com.mapi.mapinci.Utils.graph.segments.Shape;


import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;


public interface CoordinatesAPI {

    @POST("/coordinate/")
    void sendRequest(@Body Shape shape, Callback<Nodes> nodes);

}
