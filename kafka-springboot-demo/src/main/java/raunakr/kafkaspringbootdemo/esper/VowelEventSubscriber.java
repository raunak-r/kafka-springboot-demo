package raunakr.kafkaspringbootdemo.esper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import raunakr.kafkaspringbootdemo.DataModel;

import java.util.Map;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */
@Component
public class VowelEventSubscriber implements EsperSubscriber {

    /** Logger */
    private static Logger logging = LoggerFactory.getLogger(VowelEventSubscriber.class);

    /** Used as the minimum starting threshold for a critical event. */
    private static final String CRITICAL_EVENT_THRESHOLD = "100";

    /**
     * If the last event in a critical sequence is this much greater than the first - issue a
     * critical alert.
     */
    private static final String CRITICAL_EVENT_MULTIPLIER = "1.5";

    /**
     * {@inheritDoc}
     */
    public String getStatement() {
        // Example using 'Match Recognise' syntax.
        String crtiticalEventExpression = "select * from DataModel";
        return crtiticalEventExpression;
    }

    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, DataModel> eventMap) {

//        // 1st Temperature in the Critical Sequence
//        DataModel temp1 = (TemperatureEvent) eventMap.get("temp1");
//        // 2nd Temperature in the Critical Sequence
//        TemperatureEvent temp2 = (TemperatureEvent) eventMap.get("temp2");
//        // 3rd Temperature in the Critical Sequence
//        TemperatureEvent temp3 = (TemperatureEvent) eventMap.get("temp3");
//        // 4th Temperature in the Critical Sequence
//        TemperatureEvent temp4 = (TemperatureEvent) eventMap.get("temp4");

        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : CRITICAL EVENT DETECTED! ");
//        sb.append("\n* " + temp1 + " > " + temp2 + " > " + temp3 + " > " + temp4);
        sb.append("\n***************************************");

        logging.debug(sb.toString());
    }


}
