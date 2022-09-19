package marketproducts;

import com.fasterxml.jackson.databind.JsonDeserializer;
import io.github.susimsek.boot.kafka.KafkaProperties;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration

public class RestConfig {


    @Bean
    public DefaultKafkaConsumerFactory<String,String> consumerFactory() {
        Map<String,Object> props = new HashMap<>();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id",  "group-id");
        props.put("auto.offset.reset", "earliest");

        props.put("string.json.trusted.packages", "*");
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(), new StringDeserializer());
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}



















