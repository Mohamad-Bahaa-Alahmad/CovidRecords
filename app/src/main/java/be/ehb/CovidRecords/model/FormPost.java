package be.ehb.CovidRecords.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class FormPost implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String municipality, province, CASES;


    public FormPost() {
    }
    @Ignore

    public FormPost(String municipality, String province, String CASES) {
        this.municipality = municipality;
        this.province = province;
        this.CASES = CASES;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCASES() {
        return CASES;
    }

    public void setCASES(String CASES) {
        this.CASES = CASES;
    }

    @Override
    public String toString() {
        return "FormPost{" +
                "id=" + id +
                ", municipality='" + municipality + '\'' +
                ", province='" + province + '\'' +
                ", CASES='" + CASES + '\'' +
                '}';
    }
}
