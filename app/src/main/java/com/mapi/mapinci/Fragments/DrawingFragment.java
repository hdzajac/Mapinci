package com.mapi.mapinci.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapi.mapinci.R;
import com.mapi.mapinci.Utils.graph.Node;
import com.mapi.mapinci.Utils.graph.segments.Shape;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;


public class DrawingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("DrawingFragment", "onCreateView");
        return inflater.inflate(R.layout.fragment_draw, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public List<Node> sendRequest(Shape shape) {
        try {
            URL url = new URL("http://localhost");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");

            ObjectMapper objectMapper = new ObjectMapper();
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());

            objectMapper.writeValue(dataOutputStream, shape);

            dataOutputStream.flush();
            dataOutputStream.close();

            int responseCode = connection.getResponseCode();
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
