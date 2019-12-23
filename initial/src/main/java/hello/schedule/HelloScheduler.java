package hello.schedule;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class HelloScheduler extends AbstractMessageHandler implements DisposableBean {

    @Value("${retry.ms:300000}")
    private long retryMs;

    @Autowired
    @Qualifier("publishSubscribeChannel")
    private SubscribableChannel channel;

    private ScheduledExecutorService scheduledExecutorService;

    @Override
    protected void onInit() {
        channel.subscribe(this);
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        super.onInit();
    }

    @Override
    public void destroy() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
        }
        channel.unsubscribe(this);
    }

    @Override
    protected void handleMessageInternal(final Message<?> message) {
        System.out.println("Received message");
    }

    @Scheduled(fixedRate=60000, initialDelay=10000)
    public void sayHello() {
        System.out.println("Hello");
    }
}