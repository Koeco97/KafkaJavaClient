import java.util.Scanner;

public class Main {
    static Object monitor = new Object();
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
                synchronized (monitor) {
                    sender.send();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            sender.close();
        });
        producerThread.start();

        Thread consumerThread = new Thread(()->{
            while(sender.isOpen()){
                synchronized (monitor) {
                    receiver.receive();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            receiver.close();
        });
        consumerThread.start();
    }
}