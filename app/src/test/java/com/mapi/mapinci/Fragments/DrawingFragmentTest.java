package com.mapi.mapinci.Fragments;

import com.mapi.mapinci.Utils.graph.Nodes;
import com.mapi.mapinci.Utils.graph.segments.Shape;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class DrawingFragmentTest {

    private DrawingFragment underTest;


    @Before
    public void setUp() throws Exception {
        underTest = new DrawingFragment();
    }

    @Test
    public void sendRequest() throws Exception {

        Shape shape = new Shape();
        underTest.sendRequest(shape);
    }

}