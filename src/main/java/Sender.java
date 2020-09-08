import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Scanner;

public class Sender {
    private String topic;
    private Producer producer = createProducer();
    Scanner scanner = new Scanner(System.in);

    public Sender(String topic) {
        this.topic = topic;
    }

    private Producer createProducer(){
        Properties props = setProperties();
        return new KafkaProducer
                <String, String>(props);
    }
    private Properties setProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    public synchronized int send(){
        System.out.println("enter message");
        String message = scanner.nextLine();
        if (message.equals("exit")) {
            return 1;
        }
        producer.send(new ProducerRecord<String, String>(topic, message));
        return 0;
    }

    public void close(){
        producer.close();
    }
}
