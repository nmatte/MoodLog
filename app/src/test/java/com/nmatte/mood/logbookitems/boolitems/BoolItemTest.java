package com.nmatte.mood.logbookitems.boolitems;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class BoolItemTest {
    BoolItem item;

    @Before
    public void setUp(){
        item = new BoolItem((long) 1,"Test Item");
    }

    @After
    public void tearDown(){
        item = null;
    }

    @Test
    public void testSetValue() throws Exception {
        item.setValue(true);

    }

    @Test
    public void testValue() throws Exception {
        assertEquals("failed to set a value",false,item.value());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("failed toString","B1",item.toString());
    }
}