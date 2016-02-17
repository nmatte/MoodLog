package com.nmatte.mood.database;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.nmatte.mood.models.NumComponent;

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
        NumComponent item = new NumComponent("NumComponent");
        NumItemTableHelper.save(testContext,item);

        NumComponent returnedItem = NumItemTableHelper.getItemWithName(testContext,"NumComponent");
        assertNotNull(returnedItem);
        assertTrue(returnedItem.getName().equals(item.getName()));
    }


    @Test
    public void testDelete() throws Exception {
        NumComponent item = new NumComponent("DeleteNumItem");
        NumItemTableHelper.save(testContext,item);
        NumItemTableHelper.delete(testContext, item);

        assertNull(NumItemTableHelper.getItemWithName(testContext,"DeleteNumItem"));
    }

    @Test
    public void testGetAll() throws Exception {
        NumComponent itemVisible = new NumComponent("FooItemVisible");
        NumComponent itemInvisible = new NumComponent("FooItemInvisible");
        itemInvisible.setVisible(false);
        NumItemTableHelper.save(testContext, itemVisible);
        NumItemTableHelper.save(testContext, itemInvisible);

        ArrayList<NumComponent> afterSave = NumItemTableHelper.getAll(testContext);
        assertTrue("doesn't contain visible item", afterSave.contains(itemVisible));
        assertTrue("doesn't contain invisible item", afterSave.contains(itemInvisible));
    }

    @Test
    public void testGetAllVisible() throws Exception {
        NumComponent itemVisible = new NumComponent("FooItemVisible");
        NumComponent itemInvisible = new NumComponent("FooItemInvisible");
        itemInvisible.setVisible(false);
        NumItemTableHelper.save(testContext, itemVisible);
        NumItemTableHelper.save(testContext, itemInvisible);

        ArrayList<NumComponent> afterSave = NumItemTableHelper.getAll(testContext);
        assertTrue("doesn't contain visible item", afterSave.contains(itemVisible));
        assertFalse("contains invisible item", afterSave.contains(itemInvisible));

    }
}