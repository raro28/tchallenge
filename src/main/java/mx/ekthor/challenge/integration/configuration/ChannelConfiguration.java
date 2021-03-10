package mx.ekthor.challenge.integration.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHandler;

/*https://examples.javacodegeeks.com/enterprise-java/spring/integration/spring-integration-kafka-tutorial/*/
@Configuration
public class ChannelConfiguration {

    @Bean
    public DirectChannel producerChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "producerChannel")
    public MessageHandler kafkaMessageHandler(@Autowired KafkaTemplate kafkaTemplate) {
        KafkaProducerMessageHandler handler = new KafkaProducerMessageHandler(kafkaTemplate);
        handler.setMessageKeyExpression(new LiteralExpression("kafka-integration"));

        return handler;
    }
}
