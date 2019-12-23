package hello;

import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;

public class HelloMessageHandler extends AbstractMessageHandler {

    private SubscribableChannel channel;

    public HelloMessageHandler(final SubscribableChannel channel) {
        this.channel = channel;
    }

    @Override
    protected void onInit() {
        channel.subscribe(this);
        super.onInit();
    }

    @Override
    protected void handleMessageInternal(final Message<?> message) {
        System.out.println("Greeting from Spring Boot! " + message.getPayload());
    }

    @Override
    public void destroy() {
        channel.unsubscribe(this);
        super.destroy();
    }
}