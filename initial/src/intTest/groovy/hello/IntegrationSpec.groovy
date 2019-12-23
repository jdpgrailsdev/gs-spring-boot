package hello

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.support.MessageBuilder
import org.springframework.messaging.SubscribableChannel
import org.springframework.test.context.ActiveProfiles

import spock.lang.Specification

@ActiveProfiles('test')
@SpringBootTest(classes=[IntegrationTestConfiguration])
class IntegrationSpec extends Specification {

    @Qualifier('publishSubscribeChannel')
    @Autowired
    SubscribableChannel channel

    def "test that a message can be sent to the channel"() {
        setup:
            def message = MessageBuilder.withPayload('test').build()
        when:
            channel.send(message)
        then:
            notThrown Exception
    }
}

