package by.training;

import io.nats.streaming.AckHandler;
import io.nats.streaming.NatsStreaming;
import io.nats.streaming.Options;
import io.nats.streaming.StreamingConnection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

public class Publisher {
    private String urls;
    private String subject;
    private String clusterId;
    private String clientId;
    private boolean async;

    private AckHandler acb;
    private StreamingConnection sc;
    private final CountDownLatch latch = new CountDownLatch(1);
    private final String[] guid = new String[1];

    public Publisher(String urls, String subject, String clusterId, String clientId, boolean async) throws IOException, TimeoutException, InterruptedException {
        this.urls = urls;
        this.subject = subject;
        this.clusterId = clusterId;
        this.clientId = clientId;
        this.async = async;

        init();
    }

    private void init() throws InterruptedException, TimeoutException, IOException {
        Options opts = NatsStreaming.defaultOptions();
        if (urls != null) {
            opts = new Options.Builder().natsUrl(urls).build();
        }

        try {
            sc = NatsStreaming.connect(clusterId, clientId, opts);

            acb = (nuid, ex) -> {
                System.out.printf("Received ACK for guid %s\n", nuid);
                if (ex != null) {
                    System.err.printf("Error in server ack for guid %s: %s", nuid,
                            ex.getMessage());
                }
                if (!guid[0].equals(nuid)) {
                    System.err.printf(
                            "Expected a matching guid in ack callback, got %s vs %s\n", nuid,
                            guid[0]);
                }
                System.out.flush();
                latch.countDown();
            };
        } catch (IOException e) {
            sc.close();
        }
    }

    public void publish(byte[] data) throws InterruptedException, IOException {
        if (!async) {
            try {
                sc.publish(subject, data);
            } catch (Exception e) {
                System.err.printf("Error during publish: %s\n", e.getMessage());
                throw (e);
            }
        } else {
            try {
                guid[0] = sc.publish(subject, data, acb);
                latch.await();
            } catch (IOException e) {
                System.err.printf("Error during async publish: %s\n", e.getMessage());
                throw (e);
            }

            if (guid[0].isEmpty()) {
                String msg = "Expected non-empty guid to be returned.";
                System.err.println(msg);
                throw new IOException(msg);
            }
            System.out.printf("Published [%s] : '%s' [guid: %s]\n", subject, new String(data, StandardCharsets.UTF_8),
                    guid[0]);
        }
    }

    public void close() throws InterruptedException, TimeoutException, IOException {
        sc.close();
    }
}
