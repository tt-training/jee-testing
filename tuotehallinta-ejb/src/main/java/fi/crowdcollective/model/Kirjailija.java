package fi.crowdcollective.model;

//import javax.json.bind.annotation.JsonbTransient;
//import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/* Annotation XmlRootElement must be present also for proper JSON marshalling */
@XmlRootElement
@Entity
public class Kirjailija {
    @Id
    @GeneratedValue
    private long id;
    @Null
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String etunimi;
    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String sukunimi;
    @JsonIgnore @XmlTransient
    @ManyToMany(mappedBy = "kirjailijat")
    private List<Kirja> kirjat;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEtunimi() {
        return etunimi;
    }

    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    public String getSukunimi() {
        return sukunimi;
    }

    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    public List<Kirja> getKirjat() {
        return kirjat;
    }

    public void setKirjat(List<Kirja> kirjat) {
        this.kirjat = kirjat;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Kirjailija{");
        sb.append("id=").append(id);
        sb.append(", etunimi='").append(etunimi).append('\'');
        sb.append(", sukunimi='").append(sukunimi).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
