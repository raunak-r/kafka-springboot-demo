package raunakr.kafkaspringbootdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {
    private static final Logger logging = LoggerFactory.getLogger(KafkaController.class);

    private KafkaTemplate<String, DataModel> kafkaTemplate; // create a object of kafka-broker to send and receive msg

    @Autowired
    public KafkaController(KafkaTemplate<String, DataModel> kafkaTemplate){
        /**
         * Initialize this object to inject Kafka-broker object to this API.
         * All further API's to this class will utilize the privately available kafka-broker object
         * to send and receive messages.
         */
        logging.info("Configured kafkaTemplate");
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void post(@RequestBody DataModel dataModel){
        /**
         * A dataModel will be sent to the kafka-broker object via the privately declared variable above.
         */
        logging.info("Received New Data from POST API -> " + dataModel.toString());
        kafkaTemplate.send("topic1", dataModel);
    }

    @KafkaListener(topics= "topic1")    // comment this line to stop kafka listening.
    public void getFromKafka(DataModel dataModel){
        /**
         * Kafka Listener to automatically print on terminal when a msg is published.
         * */

        logging.info("Kafka Consumer Triggered - Received Data -> " + dataModel.toString());
    }
}
