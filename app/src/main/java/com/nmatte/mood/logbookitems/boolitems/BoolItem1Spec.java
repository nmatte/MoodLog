package com.nmatte.mood.logbookitems.boolitems;

import com.nmatte.mood.logbookitems.LogbookItemContract;
import com.yahoo.squidb.annotations.ColumnSpec;
import com.yahoo.squidb.annotations.TableModelSpec;

@TableModelSpec(className = "BoolItem1", tableName = LogbookItemContract.Bool.ITEM_TABLE)
public class BoolItem1Spec {
    String name;


    @ColumnSpec(name = "id")
    long id;
}
