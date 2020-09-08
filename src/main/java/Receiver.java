import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Receiver{
    private Consumer consumer = createConsumer();
    private String topicName;

    public Receiver(String topicName) {
        this.topicName = topicName;
        this.consumer.subscribe(Arrays.asList(topicName));
    }

    private KafkaConsumer createConsumer(){
        Properties props = setProperties();
        return new KafkaConsumer <String, String>(props);
    }

    private Properties setProperties(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enabe.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    public void receive(){
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, String> record : records) {
            System.out.println(record.value());
        }
    }

    public void close(){
        consumer.close();
    }
}
