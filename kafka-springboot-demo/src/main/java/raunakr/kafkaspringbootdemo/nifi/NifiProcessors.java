package raunakr.kafkaspringbootdemo.nifi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/nifi/processors/")
public class NifiProcessors {
    private static final Logger logging = LoggerFactory.getLogger(NifiProcessors.class);
    private static NifiSettings nifiSettings = new NifiSettings();

    @GetMapping("/{processorId}/")
    public String get(@PathVariable String processorId){
        logging.info("Retrieving NIFI Processor Details for -> " + processorId);

        String currURI = nifiSettings.getNifiURI() + processorId;

        RestTemplate restTemplate = new RestTemplate();
        logging.info("Calling API - " + currURI);
        String result = restTemplate.getForObject(currURI, String.class);
//
//        JsonParser springParser = JsonParserFactory.getJsonParser();
//        Map<String, Object> map = springParser.parseMap(result);

//        System.out.println(map);
        return result;
    }

    @PutMapping("/{processorId}/")
    public String put(@PathVariable String processorId, @RequestBody NifiDataModel reqBody) {
        logging.info("Retrieving NIFI Processor Details for -> " + processorId);

        String currURI = nifiSettings.getNifiURI() + processorId + "/run-status";

        HashMap<String,Object> json = new HashMap<String,Object>();
        HashMap<String, String> revision = new HashMap<String, String>();

        revision.put("clientId", reqBody.getclientId());
        revision.put("version", reqBody.getVersion());
        json.put("revision", revision);
        json.put("state", reqBody.getState());
        System.out.println(revision);

        RestTemplate restTemplate = new RestTemplate();
        try{
            restTemplate.put(currURI, json);
            return "Success";
        }
        catch(Exception e) {
            logging.error(e.getMessage());
            return e.getMessage();
        }
    }
}
