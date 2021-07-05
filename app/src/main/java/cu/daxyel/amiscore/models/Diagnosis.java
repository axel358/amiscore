package cu.daxyel.amiscore.models;

public class Diagnosis {
    private int id;
    private String full_name;
    private String ci;
    private String disease;
    private String consult_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getConsult_date() {
        return consult_date;
    }

    public void setConsult_date(String consult_date) {
        this.consult_date = consult_date;
    }
}
