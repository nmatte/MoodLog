package com.nmatte.mood.database;

import android.database.sqlite.SQLiteDatabase;
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
    DatabaseHelper DbHelper;
    SQLiteDatabase db;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        testContext = new RenamingDelegatingContext(getContext(), "test_");
        boolHelper = new BoolItemTableHelper();
        DbHelper = new DatabaseHelper(testContext);
        db = DbHelper.getWritableDatabase();
    }

    @After
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }

    @Test
    public void testSave() throws Exception {
        BoolComponent item = new BoolComponent("FooItem");
        boolHelper.save(db, item);

        BoolComponent returnedItem = boolHelper.getItemWithName(db,item.getName());
        assertNotNull(returnedItem);
        assertTrue(returnedItem.getName().equals(item.getName()));
    }

    @Test
    public void testDelete() throws Exception {
        BoolComponent itemDelete = new BoolComponent("FooItemDelete");
        boolHelper.save(db, itemDelete);

        boolHelper.delete(db, itemDelete);

        assertNull(boolHelper.getItemWithName(db, "FooItemDelete"));
    }

    @Test
    public void testGetAll() throws Exception {
        BoolComponent itemVisible = new BoolComponent("FooItemVisible");
        BoolComponent itemInvisible = new BoolComponent("FooItemInvisible");
        itemInvisible.setVisible(false);
        boolHelper.save(db, itemVisible);
        boolHelper.save(db, itemInvisible);

        ArrayList<BoolComponent> afterSave = boolHelper.getAll(db);
        assertTrue("doesn't contain visible item", afterSave.contains(itemVisible));
        assertTrue("doesn't contain invisible item", afterSave.contains(itemInvisible));
    }

    @Test
    public void testGetAllVisible() throws Exception {
        BoolComponent item1 = new BoolComponent("FooItemVisible");
        BoolComponent item2 = new BoolComponent("FooItemInvisible");
        item2.setVisible(false);
        boolHelper.save(db, item1);
        boolHelper.save(db, item2);

        ArrayList<BoolComponent> afterSave = boolHelper.getAll(db);
        assertTrue("doesn't contain visible item",afterSave.contains(item1));
        assertFalse("contains invisible item", afterSave.contains(item2));
    }
}