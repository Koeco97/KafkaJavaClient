import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Receiver implements AutoCloseable {
    private Consumer consumer;

    public Receiver(String topicName) {
        this.consumer = createConsumer();
        this.consumer.subscribe(Arrays.asList(topicName));
    }

    private KafkaConsumer createConsumer() {
        Properties props = KafkaProperties.getConsumerProperties();
        return new KafkaConsumer<String, String>(props);
    }

    public void receive() {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, String> record : records) {
            System.out.println(record.value());
        }
    }

    public void close() {
        consumer.unsubscribe();
        consumer.close();
    }
}
