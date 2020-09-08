import java.util.Scanner;

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

        Thread producerThread = new Thread(()->{
            while(sender.isOpen()){
                sender.send();
                }
            sender.close();
        });
        producerThread.start();

        Thread consumerThread = new Thread(()->{
            while(sender.isOpen()){
                receiver.receive();
            }
            receiver.close();
        });
        consumerThread.start();
    }
}