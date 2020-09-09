import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Scanner;

public class Sender implements AutoCloseable {
    private final String topic;
    private final Producer producer;
    private final Scanner scanner;
    private boolean isOpen = true;

    public Sender(String topic) {
        this.topic = topic;
        this.producer = createProducer();
        this.scanner = new Scanner(System.in);
    }

    public synchronized boolean isOpen() {
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
            isOpen = false;
        }
        producer.send(new ProducerRecord<String, String>(topic, message));
    }

    public void close() {
        producer.close();
    }
}
