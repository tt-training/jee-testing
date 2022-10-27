package com.crowcollective.simple.ejb;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.crowdcollective.simple.ejb.SimpleEJB;

@RunWith(Arquillian.class)
public class ArqSimpleEJBITARQ {
    @Inject
    SimpleEJB simpleEJB;
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "testi.war")  // could change to JavaArchive, no need for name
                .addClass(SimpleEJB.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    @Test
    public void should_create_greeting() {
        String message = simpleEJB.helloThere("Jakarta EE");
        assertTrue("message should contain \"Hello Jakarta EE!\"",
                message.endsWith("Hello Jakarta EE!"));
    }
}
