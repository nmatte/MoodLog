package com.nmatte.mood.database;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.nmatte.mood.models.NumItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class NumItemTableHelperTest extends AndroidTestCase {
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
        NumItem item = new NumItem("NumItem");
        NumItemTableHelper.save(testContext,item);

        NumItem returnedItem = NumItemTableHelper.getItemWithName(testContext,"NumItem");
        assertNotNull(returnedItem);
        assertTrue(returnedItem.getName().equals(item.getName()));
    }


    @Test
    public void testDelete() throws Exception {
        NumItem item = new NumItem("DeleteNumItem");
        NumItemTableHelper.save(testContext,item);
        NumItemTableHelper.delete(testContext, item);

        assertNull(NumItemTableHelper.getItemWithName(testContext,"DeleteNumItem"));
    }

    @Test
    public void testGetAll() throws Exception {
        NumItem itemVisible = new NumItem("FooItemVisible");
        NumItem itemInvisible = new NumItem("FooItemInvisible");
        itemInvisible.setVisible(false);
        NumItemTableHelper.save(testContext, itemVisible);
        NumItemTableHelper.save(testContext, itemInvisible);

        ArrayList<NumItem> afterSave = NumItemTableHelper.getAll(testContext);
        assertTrue("doesn't contain visible item", afterSave.contains(itemVisible));
        assertTrue("doesn't contain invisible item", afterSave.contains(itemInvisible));
    }

    @Test
    public void testGetAllVisible() throws Exception {
        NumItem itemVisible = new NumItem("FooItemVisible");
        NumItem itemInvisible = new NumItem("FooItemInvisible");
        itemInvisible.setVisible(false);
        NumItemTableHelper.save(testContext, itemVisible);
        NumItemTableHelper.save(testContext, itemInvisible);

        ArrayList<NumItem> afterSave = NumItemTableHelper.getAll(testContext);
        assertTrue("doesn't contain visible item", afterSave.contains(itemVisible));
        assertFalse("contains invisible item", afterSave.contains(itemInvisible));

    }
}