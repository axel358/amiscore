package cu.daxyel.amiscore.models;

public class Diagnosis {
    private int id;
    private String name;
    private String ci;
    private String disease;
    private String probabilityInfo;
    private String date;
    private String observations;

    public Diagnosis(int id, String name, String ci, String disease, String probabilityInfo, String date, String observations) {
        this.id = id;
        this.name = name;
        this.ci = ci;
        this.disease = disease;
        this.probabilityInfo = probabilityInfo;
        this.date = date;
        this.observations = observations;
    }

    public void setNamme(String name) {
        this.name = name;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public void setProbabilityInfo(String probabilityInfo) {
        this.probabilityInfo = probabilityInfo;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getObservations() {
        return observations;
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
