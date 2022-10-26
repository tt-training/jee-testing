package fi.crowdcollective.tuotehallinta.websocket;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import fi.crowdcollective.model.Kirja;

@ServerEndpoint(value = "/kirjaws", encoders = MessageEncoder.class)
@ApplicationScoped
public class WebIlmoittelija {
    @Inject
    private Logger log;
    private static Set<Session> asiakkaat
            = new CopyOnWriteArraySet<>();
    private Session session;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        asiakkaat.add(session);
        log.info("Uusi asiakas, nyt: " + asiakkaat.size());
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        asiakkaat.remove(session);
        log.info("Asiakas katosi, nyt: " + asiakkaat.size());
    }

    private void broadcast(Message message)
            throws IOException {
        asiakkaat.forEach(session -> {
            synchronized (session) {
                try {
                    session.getBasicRemote().
                            sendObject(message);
                } catch (IOException | EncodeException e) {
                    log.log(Level.SEVERE, "broadcast virhe: " + e);
                }
            }
        });
    }
    public void onKirjaListaMuuttunut(@Observes(notifyObserver = Reception.IF_EXISTS) Kirja event) throws IOException{
        broadcast(new Message("Luotu/poistettu kirja id:llä: " + event.getId()));
        log.info("Broadcast tehty");
    }

}

