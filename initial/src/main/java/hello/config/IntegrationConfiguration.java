package hello.config;

import hello.HelloMessageHandler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

@Configuration
@EnableAutoConfiguration
public class IntegrationConfiguration {

    @Bean
    public SubscribableChannel publishSubscribeChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public SubscribableChannel directChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageHandler messageHandler(@Qualifier("publishSubscribeChannel") final SubscribableChannel channel) {
        return new HelloMessageHandler(channel);
    }

    @Bean
    public ThreadPoolExecutorFactoryBean executor() {
        final ThreadPoolExecutorFactoryBean bean = new ThreadPoolExecutorFactoryBean();
        bean.setCorePoolSize(10);
        bean.setMaxPoolSize(10);
        bean.setWaitForTasksToCompleteOnShutdown(true);
        return bean;
    }
}