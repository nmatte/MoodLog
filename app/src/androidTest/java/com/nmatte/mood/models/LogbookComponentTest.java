package com.nmatte.mood.models;

import com.nmatte.mood.models.components.BoolComponent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LogbookComponentTest {
    BoolComponent testItem;
    @Before
    public void setUp() throws Exception {
        testItem = new BoolComponent("TestItem");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSetId() throws Exception {
        long oldID = testItem.getId();
        testItem.setId((long) 1000);
        Assert.assertTrue("doesn't change existing ID", testItem.getId() == oldID);
    }

    @Test
    public void testIsVisible() throws Exception {
        Assert.assertTrue(testItem.isVisible());
    }

    @Test
    public void testSetVisible() throws Exception {
        testItem.setVisible(false);
        Assert.assertFalse(testItem.isVisible());
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertTrue("toString doesn't follow format prfxid:name",
                testItem.toString().equals("B1:Foobool"));
    }

    @Test
    public void testEquals() throws Exception {
        BoolComponent equalNoID = new BoolComponent(testItem.getName());
        BoolComponent equalSame = new BoolComponent(testItem.getName());


        BoolComponent notEqualNoID = new BoolComponent(testItem.getName() + ";");
//        BoolComponent notEqualID = new BoolComponent(testItem.getId() + 1,testItem.getName());
//        BoolComponent notEqualSame = new BoolComponent(testItem.getId() + 1,testItem.getName() + ";");

        Assert.assertTrue("equals() doesn't detect items with same name but no ID", testItem.equals(equalNoID));
        Assert.assertTrue("equals() doesn't detect same item", testItem.equals(equalSame));
        Assert.assertFalse("equals() detects items with different ID and no name", testItem.equals(notEqualNoID));
//        Assert.assertFalse("equals() detects items with different name", testItem.equals(notEqualID));
//        Assert.assertFalse("equals() detects different item", testItem.equals(notEqualSame));
    }
}