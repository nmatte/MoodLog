package com.nmatte.mood.models;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoolItemTest extends TestCase{
    BoolItem testItem;
    @Before
    public void setUp() throws Exception {
        testItem = new BoolItem((long) 1,"Foobool");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testToString() throws Exception {
        assertTrue("toString doesn't follow format prfxid:name",
                testItem.toString().equals("B1:Foobool"));
    }








}