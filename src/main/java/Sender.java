import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Sender implements AutoCloseable {
    private final String topic;
    private final Producer producer;
    private final Scanner scanner;
    private AtomicBoolean isOpen = new AtomicBoolean(true);

    public Sender(String topic) {
        this.topic = topic;
        this.producer = createProducer();
        this.scanner = new Scanner(System.in);
    }

    public AtomicBoolean isOpen() {
        return isOpen;
    }

    private Producer createProducer() {
        Properties props = KafkaProperties.getProducerProperties();
        return new KafkaProducer
                <String, String>(props);
    }

    public void send() {
        System.out.println("enter message");
        String message = scanner.nextLine();
        if (message.equalsIgnoreCase("exit")) {
            isOpen.set(false);
        }
        producer.send(new ProducerRecord<String, String>(topic, message));
    }

    public void close() {
        producer.close();
    }
}
