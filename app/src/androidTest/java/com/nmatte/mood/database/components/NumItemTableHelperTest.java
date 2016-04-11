package com.nmatte.mood.database.components;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.nmatte.mood.models.components.NumComponent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class NumItemTableHelperTest extends AndroidTestCase {
    RenamingDelegatingContext testContext;
    NumItemTableHelper helper;
    NumComponent comp;
    @Before
    public void setUp() throws Exception {
        testContext =
                new RenamingDelegatingContext(getContext(), "test_");
        helper = new NumItemTableHelper(testContext);
        comp = new NumComponent("TestNum");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testGetAll() throws Exception {

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