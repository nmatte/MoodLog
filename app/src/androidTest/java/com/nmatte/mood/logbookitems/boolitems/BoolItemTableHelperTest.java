package com.nmatte.mood.logbookitems.boolitems;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class BoolItemTableHelperTest extends AndroidTestCase {
    RenamingDelegatingContext testContext;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        testContext = new RenamingDelegatingContext(getContext(), "test_");
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testSave() throws Exception {
        BoolItem item = new BoolItem("FooItem");
        BoolItemTableHelper.save(testContext, item);

        BoolItem returnedItem = BoolItemTableHelper.getItemWithName(testContext,item.getName());
        assertNotNull(returnedItem);
        assertTrue(returnedItem.getName().equals(item.getName()));
    }

    @Test
    public void testDelete() throws Exception {
        BoolItem itemDelete = new BoolItem("FooItemDelete");
        BoolItemTableHelper.save(testContext,itemDelete);

        BoolItemTableHelper.delete(testContext, itemDelete);

        assertNull(BoolItemTableHelper.getItemWithName(testContext, "FooItemDelete"));
    }

    @Test
    public void testGetAll() throws Exception {
        BoolItem itemVisible = new BoolItem("FooItemVisible");
        BoolItem itemInvisible = new BoolItem("FooItemInvisible");
        itemInvisible.setVisible(false);
        BoolItemTableHelper.save(testContext, itemVisible);
        BoolItemTableHelper.save(testContext, itemInvisible);

        ArrayList<BoolItem> afterSave = BoolItemTableHelper.getAll(testContext);
        assertTrue("doesn't contain visible item", afterSave.contains(itemVisible));
        assertTrue("doesn't contain invisible item", afterSave.contains(itemInvisible));
    }

    @Test
    public void testGetAllVisible() throws Exception {
        BoolItem item1 = new BoolItem("FooItemVisible");
        BoolItem item2 = new BoolItem("FooItemInvisible");
        item2.setVisible(false);
        BoolItemTableHelper.save(testContext, item1);
        BoolItemTableHelper.save(testContext, item2);

        ArrayList<BoolItem> afterSave = BoolItemTableHelper.getAll(testContext);
        assertTrue("doesn't contain visible item",afterSave.contains(item1));
        assertFalse("contains invisible item", afterSave.contains(item2));
    }
}