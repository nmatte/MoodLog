package com.nmatte.mood.models;

import com.nmatte.mood.models.components.BoolComponent;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

public class BoolItemTest extends TestCase{
    BoolComponent testItem;
    @Before
    public void setUp() throws Exception {
        testItem = new BoolComponent((long) 1,"Foobool");
    }

    @After
    public void tearDown() throws Exception {

    }

}