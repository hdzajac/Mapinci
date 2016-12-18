package com.mapi.mapinci.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mapi.mapinci.R;
import com.mapi.mapinci.RootActivity;
import com.mapi.mapinci.Utils.graph.Node;
import com.mapi.mapinci.Utils.graph.NodeFactory;
import com.mapi.mapinci.Utils.graph.Nodes;
import com.mapi.mapinci.Utils.graph.segments.Segment;
import com.mapi.mapinci.Utils.graph.segments.SegmentFactory;

import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static android.R.attr.gestureColor;
import static android.R.attr.width;
import com.loopj.android.http.*;
import com.mapi.mapinci.Utils.graph.segments.Shape;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class DrawingFragment extends Fragment {

    OnSuccessResponse callback;

    public interface OnSuccessResponse {
        public void OnSuccessResponse(Nodes nodes);
    }


    private static final String URL = "http://mapinci.azurewebsites.net/mapinci/coordinate";

    DrawView drawView;
    LinearLayout drawLayout = null;

    //data to server
    LatLng startingPoint;
    Double radius;
    Double length;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            callback = (OnSuccessResponse) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSuccessResponse");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("DrawingFragment", "onCreateView");


        View view = inflater.inflate(R.layout.fragment_draw, container, false);

        drawLayout = (LinearLayout)view.findViewById(R.id.fragment_draw);

        drawView = new DrawView((RootActivity)getActivity());
        LinearLayout.LayoutParams drawViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        drawView.setLayoutParams(drawViewParams);
        drawView.setId(View.generateViewId());

        drawLayout.addView(drawView);

//        RelativeLayout relativeLayout = (RelativeLayout) rootView.findViewById(R.id.container);
//        relativeLayout.addView(new DrawView(getActivity()));

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i("starting point", startingPoint.latitude + " " + startingPoint.longitude);
        Log.i("radius", radius.toString());
        Log.i("length", length.toString());


        Button sendButton = (Button) view.findViewById(R.id.sendButton);
        Button undoButton = (Button) view.findViewById(R.id.undoButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Shape shape = new Shape();
                shape.setLength(length);
                shape.setRadius(radius);
                shape.setStartPoint(new Node(0L, startingPoint.latitude, startingPoint.longitude));
                shape.setSegments(drawView.createSegments());
                sendToServer(shape);
            }

        });
        undoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                drawView.undoLastPoint();
            }

        });
    }

    public void setData(LatLng sp, Double radius, Double length) {
        this.startingPoint = sp;
        this.radius = radius;
        this.length = length;
    }

    private void sendToServer(Shape shape) {

        try {
            StringEntity body = new StringEntity(shape.toJson().toString());

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(20 * 1000);

            client.post(getContext(), URL, body, "application/json", new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                    System.out.println("Success! "+statusCode);
                    System.out.println(responseBody.toString());

                    Gson gson = new Gson();

                    JsonParser parser = new JsonParser();
                    JsonElement jsonElement =  parser.parse(responseBody.toString());

                    Nodes nodes = gson.fromJson(jsonElement, Nodes.class);
//                    System.out.println(nodes);
//                    System.out.println(nodes.getNodes());
//                    System.out.println(nodes.getNodes().get(0).getId());

                    callback.OnSuccessResponse(nodes);

                }

                public void onFailure(int statusCode, Header[] headers, JSONObject responseBody, Throwable error) {
                    System.out.println("failure.. "+statusCode+"  "+error.getMessage());
                }
            });

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    private class DrawView extends View {

        private ArrayList<Node> nodes = new ArrayList<>();
        private NodeFactory nf = new NodeFactory();
        private SegmentFactory sf = new SegmentFactory();

        private int counter = 0;
        private float firstX;
        private float firstY;
        private float eventX;
        private float eventY;
        private final float epsilon = 40;
        private boolean continueDraw = true;

        private final Paint mPaint;
        private final Paint pointPaint;
        private Path path = new Path();

        public DrawView(Context context) {
            super(context);
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(6);

            pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            pointPaint.setColor(Color.BLACK);
            pointPaint.setStrokeWidth(25);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if(counter>1) {
                canvas.drawPath(path, mPaint);
                canvas.drawCircle(eventX, eventY, 15, pointPaint);
            }
            else {
                if(firstX!=0 && firstY!=0) {
                    canvas.drawCircle(firstX, firstY, 15, pointPaint);
                }
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            // Makes our view repaint and call onDraw

            //check if you can draw
            if(continueDraw) {
                eventX = event.getX();
                eventY = event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Set a new starting point
                        if (counter == 0) {
                            firstX = eventX;
                            firstY = eventY;
                            path.moveTo(eventX, eventY);
                            nodes.add(nf.newNode(Double.valueOf(eventX), Double.valueOf(eventY)));
                            counter++;
                        } else {
                            //check if new point is near the first one
                            if(isOutsideEpsilon(eventX, eventY)) {
                                path.lineTo(eventX, eventY);
                                nodes.add(nf.newNode(Double.valueOf(eventX), Double.valueOf(eventY)));
                                counter++;
                            } else {
                                path.lineTo(firstX, firstY);
                                eventX = firstX;
                                eventY = firstY;
                            }
                        }
                        invalidate();
                        return true;
                    default:
                        return false;
                }

            }
            return false;
        }

        private boolean isOutsideEpsilon(double x, double y) {
            //new point is in diameter of first point
            Log.i("First node", firstX + " " + firstY);
            Log.i("New node", x + " " + y);
            Log.i("X", String.valueOf(firstX - x));
            Log.i("Y", String.valueOf(firstY - y));
            continueDraw = !((Math.abs(firstX - x) < epsilon) && (Math.abs(firstY - y) < epsilon));
            Log.i("continueDraw: ", String.valueOf(continueDraw));
            return continueDraw;
        }

        private List<Segment> createSegments() {
            ArrayList<Segment> segments = new ArrayList<>();
            for(int i = 0; i < nodes.size() - 1; i++ ) {
                segments.add(sf.newFullSegment(nodes.get(i), nodes.get(i+1)));
            }
            addPercentageLength(segments);
            return segments;
        }

        private void addPercentageLength(ArrayList<Segment> segments) {
            double perimeter = countPerimeter(segments);
            for (Segment segment : segments) {
                segment.setPercentLength(segment.getLength()/perimeter);
            }
        }

        private double countPerimeter(ArrayList<Segment> segments ) {
            double sum = 0;
            for (Segment segment : segments) {
                sum += segment.getLength();
            }
            return sum;
        }

        public void undoLastPoint() {
            if(counter>1) {
                deleteLastNode();
                undoPath();
                continueDraw = true;
                invalidate();
            }
            else {
                counter = 0;
                nodes.clear();
                firstX = 0;
                firstY = 0;
                path.reset();
                invalidate();
            }
            Log.i("counter", counter + "");
            Log.i("nodes size", nodes.size() + "");
        }


        private void deleteLastNode() {
            if(continueDraw==true) {
                nodes.remove(nodes.size()-1);
                counter--;
            }
        }

        private void undoPath() {
            path.reset();
            path.moveTo(nodes.get(0).getLongitude().floatValue(), nodes.get(0).getLatitude().floatValue());
            for(int i =1; i < nodes.size(); i++) {
                path.lineTo(nodes.get(i).getLongitude().floatValue(), nodes.get(i).getLatitude().floatValue());
            }
            eventX = nodes.get(nodes.size()-1).getLongitude().floatValue();
            eventY = nodes.get(nodes.size()-1).getLatitude().floatValue();
        }

    }

}
