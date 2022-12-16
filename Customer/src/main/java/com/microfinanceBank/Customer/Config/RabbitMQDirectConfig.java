package com.microfinanceBank.Customer.Config;

import com.fasterxml.jackson.databind.MapperFeature;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@EnableRabbit
public class RabbitMQDirectConfig implements RabbitListenerConfigurer {
//    public static final String ROUTING_TRANSACTION="routing.transaction";
    public static final String WELCOME_EMAIL_QUEUE="welcomeMailQueue.queue";
	public static final String DEPOSIT_ROUTING_TRANSACTION="deposit.transaction";
	public static final String WITHDRAWAL_ROUTING_TRANSACTION="withdrawal.transaction";
	public static final String TRANSFER_ROUTING_TRANSACTION="transfer.transaction";
	public static final String LOAN_ROUTING_PAYMENT="loan.payment";

	@Bean
	Queue DepositQueue() { return new Queue("DepositQueue", false); }

	@Bean
	Queue TransferQueue() {
		return new Queue("TransferQueue", false);
	}

	@Bean
	Queue LoanPaymentQueue() {
		return new Queue("LoanPaymentQueue", false);
	}

	@Bean
	Queue WithdrawalQueue() {
		return new Queue("WithdrawalQueue", false);
	}


	@Bean
	Queue welcomeMailQueue() {
		return new Queue("welcomeMailQueue", false);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange("direct-exchange");
	}

	@Bean
	Binding DepositBinding(Queue DepositQueue, DirectExchange exchange) {
		return BindingBuilder.bind(DepositQueue).to(exchange).with(DEPOSIT_ROUTING_TRANSACTION);
	}

	@Bean
	Binding WithdrawalBinding(Queue WithdrawalQueue, DirectExchange exchange) {
		return BindingBuilder.bind(WithdrawalQueue).to(exchange).with(WITHDRAWAL_ROUTING_TRANSACTION);
	}
	@Bean
	Binding TransferBinding(Queue TransferQueue, DirectExchange exchange) {
		return BindingBuilder.bind(TransferQueue).to(exchange).with(TRANSFER_ROUTING_TRANSACTION);
	}
	@Bean
	Binding LoanPaymentBinding(Queue LoanPaymentQueue, DirectExchange exchange) {
		return BindingBuilder.bind(LoanPaymentQueue).to(exchange).with(LOAN_ROUTING_PAYMENT);
	}

	@Bean
	Binding WelcomeEmailBinding(Queue welcomeMailQueue, DirectExchange exchange) {
		return BindingBuilder.bind(welcomeMailQueue).to(exchange).with(WELCOME_EMAIL_QUEUE);
	}

	@Bean
	public MessageConverter JsonMessageConverter() {
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


	@Bean
	MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		simpleMessageListenerContainer.setConnectionFactory(connectionFactory);

//		simpleMessageListenerContainer.setChannelTransacted(true);
//		simpleMessageListenerContainer.setTransactionManager(transactionManager(connectionFactory));
		return simpleMessageListenerContainer;
	}

	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJsonMessageConverter());

//		rabbitTemplate.setChannelTransacted(true);
		return rabbitTemplate;
	}

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
		rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerFactory());
	}

//	@Bean
//	public PlatformTransactionManager transactionManager(ConnectionFactory connectionFactory){
//		return  new RabbitTransactionManager(connectionFactory);
//	}

}
