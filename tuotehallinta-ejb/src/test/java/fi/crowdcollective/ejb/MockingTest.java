package fi.crowdcollective.ejb;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.event.Event;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fi.crowdcollective.dao.KirjaRepositorio;
import fi.crowdcollective.model.Kirja;
import fi.crowdcollective.model.Kustantaja;

@RunWith(MockitoJUnitRunner.class)
public class MockingTest {
	@InjectMocks
	private KirjaPalvelu kirjaPalvelu = new KirjaPalvelu();
	@Mock
	private KirjaRepositorio kirjaRepositorio;
	@Mock
	private Event<Kirja> kirjaEvent;
	
	@Before
	public void setUp() {
		initMocks(this);
	}
	
	@Test
	public void kaikkienKirjojenHaku() {
		Kirja kirja1 = new Kirja();
		kirja1.setId(1);
		kirja1.setNimi("Kirja 1");
		kirja1.setKustantaja(new Kustantaja());
		kirja1.setHinta(20D);
		
		when(kirjaRepositorio.findAllOrderedByName()).thenReturn(Arrays.asList(kirja1));
		
		List<Kirja> haetut = kirjaPalvelu.haeKirjat();
		assertThat("Yksi kirja", haetut.size(), is(1));
	}

	@Test
	public void luoKirja() {
		Kirja kirja1 = new Kirja();
		kirja1.setId(1);
		kirja1.setNimi("Kirja 1");
		kirja1.setKustantaja(new Kustantaja());
		kirja1.setHinta(20D);
		
		kirjaPalvelu.luoKirja(kirja1);
		assertNotNull(kirja1);
	}
}
