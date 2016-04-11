package com.nmatte.mood.database.components;

import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

import com.nmatte.mood.models.components.BoolComponent;

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

        long id = boolHelper.save(itemDelete);
        itemDelete.setId(id);
        assertEquals(1, boolHelper.delete(itemDelete));
    }

    @Test
    public void testGetAll() throws Exception {
        BoolComponent itemVisible = new BoolComponent("FooItemVisible");
        BoolComponent itemInvisible = new BoolComponent("FooItemInvisible");

        itemInvisible.setVisible(false);
        itemVisible.setId(boolHelper.save(itemVisible));
        itemInvisible.setId(boolHelper.save(itemInvisible));

        ArrayList<BoolComponent> afterSave = boolHelper.getAll();
        assertTrue("doesn't contain invisible item", afterSave.contains(itemInvisible));
        assertTrue("doesn't contain visible item", afterSave.contains(itemVisible));
    }

    @Test
    public void testGetByParentId() throws Exception {
        BoolComponent item = new BoolComponent("TestItem");
        item.setModuleId(1);

        long id = boolHelper.save(item);
        item.setId(id);
        ArrayList<BoolComponent> components = boolHelper.getByParentId(1);
        ArrayList<BoolComponent> all = boolHelper.getAll();
        assertTrue("fetches any components", components.size() > 0);
        assertTrue("fetches components by parent id", components.contains(item));
    }

    @Test
    public void testSave() throws Exception {
        BoolComponent item = new BoolComponent("TestItem");
        item.setModuleId(1);
        long id = boolHelper.save(item);
        BoolComponent returnedItem = boolHelper.find(id);

        assertTrue("BoolComponent id updates on save", id  > 0);
        assertNotNull("BoolComponent is found", returnedItem);
        assertEquals(1, returnedItem.getModuleId());
        assertTrue("Component name saves correctly",returnedItem.getName().equals(item.getName()));
    }
}