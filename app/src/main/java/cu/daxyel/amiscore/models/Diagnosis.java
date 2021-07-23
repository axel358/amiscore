package cu.daxyel.amiscore.models;

public class Diagnosis {
    private int id;
    private String name;
    private String ci;
    private String disease;
    private String probabilityInfo;
    private String date;



    public Diagnosis(int id, String name, String ci, String disease, String probabilityInfo, String date) {
        this.id = id;
        this.name = name;
        this.ci = ci;
        this.disease = disease;
        this.probabilityInfo = probabilityInfo;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCi() {
        return ci;
    }

    public String getDisease() {
        return disease;
    }

    public String getDate() {
        return date;
    }

    public String getProbabilityInfo() {
        return probabilityInfo;
    }
}
