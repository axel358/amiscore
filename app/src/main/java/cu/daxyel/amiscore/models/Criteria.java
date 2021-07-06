package cu.daxyel.amiscore.models;

public class Criteria
{
    private int weight;
    private String name;

    public Criteria(int weight, String name)
    {
        this.weight = weight;
        this.name = name;
    }

    public int getWeight()
    {
        return weight;
    }

    public String getName()
    {
        return name;
    }
}
