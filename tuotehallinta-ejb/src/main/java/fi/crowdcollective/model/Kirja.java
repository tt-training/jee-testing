package fi.crowdcollective.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Calendar;
import java.util.List;

@XmlRootElement
@Entity
public class Kirja {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String nimi;
    private String ISBN10;
    private String ISBN13;
    private String kieli;
    private double hinta;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Calendar julkaistu;
    private String editio;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "kirja_kirjailija",
            joinColumns = @JoinColumn(name = "kirja_id"),
            inverseJoinColumns = @JoinColumn(name = "kirjailija_id")
    )
    private List<Kirjailija> kirjailijat;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name="kustantaja")
    private Kustantaja kustantaja;

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

    public String getISBN10() {
        return ISBN10;
    }

    public void setISBN10(String ISBN10) {
        this.ISBN10 = ISBN10;
    }

    public String getISBN13() {
        return ISBN13;
    }

    public void setISBN13(String ISBN13) {
        this.ISBN13 = ISBN13;
    }

    public String getKieli() {
        return kieli;
    }

    public void setKieli(String kieli) {
        this.kieli = kieli;
    }

    public double getHinta() {
        return hinta;
    }

    public void setHinta(double hinta) {
        this.hinta = hinta;
    }

    public Calendar getJulkaistu() {
        return julkaistu;
    }

    public void setJulkaistu(Calendar julkaistu) {
        this.julkaistu = julkaistu;
    }

    public String getEditio() {
        return editio;
    }

    public void setEditio(String editio) {
        this.editio = editio;
    }

    public List<Kirjailija> getKirjailijat() {
        return kirjailijat;
    }

    public void setKirjailijat(List<Kirjailija> kirjailijat) {
        this.kirjailijat = kirjailijat;
    }

    public Kustantaja getKustantaja() {
        return kustantaja;
    }

    public void setKustantaja(Kustantaja kustantaja) {
        this.kustantaja = kustantaja;
    }
}
