package fi.crowdcollective.ejb;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import fi.crowdcollective.dao.KirjaRepositorio;
import fi.crowdcollective.dao.KustantajaRepositorio;
import fi.crowdcollective.model.Kirja;
import fi.crowdcollective.model.Kustantaja;

@Stateless
public class KirjaPalvelu {
    @Inject
    private Logger log;
    @Inject
    private KirjaRepositorio kirjarepo;
    @Inject
    private KustantajaRepositorio kustantajarepo;
    @Inject
    Event<Kirja> kirjaEvent;

    public List<Kirja> haeKirjat() {
        return kirjarepo.findAllOrderedByName();
    }
    public void luoKirja(Kirja kirja) {
        kirjarepo.uusiKirja(kirja);
        kirjaEvent.fire(kirja);
        log.info("Laukaistu..");
    }
    public Kirja poistaKirja(long id) {
        Kirja poistettu = kirjarepo.poistaKirja(id);
        if (poistettu!=null) {
            kirjaEvent.fire(poistettu);
        }
        return poistettu;
    }
    public void luoKustantaja(Kustantaja kustantaja) {
        kustantajarepo.uusiKustantaja(kustantaja);
    }
}
