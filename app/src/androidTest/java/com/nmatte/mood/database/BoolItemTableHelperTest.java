package com.nmatte.mood.database;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.nmatte.mood.database.components.BoolItemTableHelper;
import com.nmatte.mood.models.components.BoolComponent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class BoolItemTableHelperTest extends AndroidTestCase {
    RenamingDelegatingContext testContext;
    BoolItemTableHelper boolHelper;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        testContext = new RenamingDelegatingContext(getContext(), "test_");
        boolHelper = new BoolItemTableHelper();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testSave() throws Exception {
        BoolComponent item = new BoolComponent("TestItem");
        item = boolHelper.save(testContext, item);
        BoolComponent returnedItem = boolHelper.find(testContext, item.getId());

        assertTrue("BoolComponent id not updated on save", item.getId() > 0);
//        Log.d("yooo", returnedItem.toString());
//        assertNotNull(returnedItem);
        assertTrue(returnedItem.getName().equals(item.getName()));
    }

    @Test
    public void testDelete() throws Exception {
        BoolComponent itemDelete = new BoolComponent("FooItemDelete");

        BoolComponent comp = boolHelper.save(testContext, itemDelete);

        assertNotNull(boolHelper.find(testContext, comp.getId()));
        boolHelper.delete(testContext, itemDelete);
        assertNull(boolHelper.find(testContext, comp.getId()));
    }

    @Test
    public void testGetAll() throws Exception {
        BoolComponent itemVisible = new BoolComponent("FooItemVisible");
        BoolComponent itemInvisible = new BoolComponent("FooItemInvisible");

        itemInvisible.setVisible(false);
        boolHelper.save(testContext, itemVisible);
        boolHelper.save(testContext, itemInvisible);

        ArrayList<BoolComponent> afterSave = boolHelper.getAll(testContext);
        assertTrue("doesn't contain visible item", afterSave.contains(itemVisible));
        assertTrue("doesn't contain invisible item", afterSave.contains(itemInvisible));
//        testContext.getContentResolver().query("content:// com.nmatte.mood.provider", );
    }
}