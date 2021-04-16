package raunakr.kafkaspringbootdemo.nifi;

import java.util.Map;

public class NifiDataModel {
//    private Map revision;
    private String clientId;
    private String version;
    private String state;

    public NifiDataModel(String clientId, String version, String state){
        this.clientId = clientId;
        this.version = version;
        this.state = state;
    }

    public String getclientId() {
        return clientId;
    }

    public void setclientId(String clientId) {
        this.clientId = clientId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
