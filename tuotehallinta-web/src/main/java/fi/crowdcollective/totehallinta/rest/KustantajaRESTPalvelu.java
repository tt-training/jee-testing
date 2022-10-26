package fi.crowdcollective.totehallinta.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fi.crowdcollective.dao.KustantajaRepositorio;
import fi.crowdcollective.ejb.KirjaPalvelu;
import fi.crowdcollective.model.Kirja;
import fi.crowdcollective.model.Kustantaja;

@Path("/kustantajat")
public class KustantajaRESTPalvelu {
    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private KustantajaRepositorio repo;

    @Inject
    private KirjaPalvelu ejb;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Kustantaja> kaikkiKustantajat() {
        return repo.findAllEagerly();
    }

    @GET
    @Path("/{id:[0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Kustantaja lookupMemberById(@PathParam("id") long id) {
        Kustantaja kustantaja = repo.findById(id);
        if (kustantaja == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return kustantaja;
    }

    @GET
    @Path("/{id:[0-9]+}/kirjat")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Kirja> kustantajanKirjat(@PathParam("id") long id) {
        List<Kirja> kirjat = repo.findKirjatByKustantajaId(id);
        return kirjat;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uusiKustantaja(Kustantaja kustantaja) {
        Response.ResponseBuilder builder = null;
        try {
            validoiKustantaja(kustantaja);
            ejb.luoKustantaja(kustantaja);
//            repo.uusiKustantaja(kustantaja);
            builder = Response.ok(kustantaja); // location headerin asetus...
        } catch (ConstraintViolationException e) {
            builder = rakennaViolationResponse(e.getConstraintViolations());
        } catch (ValidationException e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("url", "Huono url");
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("virhe", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        log.info("Luotu kustantaja: " + kustantaja);
        return builder.build();
    }

    private void validoiKustantaja(Kustantaja kustantaja) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Kustantaja>> violations = validator.validate(kustantaja);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
        // Esimerkki omasta, varsin yksinkertaistetusta, validoinnista
        String url = kustantaja.getWebsite();
        if (url == null || url.isEmpty()) return;
        try {
            URI uri = new URI(kustantaja.getWebsite());
        } catch (URISyntaxException e) {
            throw new ValidationException("Huono URI violation");
        }
    }

    private Response.ResponseBuilder rakennaViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

}