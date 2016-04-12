package com.nmatte.mood.database.components;

import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

import com.nmatte.mood.models.components.NumComponent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


public class NumItemTableHelperTest extends InstrumentationTestCase {
    RenamingDelegatingContext testContext;
    NumItemTableHelper helper;
    NumComponent comp;

    @Before
    public void setUp() throws Exception {
        testContext =
                new RenamingDelegatingContext(getInstrumentation().getContext(), "test_");
        helper = new NumItemTableHelper(testContext);
        comp = new NumComponent("TestNum");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDelete() throws Exception {
        long id = helper.save(comp);
        comp.setId(id);
        assertEquals(1, helper.delete(comp));
    }

    @Test
    public void testGetAll() throws Exception {
        comp.setId(helper.save(comp));

        ArrayList<NumComponent> all = helper.getAll();

        assertTrue("getAll contains NumComponent", all.contains(comp));
    }

    @Test
    public void testGetByParentId() throws Exception {

    }

    @Test
    public void testSave() throws Exception {
        long id = helper.save(comp);

        assertTrue("id of new component returned", id > -1);
    }
}