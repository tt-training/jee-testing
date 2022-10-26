package fi.crowdcollective.totehallinta.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fi.crowdcollective.dao.KirjaRepositorio;
import fi.crowdcollective.ejb.KirjaPalvelu;
import fi.crowdcollective.model.Kirja;

@Path("/kirjat")
public class KirjaRESTPalvelu {
    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private KirjaRepositorio repo;

    @Inject
    private KirjaPalvelu ejb;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Kirja> kaikkiKirjat() {
        return repo.findAllEagerly();
    }
    @GET
    @Path("/{id:[0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Kirja lookupKirjaById(@PathParam("id") long id) {
        Kirja kirja = repo.findById(id);
        if (kirja == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return kirja;
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response luoUusiKirja(Kirja kirja) {
        Response.ResponseBuilder builder = null;
        Set<ConstraintViolation<Kirja>> violations = null;
        try {
            violations = validator.validate(kirja);

            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
            }

            ejb.luoKirja(kirja);

            builder = Response.ok(kirja); // location headerin asetus...
        } catch (ConstraintViolationException e) {

            Map<String, String> responseObj = new HashMap<>();

            for (ConstraintViolation<?> violation : violations) {
                responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
            }

            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("virhe", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        log.info("Luotu uusi kirja: " + kirja.getId());
        return builder.build();

    }

    @DELETE
    @Path("/{id:[0-9]+}")
    public Response poistaKirja(@PathParam("id") long id) {
        Kirja poistettu = ejb.poistaKirja(id);
        if (poistettu == null) {
            return Response.status(404).build();
        }
        return Response.noContent().build();
    }

}
