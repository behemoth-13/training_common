package by.training;

import io.nats.streaming.*;

import java.io.IOException;

public class SyncSubscriber {

    private StreamingConnection sc;
    private String qgroup = null;
    private SubscriptionOptions.Builder sob;

    public SyncSubscriber() throws IOException, InterruptedException {
        Options opts = new Options.Builder().natsUrl("http://localhost:5667").build();

        sc = NatsStreaming.connect(
                "myCluster",
                "syncSubscriber",
                opts
        );

        sob = new SubscriptionOptions.Builder()
                .durableName("durableName")
//                .manualAcks()
        ;
    }

    public void get(String subject) throws IOException, InterruptedException {
        sc.subscribe(subject, qgroup, message -> {
//                try {
//                    message.ack();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            System.out.println(new String(message.getData()));
        }, sob.build());
    }

}
