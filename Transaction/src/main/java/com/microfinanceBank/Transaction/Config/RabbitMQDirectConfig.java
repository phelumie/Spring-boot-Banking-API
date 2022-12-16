package com.microfinanceBank.Transaction.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@EnableRabbit
public class RabbitMQDirectConfig implements RabbitListenerConfigurer {

	public static final String MAKING_DEPOSIT_ROUTING_TRANSACTION="making.deposit.transaction";


	@Bean
	Queue MakingDepositQueue() {
		return new Queue("MakingDepositQueue", false);
	}


//    @Bean
//	Queue customerQueue() {
//		return  QueueBuilder.durable("customerQueue")
//				.withArgument("x-dead-letter-exchange","deadLetterExchange")
//				.withArgument("x-dead-letter-routing-key","deadLetter").build();
//	}
////
//
//	@Bean
//	Queue dlq() {
//		return  QueueBuilder.durable("deadLetterQueue").build();
//	}


	@Bean
	DirectExchange exchange() {
		return new DirectExchange("direct-exchange");
	}

	@Bean
	Binding MakingDepositBinding(Queue MakingDepositQueue, DirectExchange exchange) {
		return BindingBuilder.bind(MakingDepositQueue).to(exchange).with(MAKING_DEPOSIT_ROUTING_TRANSACTION);
	}


//	@Bean
//	DirectExchange deadLetterExchange() {
//		return new DirectExchange("deadLetterExchange");
//	}




//	@Bean
//	Binding DLQBinding(Queue dlq, DirectExchange exchange) {
//		return BindingBuilder.bind(dlq).to(exchange).with(ROUTING_TRANSACTION);
//	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	@Bean
	public Jackson2JsonMessageConverter producerJsonMessageConverter(){
		ObjectMapper objectMapper=new ObjectMapper();
		return new Jackson2JsonMessageConverter(objectMapper);
	}

	@Bean
	public MappingJackson2MessageConverter consumerJacksonMessageConverter(){
		return new MappingJackson2MessageConverter();
	}
	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerFactory(){
		DefaultMessageHandlerMethodFactory factory=new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJacksonMessageConverter());
		return factory;
	}

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
		rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerFactory());
	}

	@Bean
	MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
		return simpleMessageListenerContainer;
	}


	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJsonMessageConverter());
		rabbitTemplate.setChannelTransacted(true);
		return rabbitTemplate;
	}
}
