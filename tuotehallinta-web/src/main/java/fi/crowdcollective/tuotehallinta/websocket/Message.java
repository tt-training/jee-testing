package fi.crowdcollective.tuotehallinta.websocket;

public class Message {
    private String viesti;

    public Message(String viesti) {
        this.viesti = viesti;
    }

    public String getViesti() {
        return viesti;
    }

    public void setViesti(String viesti) {
        this.viesti = viesti;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("viesti='").append(viesti).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
