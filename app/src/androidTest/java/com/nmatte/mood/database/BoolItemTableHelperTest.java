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
        boolHelper = new BoolItemTableHelper(testContext);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testSave() throws Exception {
        BoolComponent item = new BoolComponent("TestItem");
        long id = boolHelper.save(item);
        BoolComponent returnedItem = boolHelper.find(id);

        assertTrue("BoolComponent id updates on save", id  > 0);
        assertNotNull("BoolComponent is found", returnedItem);
        assertTrue("Component name saves correctly",returnedItem.getName().equals(item.getName()));
    }

    @Test
    public void testDelete() throws Exception {
        BoolComponent itemDelete = new BoolComponent("FooItemDelete");

        long id = boolHelper.save(itemDelete);
        boolHelper.delete(itemDelete);
        assertNull(boolHelper.find(id));
    }

    @Test
    public void testGetAll() throws Exception {
        BoolComponent itemVisible = new BoolComponent("FooItemVisible");
        BoolComponent itemInvisible = new BoolComponent("FooItemInvisible");

        itemInvisible.setVisible(false);
        boolHelper.save(itemVisible);
        boolHelper.save(itemInvisible);

        ArrayList<BoolComponent> afterSave = boolHelper.getAll();
        assertTrue("doesn't contain visible item", afterSave.contains(itemVisible));
        assertTrue("doesn't contain invisible item", afterSave.contains(itemInvisible));
    }
}