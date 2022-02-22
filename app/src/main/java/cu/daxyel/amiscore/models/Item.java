package cu.daxyel.amiscore.models;

public class Item {
    private int icon;
    private String name, count;

    public Item(int icon, String name, String count) {
        this.icon = icon;
        this.name = name;
        this.count = count;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }

}
