package com.nmatte.mood.models.components;

/**
 * LogbookComponent is a field that the user can add to their logbook.
 * This abstract class covers the common aspects of BoolComponents and NumComponents.
 * It has an id, name, and (optional) color, and belongs to a given module.
 */
abstract public class LogbookComponent {
    protected Long id;
    protected long moduleId = -1;
    protected String name;
    protected int color = 0x000000;
    protected boolean isVisible = true;

    public LogbookComponent(Long id, String name, long moduleId) {
        this.id = id;
        this.name = name;
        this.moduleId = moduleId;
    }

    public LogbookComponent(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public LogbookComponent(String name){
        this(null,name);
    }

    public LogbookComponent(){
        this(null,null);
    }

    public Long getId() {
        if (this.id == null) {
            return (long) -1;
        }
        return id;
    }

    public void setId(Long id) {
        if (this.id == null) {
            this.id = id;
        }
    }

    public String getName() {
        if (name == null) {
            name = "";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isVisible(){
        return this.isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public String toString(){
        return prefix() + id.toString() + ":" + getName();
    }

    public String columnLabel(){
        return prefix() + id.toString();
    }

    protected abstract String prefix();
}
