package fi.crowdcollective.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.crowdcollective.model.Kirja;

@ApplicationScoped
public class KirjaRepositorio {
    @Inject
    private EntityManager em;
    @Inject
    Logger log;

    public Kirja findById(Long id) {
        return em.find(Kirja.class, id);
    }

    public List<Kirja> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Kirja> criteria = cb.createQuery(Kirja.class);
        Root<Kirja> kirja = criteria.from(Kirja.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(kirja).orderBy(cb.asc(kirja.get("nimi")));
        return em.createQuery(criteria).getResultList();
    }
    public List<Kirja> findAllEagerly() {
        Query q = em.createQuery("SELECT DISTINCT k FROM Kirja k JOIN FETCH k.kirjailijat");
        List<Kirja> tulos = q.getResultList();
        return tulos;
    }

    public void uusiKirja(Kirja kirja) {
        em.persist(kirja);
    }

    public Kirja poistaKirja(long id) {
        Kirja poistettava = em.find(Kirja.class, id);
        if (poistettava!=null) {
            em.remove(poistettava);
        }
        return poistettava;
    }
}
