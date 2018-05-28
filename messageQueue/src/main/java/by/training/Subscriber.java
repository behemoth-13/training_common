package by.training;

import io.nats.streaming.*;

import java.nio.charset.Charset;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Subscriber {
    private String url;
    private String subject;
    private String clusterId = "test-cluster";
    private String clientId = "test-client";
    private final SubscriptionOptions.Builder builder = new SubscriptionOptions.Builder();
    private String qgroup;
    private String durable;
    private int count = 0;
    private boolean unsubscribe;

    public Subscriber(String url, String subject, String clusterId, String clientId) {
        this.url = url;
        this.subject = subject;
        this.clusterId = clusterId;
        this.clientId = clientId;
    }

    public void run() throws Exception {
        Options opts = null;
        if (url != null) {
            opts = new Options.Builder().natsUrl(url).build();
        }

        final CountDownLatch done = new CountDownLatch(1);
        final CountDownLatch start = new CountDownLatch(1);
        final AtomicInteger delivered = new AtomicInteger(0);

        if (durable != null) {
            builder.durableName(durable);
        }
        Thread hook = null;

        try (final StreamingConnection sc = NatsStreaming.connect(clusterId, clientId, opts)) {
            try {
                final Subscription sub = sc.subscribe(subject, qgroup, msg -> {
                    try {
                        start.await();
                    } catch (InterruptedException e) {
                        /* NOOP */
                    }
                    System.out.printf("[#%d] Received on [%s]: '%s %d'\n",
                            delivered.incrementAndGet(), msg.getSubject(), new String(msg.getData(), Charset.forName("UTF-8")), msg.getSequence());
                    if (delivered.get() == count) {
                        done.countDown();
                    }
                }, builder.build());
                hook = new Thread() {
                    public void run() {
                        System.err.println("\nCaught CTRL-C, shutting down gracefully...\n");
                        try {
                            if (durable == null || durable.isEmpty() || unsubscribe) {
                                sub.unsubscribe();
                            }
                            sc.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        done.countDown();
                    }
                };
                Runtime.getRuntime().addShutdownHook(hook);
                System.out.printf("Listening on [%s], clientID=[%s], qgroup=[%s] durable=[%s]\n",
                        sub.getSubject(), clientId, sub.getQueue(),
                        sub.getOptions().getDurableName());
                start.countDown();
                done.await();
                if (durable == null || durable.isEmpty() || unsubscribe) {
                    sub.unsubscribe();
                }
                sc.close();
            } finally {
                Runtime.getRuntime().removeShutdownHook(hook);
            }
        }
    }

    public void startAtTime(Duration dur) {
        builder.startAtTimeDelta(dur);
    }

    public void startWithLast() {
        builder.startWithLastReceived();
    }

    public void deliverAll() {
        builder.deliverAllAvailable();
    }

    public void setStartSeq(Long seqno) {
        builder.startAtSequence(seqno);
    }

    public void setQgroup(String qgroup) {
        this.qgroup = qgroup;
    }

    public void setDurable(String durable) {
        this.durable = durable;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setUnsubscribe(boolean unsubscribe) {
        this.unsubscribe = unsubscribe;
    }
}
