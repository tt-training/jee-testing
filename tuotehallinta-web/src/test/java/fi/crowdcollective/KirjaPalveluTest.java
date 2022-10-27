package fi.crowdcollective;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.validation.Validator;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import fi.crowdcollective.dao.KirjaRepositorio;
import fi.crowdcollective.ejb.KirjaPalvelu;
import fi.crowdcollective.model.Kirja;
import fi.crowdcollective.totehallinta.rest.KirjaRESTPalvelu;

public class KirjaPalveluTest extends JerseyTest {
	@Mock
	private KirjaRepositorio service;
	@Mock
	private Validator validator;
	@Mock
	private KirjaPalvelu ejb;

	@Override
	public ResourceConfig configure() {
		MockitoAnnotations.initMocks(this);
		return new ResourceConfig().register(KirjaRESTPalvelu.class).register(new AbstractBinder() {
			protected void configure() {
				bind(service).to(KirjaRepositorio.class);
			}
		}).register(new AbstractBinder() {
			protected void configure() {
				bind(validator).to(Validator.class);
			}
		}).register(new AbstractBinder() {
			protected void configure() {
				bind(ejb).to(KirjaPalvelu.class);
			}
		});
	}

	@Test
	public void haeKirja() {
		Mockito.doReturn(new Kirja()).when(service).findById(1L);
		Response response = target("/kirjat/1").request().get();
		assertEquals("Status should be 200: ", Status.OK.getStatusCode(), response.getStatus());
		Kirja content = response.readEntity(Kirja.class);
		assertNotNull(content);
	}

}
