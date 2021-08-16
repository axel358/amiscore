package cu.daxyel.amiscore.models;

public class Criteria
{
    private int weight;
    private String name;
    private boolean isSelected;

    public Criteria(int weight, String name,boolean isSelected)
    {
        this.weight = weight;
        this.name = name;
        this.isSelected=isSelected;
    }

    public int getWeight()
    {
        return weight;
    }

    public String getName()
    {
        return name;
    }
    public boolean getSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
