package com.nmatte.mood.database;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.nmatte.mood.database.components.NumItemTableHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NumItemTableHelperTest extends AndroidTestCase{
    RenamingDelegatingContext testContext;
    NumItemTableHelper helper;
    @Before
    public void setUp() throws Exception {
        testContext =
                new RenamingDelegatingContext(getContext(), "test_");
        helper = new NumItemTableHelper(testContext);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSave() throws Exception {
//        NumComponent item = new NumComponent("NumComponent");
//        item = helper.save(db, item);
//
//
//        Assert.assertFalse("id not updated on save", item.getId() != -1);
    }


    @Test
    public void testDelete() throws Exception {
//        NumComponent item = new NumComponent("DeleteNumItem");
//        helper.save(db,item);
//        helper.delete(db, item);
//
//        Assert.assertFalse(helper.getAll(db).contains(item));
    }

    @Test
    public void testGetAll() throws Exception {
//        NumComponent itemVisible = new NumComponent("FooItemVisible");
//        NumComponent itemInvisible = new NumComponent("FooItemInvisible");
//        itemInvisible.setVisible(false);
//        helper.save(db, itemVisible);
//        helper.save(db, itemInvisible);
//
//        ArrayList<NumComponent> afterSave = helper.getAll(db);
//        Assert.assertTrue("doesn't contain visible item", afterSave.contains(itemVisible));
//        Assert.assertTrue("doesn't contain invisible item", afterSave.contains(itemInvisible));
    }

}