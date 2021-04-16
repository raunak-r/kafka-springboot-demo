package raunakr.kafkaspringbootdemo.nifi;

public class NifiSettings {
    /**
     * Class to define Nifi related configurations and settings to be used elsewhere.
     */
    public static final String nifiUri = "http://localhost:8082/nifi-api/processors/";

    public String getNifiURI(){
        return nifiUri;
    }
}
