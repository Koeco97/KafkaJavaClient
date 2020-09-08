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
            while(true){
                if(sender.send()==1){
                    sender.close();
                    receiver.close();
                    return;
                }
        }});
        producerThread.start();

        Thread consumerThread = new Thread(()->{
            while(true){
                receiver.receive();
            }});
        consumerThread.start();
    }
}