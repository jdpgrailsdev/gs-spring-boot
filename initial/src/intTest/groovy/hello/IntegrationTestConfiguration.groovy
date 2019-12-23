package hello

import static org.mockito.Mockito.mock

import hello.config.IntegrationConfiguration
import hello.schedule.HelloScheduler

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import([IntegrationConfiguration])
class IntegrationTestConfiguration {

    @Bean
    HelloScheduler helloScheduler() {
        mock(HelloScheduler)
    }
}
