package com.nmatte.mood.database.modules;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.nmatte.mood.database.components.BoolItemTableHelper;
import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.models.modules.ModuleConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ModuleTableHelperTest extends AndroidTestCase {
    RenamingDelegatingContext testContext;
    ModuleTableHelper helper;

    @Before
    public void setUp() throws Exception {
        testContext = new RenamingDelegatingContext(getContext(), "test_");
        helper = new ModuleTableHelper(testContext);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSave() throws Exception {
        long id = helper.save(ModuleContract.BOOL_MODULE_NAME);

        assertFalse("ModuleTableHelper saves module by name", id == -1);
    }

    @Test
    public void testGetModules() throws Exception {
        long id = helper.save(ModuleContract.BOOL_MODULE_NAME);
        BoolComponent comp = new BoolComponent("TestItem");
        comp.setModuleId(id);
        long compId = new BoolItemTableHelper(testContext).insert(comp);
        comp.setId(compId);

        ModuleConfig config = helper.getModules();
        assertNotNull("ModuleConfig is successfully loaded", config);
        assertTrue("Component is included in config", config.boolColumns().contains(comp.columnLabel()));
    }
}