package raunakr.kafkaspringbootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private KafkaTemplate<String, DataModel> kafkaTemplate; // create a object of kafka-broker to send and receive msg

    @Autowired
    public KafkaController(KafkaTemplate<String, DataModel> kafkaTemplate){
        /*
        Initialize this object to inject Kafka-broker object to this API.
        All further API's to this class will utilize the privately available kafka-broker object
        to send and receive messages.
         */
//        System.out.println("Inside KafkaController.KafkaController");
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void post(@RequestBody DataModel dataModel){
        /*
        A dataModel will be sent to the kafka-broker object via the privately declared variable above.
         */
        System.out.println(dataModel.getField1());
        System.out.println(dataModel.getField2());
        kafkaTemplate.send("topic1", dataModel);
    }

}
