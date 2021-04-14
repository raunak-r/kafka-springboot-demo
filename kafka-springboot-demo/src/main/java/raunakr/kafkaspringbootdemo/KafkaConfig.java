package raunakr.kafkaspringbootdemo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka // to listen to kafka events
public class KafkaConfig {

    private static final Logger logging = LoggerFactory.getLogger(KafkaConfig.class);

    @Bean
    public ProducerFactory<String, DataModel> producerFactory(){
        /*
        Method to create a Kafka-broker config object at init-time which can be used to be supplied to all such classes
        which will reference the kafka-broker config object in-order to communicate with kafka.
         */
        Map<String, Object>config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        logging.info("Configured Kafka Producer");
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, DataModel> kafkaTemplate(){
        /*
        A kafkaTemplate is basically the main method to return the kafka-broker object which can be used elsewhere.
        This is a producer config bean.
         */
        logging.info("Returning new kafkaTemplate with producer configurations.");
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, DataModel> consumerFactory(){
        /**
         * Configuration Provider for Kafka consumer handler.
         * Usage - Use @KafkaListener to consume the data.
         */

        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group-config");

        ErrorHandlingDeserializer<DataModel> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(DataModel.class, objectMapper()));

        ErrorHandlingDeserializer<String> headerErrorHandlingDeserializer
                = new ErrorHandlingDeserializer<>(new StringDeserializer());

        logging.info("Configured kafka consumer");
        return new DefaultKafkaConsumerFactory<>(config, headerErrorHandlingDeserializer, errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, DataModel> concurrentKafkaListenerContainerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();

        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory());

        logging.info("Returning new KafkaListenerTemplate with consumer configurations.");
        return concurrentKafkaListenerContainerFactory;
    }

    private ObjectMapper objectMapper() {
        logging.info("Inside objectMapper");
        return Jackson2ObjectMapperBuilder.json()
                .visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build();
    }
}
