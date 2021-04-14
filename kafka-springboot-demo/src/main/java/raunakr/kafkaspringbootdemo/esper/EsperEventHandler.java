package raunakr.kafkaspringbootdemo.esper;


import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import raunakr.kafkaspringbootdemo.DataModel;
import raunakr.kafkaspringbootdemo.esper.EsperSubscriber;



@Component
public class EsperEventHandler implements InitializingBean {
    private static final Logger logging = LoggerFactory.getLogger(EsperEventHandler.class);

    private EPServiceProvider epService;
    private EPStatement vowelEventStatement;

    @Autowired
    @Qualifier("vowelEventSubscriber")
    private EsperSubscriber vowelEventSubscriber;

    @Override
    public void afterPropertiesSet() {
        logging.debug("Configuring Esper...");
        initService();
    }

    /**
     * Configure Esper Statement(s).
     */
    public void initService() {
        logging.debug("Initializing Esper Service ..");
        Configuration config = new Configuration();
        config.addEventTypeAutoName("raunakr.kafkaspringbootdemo");
        epService = EPServiceProviderManager.getDefaultProvider(config);

        createEsperStatement();
    }

    /**
     * Handle the incoming TemperatureEvent.
     */
    public void handle(DataModel event) {
        logging.info("Received Esper Event -> {}".format(event.toString()));
        epService.getEPRuntime().sendEvent(event);
    }

    /**
     * EPL to check if any vowel is there in the statement.
     */
    private void createEsperStatement() {
        logging.debug("create vowelEventStatement");
        vowelEventStatement = epService.getEPAdministrator().createEPL(
                vowelEventSubscriber.getStatement());
        vowelEventStatement.setSubscriber(vowelEventSubscriber);
    }

}
