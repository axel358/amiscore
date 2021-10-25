package cu.daxyel.amiscore.models;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<Index> indexes;

    public Category(String name, ArrayList<Index> indexes) {
        this.name = name;
        this.indexes = indexes;
    }

    public ArrayList<Index> getIndexes() {
        return indexes;
    }

    public String getName() {
        return name;
    }

}
