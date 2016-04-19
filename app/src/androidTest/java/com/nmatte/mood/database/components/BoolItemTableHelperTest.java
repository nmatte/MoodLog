package com.nmatte.mood.database.components;

import android.database.Cursor;
import android.net.Uri;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.providers.ColumnProvider;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class BoolItemTableHelperTest extends InstrumentationTestCase {
    RenamingDelegatingContext testContext;
    BoolItemTableHelper boolHelper;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        testContext = new RenamingDelegatingContext(getInstrumentation().getContext(), "test_");
        boolHelper = new BoolItemTableHelper(testContext);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }


    @Test
    public void testFind() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {
        BoolComponent itemDelete = new BoolComponent("FooItemDelete");

        long id = boolHelper.insert(itemDelete);
        itemDelete.setId(id);
        assertEquals(1, boolHelper.delete(itemDelete));
    }

    @Test
    public void testGetAll() throws Exception {
        BoolComponent itemVisible = new BoolComponent("TestGetAll");

        itemVisible.setId(boolHelper.insert(itemVisible));

        ArrayList<BoolComponent> afterSave = boolHelper.getAll();
        assertTrue("contains visible item", afterSave.contains(itemVisible));
    }

    @Test
    public void testGetByParentId() throws Exception {
        BoolComponent item = new BoolComponent("TestGetByParentId" + DateTime.now().toString("DDDHHmmssSSS"));
        item.setModuleId(1);

        long id = boolHelper.insert(item);
        item.setId(id);
        ArrayList<BoolComponent> components = boolHelper.getByParentId(1);
        ArrayList<BoolComponent> all = boolHelper.getAll();
        assertTrue("fetches any components", components.size() > 0);
        assertTrue("fetches components by parent id", components.contains(item));
    }

    @Test
    public void testInsert() throws Exception {
        BoolComponent item = new BoolComponent("TestInsert" + DateTime.now().toString("DDDHHmmssSSS"));
        item.setModuleId(1);
        long id = boolHelper.insert(item);

        assertTrue("Inserts component with new id", id != -1);
        assertTrue("Doesn't insert component twice", boolHelper.insert(item) == -1);
        item.setId(id);


        Cursor colExists = testContext.getContentResolver().query(Uri.withAppendedPath(ColumnProvider.BASE_URI, "bools"), null, null, null, null);
        assertTrue("Component column added correctly", colExists.getColumnIndex(item.columnLabel()) != -1);
        colExists.close();

        boolHelper.delete(item);
    }
}