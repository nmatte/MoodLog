package com.nmatte.mood.database.modules;


public class ModuleInfo {
    public boolean isVisible;
    public long id;

    public ModuleInfo(long id, boolean isVisible) {
        this.isVisible = isVisible;
        this.id = id;
    }
}
