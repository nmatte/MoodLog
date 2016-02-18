package com.nmatte.mood.models.components;

/**
 * These are one of the building blocks of the chart.
 * They represent number pickers.
 */
public class NumComponent extends LogbookComponent {
    private int maxNum;
    private int defaultNum;

    public NumComponent(Long id, String name) {
        super(id, name);
    }

    public NumComponent(String name){
        super(name);
    }

    public NumComponent(Long id, String name, int maxNum, int defaultNum){
        super (id,name);
        this.maxNum = maxNum;
        this.defaultNum = defaultNum;
    }

    public NumComponent(String name, int maxNum, int defaultNum){
        super(name);
        this.maxNum = maxNum;
        this.defaultNum = defaultNum;
    }

    public int getDefaultNum() {
        return defaultNum;
    }

    public void setDefaultNum(int defaultNum) {
        this.defaultNum = defaultNum;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof NumComponent))
            return false;
        else {
            NumComponent rhs = (NumComponent) o;
            if (rhs.getId() == null || getId() == null)
                return false;
            return this.getId().equals(rhs.getId());
        }
    }

    @Override
    public int hashCode() {
        long prime = 457;
        long hash = 1;
        hash = hash * (( getId() + prime));
        return (int) hash;
    }

    @Override
    protected String prefix() {
        return "N";
    }
}
