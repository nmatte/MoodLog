package com.nmatte.mood.database.entries;

import android.content.ContentValues;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

import com.nmatte.mood.database.components.BoolItemTableHelper;
import com.nmatte.mood.database.modules.ModuleContract;
import com.nmatte.mood.database.modules.ModuleTableHelper;
import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.models.modules.BoolModule;

import org.junit.Test;


public class ChartEntryTableHelperTest extends InstrumentationTestCase {
    RenamingDelegatingContext testContext;
    ChartEntryTable helper;
    BoolModule mod;
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        testContext = new RenamingDelegatingContext(getInstrumentation().getContext(), "test_");
        helper = new ChartEntryTable(testContext);
        String modName = ModuleContract.BOOL_MODULE_NAME;

        long modId = new ModuleTableHelper(testContext).save(modName);

        BoolComponent comp = new BoolComponent("EntryTestComp");

        comp.setModuleId(modId);
        BoolItemTableHelper bHelper = new BoolItemTableHelper(testContext);
        bHelper.insert(comp);


        mod = new BoolModule(modId, modName, true, bHelper.getByParentId(modId));
        assertTrue("module initialized properly", mod.getItems().size() > 0);
    }

    @Test
    public void testGetEntryGroup() throws Exception {

    }

    @Test
    public void testSave() throws Exception {
        ContentValues testValues = new ContentValues();
        BoolComponent comp = mod.getItems().get(0);

        testValues.put(comp.columnLabel(), true);

        ChartEntry entry = new ChartEntry(testValues);

        helper.save(entry);
    }
}