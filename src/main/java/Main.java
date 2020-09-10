import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TopicCreator topicCreator = new TopicCreator();
        Sender sender;
        Receiver receiver;

        System.out.println("enter topic name");
        String topicName = scanner.nextLine();
        topicCreator.createTopic(topicName);
        sender = new Sender(topicName);
        receiver = new Receiver(topicName);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            try (sender) {
                while (true) {
                    if (!sender.isOpen().get()) {
                        break;
                    }
                    sender.send();
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.submit(() -> {
            try (receiver) {
                while (true) {
                    if (!sender.isOpen().get()) {
                        break;
                    }
                    receiver.receive();
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.shutdown();
    }
}