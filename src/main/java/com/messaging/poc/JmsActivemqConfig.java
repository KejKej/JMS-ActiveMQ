package com.messaging.poc;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;



import javax.jms.QueueConnectionFactory;
import java.util.Map;

import static com.messaging.poc.CustomMessage.TYPE_ID;

@EnableJms
@Configuration
public class JmsActivemqConfig {

    private static final String TYPE_ID_PROPERTY = "_type";

    @Bean
    public MessageConverter jsonMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName(TYPE_ID_PROPERTY);
        converter.setObjectMapper(Jackson2ObjectMapperBuilder.json().build());
        converter.setTypeIdMappings(getTypeIdMappings());
        return converter;
    }

    @Bean
    public JmsTemplate jmsJsonTemplate(QueueConnectionFactory queueConnectionFactory, MessageConverter jsonMessageConverter) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(queueConnectionFactory);
        jmsTemplate.setMessageConverter(jsonMessageConverter);
        return jmsTemplate;
    }


    @Bean
    public DefaultJmsListenerContainerFactory factory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setErrorHandler(customJmsExceptionHandler());
        return factory;
    }

    @Bean(name="queue1Factory")
    public JmsListenerContainerFactory<?> queue1Factory(@Value("${spring.activemq.broker-url}") String uri,
                                                        DefaultJmsListenerContainerFactoryConfigurer configurer){

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(uri);

        configurer.configure(factory, new PooledConnectionFactory(connectionFactory));
        return factory;
    }

    @Bean
    public JmsExceptionHandler customJmsExceptionHandler() {
        return new JmsExceptionHandler();
    }

    protected Map<String, Class<?>> getTypeIdMappings() {
        return Map.of(
                TYPE_ID, CustomMessage.class
        );
    }
}
