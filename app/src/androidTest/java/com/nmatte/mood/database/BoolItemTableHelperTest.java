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
    @Before
    public void setUp() throws Exception {
        super.setUp();
        testContext = new RenamingDelegatingContext(getContext(), "test_");
        boolHelper = new BoolItemTableHelper();
        DbHelper = new DatabaseHelper(testContext);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testSave() throws Exception {
//        BoolComponent item = new BoolComponent("FooItem");
//        SQLiteDatabase db = DbHelper.getWritableDatabase();
//        boolHelper.save(db, item);
//
//        BoolComponent returnedItem = boolHelper.getItemWithName(db,item.getName());
//        assertNotNull(returnedItem);
//        assertTrue(returnedItem.getName().equals(item.getName()));
//        db.close();
    }

    @Test
    public void testDelete() throws Exception {
        BoolComponent itemDelete = new BoolComponent("FooItemDelete");

        SQLiteDatabase db = DbHelper.getWritableDatabase();
        boolHelper.save(db, itemDelete);

        boolHelper.delete(db, itemDelete);

        db.close();
    }

    @Test
    public void testGetAll() throws Exception {
        BoolComponent itemVisible = new BoolComponent("FooItemVisible");
        BoolComponent itemInvisible = new BoolComponent("FooItemInvisible");

        SQLiteDatabase db = DbHelper.getWritableDatabase();
        itemInvisible.setVisible(false);
        boolHelper.save(db, itemVisible);
        boolHelper.save(db, itemInvisible);

        ArrayList<BoolComponent> afterSave = boolHelper.getAll(db);
        assertTrue("doesn't contain visible item", afterSave.contains(itemVisible));
        assertTrue("doesn't contain invisible item", afterSave.contains(itemInvisible));
//        testContext.getContentResolver().query("content:// com.nmatte.mood.provider", );
        db.close();
    }
}