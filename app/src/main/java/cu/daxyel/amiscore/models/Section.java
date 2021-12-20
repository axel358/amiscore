package cu.daxyel.amiscore.models;

import java.util.ArrayList;

public class Section {

    private String section_name;
    private ArrayList<Criteria> section_Items;

    public Section(String section_name, ArrayList<Criteria> section_Items) {
        this.section_name = section_name;
        this.section_Items = section_Items;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public ArrayList<Criteria> getSection_Items() {
        return section_Items;
    }

    public void setSection_Items(ArrayList<Criteria> section_Items) {
        this.section_Items = section_Items;
    }

    @Override
    public String toString() {
        return "Section{" +
                "section_name='" + section_name + '\'' +
                ", section_Items=" + section_Items +
                '}';
    }
}
