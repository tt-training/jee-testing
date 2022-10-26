package fi.crowdcollective.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Kustantaja {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String nimi;
    private String website;
    @OneToMany(mappedBy = "kustantaja", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private List<Kirja> kirjat;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
