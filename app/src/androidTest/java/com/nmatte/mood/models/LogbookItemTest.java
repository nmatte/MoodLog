package com.nmatte.mood.models;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LogbookItemTest {
    BoolItem testItem;
    @Before
    public void setUp() throws Exception {
        testItem = new BoolItem((long)1,"Foobool");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSetId() throws Exception {
        long oldID = testItem.getID();
        testItem.setId((long) 1000);
        Assert.assertTrue("changes existing ID", testItem.getID() == oldID);
    }

    @Test
    public void testGetID() throws Exception {
        Assert.assertTrue(testItem.getID() == 1);
    }

    @Test
    public void testGetName() throws Exception {
        Assert.assertTrue(testItem.getName() == "Foobool");
    }

    @Test
    public void testSetName() throws Exception {
        testItem.setName("Floobity");
        Assert.assertTrue(testItem.getName().equals("Floobity"));
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
        BoolItem equalNoID = new BoolItem(testItem.getName());
        BoolItem equalNoName = new BoolItem();
        BoolItem equalSame = new BoolItem(testItem.getID(),testItem.getName());


        BoolItem notEqualNoID = new BoolItem(testItem.getName() + ";");
        BoolItem notEqualID = new BoolItem(testItem.getID() + 1,testItem.getName());
        BoolItem notEqualSame = new BoolItem(testItem.getID() + 1,testItem.getName() + ";");
        equalNoName.setId(testItem.getID());

        Assert.assertTrue("equals() doesn't detect items with same name but no ID", testItem.equals(equalNoID));
        Assert.assertTrue("equals() doesn't detect items with same name but no name", testItem.equals(equalNoName));
        Assert.assertTrue("equals() doesn't detect same item", testItem.equals(equalSame));
        Assert.assertFalse("equals() detects items with different ID and no name", testItem.equals(notEqualNoID));
        Assert.assertFalse("equals() detects items with different name", testItem.equals(notEqualID));
        Assert.assertFalse("equals() detects different item", testItem.equals(notEqualSame));
    }
}