package com.mapi.mapinci.Fragments;

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
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mapi.mapinci.R;
import com.mapi.mapinci.RootActivity;

import java.util.ArrayList;

import static android.R.attr.width;


public class DrawingFragment extends Fragment {

    DrawView drawView;

    LinearLayout drawLayout = null;

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
    }

    private class DrawView extends View {

        ArrayList<Point> points = new ArrayList<>();

        int counter = 0;
        float eventX;
        float eventY;
        private final Paint mPaint;
        private Path path = new Path();

        public DrawView(Context context) {
            super(context);
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(6);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if(counter>1) {
                canvas.drawPath(path, mPaint);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            // Makes our view repaint and call onDraw

            eventX = event.getX();
            eventY = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Set a new starting point
                    if(counter==0) {
                        path.moveTo(eventX, eventY);
                    }
                    else {
                        path.lineTo(eventX, eventY);
                    }
                    counter++;
                    invalidate();
                    return true;
//                case MotionEvent.ACTION_MOVE:
//                    // Connect the points
//                    path.lineTo(eventX, eventY);
//                    break;
//                case MotionEvent.ACTION_UP:
//                    path.lineTo(eventX, eventY);
//                    break;
                default:
                    return false;
            }

//             Makes our view repaint and call onDraw
//            invalidate();
//            return true;
        }


    }

}
